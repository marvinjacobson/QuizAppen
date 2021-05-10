package com.example.quizapp.Model

class QuizResult {
    var resultID: String? = null
    var quizID: String? = null
    var playerID: String? = null
    var score: Int? = null

    constructor(resultID: String?, quizID: String?, playerID: String?, score: Int?){
        this.resultID =resultID
        this.quizID=quizID
        this.playerID=playerID
        this.score=score
    }

}


