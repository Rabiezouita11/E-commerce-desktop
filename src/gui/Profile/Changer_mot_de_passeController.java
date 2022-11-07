/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Profile;

import static gui.register.RegisterController.getHash;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
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
import util.Jwt;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-006
 */
public class Changer_mot_de_passeController implements Initializable {

    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private PasswordField oldpassword;
    @FXML
    private PasswordField newpassword;
    @FXML
    private PasswordField confirmpassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Statics.current_user.getMot_de_passe();

    }

    @FXML
    private void changer(ActionEvent event) throws SQLException, IOException {

        Preferences userPreferences = Preferences.userRoot();
        String xxxx = userPreferences.get("jwt", "root");

        if (xxxx.equals("root")) {

        } else {
            String id = new Jwt(xxxx).getAudience();

            String x = oldpassword.getText();
            String z = newpassword.getText();
            if (isValidated()) {

                String tt = "SELECT * FROM `users` where id ='" + id + "' ";

                Statement statement;

                statement = cnx.createStatement();
                ResultSet queryoutput = statement.executeQuery(tt);
                while (queryoutput.next()) {

                    String xx = queryoutput.getString("mot_de_passe");

                    //   String xx = Statics.current_user.getMot_de_passe();
                    String zz = getHash(x.getBytes(), "SHA-1");
                    String yyy = getHash(z.getBytes(), "SHA-1");
                    if (zz.equals(xx)) {
                        PreparedStatement ps;
                        ResultSet rs;

                        String yy = "update   users set  mot_de_passe ='" + yyy + "' where id = '" + id + "' ";
                        ps = cnx.prepareStatement(yy);
                        ps.execute();

                        showMessageDialog(null, "mot de passe modifier  avec succes");
                        Stage stage = (Stage) oldpassword.getScene().getWindow();
                        stage.close();

                        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Profile/Profile.fxml"));

                        Scene scene = new Scene(root);
                        stage.setMaximized(true);

                        stage.setMaxHeight(810);
                        stage.setMaxWidth(1315);

                        stage.setScene(scene);
                        stage.setTitle("Profile");
                        stage.getIcons().add(new Image("/img/mm.png"));
                        stage.show();

                    } else {
                        showMessageDialog(null, "old password is incorrect");
                    }

                }
            }
        }
//         PreparedStatement ps, ps2;
//            ResultSet rs;
//            String query = "select * from users WHERE nom = ? and mot_de_passe = ?";
//         
//                ps = cnx.prepareStatement(query);
//                ps.setString(1,   Statics.current_user.getNom());
//                ps.setString(2, getHash(  Statics.current_user.getMot_de_passe().getBytes(), "SHA-1"));
//
//                rs = ps.executeQuery();
//
//                if (rs.next()) {
//                    
//                }
    }

    private boolean isValidated() {

        if (oldpassword.getText().equals("")) {

            showMessageDialog(null, "old password text field cannot be blank.");
            oldpassword.requestFocus();

        } else if (newpassword.getText().equals("")) {

            showMessageDialog(null, "new password text field cannot be blank.");
            newpassword.requestFocus();
        } else if (!newpassword.getText().equals(confirmpassword.getText())) {

            showMessageDialog(null, "erreur confirm password ");
            newpassword.requestFocus();

        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void annuler(ActionEvent event) throws IOException {
        Stage stage = (Stage) oldpassword.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Profile/Profile.fxml"));

        Scene scene = new Scene(root);
        stage.setMaximized(true);

        stage.setMaxHeight(810);
        stage.setMaxWidth(1315);

        stage.setScene(scene);
        stage.setTitle("Profile");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

}
