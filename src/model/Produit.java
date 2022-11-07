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
public class Produit {

    private Integer id, quantite;
    private String Description;
    private String nom_produit;
    private Integer prix;
    private String image;
    private Categories cat;
    private String color;
    private String promotion;
    private String numberpromotion;
    private String date;
    private Integer prixold;

    public Produit(Integer id, String nom_produit, Integer prix) {
        this.id = id;
        this.nom_produit = nom_produit;
        this.prix = prix;
    }

    public Integer getPrixold() {
        return prixold;
    }

    public void setPrixold(Integer prixold) {
        this.prixold = prixold;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public String getNumberpromotion() {
        return numberpromotion;
    }

    public void setNumberpromotion(String numberpromotion) {
        this.numberpromotion = numberpromotion;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Produit() {
    }

    public Produit(Integer id, Integer quantite, String Description, String nom_produit, Integer prix, String image, Categories cat) {
        this.id = id;
        this.quantite = quantite;
        this.Description = Description;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.image = image;
        this.cat = cat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Categories getCat() {
        return cat;
    }

    public void setCat(Categories cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", quantite=" + quantite + ", Description=" + Description + ", nom_produit=" + nom_produit + ", prix=" + prix + ", image=" + image + ", cat=" + cat + '}';
    }

}
