package com.example.cloud_firestore_project.models

import com.google.firebase.firestore.Exclude

class Note {

    @Exclude
    var  documentId:String? = null
        get() = field
        set(value) {
            field = value
        }

     var title:String ? = null
        get() = field
        set(value) {
            field = value
        }

     var description:String ? = null
        get() = field
        set(value) {
            field = value
        }

    // firebase always needs public empty constructor
    public constructor(){
        // public no-arg constructor needed firebase
    }

    constructor(title: String?, description: String?) {
        this.title = title
        this.description = description
    }


}