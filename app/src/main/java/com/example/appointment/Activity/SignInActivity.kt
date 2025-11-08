package com.example.appointment.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.appointment.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    private val PREFS_NAME = "my_prefs"
    private val KEY_EMAIL = "key_email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadSavedData(prefs)

        // Clear saved email on end icon click
        binding.emailInput.setEndIconOnClickListener {
            binding.emailinputTXT.setText("")
            prefs.edit().remove(KEY_EMAIL).apply()
        }

        // Save email as user types
        binding.emailinputTXT.addTextChangedListener { saveData(prefs) }

        // Login button click
        binding.loginBTN.setOnClickListener {
            val email = binding.emailinputTXT.text.toString().trim()
            val password = binding.inputPasswordTXT.text.toString().trim()

            when {
                email.isEmpty() -> showToast("Please enter your email")
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    showToast("Please enter a valid email")
                password.isEmpty() -> showToast("Please enter your password")
                password.length < 8 -> showToast("Password must be at least 8 characters")
                else -> signInUser(email, password, prefs)
            }
        }

        // Go to SignUp screen
        binding.signupTV.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    // Firebase sign-in function
    private fun signInUser(email: String, password: String, prefs: SharedPreferences) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("Login Successful ✅")
                    prefs.edit().putBoolean("isLoggedIn", true).apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    showToast("Login failed: ${task.exception?.message}")
                }
            }
    }

    // Save email to SharedPreferences
    private fun saveData(prefs: SharedPreferences) {
        val email = binding.emailinputTXT.text.toString().trim()
        if (email.isNotEmpty()) {
            prefs.edit().putString(KEY_EMAIL, email).apply()
        }
    }

    // Load saved email
    private fun loadSavedData(prefs: SharedPreferences) {
        binding.emailinputTXT.setText(prefs.getString(KEY_EMAIL, ""))
    }

    // Hide keyboard when touching outside EditText
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    // Helper function for toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
