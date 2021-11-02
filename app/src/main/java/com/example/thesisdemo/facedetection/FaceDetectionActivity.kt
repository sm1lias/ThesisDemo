package com.example.thesisdemo.facedetection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.thesisdemo.Utils
import com.example.thesisdemo.databinding.ActivityFaceDetectionBinding
import com.example.thesisdemo.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import java.io.File

class FaceDetectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaceDetectionBinding
    private lateinit var image:InputImage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceDetectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val imagePath = intent.getStringExtra("imagePath")
        val imgFile= File(imagePath)

        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()


        var myBitmap: Bitmap? = null
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            myBitmap= Utils.rotateBitmap(myBitmap, 90F)
            image = InputImage.fromBitmap(myBitmap, 0)
            binding.imageViewFace.setImageBitmap(myBitmap)
        }

        val detector = FaceDetection.getClient(highAccuracyOpts)

        detector.process(image)
            .addOnSuccessListener { faces ->
                faceProcess(faces)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Something happened, please try again!", Toast.LENGTH_LONG)
            }

    }
    private fun faceProcess(faces: List<Face>){
        for (face in faces) {
            val bounds = face.boundingBox
            val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
            val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees

            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
            // nose available):
            val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
            leftEar?.let {
                val leftEarPos = leftEar.position
            }

            // If contour detection was enabled:
            val leftEyeContour = face.getContour(FaceContour.LEFT_EYE)?.points
            val upperLipBottomContour = face.getContour(FaceContour.UPPER_LIP_BOTTOM)?.points

            // If classification was enabled:
            if (face.smilingProbability != null) {
                val smileProb = face.smilingProbability
            }
            if (face.rightEyeOpenProbability != null) {
                val rightEyeOpenProb = face.rightEyeOpenProbability
            }

            // If face tracking was enabled:
            if (face.trackingId != null) {
                val id = face.trackingId
            }
        }
    }
}