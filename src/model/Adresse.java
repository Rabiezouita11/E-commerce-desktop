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
public class Adresse {

    private Integer id;
    private String code_postale;
    private Integer id_user;
    private String nom_user;
    private String Image_user;
    private String rue;
    private String numero_boite_lettre;
    private String ville;

    public Adresse() {
    }

    public Adresse(String ville) {
        this.ville = ville;
    }

    
    public Adresse(Integer id, String code_postale, Integer id_user, String nom_user, String Image_user, String rue, String numero_boite_lettre, String ville) {
        this.id = id;
        this.code_postale = code_postale;
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.Image_user = Image_user;
        this.rue = rue;
        this.numero_boite_lettre = numero_boite_lettre;
        this.ville = ville;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode_postale() {
        return code_postale;
    }

    public void setCode_postale(String code_postale) {
        this.code_postale = code_postale;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getImage_user() {
        return Image_user;
    }

    public void setImage_user(String Image_user) {
        this.Image_user = Image_user;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getNumero_boite_lettre() {
        return numero_boite_lettre;
    }

    public void setNumero_boite_lettre(String numero_boite_lettre) {
        this.numero_boite_lettre = numero_boite_lettre;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return ville;
    }


}
