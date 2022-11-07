/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecommerce;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import static javax.swing.JOptionPane.showMessageDialog;
import org.json.JSONException;
import org.json.JSONObject;
import util.Jwt;
import util.MaConnexion;
import util.Notification;

/**
 *
 * @author bouden
 */
public class main extends Application {

    Connection cnx = MaConnexion.getInstance().getCnx();
    public static final String CURRENCY = "TND";

    @Override
    public void start(Stage primaryStage) throws JSONException, InvalidKeyException, IOException {
        System.out.println(cnx);

        if (cnx == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Connexion");
            alert.setHeaderText("Connexion erreur");
            Notification.notificationError("Connexion erreur");
            if (alert.showAndWait().get() == ButtonType.OK) {

            }

        } else {

            Preferences userPreferences = Preferences.userRoot();
            String aaa = userPreferences.get("jwt", "root");
            // System.out.println(aaa);
            if (aaa.equals("root")) {
                Parent root = FXMLLoader.load(getClass().getResource("../gui/homeClient/accueilClient.fxml"));
                Scene scene = new Scene(root);
                primaryStage.setTitle("Accueil");

                primaryStage.getIcons().add(new Image("/img/mm.png"));
                primaryStage.setScene(scene);

                primaryStage.show();

            } else {
                String id = new Jwt(aaa).getAudience();
                String role = new Jwt(aaa).getSubject();
                //   System.out.println(aaa);
                if (role.equals("Client")) {

                    //   System.out.println(id);
                    //  System.out.println(role);
                    Parent root = FXMLLoader.load(getClass().getResource("../gui/homeClient/accueilClient.fxml"));
                    Scene scene = new Scene(root);
                    primaryStage.setTitle("Accueil");

                    primaryStage.getIcons().add(new Image("/img/mm.png"));
                    primaryStage.setScene(scene);

                    primaryStage.show();

                } else {

                    Parent root = FXMLLoader.load(getClass().getResource("../gui/homeAdmin/homeadmin.fxml"));
                    Scene scene = new Scene(root);
                    primaryStage.setTitle("Admin");

                    primaryStage.getIcons().add(new Image("/img/mm.png"));
                    primaryStage.setScene(scene);

                    primaryStage.show();

                }
                // Parent root = FXMLLoader.load(getClass().getResource("../gui/homeAdmin/homeadmin.fxml"));
                // Parent root = FXMLLoader.load(getClass().getResource("../gui/login/Login.fxml"));
                //  Parent root = FXMLLoader.load(getClass().getResource("../gui/Produit/Produit.fxml"));
                //  Parent root = FXMLLoader.load(getClass().getResource("../gui/homeClient/accueilClient.fxml"));
                // Parent root = FXMLLoader.load(getClass().getResource("../gui/Categorie_Client/Categorie_Client.fxml"));

            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
