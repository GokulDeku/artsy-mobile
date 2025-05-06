package com.example.artsyactivity

import android.app.Application
import com.example.artsyactivity.network.NetworkModule
import com.example.artsyactivity.service.ArtInfoService
import com.example.artsyactivity.service.AuthService
import com.example.artsyactivity.service.SearchService

class ArtsyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: ArtsyApplication

        fun providesAuthService(): AuthService {
            return NetworkModule.provideApiService<AuthService>(instance)
        }

        fun providesSearchService(): SearchService {
            return NetworkModule.provideApiService<SearchService>(instance)
        }

        fun providesArtInfoService(): ArtInfoService {
            return NetworkModule.provideApiService<ArtInfoService>(instance)
        }
    }
}