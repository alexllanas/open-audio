package com.alexllanas.openaudio.presentation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val SESSION_TOKEN = stringPreferencesKey("session_token")

@HiltAndroidApp
class MainApplication : MultiDexApplication()