/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Profile;

import Interface.InterCategorie;
import dao.daocategorie;
import gui.Panier.PanierController;
import static gui.Produit.ProduitController.randomStringforimage;
import gui.homeClient.AccueilClientController;
import static gui.register.RegisterController.getHash;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-006
 */
public class ProfileController implements Initializable {

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
    private Button avis;
    @FXML
    private Button btnprofile;
    @FXML
    private Button historique;
    @FXML
    private Button quiz;
    private Button panier;
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
    private ImageView imageprofile;
    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtprenom;
    @FXML
    private TextField txtcin;
    @FXML
    private TextField txtemail;
    String filename = null;
    File xxx = null;
    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private Button btnsupprimer;
    @FXML
    private Button btnannulersupprimer;
    @FXML
    private Label showmsg;
    @FXML
    private StackPane xx;
    @FXML
    private HBox hbox;
    @FXML
    private HBox avishbox;
    @FXML
    private HBox profilehbox;
    @FXML
    private HBox histbox;
    @FXML
    private HBox quizhbox;
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
        //System.out.println(currentdDate1);
        FadeTransition fade = new FadeTransition();
        fade.setNode(logo);
        fade.setDuration(Duration.millis(3000));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        Preferences userPreferences = Preferences.userRoot();
        String xxxx = userPreferences.get("jwt", "root");
        try {
            if (xxxx.equals("root")) {
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
                String id = new Jwt(xxxx).getAudience();
                String tt = "SELECT * FROM `users` where id ='" + id + "' ";

                Statement statement;

                statement = cnx.createStatement();
                ResultSet queryoutput = statement.executeQuery(tt);
                while (queryoutput.next()) {
                    String a = queryoutput.getString("prenom");
                    String x = queryoutput.getString("nom");
                    String y = queryoutput.getString("image");
                    String z = queryoutput.getString("cin");
                    String e = queryoutput.getString("email");
                    labelnom.setText("welcome " + x);

                    Image image = new Image("file:" + y);
                    this.image.setImage(image);

                    txtnom.setText(x);
                    txtemail.setText(e);
                    txtprenom.setText(a);
                    txtcin.setText(z);
                    this.imageprofile.setImage(image);
                }
                try {
                    totalpanier.setText(String.valueOf(refreshpanier()));
                    // System.out.println(refreshpanier());
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

                PreparedStatement ps, ps2;
                ResultSet rs;
                String query = "select * from users WHERE id = ? ";

                try {
                    ps = cnx.prepareStatement(query);
                    ps.setString(1, id);

                    rs = ps.executeQuery();

                    if (rs.next()) {
                        String s1 = rs.getString("date_supprimer_compte");
                        if (s1 == null) {
                            btnsupprimer.setVisible(true);
                            btnannulersupprimer.setVisible(false);
                        } else if ("null".equals(s1)) {

                            btnsupprimer.setVisible(true);
                            btnannulersupprimer.setVisible(false);
                        } else if (s1.equals(currentdDate1.toString())) {

                            PreparedStatement psxx;
                            ResultSet rsxx;

                            String yy = "delete   from  users where id = '" + id + "' ";
                            psxx = cnx.prepareStatement(yy);
                            psxx.execute();
                            Statics.current_user.setNom(null);

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("supprimer");

                            alert.setContentText("compte supprimer see you ! ");
                            if (alert.showAndWait().get() == ButtonType.OK) {

                            }

                        } else {
                            btnsupprimer.setVisible(false);
                            btnannulersupprimer.setVisible(true);
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String strDate = sdf.format(cal.getTime());

                            SimpleDateFormat aaa = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date dateAvant = aaa.parse(strDate);
                                Date dateApres = aaa.parse(s1);
                                long diff = dateApres.getTime() - dateAvant.getTime();
                                float res = (diff / (1000 * 60 * 60 * 24));
                                //  System.out.println("Nombre de jours entre les deux dates est: " + res);
                                showmsg.setText("il vous reste " + String.valueOf(res) + " " + "jours jusqu'a la suppression finale de compte ");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (JSONException ex) {
            Logger.getLogger(AccueilClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccueilClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccueilClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void checkuser() {
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
                    String a = queryoutput.getString("prenom");
                    String x = queryoutput.getString("nom");
                    String y = queryoutput.getString("image");
                    String z = queryoutput.getString("cin");
                    String e = queryoutput.getString("email");
                    labelnom.setText("welcome " + x);

                    Image image = new Image("file:" + y);
                    this.image.setImage(image);

                    txtnom.setText(x);
                    txtemail.setText(e);
                    txtprenom.setText(a);
                    txtcin.setText(z);
                    this.imageprofile.setImage(image);
                }
                try {
                    totalpanier.setText(String.valueOf(refreshpanier()));
                    // System.out.println(refreshpanier());
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

//        if (Statics.current_user.getNom() == null) {
//            labelnom.setVisible(false);
//            btnSignout.setVisible(false);
//            btnprofile.setVisible(false);
//            historique.setVisible(false);
//            quiz.setVisible(false);
//            avis.setVisible(false);
//            panier.setVisible(false);
//        } else {
//            panier.setVisible(true);
//            register.setVisible(false);
//            login.setVisible(false);
//            btnSignout.setVisible(true);
//            btnprofile.setVisible(true);
//            labelnom.setVisible(true);
//            historique.setVisible(true);
//            quiz.setVisible(true);
//            avis.setVisible(true);
//            labelnom.setText("welcome " + Statics.current_user.getNom());
//            String path = Statics.current_user.getImage();
//            Image image = new Image("file:" + path);
//            this.image.setImage(image);
//
//            txtnom.setText(Statics.current_user.getNom());
//            txtemail.setText(Statics.current_user.getEmail());
//            txtprenom.setText(Statics.current_user.getPrenom());
//            txtcin.setText(String.valueOf(Statics.current_user.getCin()));
//            this.imageprofile.setImage(image);
//
//        }
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

    @FXML
    private void about(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();
        Statics.current_user.setNom(null);

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/About/About.fxml"));

        Scene scene = new Scene(root);
        stage.setMaxHeight(1000);
        stage.setMaxWidth(1500);

        stage.setScene(scene);
        stage.setTitle("About");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

    @FXML
    private void contact(ActionEvent event) throws IOException {
        if (Statics.current_user.getNom() == null) {
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
    private void btnSignout(ActionEvent event) throws IOException, BackingStoreException {

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
    private void totalpanier(ActionEvent event) throws IOException {
        if (Statics.current_user.getNom() == null) {
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
            System.out.println("bb");
        }

    }

    @FXML
    private void uploid_image(ActionEvent event) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fd = new FileNameExtensionFilter("PNG JPG", "png", "jpg");
        chooser.addChoosableFileFilter(fd);

        int response = chooser.showOpenDialog(null); //select file to open
        //int response = fileChooser.showSaveDialog(null); //select file to save

        if (response == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (fd.accept(f)) {
                filename = f.getAbsolutePath();

                String newpath = "uploids/profile/";
                File dir = new File(newpath);
                if (!dir.exists()) {
                    // folder wa7dd ken barchaa mkdirs
                    dir.mkdirs();
                }
                File sourceFile = null;
                File destinationFile = null;
                String extension = filename.substring(filename.lastIndexOf('.') + 1);
                sourceFile = new File(filename);
                xxx = new File(newpath + randomStringforimage() + "." + extension);
                Files.copy(sourceFile.toPath(), xxx.toPath());
                //   System.out.println(destinationFile);
            } else {
                showMessageDialog(null, "invalid extension");
            }

        } else {
            showMessageDialog(null, "you must select photo");
        }
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException, IOException {

        Preferences userPreferences = Preferences.userRoot();
        String xxxx = userPreferences.get("jwt", "root");

        if (xxxx.equals("root")) {

        } else {
            String id = new Jwt(xxxx).getAudience();

            LocalDate currentdDate1 = LocalDate.now();
            System.out.println(currentdDate1);
            LocalDate currentDatePlus1 = currentdDate1.plusDays(15);
            System.out.println("Adding 1 day to current date: " + currentDatePlus1);

            PreparedStatement ps;
            ResultSet rs;

            //  Integer xx = Statics.current_user.getId();
            String yy = "update   users set   date_supprimer_compte ='" + currentDatePlus1 + "'  where id = '" + id + "' ";

            ps = cnx.prepareStatement(yy);
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("supprimer compte ");

            alert.setContentText("la supression sera confirmer apres 15 jours vous pouvez l'annuler a tout moment ");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Stage stage = (Stage) register.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("../../gui/Profile/Profile.fxml"));

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle("Profile");
                stage.getIcons().add(new Image("/img/mm.png"));
                stage.show();
            }
        }
        // end 
        // start

//        
//        PreparedStatement ps;
//        ResultSet rs;
//
//        String yy = "delete   from  users where id = '" + Statics.current_user.getId() + "' ";
//        ps = cnx.prepareStatement(yy);
//        ps.execute();
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Supprimer compte");
//        alert.setHeaderText("You're about to delete compte!");
//       
//        if (alert.showAndWait().get() == ButtonType.OK) {
//            
//            
//             showMessageDialog(null, "compte supprimer avec succes");
//            
//            Stage stage = (Stage) register.getScene().getWindow();
//            stage.close();
//
//            Parent root = FXMLLoader.load(getClass().getResource("../../gui/login/Login.fxml"));
//
//            Scene scene = new Scene(root);
//            stage.setMaxHeight(500);
//
//            stage.setMaxWidth(600);
//            stage.setScene(scene);
//            stage.setResizable(true);
//
//            stage.setTitle("Login");
//            //
//            stage.show();
//        }
    }

    @FXML
    private void modifier(ActionEvent event) throws SQLException, IOException {

        Preferences userPreferences = Preferences.userRoot();
        String xxxx = userPreferences.get("jwt", "root");

        if (xxxx.equals("root")) {

        } else {
            String id = new Jwt(xxxx).getAudience();

            if (this.isValidated()) {

                PreparedStatement cat;
                ResultSet rs2;

                String tt = "SELECT * FROM `users` where id ='" + id + "' ";

                Statement statement;

                statement = cnx.createStatement();
                ResultSet queryoutput = statement.executeQuery(tt);
                while (queryoutput.next()) {

                    String y = queryoutput.getString("image");

                   

                    PreparedStatement ps;
                    ResultSet rs;

                    //   Integer xx = Statics.current_user.getId();
                    String yy = "update   users set  nom ='" + txtnom.getText() + "' , image =? , prenom ='" + txtprenom.getText() + "', cin ='" + txtcin.getText() + "' , email ='" + txtemail.getText() + "'  where id = '" + id + "' ";

                    ps = cnx.prepareStatement(yy);

                    if (xxx == null) {
                        ps.setString(1, String.valueOf(y));
                        ps.execute();
                    } else {
                        ps.setString(1, String.valueOf(xxx));
                        ps.execute();
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login ");
                    alert.setHeaderText("login required!");
                    alert.setContentText("profile modifier avec succes , veuillez réconnecter de nouveau");
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
                }
            }
        }
    }

    private boolean isValidated() {

        String regex = "^(.+)@(.+)$";
        String number = "[0-9]+";
        Pattern x = Pattern.compile(number);
        Pattern pattern = Pattern.compile(regex);

        if (txtcin.getText().equals("")) {

            showMessageDialog(null, "cin text field cannot be blank.");
            txtcin.requestFocus();

        } else if (txtemail.getText().equals("")) {

            showMessageDialog(null, "email text field cannot be blank.");
            txtemail.requestFocus();

        } else if (txtnom.getText().equals("")) {

            showMessageDialog(null, "nom  text field cannot be blank.");
            txtnom.requestFocus();

        } else if (txtprenom.getText().equals("")) {

            showMessageDialog(null, "prenom text field cannot be blank.");
            txtprenom.requestFocus();

        } else if (!pattern.matcher(txtemail.getText()).matches()) {

            showMessageDialog(null, "email invalid");
            txtemail.requestFocus();

        } else if (!x.matcher(txtcin.getText()).matches()) {
            showMessageDialog(null, "cin contains only number.");
            txtcin.requestFocus();
        } else if (x.matcher(txtnom.getText()).matches()) {
            showMessageDialog(null, "nom contains only text.");
            txtnom.requestFocus();
        } else if (x.matcher(txtprenom.getText()).matches()) {
            showMessageDialog(null, "prenom contains only text.");
            txtprenom.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void changer_mot_de_passe(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("Changer_mot_de_passe.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaxHeight(500);

        stage.setMaxWidth(600);
        stage.setTitle("Changer mot de passe");
        //
        stage.show();
    }

    @FXML
    private void annulersupprimer(ActionEvent event) throws SQLException, IOException {

        
         Preferences userPreferences = Preferences.userRoot();
        String xxxx = userPreferences.get("jwt", "root");

        if (xxxx.equals("root")) {

        } else {
            String id = new Jwt(xxxx).getAudience();

        
        
        
        String xxx = null;
        PreparedStatement ps;
        ResultSet rs;

      //  Integer xx = Statics.current_user.getId();

        String yy = "update   users set   date_supprimer_compte ='" + null + "'  where id = '" + id + "' ";

        ps = cnx.prepareStatement(yy);
        ps.execute();
        showMessageDialog(null, "annulation de la suppression du compte!");
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Profile/Profile.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Profile");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();

    }
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


}
