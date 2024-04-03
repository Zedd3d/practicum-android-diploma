package ru.practicum.android.diploma.app

import android.app.Application
import ru.practicum.android.diploma.di.app.AppComponent
import ru.practicum.android.diploma.di.app.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}
