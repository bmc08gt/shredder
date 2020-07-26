package dev.bmcreations.shredder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import dev.bmcreations.shredder.core.AppComponent
import dev.bmcreations.shredder.core.components
import dev.bmcreations.shredder.ui.ShredderApp
import dev.bmcreations.shredder.ui.navigation.NavigationViewModel

class MainActivity : AppCompatActivity() {

    private val navigationViewModel by viewModels<NavigationViewModel>()

    private val appComponent by components<AppComponent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ShredderApp(appComponent, navigationViewModel)
        }
    }

    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}
