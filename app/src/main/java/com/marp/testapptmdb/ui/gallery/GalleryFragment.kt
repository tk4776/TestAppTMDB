package com.marp.testapptmdb.ui.gallery

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.marp.testapptmdb.R

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    //FireStore Init
    val db =  FirebaseFirestore.getInstance()
    val user = hashMapOf(
        "first" to "Alejandro",
        "last"  to "Perez",
        "born"  to 1997
    )


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

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


}