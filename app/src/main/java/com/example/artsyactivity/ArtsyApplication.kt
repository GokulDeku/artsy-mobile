package com.example.artsyactivity

import android.app.Application
import com.example.artsyactivity.network.NetworkModule
import com.example.artsyactivity.service.AuthService

class ArtsyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: ArtsyApplication

        fun providesAuthService(): AuthService {
            return NetworkModule.provideAuthService(instance)
        }
    }
}