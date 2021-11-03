package com.example.thesisdemo.poserecognition

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.thesisdemo.Utils.dlFile
import com.example.thesisdemo.Utils.rotateBitmap
import com.example.thesisdemo.databinding.ActivityPoseRecognitionBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import java.io.File

class PoseRecognitionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPoseRecognitionBinding
    private lateinit var image:InputImage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoseRecognitionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val imagePath = intent.getStringExtra("imagePath")
        val imgFile= File(imagePath)

        val options: AccuratePoseDetectorOptions = AccuratePoseDetectorOptions.Builder()
            .setDetectorMode(AccuratePoseDetectorOptions.SINGLE_IMAGE_MODE)
            .build()
        val poseDetector = PoseDetection.getClient(options)

        var myBitmap:Bitmap? = null
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            myBitmap=rotateBitmap(myBitmap, 90F)
            image = InputImage.fromBitmap(myBitmap, 0)
            binding.imageViewPose.setImageBitmap(myBitmap)
        }

        poseDetector.process(image).addOnSuccessListener {
            processPose(it, myBitmap!!)

        }
            .addOnFailureListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
            }
        dlFile(imgFile)
    }


    private fun processPose(pose: Pose, bitmap: Bitmap) {
        try {

            val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
            val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)

            val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
            val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)

            val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
            val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)


            val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
            val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)


            val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
            val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)


            val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
            val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

            val leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
            val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)


            val leftEyePosition = leftEye.position
            val lEyeX = leftEyePosition.x
            val lEyeY = leftEyePosition.y
            val rightEyePosition = rightEye.position
            val rEyeX = rightEyePosition.x
            val rEyeY = rightEyePosition.y

            val leftShoulderP = leftShoulder.position
            val lShoulderX = leftShoulderP.x
            val lShoulderY = leftShoulderP.y
            val rightSoulderP = rightShoulder.position
            val rShoulderX = rightSoulderP.x
            val rShoulderY = rightSoulderP.y

            val leftElbowP = leftElbow.position
            val lElbowX = leftElbowP.x
            val lElbowY = leftElbowP.y
            val rightElbowP = rightElbow.position
            val rElbowX = rightElbowP.x
            val rElbowY = rightElbowP.y

            val leftWristP = leftWrist.position
            val lWristX = leftWristP.x
            val lWristY = leftWristP.y
            val rightWristP = rightWrist.position
            val rWristX = rightWristP.x
            val rWristY = rightWristP.y

            val leftHipP = leftHip.position
            val lHipX = leftHipP.x
            val lHipY = leftHipP.y
            val rightHipP = rightHip.position
            val rHipX = rightHipP.x
            val rHipY = rightHipP.y

            val leftKneeP = leftKnee.position
            val lKneeX = leftKneeP.x
            val lKneeY = leftKneeP.y
            val rightKneeP = rightKnee.position
            val rKneeX = rightKneeP.x
            val rKneeY = rightKneeP.y

            val leftAnkleP = leftAnkle.position
            val lAnkleX = leftAnkleP.x
            val lAnkleY = leftAnkleP.y
            val rightAnkleP = rightAnkle.position
            val rAnkleX = rightAnkleP.x
            val rAnkleY = rightAnkleP.y


            displayAll(
                lShoulderX, lShoulderY, rShoulderX, rShoulderY,
                lElbowX, lElbowY, rElbowX, rElbowY,
                lWristX, lWristY, rWristX, rWristY,
                lHipX, lHipY, rHipX, rHipY,
                lKneeX, lKneeY, rKneeX, rKneeY,
                lAnkleX, lAnkleY, rAnkleX, rAnkleY, bitmap, lEyeX, lEyeY, rEyeX, rEyeY
            )
        } catch (e: Exception) {
            Toast.makeText(this, "Pose Landmarks failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayAll(
        lShoulderX: Float, lShoulderY: Float, rShoulderX: Float, rShoulderY: Float,
        lElbowX: Float, lElbowY: Float, rElbowX: Float, rElbowY: Float,
        lWristX: Float, lWristY: Float, rWristX: Float, rWristY: Float,
        lHipX: Float, lHipY: Float, rHipX: Float, rHipY: Float,
        lKneeX: Float, lKneeY: Float, rKneeX: Float, rKneeY: Float,
        lAnkleX: Float, lAnkleY: Float, rAnkleX: Float, rAnkleY: Float,bitmap: Bitmap,
        lEyeX: Float, lEyeY: Float, rEyeX: Float, rEyeY: Float

    ) {
        val paint = Paint()
        paint.color = Color.BLUE
        val strokeWidth = 6.0f
        paint.strokeWidth = strokeWidth

        val canvas = Canvas(bitmap)

        canvas.drawBitmap(bitmap, 0f, 0f, null)

        canvas.drawLine(lEyeX, lEyeY, rEyeX, rEyeY, paint)

        canvas.drawLine(lShoulderX, lShoulderY, rShoulderX, rShoulderY, paint)

        canvas.drawLine(rShoulderX, rShoulderY, rElbowX, rElbowY, paint)

        canvas.drawLine(rElbowX, rElbowY, rWristX, rWristY, paint)

        canvas.drawLine(lShoulderX, lShoulderY, lElbowX, lElbowY, paint)

        canvas.drawLine(lElbowX, lElbowY, lWristX, lWristY, paint)

        canvas.drawLine(rShoulderX, rShoulderY, rHipX, rHipY, paint)

        canvas.drawLine(lShoulderX, lShoulderY, lHipX, lHipY, paint)

        canvas.drawLine(lHipX, lHipY, rHipX, rHipY, paint)

        canvas.drawLine(rHipX, rHipY, rKneeX, rKneeY, paint)

        canvas.drawLine(lHipX, lHipY, lKneeX, lKneeY, paint)

        canvas.drawLine(rKneeX, rKneeY, rAnkleX, rAnkleY, paint)

        canvas.drawLine(lKneeX, lKneeY, lAnkleX, lAnkleY, paint)

        binding.imageViewPose.setImageBitmap(bitmap)

    }
}