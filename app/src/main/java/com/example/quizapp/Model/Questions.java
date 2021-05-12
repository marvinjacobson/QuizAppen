package com.example.quizapp.Model;

public class Questions {
    private String Question, AnswerA, AnswerB, AnswerC, AnswerD, CorrectAnswer, CategoryId;

    public Questions() {
    }

    public Questions(String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer, String categoryId) {
        Question = question;
        this.AnswerA = answerA;
        this.AnswerB = answerB;
        this.AnswerC = answerC;
        this.AnswerD = answerD;
        CorrectAnswer = correctAnswer;
        CategoryId = categoryId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String questions) {
        Question = questions;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        this.AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        this.AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        this.AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        this.AnswerD = answerD;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
