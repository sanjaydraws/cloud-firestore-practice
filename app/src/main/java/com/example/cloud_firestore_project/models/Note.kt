package com.example.cloud_firestore_project.models

class Note {

    private var title:String ? = null
        get() = field
        set(value) {
            field = value
        }

    private var description:String ? = null
        get() = field
        set(value) {
            field = value
        }

    public constructor(){
        // public no-arg constructor needed firebase
    }

    constructor(title: String?, description: String?) {
        this.title = title
        this.description = description
    }


}