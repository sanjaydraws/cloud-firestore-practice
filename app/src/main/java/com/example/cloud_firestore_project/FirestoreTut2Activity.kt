package com.example.cloud_firestore_project

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cloud_firestore_project.databinding.ActivityFirestoreTut2Binding
import com.example.cloud_firestore_project.models.Note
import com.google.firebase.firestore.*


class FirestoreTut2Activity : AppCompatActivity() {

    val db by lazy{
       FirebaseFirestore.getInstance()
    }
    val notebookRef by lazy{
        db.collection("Notebook")
    }
    val noteRef by lazy{
        db.document("Notebook/My First Note")
    }

    var binding:ActivityFirestoreTut2Binding ? = null
    companion object{
        const val TAG = "FirestoreTut2Activity"
        fun  start(context: Activity?=null){
            val intent = Intent(context,FirestoreTut2Activity::class.java )
            context?.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirestoreTut2Binding.inflate(layoutInflater)
        binding.apply {
            setContentView(binding?.root)
        }

    }
    fun addNote(view: View){
        val title: String = binding?.editTextTitle?.getText().toString()
        val description: String = binding?.editTextDescription?.getText().toString()
        val note = Note(title, description)
        notebookRef.add(note).addOnSuccessListener {

        }.addOnFailureListener{

        }

    }
    fun loadNotes(view: View){
        notebookRef.get()
            .addOnSuccessListener {
                var data:String? = ""
                // Querysnapshot contains multiple document snapshot
                //Query snapshot allways access it's sub class of DocumentSnapshot
                for (documentSnapshot in it) {
                    val note:Note? = documentSnapshot.toObject(Note::class.java)
                    note?.documentId = documentSnapshot?.id
                    val documentId: String? = note?.documentId
                    val title: String? = note?.title
                    val description: String? = note?.description
                    data += "ID: documentId\nTitle: $title\nDescription: $description\n\n";
                }
                binding?.textViewData?.setText(data)
            }
    }

    override fun onStart() {
        super.onStart()
        notebookRef.addSnapshotListener(this, object : EventListener<QuerySnapshot?> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    return
                }
                var data: String? = ""

                if (value != null) {
                    for (documentSnapshot in value) {
                        val note: Note? = documentSnapshot.toObject(Note::class.java)
                        note?.documentId = documentSnapshot?.id
                        val documentId: String? = note?.documentId
                        val title: String? = note?.title
                        val description: String? = note?.description
                        data += "ID: $documentId\nTitle: $title\nDescription: $description\n\n";

                        //reference of particular document
//                        if (documentId != null) {
////                            notebookRef.document(documentId).update()
//                        }


                    }
                }
                binding?.textViewData?.setText(data)


            }
    })
        }

}