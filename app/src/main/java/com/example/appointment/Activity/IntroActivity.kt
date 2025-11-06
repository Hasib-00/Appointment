package com.example.appointment.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.appointment.databinding.ActivityIntroBinding

/**
 * IntroActivity
 * --------------
 * This is the first screen (intro or splash screen) of your app.
 * It welcomes the user and provides a "Get Started" or "Start" button
 * to move into the main part of the app.
 *
 * It extends BaseActivity, which already applies full-screen layout flags.
 */
class IntroActivity : BaseActivity() {

    // ViewBinding gives you direct access to your layout’s views
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables modern edge-to-edge display (content under status/navigation bars)
        enableEdgeToEdge()

        // Inflate layout using ViewBinding (connects XML and Kotlin)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle button click to go to the main part of the app
        binding.startBtn.setOnClickListener {
            // Create an Intent to move from IntroActivity → MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Optionally finish this activity so user can't come back by pressing back
            finish()
        }
    }
}
