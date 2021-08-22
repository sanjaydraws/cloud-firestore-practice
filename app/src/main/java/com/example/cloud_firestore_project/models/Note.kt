package com.example.cloud_firestore_project.models

class Note {

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