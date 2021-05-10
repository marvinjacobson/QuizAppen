package com.example.quizapp.Model

class Question {
    var questionID: String? = null
    var questiontext: String? = null
    var questionnumber: Int? = null
    var nextQuestion: String? = null
    var correctanswer: Int? = null
    var answer1: String? = null
    var answer2: String? = null
    var answer3: String? = null
    var quizId: String? = null

    constructor() {}
    constructor(questionID: String?, questiontext: String?,questionnumber: Int?, nextQuestion: String?, correctanswer: Int?, answer1: String?, answer2: String?, answer3: String?, quizId: String?) {
        this.questionID = questionID
        this.questiontext = questiontext
        this.correctanswer = correctanswer
        this.questionnumber = questionnumber
        this.nextQuestion = nextQuestion;
        this.answer1 = answer1
        this.answer2 = answer2
        this.answer3 = answer3
        this.quizId = quizId

    }


}