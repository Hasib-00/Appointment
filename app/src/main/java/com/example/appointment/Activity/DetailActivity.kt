package com.example.appointment.Activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appointment.Database.Doctor
import com.example.appointment.R
import com.example.appointment.databinding.ActivityDetailBinding

/**
 * DetailActivity
 * --------------
 * This screen shows the full profile of a selected doctor.
 * It displays details like name, specialization, biography, contact info,
 * and allows the user to call, message, view the clinic on maps, or book an appointment.
 */
class DetailActivity : AppCompatActivity() {

    // ViewBinding gives direct access to layout views
    private lateinit var binding: ActivityDetailBinding

    // Variable to hold the doctor object passed from previous screen
    private var item: Doctor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load doctor data and setup button click actions
        getDoctorData()
        setupClicks()
    }

    /**
     * Retrieves the Doctor object sent from the previous Activity.
     */
    @Suppress("DEPRECATION")
    private fun getDoctorData() {
        // Get the Doctor object depending on the Android version
        item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For Android 13 (API 33) and above
            intent.getSerializableExtra("Object", Doctor::class.java)
        } else {
            // For older Android versions
            intent.getSerializableExtra("Object") as? Doctor
        }

        // If no doctor data found, close the activity
        if (item == null) {
            finish()
            return
        }

        // Fill in UI elements with doctor data
        binding.apply {
            tvName.text = item!!.name
            tvSpeciality.text = item!!.specialization
            ratingTxt.text = item!!.rating.toString()
            experienceTxt.text =
                item!!.year.ifEmpty { "${item!!.experience} yrs" } // Show years of experience
            bioTxt.text =
                if (item!!.biography.isNotEmpty()) item!!.biography else "No biography available"
            tvAddress.text =
                if (item!!.address.isNotEmpty()) item!!.address else "Address not available"

            // Load image using Glide (handles URI, resource ID, or placeholder)
            val source = item!!.imageUri?.let { Uri.parse(it) }
                ?: item!!.picture.takeIf { it != 0 }
                ?: R.drawable.doctor

            Glide.with(this@DetailActivity)
                .load(source)
                .placeholder(R.drawable.doctor)
                .error(R.drawable.doctor)
                .into(imgDoctor)
        }
    }

    /**
     * Sets up click listeners for all buttons (Call, Message, Website, etc.)
     */
    private fun setupClicks() {
        binding.apply {

            // Back button returns to the previous screen
            btnBack.setOnClickListener { finish() }

            // Open doctor's website (if available)
            websiteBtn.setOnClickListener {
                item?.site?.takeIf { it.isNotEmpty() }?.let { url ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            }

            // Open SMS app to send a message to doctor
            messageBtn.setOnClickListener {
                item?.mobile?.takeIf { it.isNotEmpty() }?.let { phone ->
                    val uri = Uri.parse("smsto:$phone")
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    intent.putExtra("sms_body", "Hello Doctor ${item?.name}")
                    startActivity(intent)
                }
            }

            // Open phone dialer with doctor's number
            callBtn.setOnClickListener {
                item?.mobile?.takeIf { it.isNotEmpty() }?.let { phone ->
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                    startActivity(intent)
                }
            }

            // Open Google Maps for doctor’s location
            directionBtn.setOnClickListener {
                item?.location?.takeIf { it.isNotEmpty() }?.let { loc ->
                    val uri = if (loc.startsWith("http", true))
                        Uri.parse(loc)
                    else
                        Uri.parse("geo:0,0?q=${Uri.encode(loc)}")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
            }

            // Share doctor info through any app (WhatsApp, Gmail, etc.)
            shareBtn.setOnClickListener {
                val text = "${item?.name} - ${item?.specialization}"
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, text)
                startActivity(Intent.createChooser(intent, "Share Doctor Info"))
            }

            // Book appointment — opens AppointmentReg activity
            btnBook.setOnClickListener {
                val intent = Intent(this@DetailActivity, AppointmentReg::class.java)
                intent.putExtra("DoctorName", item?.name)
                startActivity(intent)
            }
        }
    }
}
