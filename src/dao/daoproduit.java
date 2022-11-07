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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Categories;
import model.Produit;
import util.MaConnexion;

/**
 *
 * @author bouden
 */
public class daoproduit implements interProduit {

    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void insertproduit(Produit st) {
        String requete = "INSERT INTO `produits`(`nom`,`image`,`prix`,`Description`,`categorie`,`quantite`) VALUES ( ?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1, st.getNom_produit());
            ps.setString(2, st.getImage());
            ps.setFloat(3, st.getPrix());
            ps.setString(4, st.getDescription());
            ps.setInt(5, st.getCat().getId());
            ps.setInt(6, st.getQuantite());
            ps.executeUpdate();
            System.out.println("Ajout effectuée avec succès");
        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de l'insertion " + ex.getMessage());
        }
    }

    @Override
    public void updateProduit(Produit st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteProduit(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static interProduit daoproduit;

    public static interProduit getInstance() {
        if (daoproduit == null) {
            daoproduit = new daoproduit();
        }
        return daoproduit;
    }

    @Override
    public ObservableList<Produit> DisplayAllproduit() {

        ObservableList<Produit> listedepots = FXCollections.observableArrayList();
        String requete = "select * from produits";
        try {
            Statement statement = cnx.createStatement();
            ResultSet resultat = statement.executeQuery(requete);
            InterCategorie deptdao = daocategorie.getInstance();

            while (resultat.next()) {
                Produit pr = new Produit();
                pr.setId(resultat.getInt(1));
                pr.setNom_produit(resultat.getString(2));
                pr.setImage(resultat.getString(3));
                pr.setPrix(resultat.getInt(4));
                pr.setDescription(resultat.getString("Description"));
                pr.setCat(deptdao.findcatById(resultat.getInt(6)));
                pr.setQuantite(resultat.getInt(7));

                listedepots.add(pr);
            }
            return listedepots;
        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors du chargement des stocks " + ex.getMessage());
            return null;
        }
    }
}
