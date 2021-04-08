package com.example.quizapp.Model

class Quiz {
    var maker: String? = null
    var quizType: String? = null
    var questionCount: Int? = null

    constructor() {}
    constructor(maker: String?, type: String?, count: Int?) {
        this.maker = maker
        this.quizType = type
        this.questionCount = count
    }
}