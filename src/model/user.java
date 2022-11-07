/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.InputStream;
import javafx.scene.image.Image;

/**
 *
 * @author bouden
 */
public class user {

    private int id;
    private String nom;
    private String prenom;
    private String mot_de_passe;
    private String role;
    private String email;
    private String Image;
    private String banier;
    private Integer Cin;

    public user() {
    }

    

    public user(int id, String nom, String prenom, String email, String banier, Integer Cin) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.banier = banier;
        this.Cin = Cin;
    }

    
    
    
    
    
    
    public user(int id, String nom, String prenom, String mot_de_passe, String role, String email, String Image, Integer Cin) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
        this.email = email;
        this.Image = Image;
        this.Cin = Cin;
    }

    public user(String nom, String prenom, String mot_de_passe, String role, String email, String Image, Integer Cin) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
        this.email = email;
        this.Image = Image;
        this.Cin = Cin;
    }

    public String getBanier() {
        return banier;
    }

    public void setBanier(String banier) {
        this.banier = banier;
    }

    public Integer getCin() {
        return Cin;
    }

    public void setCin(Integer Cin) {
        this.Cin = Cin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return Image;
    }

    @Override
    public String toString() {
        return nom;
    }

    public void setImage(String imge) {
        this.Image = imge;
    }

}
