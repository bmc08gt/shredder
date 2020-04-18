package dev.bmcreations.shredder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dev.bmcreations.shredder.core.di.Components
import dev.bmcreations.shredder.core.di.CoreComponent
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.login.view.LoginActivity

class MainActivity : AppCompatActivity() {

    private val core get() = Components.CORE.component<CoreComponent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        when {
            core.prefs.isLoggedIn -> startActivity(
                Intent(
                    this@MainActivity,
                    HomeActivity::class.java
                )
            )
            else -> findNavController(R.id.nav_host_fragment).navigate(R.id.login_or_create_account)
        }
        finish()
    }
}
