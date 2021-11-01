package com.example.thesisdemo

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.thesisdemo.databinding.ActivityMainBinding
import com.example.thesisdemo.imagerecognition.ImageRecognitionActivity
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val CAMERA_REQUEST_CODE = 0
    private var mCurrentPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        binding.btnImageAnalyzer.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (cameraIntent.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.i(TAG, "IOException")
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    cameraIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                            this, BuildConfig.APPLICATION_ID + ".provider",
                            photoFile
                        )
                    )
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
                }
            }

        }
    }

    var mImageBitmap: Bitmap? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
//            val bitmap = data.extras?.get("data") as Bitmap
//            val image = InputImage.fromBitmap(bitmap, 0)
//
//            val intent = Intent(this@MainActivity, ImageRecognitionActivity::class.java)
//            intent.putExtra("bitmap", bitmap)
//
//            startActivity(intent)

            try {
//                mImageBitmap = MediaStore.Images.Media.getBitmap(
//                    this.contentResolver,
//                    Uri.parse(mCurrentPhotoPath)
//                )
                val intent = Intent(this@MainActivity, ImageRecognitionActivity::class.java)
                intent.putExtra("imagePath", mCurrentPhotoPath)
                startActivity(intent)
//                mImageView.setImageBitmap(mImageBitmap)
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





