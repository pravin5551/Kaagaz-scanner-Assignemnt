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

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PERMISSION_GRANTED)
        {
            startCamera()

        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 0)

        }

        captureButton.setOnClickListener {
            takePhoto()
        }


        //ViewModel Implementation
//        val appObject = application as KaagazApplication
//        val repository = appObject.repository
//        val imageViewModelFactory = ImageViewModelFactory(repository)

        imageDatabase = ImageDatabase.getImageDatabase(this)
        imageDao = imageDatabase.getImageDao()
        val repository = RepositoryKagaz(imageDao)

        val imageViewModelFactory = ImageViewModelFactory(repository)

        viewModelKaagaz = ViewModelProviders.of(this, imageViewModelFactory).get(ViewModelKaagaz::class.java)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun takePhoto() {

        val timestamp =  SimpleDateFormat("yyyy-MM-dd   HH:mm:ss").format(Date())
        val imagename = "Kaagaz- ${System.currentTimeMillis()}.jpg"
        val photoFile = File(externalMediaDirs.firstOrNull(), "cameraApp- ${System.currentTimeMillis()}.jpg")
        val output = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture?.takePicture(output, ContextCompat.getMainExecutor(this),object: ImageCapture.OnImageSavedCallback{
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                image_uri = Uri.fromFile(photoFile)

                val imageEntity = ImageEntity(image_uri.toString(),timestamp.toString(),"album2",imagename);

                CoroutineScope(Dispatchers.IO).launch {
                    viewModelKaagaz.addImageDetails(imageEntity)
                }


            }

            override fun onError(exception: ImageCaptureException) {
                TODO("Not yet implemented")
            }

         }
        )

//        val imageEntity = ImageEntity(temp.toString(),timestamp.toString(),"album1");
//
//        CoroutineScope(Dispatchers.IO).launch {
//            viewModelKaagaz.addImageDetails(imageEntity)
//        }
//
//        if (imageEntity!=null) {
//
//            Toast.makeText(this,"Image is added", Toast.LENGTH_SHORT).show()
//        }else  Toast.makeText(this,"Failed to add Image", Toast.LENGTH_SHORT).show()


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
        //start camera stuff

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {

        val cameraProvider = cameraProviderFuture.get()
        preview=Preview.Builder().build()
        preview?.setSurfaceProvider(cameraView.createSurfaceProvider(camera?.cameraInfo))

        imageCapture=ImageCapture.Builder().build()
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        cameraProvider.unbindAll()
        camera=cameraProvider.bindToLifecycle(this, cameraSelector, preview,imageCapture)
    },ContextCompat.getMainExecutor(this))
    }
}

