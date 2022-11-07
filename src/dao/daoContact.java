/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Interface.InterCategorie;
import Interface.interContact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Contact;
import util.MaConnexion;

/**
 *
 * @author Elife-Kef-006
 */
public class daoContact implements interContact {

    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void AjouterContact(Contact st) {
        String requete = "insert into contacts (nom,email,message) values (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1, st.getNom());
            ps.setString(2, st.getEmail());
            ps.setString(3, st.getMessage());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(daocategorie.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static interContact daoContact;

    public static interContact getInstance() {
        if (daoContact == null) {
            daoContact = new daoContact();
        }
        return daoContact;
    }

}
