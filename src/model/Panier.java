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
public class Panier {

    private Integer id;
    private String nom_produit;
    private String image;
    private String description;
    private String categorie;
    private Integer quantite;
    private String prix;
    private Integer id_user;
    private Integer id_produit;
    private String Color;
private String newprix;
    public Panier() {
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public Panier(Integer id, String nom_produit, String image, String description, String categorie, Integer quantite, String prix, Integer id_produit) {
        this.id = id;
        this.nom_produit = nom_produit;
        this.image = image;
        this.description = description;
        this.categorie = categorie;
        this.quantite = quantite;
        this.prix = prix;
        this.id_produit = id_produit;
    }

    public Integer getId_produit() {
        return id_produit;
    }

    public String getNewprix() {
        return newprix;
    }

    public void setNewprix(String newprix) {
        this.newprix = newprix;
    }

    public void setId_produit(Integer id_produit) {
        this.id_produit = id_produit;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Panier(Integer id, String nom_produit, String image, String description, String categorie, Integer quantite, Integer id_user) {
        this.id = id;
        this.nom_produit = nom_produit;
        this.image = image;
        this.description = description;
        this.categorie = categorie;
        this.quantite = quantite;
        this.id_user = id_user;
    }

    public Panier(String nom_produit, String image, String description, String categorie, Integer quantite, Integer id_user) {
        this.nom_produit = nom_produit;
        this.image = image;
        this.description = description;
        this.categorie = categorie;
        this.quantite = quantite;
        this.id_user = id_user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return  nom_produit + "*"+ quantite ;
    }

   

}
