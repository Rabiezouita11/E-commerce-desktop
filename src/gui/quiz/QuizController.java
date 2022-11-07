/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Quiz;

import ecommerce.Myquiz;
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
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Quiz;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Notification;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class QuizController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private TextFlow fruitNameLable;
    @FXML
    private Label promotions;
    @FXML
    private Label date;
    @FXML
    private TextFlow txtarea;
    @FXML
    private RadioButton noter1;
    @FXML
    private ToggleGroup produit;
    @FXML
    private RadioButton noter2;
    @FXML
    private RadioButton noter3;
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
    private List<Quiz> fruits = new ArrayList<>();
    private Myquiz myListener;
    @FXML
    private Label msg2;
    @FXML
    private Button btnverifier;
    @FXML
    private Label incorrectanswer;
    @FXML
    private Label correctreponse;
    @FXML
    private Label reponse;
    @FXML
    private TextField idquiz;

    /**
     * Initializes the controller class.
     */
    private List<Quiz> getData() throws SQLException {
        List<Quiz> fruits = new ArrayList<>();
        Quiz fruit;
        String tt = "SELECT * FROM `quiz`";
        Statement statement;

        statement = cnx.createStatement();
        ResultSet queryoutput = statement.executeQuery(tt);

        while (queryoutput.next()) {

            fruit = new Quiz();
            fruit.setId(queryoutput.getInt("id"));
            fruit.setTitre_quiz(queryoutput.getString("titre_quiz"));
            fruit.setOption1(queryoutput.getString("option1"));
            fruit.setOption2(queryoutput.getString("option2"));
            fruit.setOption3(queryoutput.getString("option3"));
            fruit.setColor("B0E0E6");
            fruits.add(fruit);

        }

        return fruits;
    }

    private void setChosenFruit(Quiz fruit) {

        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        String id = null;
        try {
            id = new Jwt(aaa).getAudience();
        } catch (JSONException | IOException ex) {
            Logger.getLogger(QuizController.class.getName()).log(Level.SEVERE, null, ex);
        }

        PreparedStatement ps, ps2, ps3;
        ResultSet rs, rs2, rs3;

        String yy = "SELECT * FROM reponse WHERE id_user ='" + id + "'  and id_quiz= '" + fruit.getId() + "' ";
        String zz = "SELECT * FROM quiz WHERE id ='" + fruit.getId() + "'";
        //   String query = "select * from categories WHERE name = ?";

        try {

            ps = cnx.prepareStatement(yy);
            ps2 = cnx.prepareStatement(zz);
            rs2 = ps2.executeQuery();
            rs = ps.executeQuery();
            if (rs.next() && rs2.next()) {

                nomproduit.requestFocus();
                //   fruitNameLable.setVisible(false);
                noter1.setVisible(false);
                noter2.setVisible(false);
                noter3.setVisible(false);
                btnverifier.setVisible(false);
                //  chosenFruitCard.setVisible(false);

                String s1 = rs.getString("reponse");
                String s2 = rs2.getString("bonne_reponse");
                if (s1.equals(s2)) {
                    correctreponse.setVisible(true);
                    reponse.setVisible(true);

                    fruitNameLable.getChildren().clear();
                    Text t1 = new Text( fruit.getTitre_quiz());
                  
                      
                    fruitNameLable.getChildren().add(t1);
                    chosenFruitCard.setVisible(true);

                    reponse.setText("votre réponse est" + " " + s1);
                    correctreponse.setText("votre reponse est correcte");
                    incorrectanswer.setVisible(false);
                } else {
                    reponse.setVisible(true);
                    fruitNameLable.getChildren().clear();
                    Text t1 = new Text( fruit.getTitre_quiz());
                    fruitNameLable.getChildren().add(t1);
                    correctreponse.setVisible(false);
                    incorrectanswer.setVisible(true);
                    reponse.setText("votre réponse est" + " " + s1);
                    incorrectanswer.setText("votre reponse est incorrecte");

                }

            } else {
                incorrectanswer.setVisible(false);

                correctreponse.setVisible(false);
                reponse.setVisible(false);
                chosenFruitCard.setVisible(true);
                fruitNameLable.getChildren().clear();
                Text t1 = new Text( fruit.getTitre_quiz());
                   t1.setFont(Font.font("Helvetica", FontPosture.ITALIC, 20));
                fruitNameLable.getChildren().add(t1);

                noter1.setText(fruit.getOption1());
                noter2.setText(fruit.getOption2());
                noter3.setText(fruit.getOption3());
                fruitNameLable.setVisible(true);
                noter1.setVisible(true);
                noter2.setVisible(true);
                noter3.setVisible(true);
                btnverifier.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizController.class.getName()).log(Level.SEVERE, null, ex);
        }

        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n"
                + "    -fx-background-radius: 30;");

        idquiz.setText(String.valueOf(fruit.getId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        msg2.setVisible(false);
        try {
            fruits.addAll(getData());

            if (fruits.size() > 0) {
                setChosenFruit(fruits.get(0));

                myListener = this::setChosenFruit;
                int column = 0;
                int row = 1;
                try {
                    for (int i = 0; i < fruits.size(); i++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("../../gui/Quiz/item.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();

                        ItemController itemController = fxmlLoader.getController();
                        itemController.setData(fruits.get(i), myListener);

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

            } else {
                chosenFruitCard.setVisible(false);
                msg2.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
    private void noterproduit(ActionEvent event) {
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
    private void btnSignout(ActionEvent event) throws IOException {

        try {
            Preferences userPreferences = Preferences.userRoot();
            userPreferences.clear();

        } catch (BackingStoreException ex) {
            Logger.getLogger(AccueilClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();
     

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
    @FXML
    private void Vérifier(ActionEvent event) throws JSONException, IOException, SQLException {
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        String id = new Jwt(aaa).getAudience();
        if (this.isValidated()) {
            PreparedStatement ps, ps1, ps2, ps3;
            ResultSet rs, rs1, rs2;
            String yy = "INSERT INTO reponse(reponse,id_quiz,id_user) VALUES ( ?,?,?)";
            ps = cnx.prepareStatement(yy);

            if (noter1.isSelected()) {
                ps.setString(1, noter1.getText());
            } else if (noter2.isSelected()) {
                ps.setString(1, noter2.getText());
            } else if (noter3.isSelected()) {
                ps.setString(1, noter3.getText());
            }
            ps.setString(2, idquiz.getText());
            ps.setString(3, id);
            ps.execute();

            String aa = "SELECT * FROM reponse WHERE id_user ='" + id + "'  and id_quiz= '" + idquiz.getText() + "' ";
            String zz = "SELECT * FROM quiz WHERE id ='" + idquiz.getText() + "'";
            ps1 = cnx.prepareStatement(aa);
            ps2 = cnx.prepareStatement(zz);
            rs2 = ps2.executeQuery();
            rs = ps1.executeQuery();

            if (rs.next() && rs2.next()) {
                String s1 = rs.getString("reponse");
                String s2 = rs2.getString("bonne_reponse");
                if (s1.equals(s2)) {
                    String xx = "INSERT INTO coupon(prix,date_expiration,id_user) VALUES ( ?,?,?)";
                    ps3 = cnx.prepareStatement(xx);

                    Random rand = new Random(); //instance of random class
                    int upperbound = 1000;
                    int int_random = rand.nextInt(upperbound);

                    ps3.setInt(1, int_random);

                    LocalDate currentdDate1 = LocalDate.now();
                    System.out.println(currentdDate1);
                    LocalDate currentDatePlus1 = currentdDate1.plusDays(7);

                    ps3.setString(2, String.valueOf(currentDatePlus1));
                    ps3.setString(3, id);
                    ps3.execute();
                    Notification.notificationSuccess("vous avez ganger un coupon de  " + int_random + "TND", "jusqu'a" + " " + currentDatePlus1);
                } else {
                    Notification.notificationError("echec votre reponse est incorrectes");
                }
            } else {

            }

            // Notification.notificationSuccess("Vérifier avec succées", "");
            grid.getChildren().clear();
            fruits.clear();
            try {
                fruits.addAll(getData());

                if (fruits.size() > 0) {

                    setChosenFruit(fruits.get(0));

                    myListener = this::setChosenFruit;
                    int column = 0;
                    int row = 1;
                    try {
                        for (int i = 0; i < fruits.size(); i++) {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("../../gui/Quiz/item.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();

                            ItemController itemController = fxmlLoader.getController();
                            itemController.setData(fruits.get(i), myListener);

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

                } else {
                    chosenFruitCard.setVisible(false);
                    msg2.setVisible(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(QuizController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private boolean isValidated() {

        if (!(noter1.isSelected() || noter2.isSelected() || noter3.isSelected())) {

            Notification.notificationError("error you must select one reponse");

        } else {
            return true;
        }
        return false;
    }

}
