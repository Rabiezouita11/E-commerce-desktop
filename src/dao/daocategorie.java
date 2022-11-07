/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Interface.InterCategorie;
import Interface.interProduit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Categories;
import model.user;
import util.MaConnexion;

/**
 *
 * @author bouden
 */
public class daocategorie implements InterCategorie {

    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void insertcat(Categories st) {
        String requete = "insert into categories (name) values (?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1, st.getName());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(daocategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updatecat(Categories st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletecat(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Categories> DisplayAllcat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private static InterCategorie daocategorie;

    public static InterCategorie getInstance() {
        if (daocategorie == null) {
            daocategorie = new daocategorie();
        }
        return daocategorie;
    }

    @Override
    public Categories findcatBynom(String nom) {
        Categories cat = new Categories();
        String requete = "select * from categories where name=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1, nom);
            ResultSet resultat = ps.executeQuery();
            while (resultat.next()) {
                cat.setId(resultat.getInt(1));
                cat.setName(resultat.getString(2));
            }
            return cat;

        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la recherche du depot " + ex.getMessage());
            return null;
        }
    }

    @Override
    public Categories findcatById(int id) {
        Categories cat = new Categories();
        String requete = "select * from categories where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet resultat = ps.executeQuery();
            while (resultat.next()) {
                cat.setId(resultat.getInt(1));
                cat.setName(resultat.getString(2));
            }
            return cat;

        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la recherche du depot " + ex.getMessage());
            return null;
        }
    }

    @Override
    public user findnomById(int id) {
        user cat = new user();
        String requete = "select * from users where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet resultat = ps.executeQuery();
            while (resultat.next()) {

                cat.setNom(resultat.getString("nom"));
            }
            return cat;

        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la recherche du depot " + ex.getMessage());
            return null;
        }
    }

    @Override
    public user findemailById(int id) {
        user cat = new user();
        String requete = "select * from users where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet resultat = ps.executeQuery();
            while (resultat.next()) {

                cat.setNom(resultat.getString("email"));
            }
            return cat;

        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la recherche du depot " + ex.getMessage());
            return null;
        }
    }
}
