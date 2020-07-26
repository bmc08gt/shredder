package dev.bmcreations.shredder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import dev.bmcreations.shredder.ui.ShredderApp
import dev.bmcreations.shredder.ui.navigation.NavigationViewModel

class MainActivity : AppCompatActivity() {

    val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as ShredderApplication).container
        setContent {
            ShredderApp(appContainer, navigationViewModel)
        }
    }

    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}
