package com.example.quizapp.Model

class User {
    var uID: String? = null
    var userName: String? = null


    constructor(name: String) {}
    constructor(userName: String?, uID: String?) {
        this.userName = userName
        this.uID = uID

    }
}