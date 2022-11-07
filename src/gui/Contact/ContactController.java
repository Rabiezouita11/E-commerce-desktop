/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Contact;

import Interface.interContact;
import dao.daoContact;
import gui.Panier.PanierController;
import gui.Profile.ProfileController;
import gui.homeClient.AccueilClientController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import static javax.swing.JOptionPane.showMessageDialog;
import model.Contact;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-006
 */
public class ContactController implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private Label labelnom;
    @FXML
    private Button btnAccueil;
    @FXML
    private Button btnAboutus;
    @FXML
    private Button btnContact;
    @FXML
    private Button idcat;
    @FXML
    private Button btnprofile;
    @FXML
    private Button btnSignout;
    @FXML
    private Button register;
    @FXML
    private Button login;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView img;
    @FXML
    private Button totalpanier;
    @FXML
    private TextField email;
    @FXML
    private TextArea message;
    @FXML
    private TextField txtnom;
    @FXML
    private WebView webview;
    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private StackPane xx;
    @FXML
    private HBox avishbox;
    @FXML
    private Button avis;
    @FXML
    private HBox profilehbox;
    @FXML
    private HBox histbox;
    @FXML
    private Button historique;
    @FXML
    private HBox quizhbox;
    @FXML
    private Button quiz;
    @FXML
    private HBox adresshbox;
    @FXML
    private Button btnadresse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LocalDate currentdDate1 = LocalDate.now();

        PreparedStatement ps, ps2;
        ResultSet rs;
        String query = "select * from users WHERE id = ? ";

        try {
            ps = cnx.prepareStatement(query);
            ps.setInt(1, Statics.current_user.getId());

            rs = ps.executeQuery();

            if (rs.next()) {
                String s1 = rs.getString("date_supprimer_compte");
                if (s1 == null) {

                } else if ("null".equals(s1)) {

                } else if (s1.equals(currentdDate1.toString())) {

                    PreparedStatement psxx;
                    ResultSet rsxx;

                    String yy = "delete   from  users where id = '" + Statics.current_user.getId() + "' ";
                    psxx = cnx.prepareStatement(yy);
                    psxx.execute();

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("supprimer");

                    alert.setContentText("compte supprimer see you ! ");
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        Statics.current_user.setNom(null);

                    }

                } else {

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

        WebEngine e = webview.getEngine();
        e.load("https://google.com/maps/d/u/0/edit?mid=1m8THNBCN9JB_hbYFqdnGD1Cq2YTsgt0&ll=36.178255973736256%2C8.709290100000002&z=18");

        FadeTransition fade = new FadeTransition();
        fade.setNode(logo);
        fade.setDuration(Duration.millis(3000));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        try {
            if (aaa.equals("root")) {
                labelnom.setVisible(false);
                btnSignout.setVisible(false);
                btnprofile.setVisible(false);
                historique.setVisible(false);
                quiz.setVisible(false);
                avis.setVisible(false);

                avishbox.setVisible(false);
                profilehbox.setVisible(false);
                histbox.setVisible(false);
                quizhbox.setVisible(false);
                adresshbox.setVisible(false);

                btnadresse.setVisible(false);

            } else {
                String id = new Jwt(aaa).getAudience();
                String tt = "SELECT * FROM `users` where id ='" + id + "' ";

                Statement statement;

                statement = cnx.createStatement();
                ResultSet queryoutput = statement.executeQuery(tt);
                while (queryoutput.next()) {

                    String x = queryoutput.getString("nom");
                    String y = queryoutput.getString("image");
                    String z = queryoutput.getString("email");
                    labelnom.setText("welcome " + x);

                    txtnom.setText(x);
                    email.setText(z);
                    Image image = new Image("file:" + y);
                    this.image.setImage(image);
                }
                try {
                    totalpanier.setText(String.valueOf(refreshpanier()));
                    System.out.println(refreshpanier());
                } catch (SQLException ex) {
                    Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                }

                btnadresse.setVisible(true);

                register.setVisible(false);
                login.setVisible(false);
                btnSignout.setVisible(true);
                btnprofile.setVisible(true);
                labelnom.setVisible(true);
                historique.setVisible(true);
                quiz.setVisible(true);
                avis.setVisible(true);

            }

        } catch (JSONException ex) {
            Logger.getLogger(AccueilClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccueilClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccueilClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void Accueil(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("../../gui/homeClient/accueilClient.fxml"));

        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setMaxHeight(834);
        stage.setMaxWidth(1050);

        stage.setScene(scene);
        stage.setTitle("Accueil");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

    public Integer refreshpanier() throws SQLException, JSONException, IOException {
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        String id = new Jwt(aaa).getAudience();
        int x = 0;
        Statement stmt = cnx.createStatement();
        String query = "select SUM(quantite) from panier where id_user='" + id + "'";

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        x = rs.getInt(1);
        return x;

    }

    @FXML
    private void about(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/About/About.fxml"));

        Scene scene = new Scene(root);

        stage.centerOnScreen();
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setMaxHeight(900);
        stage.setMaxWidth(1600);

        stage.setScene(scene);
        stage.setTitle("About");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

     @FXML
    private void contact(ActionEvent event) throws IOException {
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");

        if (aaa.equals("root")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("login");
            alert.setHeaderText("login required!");
            alert.setContentText("you must login  ");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Stage stage = (Stage) register.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("../../gui/login/Login.fxml"));

                Scene scene = new Scene(root);
                stage.setMaxHeight(500);

                stage.setMaxWidth(600);
                stage.setScene(scene);
                stage.setResizable(true);

                stage.setTitle("Login");
                //
                stage.show();

            }
        } else {
            Stage stage = (Stage) register.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("../../gui/Contact/contact.fxml"));

            Scene scene = new Scene(root);

            stage.centerOnScreen();

            stage.setScene(scene);
            stage.setResizable(true);

            stage.setTitle("Contact");
            //
            stage.show();
        }

    }


 @FXML
    private void categorie(ActionEvent event) throws IOException {

        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Categorie_Client/Categorie_Client.fxml"));

        Scene scene = new Scene(root);
         stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setMaxHeight(1100);
        stage.setMaxWidth(1600);
        stage.setScene(scene);
        // stage.setFullScreen(true);
   

        stage.setTitle("Produits");
        //
        stage.show();
    }
   @FXML
    private void Profile(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Profile/Profile.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Profile");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

    @FXML
    private void btnSignout(ActionEvent event) throws IOException {
        try {
            Preferences userPreferences = Preferences.userRoot();
            userPreferences.clear();

        } catch (BackingStoreException ex) {
            Logger.getLogger(AccueilClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();
        Statics.current_user.setNom(null);

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/homeClient/accueilClient.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Accueil");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();

    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/login/Login.fxml"));

        Scene scene = new Scene(root);
        stage.setMaxHeight(500);

        stage.setMaxWidth(600);
        stage.setScene(scene);
        stage.setResizable(true);

        stage.setTitle("Login");
        //
        stage.show();
    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/register/Register.fxml"));

        Scene scene = new Scene(root);
        stage.setMaxHeight(700);
        stage.setMaxWidth(600);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("register");
        //
        stage.show();
    }

   
    @FXML
    private void envoyer(ActionEvent event) {
        if (this.isValidated()) {
            Contact aa = new Contact();
            aa.setNom(txtnom.getText());
            aa.setEmail(email.getText());
            aa.setMessage(message.getText());
            interContact xx = daoContact.getInstance();
            xx.AjouterContact(aa);
            showMessageDialog(null, "Message envoyer avec succes");
            message.clear();
        }

    }

    private boolean isValidated() {

        String regex = "^(.+)@(.+)$";
        String number = "[0-9]+";
        Pattern x = Pattern.compile(number);
        Pattern pattern = Pattern.compile(regex);

        if (txtnom.getText().equals("")) {

            showMessageDialog(null, "nom text field cannot be blank.");
            txtnom.requestFocus();

        } else if (email.getText().equals("")) {

            showMessageDialog(null, "email text field cannot be blank.");
            email.requestFocus();
        } else if (email.getText().equals("")) {

            showMessageDialog(null, "email text field cannot be blank.");
            email.requestFocus();

        } else if (!pattern.matcher(email.getText()).matches()) {

            showMessageDialog(null, "email invalid");
            email.requestFocus();
        } else if (message.getText().equals("")) {

            showMessageDialog(null, "message password text field cannot be blank.");
            message.requestFocus();

        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void showpanier(MouseEvent event) throws IOException {
              
           Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        if (aaa.equals("root")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("login");
            alert.setHeaderText("login required!");
            alert.setContentText("you must login  ");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Stage stage = (Stage) register.getScene().getWindow();
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
        } else {
           Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Panier/Panier.fxml"));

        Scene scene = new Scene(root);
        stage.setMaxHeight(1000);
        stage.setMaxWidth(1500);
        stage.setScene(scene);
        stage.setResizable(true);

        stage.setTitle("Panier");
        //
        stage.show();

        }

    }

      @FXML
    private void avis(ActionEvent event) throws IOException {
         Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");

        if (aaa.equals("root")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("login");
            alert.setHeaderText("login required!");
            alert.setContentText("you must login  ");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Stage stage = (Stage) register.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("../../gui/login/Login.fxml"));

                Scene scene = new Scene(root);
                stage.setMaxHeight(500);

                stage.setMaxWidth(600);
                stage.setScene(scene);
                stage.setResizable(true);

                stage.setTitle("Login");
                //
                stage.show();

            }
        } else {
         Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Avis/Avis.fxml"));

        Scene scene = new Scene(root);

        stage.centerOnScreen();
        stage.setMaxHeight(810);
        stage.setMaxWidth(1408);
        stage.setResizable(true);
        stage.setScene(scene);

        stage.setTitle("Avis");
        //
        stage.show();
        }
    }


      @FXML
    private void Historique(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Historique/Historique.fxml"));

        Scene scene = new Scene(root);
         stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setMaxHeight(1100);
        stage.setMaxWidth(1600);
        stage.setScene(scene);
        // stage.setFullScreen(true);
   

        stage.setTitle("Historique");
        //
        stage.show();
    }


      @FXML
    private void quiz(ActionEvent event) throws IOException {
           Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Quiz/Quiz.fxml"));

        Scene scene = new Scene(root);
         stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setMaxHeight(1100);
        stage.setMaxWidth(1600);
        stage.setScene(scene);
        // stage.setFullScreen(true);
   

        stage.setTitle("Quiz");
        //
        stage.show();
    }
   @FXML
    private void adresse(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Adresse/Adresse.fxml"));

        Scene scene = new Scene(root);

        stage.centerOnScreen();
        stage.setMaxHeight(810);
        stage.setMaxWidth(1408);
        stage.setResizable(true);
        stage.setScene(scene);

        stage.setTitle("Adresse");
        //
        stage.show();
    }
    @FXML
    private void totalpanier(ActionEvent event) {
    }

}
