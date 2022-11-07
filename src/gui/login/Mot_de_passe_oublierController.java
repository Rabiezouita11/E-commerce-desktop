/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.login;

import ecommerce.Mail;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.mail.MessagingException;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class Mot_de_passe_oublierController implements Initializable {

    Window window;
    @FXML
    private TextField email;
    @FXML
    private Label msg;
    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private Label grren;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnemail(ActionEvent event) throws SQLException, IOException, MessagingException {
        String pass = email.getText();
        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            String query = "select * from users WHERE email = ?";

            ps = cnx.prepareStatement(query);
            ps.setString(1, email.getText());

            rs = ps.executeQuery();

            if (rs.next()) {
                Random rand = new Random(); //instance of random class
                int upperbound = 100000;
                int int_random = rand.nextInt(upperbound);
                PreparedStatement pss;
                ResultSet rss;
                int xx = rs.getInt("id");
                String yy = "update   users set  code ='" + int_random + "' where id = '" + xx + "' ";
                pss = cnx.prepareStatement(yy);
                pss.execute();

                //   String x = rs.getString("code");
                grren.setVisible(true);
                grren.setText("nous avons envoyé votre code par e-mail");
                msg.setVisible(false);
//                motdepasseoublier a = new motdepasseoublier();
//                a.sendemailwelcom(pass, int_random, rs.getString("nom"));
                Mail mail = new Mail();
                mail.setupServerProperties();
                mail.draftEmail(pass, rs.getString("nom"), int_random);
                mail.sendEmail();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("confirmation");
                alert.setHeaderText("code");
                alert.setContentText("nous avons envoyé votre code par e-mail");
                if (alert.showAndWait().get() == ButtonType.OK) {

                }
                Stage stage = (Stage) email.getScene().getWindow();
                stage.close();
                Statics.current_user.setEmail(email.getText());
                Parent root = FXMLLoader.load(getClass().getResource("code.fxml"));

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle("code");
                stage.getIcons().add(new Image("/img/mm.png"));
                stage.show();

            } else {
                grren.setVisible(false);
                msg.setVisible(true);
                msg.setText("nous ne trouvons pas d'utilisateur avec cette adresse e-mail");

            }
        }
    }

    private boolean isValidated() {

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (email.getText().equals("")) {
            msg.setVisible(true);
            grren.setVisible(false);
            msg.setText("email text field cannot be blank.");
            email.requestFocus();

        } else if (!pattern.matcher(email.getText()).matches()) {
            msg.setVisible(true);
            grren.setVisible(false);
            msg.setText("email invalid");

            email.requestFocus();
        } else {
            return true;
        }
        return false;
    }

//    public void init() {
//
//        ObservableList<user> mList = FXCollections.observableArrayList(this.afficherRole());
//        combox.setItems(mList);
//        
//        
//    }
    @FXML
    private void home(ActionEvent event) throws IOException {
        Stage stage = (Stage) email.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/login/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("login");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }
}
