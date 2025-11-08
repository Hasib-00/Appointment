package com.example.appointment.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.appointment.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    private val PREFS_NAME = "my_prefs"
    private val KEY_USERNAME = "key_username"
    private val KEY_EMAIL = "key_email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Load saved data
        loadSavedData(prefs)

        // Auto-save username & email
        binding.usernameInputTXT.addTextChangedListener { saveData(prefs) }
        binding.emailregTXT.addTextChangedListener { saveData(prefs) }

        // ✅ Sign-Up button click
        binding.loginBTN.setOnClickListener {
            val username = binding.usernameInputTXT.text.toString().trim()
            val email = binding.emailregTXT.text.toString().trim()
            val password = binding.PasswordregTXT.text.toString().trim()
            val repassword = binding.rePasswordregTXT.text.toString().trim()

            when {
                username.isEmpty() ->
                    showToast("Please enter your username")

                username.length < 4 ->
                    showToast("Username must be at least 4 characters")

                username.length > 15 ->
                    showToast("Username cannot be longer than 15 characters")

                !username.matches(Regex("^[A-Za-z0-9]+$")) ->
                    showToast("Only letters, numbers, allowed")

                username.contains("..") || username.contains("__") ->
                    showToast("No consecutive dots or underscores allowed")

                email.isEmpty() ->
                    showToast("Please enter your email")

                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    showToast("Please enter a valid email")

                password.isEmpty() ->
                    showToast("Please enter your password")

                password.length < 8 ->
                    showToast("Password must be at least 8 characters")

                !password.matches(Regex(".*[A-Z].*")) ->
                    showToast("Password must contain at least one uppercase letter")

                !password.matches(Regex(".*[a-z].*")) ->
                    showToast("Password must contain at least one lowercase letter")

                !password.matches(Regex(".*\\d.*")) ->
                    showToast("Password must contain at least one number")

                !password.matches(Regex(".*[!@#\$%^&*(),.?\":{}|<>].*")) ->
                    showToast("Password must contain at least one special character")

                repassword.isEmpty() ->
                    showToast("Please confirm your password")

                password != repassword ->
                    showToast("Passwords do not match")

                else -> createUser(email, password, prefs)
            }
        }

        // 🔁 Go to Sign-In screen
        binding.signinTV.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    // ✅ Firebase account creation
    private fun createUser(email: String, password: String, prefs: SharedPreferences) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("Signup Successful 🎉")
                    prefs.edit()
                        .putBoolean("isLoggedIn", true)
                        .putString(KEY_EMAIL, email)
                        .apply()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    showToast("Signup failed: ${task.exception?.message}")
                }
            }
    }

    // Save data locally
    private fun saveData(prefs: SharedPreferences) {
        val username = binding.usernameInputTXT.text.toString().trim()
        val email = binding.emailregTXT.text.toString().trim()
        prefs.edit()
            .putString(KEY_USERNAME, username)
            .putString(KEY_EMAIL, email)
            .apply()
    }

    // Load saved data
    private fun loadSavedData(prefs: SharedPreferences) {
        binding.usernameInputTXT.setText(prefs.getString(KEY_USERNAME, ""))
        binding.emailregTXT.setText(prefs.getString(KEY_EMAIL, ""))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
