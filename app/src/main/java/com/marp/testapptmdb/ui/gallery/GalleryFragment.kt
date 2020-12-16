package com.marp.testapptmdb.ui.gallery

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.marp.testapptmdb.R
import retrofit2.http.Url
import java.io.File
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var auth: FirebaseAuthException
    lateinit var storage: FirebaseStorage

    //FireStore Init
    val db =  FirebaseFirestore.getInstance()
    val user = hashMapOf(
        "first" to "Alejandro",
        "last"  to "Perez",
        "born"  to 1997
    )

    val GALLERY = 0
    val FILE = 1

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        storage = FirebaseStorage.getInstance()


        galleryViewModel =
                ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        val upload = root.findViewById<Button>(R.id.up_photo)
        upload.setOnClickListener {
            openAlbum()
        }
        db.collection("users")
            .add(user)
            .addOnSuccessListener{documentReference ->
                Log.d(TAG, "Document Snapshot Added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        return root
    }

    fun openAlbum(){
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY)
    }

    fun loadPhoto(downloadUrl: String){
        var showP: ImageView? = view?.findViewById(R.id.visualizer)
        showP?.let { Glide.with(this).load(downloadUrl).into(it) }
    }

    fun uploadPhoto(photoUri: Uri) {
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var fileName = "IMAGE_" + timestamp + "_.png"

        var storageRef = FirebaseStorage.getInstance().reference.child("images").child(fileName)

        storageRef.putFile(photoUri).addOnSuccessListener {
            Toast.makeText(this@GalleryFragment.context, "Upload photo completed", Toast.LENGTH_LONG).show()
        }
    }

    fun uploadFile(fileUri: Uri) {
        var metaCursor = activity?.getContentResolver()?.query(fileUri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME),null,null,null)!!
        metaCursor.moveToFirst()
        var fileName = metaCursor.getString(0)
        metaCursor.close()

        var storageRef = FirebaseStorage.getInstance().reference.child("files").child(fileName)

        storageRef.putFile(fileUri).addOnSuccessListener {
            Toast.makeText(this@GalleryFragment.context, "Upload photo completed", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var showP: ImageView? = view?.findViewById(R.id.visualizer)
        if(requestCode == GALLERY) {
            var photoUri: Uri = data?.data!!
            if (showP != null) {
                showP.setImageURI(photoUri)
                uploadPhoto(photoUri)
            }
            uploadPhoto(photoUri)
        } else if (requestCode == FILE){
            var fileUri = data?.data!!
            uploadFile(fileUri)
        }
    }
}