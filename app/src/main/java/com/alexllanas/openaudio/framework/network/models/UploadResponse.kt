package com.alexllanas.openaudio.framework.network.models

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("file")
    val file: FileResponse
) {
    data class FileResponse(
        @SerializedName("name")
        val fileName: String? = null,

        @SerializedName("mime")
        val mediaType: String? = null,

        @SerializedName("path")
        val path: String? = null
    )
}