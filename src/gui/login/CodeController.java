/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.login;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static javax.swing.JOptionPane.showMessageDialog;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class CodeController implements Initializable {

    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private PasswordField code;
    @FXML
    private TextField aa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    aa.setText(Statics.current_user.getEmail());
    }

    @FXML
    private void confirmer(ActionEvent event) throws SQLException, IOException {
        // String pass = email.getText();
        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            String query = "select * from users WHERE code = ?";

            ps = cnx.prepareStatement(query);
            ps.setString(1, code.getText());

            rs = ps.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("confirmation");
                alert.setHeaderText("code");
                alert.setContentText("code valide");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    Stage stage = (Stage) code.getScene().getWindow();
                    stage.close();

                    Parent root = FXMLLoader.load(getClass().getResource("confirmpass.fxml"));

                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.setTitle("login");
                    stage.getIcons().add(new Image("/img/mm.png"));
                    stage.show();

                }

            } else {
   Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("confirmation");
                alert.setHeaderText("code");
                alert.setContentText("code invalide try again");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    
                }
            }
        }

    }

    @FXML
    private void annuler(ActionEvent event) throws IOException {
        Stage stage = (Stage) code.getScene().getWindow();
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
        String number = "[0-9]+";
        Pattern x = Pattern.compile(number);
        if (code.getText().equals("")) {

            showMessageDialog(null, "code text field cannot be blank.");
            code.requestFocus();

        } else if (!x.matcher(code.getText()).matches()) {
            showMessageDialog(null, "code contains only number.");
            code.requestFocus();

        } else {
            return true;
        }
        return false;
    }

}
