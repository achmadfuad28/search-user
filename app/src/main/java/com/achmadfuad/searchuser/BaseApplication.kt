package com.achmadfuad.searchuser

import android.annotation.SuppressLint
import android.app.Application
import com.achmadfuad.searchuser.data.constant.KoinPropertyConstants
import com.achmadfuad.searchuser.data.di.component.baseApiComponent
import com.achmadfuad.searchuser.framework.webapi.WebApi
import com.achmadfuad.searchuser.presentation.di.component.baseAppComponent
import com.ashokvarma.gander.Gander
import com.ashokvarma.gander.imdb.GanderIMDB
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class BaseApplication : Application() {

    @SuppressLint("MissingSuperCall")
    override fun onCreate() {
        super.onCreate()
        startKoin()
        Gander.setGanderStorage(GanderIMDB.getInstance())
    }

    private fun createApi(): WebApi {
        return WebApi.Builder("https://api.github.com/")
            .setConnectTimeout(30)
            .setReadTimeout(30)
            .setLoggingEnabled(BuildConfig.DEBUG)
            .setHttpInspector(this, BuildConfig.DEBUG)
            .build()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@BaseApplication)
            androidLogger()
            properties(mapOf(Pair(KoinPropertyConstants.API_PROPERTY, createApi()),
                Pair(KoinPropertyConstants.APPLICATION_CONTEXT_PROPERTY, applicationContext)))
            modules(baseApiComponent)
            modules(baseAppComponent)
        }
    }
}
