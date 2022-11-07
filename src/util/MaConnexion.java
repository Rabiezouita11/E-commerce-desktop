/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Elife-Kef-139
 */
public class MaConnexion {
    
    //DB
    final String URL = "jdbc:mysql://localhost:3306/elif";
    final String USR  = "root";
    final String PWD = "";
    
    //Var
    Connection cnx;
    
    //1 : instance
    private static MaConnexion instance = null;
    
    //const
    //2: private constructor
    public MaConnexion(){
    
        try {
            cnx = DriverManager.getConnection(URL, USR, PWD);
            System.out.println("Connexion établie avec succés!");
             showMessageDialog(null, "Connexion établie avec succés!");
        } catch (SQLException ex) {
         showMessageDialog(null, "Connexion erreur");
        }
        
       
        
        
    }
    
 //3 : get instance
    public static MaConnexion getInstance() {
        if(instance == null)
            instance = new MaConnexion();
        
        return instance;
    }

    public Connection getCnx() {
        
        return cnx;
    }
    
    
    
    
}
