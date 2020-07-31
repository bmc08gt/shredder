package dev.bmcreations.shredder.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import dev.bmcreations.shredder.core.di.AppComponent
import dev.bmcreations.shredder.core.di.components
import dev.bmcreations.shredder.home.di.HomeComponent
import dev.bmcreations.shredder.ui.navigation.NavigationViewModel

class MainActivity : AppCompatActivity() {

    private val navigationViewModel by viewModels<NavigationViewModel>()

    private val appComponent by components<AppComponent>()
    private val homeComponent by components<HomeComponent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ShredderApp(appComponent, homeComponent, navigationViewModel)
        }
    }

    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}
