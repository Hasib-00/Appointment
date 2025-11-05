package com.example.appointment.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.appointment.Data.TopDoctorModel
import com.example.appointment.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var item: TopDoctorModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDoctorData()
        setupClicks()

    }

    private fun getDoctorData() {

        item = intent.getSerializableExtra("Object") as? TopDoctorModel

        if (item == null) {
            finish()
            return
        }

        binding.apply {
            tvName.text = item!!.Name
            tvSpeciality.text = item!!.Specialization
            ratingTxt.text = item!!.Rating.toString()
            experienceTxt.text = item!!.Year // (example: "5 yrs")

            // For now — no bio or address in your model
            bioTxt.text = "Doctor details coming soon..."
            tvAddress.text = "Not Available"

            Glide.with(this@DetailActivity)
                .load(item!!.Picture)
                .into(imgDoctor)
        }
    }

    private fun setupClicks() {
        binding.btnBack.setOnClickListener { finish() }
        // Since your model has no phone/email/site — buttons disabled for now
        binding.websiteBtn.setOnClickListener {
            item?.Site?.let {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            }
        }
        binding.messageBtn.setOnClickListener {
            item?.Mobile?.let {
                val uri = Uri.parse("smsto:$it")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra("sms_body", "Hello Doctor")
                startActivity(intent)
            }
        }
        binding.callBtn.setOnClickListener {
            item?.Mobile?.let {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it"))
                startActivity(intent)
            }
        }
        binding.directionBtn.setOnClickListener {
            item?.Location?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            }
        }
        binding.shareBtn.setOnClickListener {
            val text = "${item?.Name} - ${item?.Specialization}"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(intent, "Share Doctor Info"))
        }
        binding.btnBook.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("DoctorName", item?.Name)
            startActivity(intent)
        }
        binding.btnBook.setOnClickListener {
            val intent = Intent(this, AppointmentReg::class.java)
            intent.putExtra("DoctorName", item?.Name)
            startActivity(intent)
        }


    }
}
