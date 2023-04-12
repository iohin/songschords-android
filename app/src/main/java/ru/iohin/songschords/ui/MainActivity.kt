package ru.iohin.songschords.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.iohin.songschords.R
import ru.iohin.songschords.core_api.ActivityProviderHolder
import ru.iohin.songschords.core_api.di.ActivityProvider
import ru.iohin.songschords.di.MainActivityComponent

class MainActivity : AppCompatActivity(), ActivityProviderHolder {
    lateinit var mainActivityComponent: MainActivityComponent
    override val activityProvider: ActivityProvider
        get() = mainActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityComponent = MainActivityComponent.getMainActivityComponent(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}