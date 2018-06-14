package com.android.taitasciore.chhtest

import android.app.Application
import com.android.taitasciore.chhtest.dagger.AppComponent
import com.android.taitasciore.chhtest.dagger.DaggerAppComponent
import io.realm.Realm

/**
 * Base application class
 */
class BaseApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .app(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this) // Inject AppComponent
        Realm.init(this) // Init Realm
    }
}