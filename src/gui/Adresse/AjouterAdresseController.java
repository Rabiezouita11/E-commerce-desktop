/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Adresse;

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

/**
 * FXML Controller class
 *
 * @author Elife-Kef-108
 */
public class AjouterAdresseController implements Initializable {

    @FXML
    private TextField txtcodepostale;
    @FXML
    private TextField txtrue;
    @FXML
    private TextField txtnumero;
    @FXML
    private TextField txtville;
    Connection cnx = MaConnexion.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Ajouter(ActionEvent event) throws SQLException, JSONException, IOException {

        if (this.isValidated()) {
            PreparedStatement ps, ps1;
            ResultSet rs;
            String aa = "SELECT * FROM adresse WHERE code_postale ='" + txtcodepostale.getText() + "' or rue =  '" + txtrue.getText() + "'   or numero_boite_lettre =  '" + txtnumero.getText() + "' or ville =  '" + txtville.getText() + "' ";
            String yy = "INSERT INTO adresse(code_postale,rue,numero_boite_lettre,ville,id_user) VALUES ( ?,?,?,?,?)";

            ps1 = cnx.prepareStatement(aa);

            rs = ps1.executeQuery();
            if (rs.next()) {
                showMessageDialog(null, "Adresse deja ajouter ");
            } else {

                ps = cnx.prepareStatement(yy);
                ps.setString(1, txtcodepostale.getText());
                ps.setString(2, txtrue.getText());
                ps.setString(3, txtnumero.getText());
                ps.setString(4, txtville.getText());

                Preferences userPreferences = Preferences.userRoot();
                String aaa = userPreferences.get("jwt", "root");

                String id = new Jwt(aaa).getAudience();
                System.out.println(id);
                ps.setString(5, id);
                ps.executeUpdate();
                showMessageDialog(null, "Ajouter avec succes");
                Stage stage = (Stage) txtcodepostale.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("../../gui/Adresse/Adresse.fxml"));

                Scene scene = new Scene(root);

               
                stage.setMaxHeight(810);
                stage.setMaxWidth(1408);
           
                stage.setScene(scene);

                stage.setTitle("Adresse");
                //
                stage.show();
            }
        }

    
}

@FXML
        private void annuler(ActionEvent event) throws IOException {
        Stage stage = (Stage) txtcodepostale.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Adresse/Adresse.fxml"));

        Scene scene = new Scene(root);

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

            showMessageDialog(null, "code postale text field cannot be blank.");
            txtcodepostale.requestFocus();
        } else if (txtnumero.getText().equals("")) {
            showMessageDialog(null, "numero text field cannot be blank.");
            txtnumero.requestFocus();

        } else if (txtrue.getText().equals("")) {
            showMessageDialog(null, "rue text field cannot be blank.");
            txtrue.requestFocus();
        } else if (txtville.getText().equals("")) {
            showMessageDialog(null, "ville text field cannot be blank.");
            txtrue.requestFocus();

        } else if (!x.matcher(txtcodepostale.getText()).matches()) {
            showMessageDialog(null, "code postale contains only number.");
            txtcodepostale.requestFocus();

        } else {
            return true;
        }
        return false;
    }

}
