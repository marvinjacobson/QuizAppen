package com.example.quizapp.Model

class User {
    var userName: String? = null
    var uID: String? = null
    var favCategory: String? = null



    constructor(name: String) {}
    constructor(userName: String?, uID: String?, category: String?) {
        this.userName = userName
        this.uID = uID
        this.favCategory = category

    }
}