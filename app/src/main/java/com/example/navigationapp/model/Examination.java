package com.example.navigationapp.model;

/**
 * Created by 占大帅 on 2017/3/6.
 */

public class Examination {
    private int id;
    private String question;
    private String answera;
    private String answerb;
    private String answerc;
    private String answerd;
    private int answer;

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getAnswerd() {

        return answerd;
    }

    public void setAnswerd(String answerd) {
        this.answerd = answerd;
    }

    public String getAnswerc() {

        return answerc;
    }

    public void setAnswerc(String answerc) {
        this.answerc = answerc;
    }

    public String getAnswerb() {

        return answerb;
    }

    public void setAnswerb(String answerb) {
        this.answerb = answerb;
    }

    public String getAnswera() {

        return answera;
    }

    public void setAnswera(String answera) {
        this.answera = answera;
    }

    public String getQuestion() {

        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
