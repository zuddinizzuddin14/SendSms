package com.example.chapter5

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import com.example.chapter5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSms.setOnClickListener {
            val permissionCheck = checkSelfPermission(Manifest.permission.SEND_SMS)

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Send SMS DIIZINKAN", Toast.LENGTH_LONG).show()
                getSms()
            } else {
                Toast.makeText(this, "Permission SendSms DITOLAK", Toast.LENGTH_LONG).show()
                requestSendSmsPermission()
            }
        }
    }

    private fun requestSendSmsPermission() {
        requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 201)
    }


    @SuppressLint("MissingPermission")
    fun getSms() {
        val smsManager = SmsManager.getDefault()

        val phoneNumber = binding.etPhoneNumber.text.toString()
        val message = binding.etMessage.text.toString()

        smsManager.sendTextMessage(phoneNumber, null, message, null, null)

        val smsText = "SMS Sended"
        Log.d(MainActivity::class.simpleName, smsText)
        Toast.makeText(
            this,
            smsText,
            Toast.LENGTH_LONG
        ).show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    permissions[0] == Manifest.permission.SEND_SMS
                ) {
                    Toast.makeText(this, "Permissions for Send SMS Permitted", Toast.LENGTH_LONG)
                        .show()
                    getSms()
                } else {
                    Toast.makeText(this, "Permissions for Send SMS Denied", Toast.LENGTH_LONG)
                        .show()
                }
            }
            else -> {
                Toast.makeText(this, "The request code doesn't match", Toast.LENGTH_LONG).show()
            }
        }
    }

}