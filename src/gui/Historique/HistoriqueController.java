/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Historique;

import Interface.InterAdresse;
import dao.daoadresse;
import ecommerce.MyHistorique;
import gui.Adresse.AdresseController;
import gui.Adresse.ItemAdresseController;
import gui.Categorie_Client.Categorie_ClientController;
import gui.Panier.PanierController;
import gui.homeClient.AccueilClientController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Adresse;
import model.Historique;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class HistoriqueController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label showstock;
    @FXML
    private TextField quantite;
    @FXML
    private TextField idproduit;
    @FXML
    private TextField txtimage;
    @FXML
    private TextField txtcat;
    @FXML
    private TextField nomproduit;
    @FXML
    private TextField txtdes;
    @FXML
    private TextField prix;
    @FXML
    private ImageView img;
    @FXML
    private Button totalpanier;
    @FXML
    private Button register;
    @FXML
    private Button login;
    @FXML
    private Label labelnom;
    @FXML
    private Button btnSignout;
    @FXML
    private ImageView image;
    @FXML
    private Button btnAboutus;
    @FXML
    private Button btnContact;
    @FXML
    private Button idcat;
    @FXML
    private HBox avishbox;
    @FXML
    private Button avis;
    @FXML
    private HBox profilehbox;
    @FXML
    private Button btnprofile;
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
    @FXML
    private Button btnAccueil;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    Connection cnx = MaConnexion.getInstance().getCnx();
    private List<Historique> hist = new ArrayList<>();
    @FXML
    private TextField txtproduit;
    @FXML
    private TextField txtprixtotale;
    @FXML
    private TextField txtetat;
    @FXML
    private TextField txtdate;
    private MyHistorique myListener;

    /**
     * Initializes the controller class.
     */
    private List<Historique> getData() throws SQLException, JSONException, IOException {
        List<Historique> hist = new ArrayList<>();
        Historique histt;
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");

        String id = new Jwt(aaa).getAudience();
        String tt = "SELECT * FROM `commandes`  where id_user ='" + id + "' ";

        Statement statement;

        statement = cnx.createStatement();
        ResultSet queryoutput = statement.executeQuery(tt);
        while (queryoutput.next()) {
            histt = new Historique();
            InterAdresse aa = daoadresse.getInstance();
            histt.setId(queryoutput.getInt("id"));
            histt.setProduit(queryoutput.getString("produit"));
            histt.setEtat_commande(queryoutput.getString("etat_commande"));
            histt.setTotal_prix(Integer.valueOf(queryoutput.getString("total_prix")));

            histt.setDate(queryoutput.getString("created"));
            hist.add(histt);

        }

        return hist;
    }

    private void setChosenFruit(Historique hist) {
        txtproduit.setText(hist.getProduit());
        txtprixtotale.setText(String.valueOf(hist.getTotal_prix()));
        txtdate.setText(hist.getDate());
        txtetat.setText(hist.getEtat_commande());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            hist.addAll(getData());

            if (hist.size() == 0) {
                chosenFruitCard.setVisible(false);
            }
            if (hist.size() > 0) {
                setChosenFruit(hist.get(0));

                myListener = this::setChosenFruit;

            }
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < hist.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../../gui/Historique/item.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(hist.get(i), myListener);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column++, row); //(child,column,row)
                    //set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(AdresseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdresseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        if (aaa.equals("root")) {

        } else {
            try {
                String id = new Jwt(aaa).getAudience();
                LocalDate currentdDate1 = LocalDate.now();

                PreparedStatement ps, ps2;
                ResultSet rs;
                String query = "select * from users WHERE id = ? ";

                ps = cnx.prepareStatement(query);
                ps.setString(1, id);

                rs = ps.executeQuery();

                if (rs.next()) {
                    String s1 = rs.getString("date_supprimer_compte");
                    if (s1 == null) {

                    } else if ("null".equals(s1)) {

                    } else if (s1.equals(currentdDate1.toString())) {

                        PreparedStatement psxx;
                        ResultSet rsxx;

                        String yy = "delete   from  users where id = '" + id + "' ";
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
                Logger.getLogger(AdresseController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(AdresseController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AdresseController.class.getName()).log(Level.SEVERE, null, ex);
            }

            FadeTransition fade = new FadeTransition();
            fade.setNode(logo);
            fade.setDuration(Duration.millis(3000));
            fade.setCycleCount(TranslateTransition.INDEFINITE);
            fade.setInterpolator(Interpolator.LINEAR);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

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
                        labelnom.setText("welcome " + x);

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
    }

    public Integer refreshpanier() throws SQLException, JSONException, IOException {
        Integer a = null;
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        if (aaa.equals("root")) {

        } else {
            String id = new Jwt(aaa).getAudience();
            int x = 0;
            Statement stmt = cnx.createStatement();
            String query = "select SUM(quantite) from panier where id_user='" + id + "'";

            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            a = rs.getInt(1);
            return a;
        }
        return a;
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
    private void totalpanier(ActionEvent event) {
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
    private void Accueil(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/homeClient/accueilClient.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Accueil");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }


}
