/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Elife-Kef-108
 */
public class Coupon {
    private int id;
    private String date_expiration;
    private int prix;
    private int id_user;
    private String etat;

    public Coupon() {
    }

    public Coupon(int id, String date_expiration, int prix) {
        this.id = id;
        this.date_expiration = date_expiration;
        this.prix = prix;
    }

    
    
    
    
    
    public Coupon(int id, String date_expiration, int prix, int id_user, String etat) {
        this.id = id;
        this.date_expiration = date_expiration;
        this.prix = prix;
        this.id_user = id_user;
        this.etat = etat;
    }

    public Coupon(String date_expiration, int prix, int id_user, String etat) {
        this.date_expiration = date_expiration;
        this.prix = prix;
        this.id_user = id_user;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(String date_expiration) {
        this.date_expiration = date_expiration;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return   String.valueOf(prix) ;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
