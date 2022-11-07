/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.homeAdmin;

import gui.homeClient.AccueilClientController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import static javax.swing.JOptionPane.showMessageDialog;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class homeadminController implements Initializable {

    @FXML
    private Label nom;
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private Label welcome;
    @FXML
    private Label totalclient;
    @FXML
    private Label totalproduit;
    @FXML
    private Label totalcommande;
    @FXML
    private Label totalcontact;
    @FXML
    private Label totallivreur;
    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private ImageView image;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSettings1;
    @FXML
    private Button btnCustomers11;
    @FXML
    private Button btnOverview1;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            totalclient.setText(String.valueOf(showusers()));
            totalproduit.setText(String.valueOf(showproduit()));
            totalcommande.setText(String.valueOf(showcommande()));
            totalcontact.setText(String.valueOf(showcontact()));
            totallivreur.setText(String.valueOf(showquiz()));

            // totalclient.setText(afficherRole().toString());
        } catch (SQLException ex) {
            Logger.getLogger(homeadminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");

        if (aaa.equals("root")) {

        } else {

            try {
                String id = new Jwt(aaa).getAudience();
                String tt = "SELECT * FROM `users` where id ='" + id + "' ";

                Statement statement;

                try {
                    statement = cnx.createStatement();
                    ResultSet queryoutput = statement.executeQuery(tt);
                    while (queryoutput.next()) {

                        String x = queryoutput.getString("nom");
                        String y = queryoutput.getString("image");
                        nom.setText("welcome " + x);

                        // String path = Statics.current_user.getImage();
                        Image image = new Image("file:" + y);
                        this.image.setImage(image);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(homeadminController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (JSONException ex) {
                Logger.getLogger(homeadminController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(homeadminController.class.getName()).log(Level.SEVERE, null, ex);
            }

//            if (Statics.current_user.getNom() == null) {
//                nom.setText("erreur");
//            } else {
//                nom.setText(Statics.current_user.getNom());
//                welcome.setText("Welcome back " + Statics.current_user.getNom());
//
////         input = getBinaryStream( Statics.current_user.getImage());
////                        Image imge = new Image(input);
////                      
//                //System.out.println(imge);
//                //        System.out.println(Statics.current_user.getImage());
////               String path = idimage.getCellData(index);
////       
////        this.img.setImage(image);
//                String path = Statics.current_user.getImage();
//                Image image = new Image("file:" + path);
//                this.image.setImage(image);
//           String img = Statics.current_user.getImage();
//            Image imge = new Image(img);
//               image.setImage(imge);
            //   image.setImage(imge);
            // image.setImage(Statics.current_user.getImage());
        }

    }

    public Integer showusers() throws SQLException {
        int count = 0;
        Statement stmt = cnx.createStatement();
        String query = "select count(*) from users where role='Client'";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        count = rs.getInt(1);
        return count;

    }

    public Integer showproduit() throws SQLException {
        int count = 0;
        Statement stmt = cnx.createStatement();
        String query = "select count(*) from produits ";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        count = rs.getInt(1);
        return count;

    }

    public Integer showcommande() throws SQLException {
        int count = 0;
        Statement stmt = cnx.createStatement();
        String query = "select count(*) from commandes ";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        count = rs.getInt(1);
        return count;

    }

    public Integer showcontact() throws SQLException {
        int count = 0;
        Statement stmt = cnx.createStatement();
        String query = "select count(*) from contacts ";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        count = rs.getInt(1);
        return count;

    }

    public Integer showquiz() throws SQLException {
        int count = 0;
        Statement stmt = cnx.createStatement();
        String query = "select count(*) from quiz ";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        count = rs.getInt(1);
        return count;

    }

    @FXML
    private void Accueil(ActionEvent event) throws IOException {
          Stage stage = (Stage) btnOverview.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/homeAdmin/homeadmin.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Accueil");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

    @FXML
    private void categorie(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnOverview.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/categorie/Categorie.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Categorie");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();

    }

    @FXML
    private void Produit(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnOverview.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Produit/Produit.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Produit");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();

    }

    @FXML
    private void btnSignout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to lgout!");
        alert.setContentText("Do you want to delete ");
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                Preferences userPreferences = Preferences.userRoot();
                userPreferences.clear();

            } catch (BackingStoreException ex) {
                Logger.getLogger(AccueilClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = (Stage) btnOverview.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("../../gui/login/Login.fxml"));

            Scene scene = new Scene(root);
            stage.setMaxHeight(500);
            stage.centerOnScreen();
            stage.setMaxWidth(600);
            stage.setScene(scene);
            stage.setResizable(true);

            stage.setTitle("Login");
            //
            stage.show();

        }
    }

    @FXML
    private void Client(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnOverview.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Client/Client.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Client");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();

    }

    @FXML
    private void Commande(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnOverview.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/CommandeAdmin/CommandeAdmin.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Commande");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

    @FXML
    private void Livreur(ActionEvent event) {
    }

    @FXML
    private void Contact(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnOverview.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/ContactAdmin/ContactAdmin.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Contact");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

    @FXML
    private void Promotion(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnOverview.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Promotion/Promotion.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Promotion");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

    @FXML
    private void Quiz(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnOverview.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/QuizAdmin/QuizAdmin.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Quiz");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();

    }

}
