package com.example.cloud_firestore_project

import android.os.Bundle
import android.util.Log
import android.view.KeyCharacterMap
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cloud_firestore_project.databinding.ActivityMainBinding
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    var binding:ActivityMainBinding? = null
    // instance of firestore
    val db by lazy{
        FirebaseFirestore.getInstance()
    }
    val noteRef by lazy{
//        db.collection("Notebook").document("My First Note")
        db.document("Notebook/My First Note")
    }
    private var noteListner: ListenerRegistration? = null

    companion object{
        const val  KEY_TITLE = "title"
        const val  KEY_DESCRIPTION = "Description"
        private const val TAG = "MainActivity"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


    }

    fun saveNote(view: View) {
        val title = binding?.editTextTitle?.text.toString().trim()
        val description = binding?.editTextDescription?.text.toString().trim()
        val note: MutableMap<String, Any> = HashMap()
        note[KEY_TITLE] = title
        note[KEY_DESCRIPTION] = description
        noteRef.set(note).addOnSuccessListener {
            Toast.makeText(this@MainActivity, "Note saved", Toast.LENGTH_SHORT).show();
        }.addOnFailureListener {
            Toast.makeText(this@MainActivity, "Error!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, it.toString());
        }
    }

    fun loadNote(view: View){
        noteRef.get()
            .addOnSuccessListener {
                if(it.exists()){
                    val title =  it.getString(KEY_TITLE)
                    val description = it.getString(KEY_DESCRIPTION)
//                    val note: MutableMap<String, Any> = it.data as MutableMap<String, Any>
                    binding?.textViewData?.text = "Title: $title \n + Description $description"
                }else{
                    Toast.makeText(this@MainActivity, "Toast doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
            .addOnFailureListener{

            }
    }
    fun updateDescription(view:View){
        val description = binding?.editTextDescription?.text.toString()
//        val note: MutableMap<String, Any> = HashMap()
//        note[KEY_DESCRIPTION] = description
//        noteRef.set(note, SetOptions.merge()) // merge ->calls to only replace the values specified in its data
//        =============
        //        noteRef.update(note) // can also pass map, and key, not create new document
        noteRef.update(KEY_DESCRIPTION, description)   /// note create new document

    }

    fun deleteDescription(view: View){
//        val note:MutableMap<String, Any>  = HashMap()
//        note.put(KEY_DESCRIPTION, FieldValue.delete())
//        noteRef.update(note)

        //also can do this
        noteRef.update(KEY_DESCRIPTION, FieldValue.delete())

    }
    fun deleteNote(view: View){
        noteRef.delete()
    }
    override fun onStart() {
        super.onStart()

//        noteListner = noteRef.addSnapshotListener(object :EventListener<DocumentSnapshot>{

            // you don't need to deattach it's automatically deattach our listner when you pass this keyword
           // no need of notelistener
           noteRef.addSnapshotListener(this,object :EventListener<DocumentSnapshot>{

            override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                if(error!= null ){
                    Toast.makeText(this@MainActivity, "Error while loading ", Toast.LENGTH_SHORT).show();
                }
                if(value?.exists() == true){
                    val title =  value.getString(KEY_TITLE)
                    val description = value.getString(KEY_DESCRIPTION)
//                    val note: MutableMap<String, Any> = it.data as MutableMap<String, Any>
                    binding?.textViewData?.text = "Title: $title \n + Description $description"
                }else{
                    // when it's false you delete note you will see here old data
                    binding?.textViewData?.text = ""
                }
            }

        })
    }
    override fun onStop() {
        super.onStop()
//        noteListner?.remove()

    }
}


