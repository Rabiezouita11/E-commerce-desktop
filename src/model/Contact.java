/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Elife-Kef-006
 */
public class Contact {
    private int id;
    private String nom;
    private String email;
    private String message;

    public Contact() {
    }

    public Contact(int id, String nom, String email, String message) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.message = message;
    }

    public Contact(String nom, String email, String message) {
        this.nom = nom;
        this.email = email;
        this.message = message;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Contact{" + "id=" + id + ", nom=" + nom + ", email=" + email + ", message=" + message + '}';
    }

    
    
    
    
    
    
    
    
    
    
}
