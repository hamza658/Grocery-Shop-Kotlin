package com.example.shopp

import android.app.Application

class MyApplication : Application() {
    companion object {
        var selectedLanguage: String = ""
    }
}