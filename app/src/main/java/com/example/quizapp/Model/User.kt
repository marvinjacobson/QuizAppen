package com.example.quizapp.Model

class User {
    var userName: String? = null
    var password: String? = null
    var email: String? = null

    constructor() {}
    constructor(userName: String?, password: String?, email: String?) {
        this.userName = userName
        this.password = password
        this.email = email
    }
}