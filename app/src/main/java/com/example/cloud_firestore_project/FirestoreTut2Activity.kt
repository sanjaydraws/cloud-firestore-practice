package com.example.cloud_firestore_project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cloud_firestore_project.databinding.ActivityFirestoreTut2Binding
import com.example.cloud_firestore_project.models.Note
import com.google.firebase.firestore.FirebaseFirestore

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
        notebookRef.add(note)

    }
    fun loadNotes(){

    }

}