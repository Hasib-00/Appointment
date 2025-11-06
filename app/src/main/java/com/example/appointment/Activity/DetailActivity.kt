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

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var item: Doctor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDoctorData()
        setupClicks()
    }

    @Suppress("DEPRECATION")
    private fun getDoctorData() {
        item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("Object", Doctor::class.java)
        } else {
            intent.getSerializableExtra("Object") as? Doctor
        }

        if (item == null) {
            finish()
            return
        }

        binding.apply {
            tvName.text = item!!.name
            tvSpeciality.text = item!!.specialization
            ratingTxt.text = item!!.rating.toString()
            experienceTxt.text = item!!.year.ifEmpty { "${item!!.experience} yrs" }
            bioTxt.text = if (item!!.biography.isNotEmpty()) item!!.biography else "No biography available"
            tvAddress.text = if (item!!.address.isNotEmpty()) item!!.address else "Address not available"

            val source = item!!.imageUri?.let { Uri.parse(it) } ?: item!!.picture.takeIf { it != 0 } ?: R.drawable.doctor
            Glide.with(this@DetailActivity)
                .load(source)
                .placeholder(R.drawable.doctor)
                .error(R.drawable.doctor)
                .into(imgDoctor)
        }
    }

    private fun setupClicks() {
        binding.apply {
            btnBack.setOnClickListener { finish() }

            websiteBtn.setOnClickListener {
                item?.site?.takeIf { it.isNotEmpty() }?.let {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                }
            }

            messageBtn.setOnClickListener {
                item?.mobile?.takeIf { it.isNotEmpty() }?.let {
                    val uri = Uri.parse("smsto:$it")
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    intent.putExtra("sms_body", "Hello Doctor ${item?.name}")
                    startActivity(intent)
                }
            }

            callBtn.setOnClickListener {
                item?.mobile?.takeIf { it.isNotEmpty() }?.let {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it"))
                    startActivity(intent)
                }
            }

            directionBtn.setOnClickListener {
                item?.location?.takeIf { it.isNotEmpty() }?.let { loc ->
                    val uri = if (loc.startsWith("http", true)) Uri.parse(loc)
                    else Uri.parse("geo:0,0?q=${Uri.encode(loc)}")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
            }

            shareBtn.setOnClickListener {
                val text = "${item?.name} - ${item?.specialization}"
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, text)
                startActivity(Intent.createChooser(intent, "Share Doctor Info"))
            }

            btnBook.setOnClickListener {
                val intent = Intent(this@DetailActivity, AppointmentReg::class.java)
                intent.putExtra("DoctorName", item?.name)
                startActivity(intent)
            }
        }
    }
}
