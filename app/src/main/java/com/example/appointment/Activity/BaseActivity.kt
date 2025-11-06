package com.example.appointment.Activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * BaseActivity
 * -------------
 * This is a simple base class that other activities can extend.
 * It removes layout limits so the UI can draw behind the status bar
 * or navigation bar — giving a full-screen modern look.
 *
 * Example:
 * class MainActivity : BaseActivity() { ... }
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This makes your activity’s layout extend behind the system bars
        // (like the status bar and navigation bar)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // allow drawing beyond the normal area
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}
