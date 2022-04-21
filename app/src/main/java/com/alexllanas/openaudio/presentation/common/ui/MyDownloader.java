package com.alexllanas.openaudio.presentation.common.ui;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexllanas.openaudio.BuildConfig;
import com.alexllanas.openaudio.presentation.util.CookieUtils;
import com.alexllanas.openaudio.presentation.util.TLSSocketFactoryCompat;

import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Request;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MyDownloader extends Downloader {

    public static final String USER_AGENT
            = "Mozilla/5.0 (Windows NT 10.0; rv:78.0) Gecko/20100101 Firefox/78.0";
    public static final String YOUTUBE_RESTRICTED_MODE_COOKIE_KEY
            = "youtube_restricted_mode_key";
    public static final String RECAPTCHA_COOKIES_KEY = "recaptcha_cookies";

    public static final String YOUTUBE_RESTRICTED_MODE_COOKIE = "PREF=f2=8000000";
    public static final String YOUTUBE_DOMAIN = "youtube.com";
    private final Map<String, String> mCookies;
    private final OkHttpClient client;
    private static MyDownloader instance;

    private MyDownloader(final OkHttpClient.Builder builder) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            enableModernTLS(builder);
        }
        this.client = builder
                .readTimeout(30, TimeUnit.SECONDS)
//                .cache(new Cache(new File(context.getExternalCacheDir(), "okhttp"),
//                        16 * 1024 * 1024))
                .build();
        this.mCookies = new HashMap<>();
    }

    public static MyDownloader init(@Nullable final OkHttpClient.Builder builder) {
        instance = new MyDownloader(
                builder != null ? builder : new OkHttpClient.Builder());
        return instance;
    }

    private static void enableModernTLS(final OkHttpClient.Builder builder) {
        try {
            // get the default TrustManager
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            final TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            final X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

            // insert our own TLSSocketFactory
            final SSLSocketFactory sslSocketFactory = TLSSocketFactoryCompat.getInstance();

            builder.sslSocketFactory(sslSocketFactory, trustManager);

            // This will try to enable all modern CipherSuites(+2 more)
            // that are supported on the device.
            // Necessary because some servers (e.g. Framatube.org)
            // don't support the old cipher suites.
            // https://github.com/square/okhttp/issues/4053#issuecomment-402579554
            final List<CipherSuite> cipherSuites =
                    new ArrayList<>(ConnectionSpec.MODERN_TLS.cipherSuites());
            cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA);
            cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA);
            final ConnectionSpec legacyTLS = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .cipherSuites(cipherSuites.toArray(new CipherSuite[0]))
                    .build();

            builder.connectionSpecs(Arrays.asList(legacyTLS, ConnectionSpec.CLEARTEXT));
        } catch (final KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public String getCookie(final String key) {
        return mCookies.get(key);
    }

    public String getCookies(final String url) {
        final List<String> resultCookies = new ArrayList<>();
        if (url.contains(YOUTUBE_DOMAIN)) {
            final String youtubeCookie = getCookie(YOUTUBE_RESTRICTED_MODE_COOKIE_KEY);
            if (youtubeCookie != null) {
                resultCookies.add(youtubeCookie);
            }
        }
        // Recaptcha cookie is always added TODO: not sure if this is necessary
        final String recaptchaCookie = getCookie(RECAPTCHA_COOKIES_KEY);
        if (recaptchaCookie != null) {
            resultCookies.add(recaptchaCookie);
        }
        return CookieUtils.concatCookies(resultCookies);
    }

    @Override
    public Response execute(@NonNull final Request request)
            throws IOException, ReCaptchaException {
        final String httpMethod = request.httpMethod();
        final String url = request.url();
        final Map<String, List<String>> headers = request.headers();
        final byte[] dataToSend = request.dataToSend();

        RequestBody requestBody = null;
        if (dataToSend != null) {
            requestBody = RequestBody.create(null, dataToSend);
        }

        final okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
                .method(httpMethod, requestBody).url(url)
                .addHeader("User-Agent", USER_AGENT);

        final String cookies = getCookies(url);
        if (!cookies.isEmpty()) {
            requestBuilder.addHeader("Cookie", cookies);
        }

        for (final Map.Entry<String, List<String>> pair : headers.entrySet()) {
            final String headerName = pair.getKey();
            final List<String> headerValueList = pair.getValue();

            if (headerValueList.size() > 1) {
                requestBuilder.removeHeader(headerName);
                for (final String headerValue : headerValueList) {
                    requestBuilder.addHeader(headerName, headerValue);
                }
            } else if (headerValueList.size() == 1) {
                requestBuilder.header(headerName, headerValueList.get(0));
            }
        }

        final okhttp3.Response response = client.newCall(requestBuilder.build()).execute();

        if (response.code() == 429) {
            response.close();

            throw new ReCaptchaException("reCaptcha Challenge requested", url);
        }

        final ResponseBody body = response.body();
        String responseBodyToReturn = null;

        if (body != null) {
            responseBodyToReturn = body.string();
        }

        final String latestUrl = response.request().url().toString();
        return new Response(response.code(), response.message(), response.headers().toMultimap(),
                responseBodyToReturn, latestUrl);
    }
}
