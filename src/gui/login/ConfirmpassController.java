/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.login;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.xml.bind.DatatypeConverter;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class ConfirmpassController implements Initializable {

    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private PasswordField motpass;
    @FXML
    private PasswordField confirmpass;
    @FXML
    private TextField email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        email.setText(Statics.current_user.getEmail());
    }

    @FXML
    private void confirmer(ActionEvent event) throws SQLException, IOException {
        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            String query = "select * from users WHERE email = ?";

            ps = cnx.prepareStatement(query);
            ps.setString(1, Statics.current_user.getEmail());

            rs = ps.executeQuery();

            if (rs.next()) {

                PreparedStatement pss;
                ResultSet rss;
                int xx = rs.getInt("id");
                String x = motpass.getText();
                String z = "";
                String yy = "update   users set  mot_de_passe ='" + getHash(x.getBytes(), "SHA-1") + "' , code ='"+ z +"' where id = '" + xx + "' ";
                pss = cnx.prepareStatement(yy);
                pss.execute();
                showMessageDialog(null, "mot de passe modifier avec succès.");
                Stage stage = (Stage) motpass.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle("User Login");
                //
                stage.show();

            } else {

            }
        }
    }

    @FXML
    private void annuler(ActionEvent event) throws IOException {
        Stage stage = (Stage) email.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/login/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("login");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

    private boolean isValidated() {
//        String s = categorie.getSelectionModel().getSelectedItem().toString();

        if (motpass.getText().equals("")) {

            showMessageDialog(null, "mot de passe field cannot be blank.");
            motpass.requestFocus();

        } else if (confirmpass.getText().equals("")) {

            showMessageDialog(null, "confirme mot de passe field cannot be blank.");
            confirmpass.requestFocus();
        } else if (!motpass.getText().equals(confirmpass.getText())) {

            showMessageDialog(null, "erreur confirm password  ");
            motpass.requestFocus();

        } else {
            return true;
        }
        return false;
    }

    public static String getHash(byte[] inputBytes, String algorithme) {
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithme);
            messageDigest.update(inputBytes);
            byte[] digesteBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digesteBytes).toLowerCase();

        } catch (Exception e) {

        }
        return hashValue;
    }

}
