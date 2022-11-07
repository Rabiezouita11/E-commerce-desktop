/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author bouden
 */
public class Reponse {
    private int id;
    private String reponse;
    private int id_quiz;
    private int id_user;

    public Reponse() {
    }

    public Reponse(int id, String reponse, int id_quiz, int id_user) {
        this.id = id;
        this.reponse = reponse;
        this.id_quiz = id_quiz;
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Reponse{" + "id=" + id + ", reponse=" + reponse + ", id_quiz=" + id_quiz + ", id_user=" + id_user + '}';
    }
    
}
