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
public class Quiz {

    private int id;
    private String titre_quiz;
    private String option1;
    private String option2;
    private String option3;
    private String bonne_reponse;
   private String color;
    public Quiz() {
    }

    public Quiz(String titre_quiz, String option1, String option2, String option3, String bonne_reponse) {
        this.titre_quiz = titre_quiz;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.bonne_reponse = bonne_reponse;
    }

    public Quiz(int id, String titre_quiz, String option1, String option2, String option3, String bonne_reponse) {
        this.id = id;
        this.titre_quiz = titre_quiz;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.bonne_reponse = bonne_reponse;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre_quiz() {
        return titre_quiz;
    }

    public void setTitre_quiz(String titre_quiz) {
        this.titre_quiz = titre_quiz;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getBonne_reponse() {
        return bonne_reponse;
    }

    public void setBonne_reponse(String bonne_reponse) {
        this.bonne_reponse = bonne_reponse;
    }

    @Override
    public String toString() {
        return "Quiz{" + "id=" + id + ", titre_quiz=" + titre_quiz + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3 + ", bonne_reponse=" + bonne_reponse + '}';
    }

    
    
    
    
    
}
