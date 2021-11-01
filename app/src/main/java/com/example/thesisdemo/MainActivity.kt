package com.example.thesisdemo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.thesisdemo.databinding.ActivityMainBinding
import android.widget.Toast
import java.util.*

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.example.thesisdemo.imagerecognition.ImageRecognitionActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val CAMERA_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        binding.btnImageAnalyzer.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE && data != null) {
            val bitmap = data.extras?.get("data") as Bitmap
            val image = InputImage.fromBitmap(bitmap, 0)

            val intent = Intent(this@MainActivity, ImageRecognitionActivity::class.java)
            intent.putExtra("bitmap", bitmap)

            startActivity(intent)


        }
    }
}





