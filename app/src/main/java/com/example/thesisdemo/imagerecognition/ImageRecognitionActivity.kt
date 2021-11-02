package com.example.thesisdemo.imagerecognition

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.thesisdemo.databinding.ActivityImageRecognitionBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.io.File

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.view.View
import android.widget.ImageView
import com.example.thesisdemo.Utils
import com.example.thesisdemo.Utils.rotateBitmap


class ImageRecognitionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageRecognitionBinding
    private lateinit var image:InputImage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageRecognitionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val imagePath = intent.getStringExtra("imagePath")
        val imgFile= File(imagePath)

        if (imgFile.exists()) {
            var myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            myBitmap= rotateBitmap(myBitmap, 90F)
            image = InputImage.fromBitmap(myBitmap, 0)
            binding.imageView.setImageBitmap(myBitmap)
        }



        val options = ImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.7f)
            .build()
        val labeler = ImageLabeling.getClient(options)
        var result = ""
        val resultText: StringBuilder = StringBuilder()

        labeler.process(image)
            .addOnSuccessListener { labels ->
                for (label in labels) {
                    val text = label.text
                    val confidence = label.confidence
                    val index = label.index
                    resultText.append("\nImage Text: " + text + "\nConfidence: " + confidence)
                }
                result = resultText.toString()
                binding.textView.text = result
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
            }


        }



}
