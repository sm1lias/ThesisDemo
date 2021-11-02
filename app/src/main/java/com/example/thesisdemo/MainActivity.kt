package com.example.thesisdemo

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.thesisdemo.databinding.ActivityMainBinding
import com.example.thesisdemo.facedetection.FaceDetectionActivity
import com.example.thesisdemo.imagerecognition.ImageRecognitionActivity
import com.example.thesisdemo.poserecognition.PoseRecognitionActivity
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val CAMERA_OBJECT_RECOGNITION_CODE = 0
    val CAMERA_POSE_DETECTION_CODE = 1
    val CAMERA_FACE_DETECTION_CODE = 2
    private var mCurrentPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.btnImageAnalyzer.setOnClickListener {
            createCameraIntent(CAMERA_OBJECT_RECOGNITION_CODE)
        }
        binding.btnPoseDetection.setOnClickListener {
            createCameraIntent(CAMERA_POSE_DETECTION_CODE)
        }
        binding.btnFaceDetection.setOnClickListener {
            createCameraIntent(CAMERA_FACE_DETECTION_CODE)
        }
    }


    private fun createCameraIntent(CODE: Int) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                Log.i(TAG, "IOException")
            }
            if (photoFile != null) {
                cameraIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                        this, BuildConfig.APPLICATION_ID + ".provider",
                        photoFile
                    )
                )
                startActivityForResult(cameraIntent, CODE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_OBJECT_RECOGNITION_CODE) {
            try {
                val intent = Intent(this@MainActivity, ImageRecognitionActivity::class.java)
                intent.putExtra("imagePath", mCurrentPhotoPath)
                startActivity(intent)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_POSE_DETECTION_CODE) {
            try {
                val intent = Intent(this@MainActivity, PoseRecognitionActivity::class.java)
                intent.putExtra("imagePath", mCurrentPhotoPath)
                startActivity(intent)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_FACE_DETECTION_CODE) {
            try {
                val intent = Intent(this@MainActivity, FaceDetectionActivity::class.java)
                intent.putExtra("imagePath", mCurrentPhotoPath)
                startActivity(intent)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        val image = File.createTempFile(
            "temp",  // prefix
            ".jpg",  // suffix
            storageDir // directory
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }
}





