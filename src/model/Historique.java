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
public class Historique {

    private int id;
    private String Produit;
    private int total_prix;
    private String Ville;
    private String etat_commande;
    private String date;
    private String id_user;
    private String email;
    public String getId_user() {
        return id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
    
    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public Historique(int id, String Produit, int total_prix, String Ville, String etat_commande, String date, String id_user, String email) {
        this.id = id;
        this.Produit = Produit;
        this.total_prix = total_prix;
        this.Ville = Ville;
        this.etat_commande = etat_commande;
        this.date = date;
        this.id_user = id_user;
        this.email = email;
    }

    
    
    
    
    public Historique(int id, String Produit, int total_prix, String Ville, String etat_commande, String date, String id_user) {
        this.id = id;
        this.Produit = Produit;
        this.total_prix = total_prix;
        this.Ville = Ville;
        this.etat_commande = etat_commande;
        this.date = date;
        this.id_user = id_user;
    }

    public Historique() {
    }

    public Historique(int id, String Produit, int total_prix, String Ville, String etat_commande) {
        this.id = id;
        this.Produit = Produit;
        this.total_prix = total_prix;
        this.Ville = Ville;
        this.etat_commande = etat_commande;
    }

    public Historique(String Produit, int total_prix, String Ville, String etat_commande) {
        this.Produit = Produit;
        this.total_prix = total_prix;
        this.Ville = Ville;
        this.etat_commande = etat_commande;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduit() {
        return Produit;
    }

    public void setProduit(String Produit) {
        this.Produit = Produit;
    }

    public int getTotal_prix() {
        return total_prix;
    }

    public void setTotal_prix(int total_prix) {
        this.total_prix = total_prix;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String Ville) {
        this.Ville = Ville;
    }

    public String getEtat_commande() {
        return etat_commande;
    }

    public void setEtat_commande(String etat_commande) {
        this.etat_commande = etat_commande;
    }

    @Override
    public String toString() {
        return "Historique{" + "id=" + id + ", Produit=" + Produit + ", total_prix=" + total_prix + ", Ville=" + Ville + ", etat_commande=" + etat_commande + '}';
    }

}
