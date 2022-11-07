/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.QuizAdmin;

import Interface.InterCategorie;
import dao.daocategorie;
import gui.homeAdmin.homeadminController;
import gui.homeClient.AccueilClientController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static javax.swing.JOptionPane.showMessageDialog;
import model.Categories;
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
public class QuizAdminController implements Initializable {

    int index = -1;
    @FXML
    private ImageView image;
    @FXML
    private Label nom;
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSettings1;
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
    private TableView<Quiz> studentsTable;
    @FXML
    private TableColumn<Quiz, Integer> idCol;
    @FXML
    private TextField id;
    @FXML
    private Button addd11;
    @FXML
    private Button addd;
    @FXML
    private Button addd1;
    @FXML
    private Label validationcat;
    @FXML
    private TableColumn<Quiz, String> coloption1;
    @FXML
    private TableColumn<Quiz, String> coloption2;
    @FXML
    private TableColumn<Quiz, String> coloption3;
    @FXML
    private TableColumn<Quiz, String> colobonnereponse;
    @FXML
    private TextField txttitre;
    @FXML
    private TextField txtoption1;
    @FXML
    private TextField txtoption2;
    @FXML
    private TextField txtoption3;
    @FXML
    private Button btnOverview1;
    @FXML
    private Button btnCustomers11;
    Connection cnx = MaConnexion.getInstance().getCnx();
    private final ObservableList<Quiz> dataList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Quiz, String> coltitre;
    @FXML
    private TextField txtbonnereponse;
    @FXML
    private Button addd12;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        }
        String tt = "SELECT * FROM `quiz`";

        Statement statement;

        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                Integer y = queryoutput.getInt("id");
                String titre_quiz = queryoutput.getString("titre_quiz");
                String option1 = queryoutput.getString("option1");
                String option2 = queryoutput.getString("option2");
                String option3 = queryoutput.getString("option3");
                String bonne_reponse = queryoutput.getString("bonne_reponse");
                dataList.add(new Quiz(y, titre_quiz, option1, option2, option3, bonne_reponse));
            }
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            colobonnereponse.setCellValueFactory(new PropertyValueFactory<>("bonne_reponse"));
            coloption1.setCellValueFactory(new PropertyValueFactory<>("option1"));
            coloption2.setCellValueFactory(new PropertyValueFactory<>("option2"));
            coloption3.setCellValueFactory(new PropertyValueFactory<>("option3"));
            coltitre.setCellValueFactory(new PropertyValueFactory<>("titre_quiz"));
            studentsTable.setItems(dataList);
        } catch (SQLException ex) {
            Logger.getLogger(QuizAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
    private void btnSignout(ActionEvent event) throws IOException, BackingStoreException {
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
    private void getSelected(MouseEvent event) {
        index = studentsTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtbonnereponse.setText(colobonnereponse.getCellData(index));

        txtoption1.setText(coloption1.getCellData(index));
        txtoption2.setText(coloption2.getCellData(index));
        txtoption3.setText(coloption3.getCellData(index));
        txttitre.setText(coltitre.getCellData(index));
        id.setText(String.valueOf(idCol.getCellData(index)));

    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            //   String nom = nomcategorie.getText();
            String xx = id.getText();
            String yy = "delete   from  quiz where id = '" + xx + "' ";
            ps = cnx.prepareStatement(yy);
            ps.execute();

            // showMessageDialog(null, "quiz supprimer avec succes");
            Notification.notificationSuccess("Supprimer", "quiz supprimer avec succes");
            txtbonnereponse.clear();
            txtoption1.clear();
            txtoption2.clear();
            txtoption3.clear();
            txttitre.clear();
            id.clear();
            dataList.clear();
            String tt = "SELECT * FROM `quiz`";

            Statement statement;

            try {
                statement = cnx.createStatement();
                ResultSet queryoutput = statement.executeQuery(tt);
                while (queryoutput.next()) {
                    Integer y = queryoutput.getInt("id");
                    String titre_quiz = queryoutput.getString("titre_quiz");
                    String option1 = queryoutput.getString("option1");
                    String option2 = queryoutput.getString("option2");
                    String option3 = queryoutput.getString("option3");
                    String bonne_reponse = queryoutput.getString("bonne_reponse");
                    dataList.add(new Quiz(y, titre_quiz, option1, option2, option3, bonne_reponse));
                }
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                colobonnereponse.setCellValueFactory(new PropertyValueFactory<>("bonne_reponse"));
                coloption1.setCellValueFactory(new PropertyValueFactory<>("option1"));
                coloption2.setCellValueFactory(new PropertyValueFactory<>("option2"));
                coloption3.setCellValueFactory(new PropertyValueFactory<>("option3"));
                coltitre.setCellValueFactory(new PropertyValueFactory<>("titre_quiz"));
                studentsTable.setItems(dataList);
            } catch (SQLException ex) {
                Logger.getLogger(QuizAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void add(ActionEvent event) throws SQLException {

        if (this.isValidated()) {
            PreparedStatement ps, ps2;
            ResultSet rs;
            // String req = "INSERT INTO `categories`(`name`) VALUES ( ?)";
            String yy = "SELECT * FROM quiz WHERE titre_quiz ='" + txttitre.getText() + "'";
            String req = "INSERT INTO `quiz`(`titre_quiz`,`option1`,`option2`,`option3`,`bonne_reponse`) VALUES ( ?,?,?,?,?)";
            ps = cnx.prepareStatement(yy);

            rs = ps.executeQuery();
            if (rs.next()) {

                Notification.notificationError("deja existe");
            } else {

                ps2 = cnx.prepareStatement(req);
                ps2.setString(1, txttitre.getText());
                ps2.setString(2, txtoption1.getText());
                ps2.setString(3, txtoption2.getText());
                ps2.setString(4, txtoption3.getText());
                ps2.setString(5, txtbonnereponse.getText());
                ps2.execute();
                Notification.notificationSuccess("ajouter", "ajouter avec succés");

                dataList.clear();

                String tt = "SELECT * FROM `quiz`";

                Statement statement;

                try {
                    statement = cnx.createStatement();
                    ResultSet queryoutput = statement.executeQuery(tt);
                    while (queryoutput.next()) {
                        Integer y = queryoutput.getInt("id");
                        String titre_quiz = queryoutput.getString("titre_quiz");
                        String option1 = queryoutput.getString("option1");
                        String option2 = queryoutput.getString("option2");
                        String option3 = queryoutput.getString("option3");
                        String bonne_reponse = queryoutput.getString("bonne_reponse");
                        dataList.add(new Quiz(y, titre_quiz, option1, option2, option3, bonne_reponse));
                    }
                    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                    colobonnereponse.setCellValueFactory(new PropertyValueFactory<>("bonne_reponse"));
                    coloption1.setCellValueFactory(new PropertyValueFactory<>("option1"));
                    coloption2.setCellValueFactory(new PropertyValueFactory<>("option2"));
                    coloption3.setCellValueFactory(new PropertyValueFactory<>("option3"));
                    coltitre.setCellValueFactory(new PropertyValueFactory<>("titre_quiz"));
                    studentsTable.setItems(dataList);
                } catch (SQLException ex) {
                    Logger.getLogger(QuizAdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @FXML
    private void update(ActionEvent event) throws SQLException {
        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            // String nom = nomcategorie.getText();
            String xx = id.getText();
            String yy = "update   quiz set  titre_quiz ='" + txttitre.getText() + "' , option1 ='" + txtoption1.getText() + "',option2 ='" + txtoption2.getText() + "',option3 ='" + txtoption3.getText() + "',bonne_reponse ='" + txtbonnereponse.getText() + "' where id = '" + xx + "' ";
            ps = cnx.prepareStatement(yy);
            ps.execute();

            Notification.notificationSuccess("modifier", "quiz modifier avec succes");
            dataList.clear();

            String tt = "SELECT * FROM `quiz`";

            Statement statement;

            try {
                statement = cnx.createStatement();
                ResultSet queryoutput = statement.executeQuery(tt);
                while (queryoutput.next()) {
                    Integer y = queryoutput.getInt("id");
                    String titre_quiz = queryoutput.getString("titre_quiz");
                    String option1 = queryoutput.getString("option1");
                    String option2 = queryoutput.getString("option2");
                    String option3 = queryoutput.getString("option3");
                    String bonne_reponse = queryoutput.getString("bonne_reponse");
                    dataList.add(new Quiz(y, titre_quiz, option1, option2, option3, bonne_reponse));
                }
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                colobonnereponse.setCellValueFactory(new PropertyValueFactory<>("bonne_reponse"));
                coloption1.setCellValueFactory(new PropertyValueFactory<>("option1"));
                coloption2.setCellValueFactory(new PropertyValueFactory<>("option2"));
                coloption3.setCellValueFactory(new PropertyValueFactory<>("option3"));
                coltitre.setCellValueFactory(new PropertyValueFactory<>("titre_quiz"));
                studentsTable.setItems(dataList);
            } catch (SQLException ex) {
                Logger.getLogger(QuizAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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

    private boolean isValidated() {

        if (txtoption1.getText().equals("")) {
            // validationcat.setText("nom categorie text field cannot be blank.");
            Notification.notificationError("sasie option 1");
            txtoption1.requestFocus();
        } else if (txtoption2.getText().equals("")) {
            Notification.notificationError("sasie option 2");
            txtoption2.requestFocus();
        } else if (txtoption3.getText().equals("")) {
            Notification.notificationError("sasie option 3");
            txtoption3.requestFocus();
        } else if (txttitre.getText().equals("")) {
            Notification.notificationError("sasie le titre de quiz");
            txttitre.requestFocus();
        } else if (txtbonnereponse.getText().equals("")) {
            Notification.notificationError("sasie bonne reponse");
            txttitre.requestFocus();

        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void reset(ActionEvent event) {
        txtbonnereponse.clear();
        txtoption1.clear();
        txtoption2.clear();
        txtoption3.clear();
        txttitre.clear();
        id.clear();
    }
}
