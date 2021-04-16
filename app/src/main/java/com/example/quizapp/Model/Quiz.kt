package com.example.quizapp.Model

class Quiz {
    var quizName: String? = null
    var maker: String? = null
    var quizType: String? = null
    var questionCount: Int? = null
    var quizID: String? = null

    constructor() {}
    constructor( name: String?, maker: String?, type: String?, count: Int?, id: String?) {
        this.quizName = name
        this.maker = maker
        this.quizType = type
        this.questionCount = count
        this.quizID = id
    }
}