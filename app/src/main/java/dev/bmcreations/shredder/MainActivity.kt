package dev.bmcreations.shredder

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.bmcreations.shredder.core.extensions.colors

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(colors[R.color.color_background]))
    }
}
