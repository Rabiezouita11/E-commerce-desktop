/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Interface.InterAdresse;
import Interface.interContact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Adresse;
import model.Categories;
import model.user;
import util.MaConnexion;

/**
 *
 * @author Elife-Kef-108
 */
public class daoadresse implements InterAdresse {

    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public user findUserById(int id) {
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

    private static InterAdresse daoadresse;

    public static InterAdresse getInstance() {
        if (daoadresse == null) {
            daoadresse = new daoadresse();
        }
        return daoadresse;
    }

    @Override
    public user findUserImageById(int id) {
        user cat = new user();
        String requete = "select * from users where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet resultat = ps.executeQuery();
            while (resultat.next()) {
              
                cat.setImage(resultat.getString("image"));
            }
            return cat;

        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la recherche du depot " + ex.getMessage());
            return null;
        }
    }
}
