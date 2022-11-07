/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Avis;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static javax.swing.JOptionPane.showMessageDialog;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Notification;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class AjouterAvisController implements Initializable {

    @FXML
    private TextField txtcodepostale;
    Connection cnx = MaConnexion.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Ajouter(ActionEvent event) throws SQLException, IOException, JSONException, JSONException {
        if (this.isValidated()) {
            PreparedStatement ps, ps1;
            ResultSet rs;
            String yy = "INSERT INTO Avis(message,id_user) VALUES ( ?,?)";

            ps = cnx.prepareStatement(yy);
            ps.setString(1, txtcodepostale.getText());

            Preferences userPreferences = Preferences.userRoot();
            String aaa = userPreferences.get("jwt", "root");

            String id = new Jwt(aaa).getAudience();
            System.out.println(id);
            ps.setString(2, id);
            ps.executeUpdate();

            Notification.notificationSuccess("ajouter", "Ajouter avec succes");
            Stage stage = (Stage) txtcodepostale.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("../../gui/Avis/avis.fxml"));

            Scene scene = new Scene(root);
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.setMaxHeight(810);
            stage.setMaxWidth(1408);

            stage.setScene(scene);

            stage.setTitle("Avis");
            //
            stage.show();
        }
    }

    @FXML
    private void annuler(ActionEvent event) throws IOException {
        Stage stage = (Stage) txtcodepostale.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Avis/avis.fxml"));

        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setMaxHeight(810);
        stage.setMaxWidth(1408);
        stage.setScene(scene);

        stage.setTitle("Adresse");
        //
        stage.show();
    }

    private boolean isValidated() {
//        String s = categorie.getSelectionModel().getSelectedItem().toString();
        String number = "[0-9]+";
        Pattern x = Pattern.compile(number);
        if (txtcodepostale.getText().equals("")) {

            Notification.notificationError("Message text field cannot be blank.");
            txtcodepostale.requestFocus();

        } else if (x.matcher(txtcodepostale.getText()).matches()) {

            Notification.notificationError("Message ne contient pas des chiffres.");
            txtcodepostale.requestFocus();

        } else {
            return true;
        }
        return false;
    }
}
