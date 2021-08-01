package com.example.kaagazscanner.activities

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.ViewModelProviders
import com.example.kaagazscanner.R
import com.example.kaagazscanner.database.ImageDao
import com.example.kaagazscanner.database.ImageDatabase
import com.example.kaagazscanner.database.ImageEntity
import com.example.kaagazscanner.repository.RepositoryKagaz
import com.example.kaagazscanner.viewmodel.ViewModelKaagaz
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    var camera: Camera ?=null
    var preview: Preview?=null
    var imageCapture:ImageCapture?=null
    private lateinit var viewModelKaagaz: ViewModelKaagaz
    lateinit var imageDatabase: ImageDatabase
    lateinit var imageDao: ImageDao
    lateinit var image_uri: Uri;

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Here I'm checking if user have given permission of camera or not

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PERMISSION_GRANTED)
        {
            // If permission granted then camera will start
            startCamera()

        }else{

            // Else I'll ask user again camera permissions
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 0)

        }


        //Here I'm clicking picture onclick of button
        captureButton.setOnClickListener {
            takePhoto()
        }



        //Lateinit properties of database, viewmodel and Dao are declared here

        imageDatabase = ImageDatabase.getImageDatabase(this)
        imageDao = imageDatabase.getImageDao()
        val repository = RepositoryKagaz(imageDao)

        val imageViewModelFactory = ImageViewModelFactory(repository)

        viewModelKaagaz = ViewModelProviders.of(this, imageViewModelFactory).get(ViewModelKaagaz::class.java)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun takePhoto() {

        /**
         * Below code is CameraX Implementation code
         */

        /**
         *
         *  Here I'm taking the time of picture clicking time in timestamp variable
         *  also taking name of picture "Kaagaz- ${System.currentTimeMillis()}.jpg" by interpolating System.currentTimeMillis() in String name
         *
         *  also in photoFile folder I'm saving the directory of photo to local storage
         *
         */
        val timestamp =  SimpleDateFormat("yyyy-MM-dd   HH:mm:ss").format(Date())
        val imagename = "Kaagaz- ${System.currentTimeMillis()}.jpg"


        val photoFile = File(externalMediaDirs.firstOrNull(), "cameraApp- ${System.currentTimeMillis()}.jpg")
        val output = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture?.takePicture(output, ContextCompat.getMainExecutor(this),object: ImageCapture.OnImageSavedCallback{
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                /**
                 * Here I'm taking the uri of photo
                 */
                image_uri = Uri.fromFile(photoFile)

                /**
                 * After taking all the required data(name, image, time and date) I'm sending to our  "KaagazScanne.db" database and inserting them to "kaagazscanner" table
                 */

                val imageEntity = ImageEntity(image_uri.toString(),timestamp.toString(),"album2",imagename);


                // Sening all the data to Livedata using ViewModel
                CoroutineScope(Dispatchers.IO).launch {
                    viewModelKaagaz.addImageDetails(imageEntity)
                }


            }

            override fun onError(exception: ImageCaptureException) {
                TODO("Not yet implemented")
            }

         }
        )



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PERMISSION_GRANTED)
        {
            startCamera()
        }else{
           Toast.makeText(this, "Please give the permission", Toast.LENGTH_LONG).show()
        }
    }

    private fun startCamera() {
        //start camera things

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        //using interface to listen the action on camera
        cameraProviderFuture.addListener(Runnable {

        val cameraProvider = cameraProviderFuture.get()

            //camera preview here
        preview=Preview.Builder().build()
        preview?.setSurfaceProvider(cameraView.createSurfaceProvider(camera?.cameraInfo))

        imageCapture=ImageCapture.Builder().build()

            //Selecting the camera lens like which one you want to open front camera or back camera
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        cameraProvider.unbindAll()
        camera=cameraProvider.bindToLifecycle(this, cameraSelector, preview,imageCapture)
    },ContextCompat.getMainExecutor(this))
    }
}

