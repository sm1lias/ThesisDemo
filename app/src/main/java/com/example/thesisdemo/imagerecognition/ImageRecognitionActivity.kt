package com.example.thesisdemo.imagerecognition

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.thesisdemo.databinding.ActivityImageRecognitionBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class ImageRecognitionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageRecognitionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageRecognitionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bitmap = intent.getParcelableExtra("bitmap") as Bitmap?
        binding.imageView.setImageBitmap(bitmap)
        val image = InputImage.fromBitmap(bitmap, 0)

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
