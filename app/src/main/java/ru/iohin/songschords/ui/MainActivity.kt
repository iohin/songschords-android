package ru.iohin.songschords.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import ru.iohin.songschords.R
import ru.iohin.songschords.core_api.ActivityProviderHolder
import ru.iohin.songschords.core_api.di.ActivityProvider
import ru.iohin.songschords.di.MainActivityComponent
import ru.iohin.songschords.core_api.navigation.NavControllerHolder

class MainActivity : AppCompatActivity(), ActivityProviderHolder, NavControllerHolder {
    lateinit var mainActivityComponent: MainActivityComponent
    override val activityProvider: ActivityProvider
        get() = mainActivityComponent

    override val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityComponent = MainActivityComponent.getMainActivityComponent(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}