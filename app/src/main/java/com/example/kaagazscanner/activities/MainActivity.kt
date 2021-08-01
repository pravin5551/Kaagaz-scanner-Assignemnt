package com.example.kaagazscanner.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.ViewModelProviders
import androidx.room.TypeConverter
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
import java.security.Permission
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    var camera: Camera ?=null
    var preview: Preview?=null
    var imageCapture:ImageCapture?=null
    private lateinit var viewModelKaagaz: ViewModelKaagaz
    lateinit var imageDatabase: ImageDatabase
    lateinit var imageDao: ImageDao
    lateinit var temp: Uri;

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

    private fun takePhoto() {
        //save photos here

        val timestamp = System.currentTimeMillis()





        val photoFile = File(externalMediaDirs.firstOrNull(), "cameraApp- ${System.currentTimeMillis()}.jpg")
        val output = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture?.takePicture(output, ContextCompat.getMainExecutor(this),object: ImageCapture.OnImageSavedCallback{
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                temp = Uri.fromFile(photoFile)

                val imageEntity = ImageEntity(temp.toString(),timestamp.toString(),"album2");

                CoroutineScope(Dispatchers.IO).launch {
                    viewModelKaagaz.addImageDetails(imageEntity)
                }

//                if (imageEntity!=null) {
//
//                    Toast.makeText(this,"Image is added", Toast.LENGTH_SHORT).show()
//                }else  Toast.makeText(this,"Failed to add Image", Toast.LENGTH_SHORT).show()
//                Toast.makeText(applicationContext, "Image saved", Toast.LENGTH_SHORT).show()
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


/*
@TypeConverter
    public static byte [] getStringFromBitmap(Bitmap bitmapPicture){
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.JPEG,0,byteArrayBitmapStream);
       byte[] b = byteArrayBitmapStream.toByteArray();
       return b;
   }
//   @TypeConverter
//    public static Bitmap getBitmapFromStr(byte[] arr){
//        return BitmapFactory.decodeByteArray(arr,0,arr.length);
//    }
 */