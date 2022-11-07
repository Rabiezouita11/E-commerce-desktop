/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ContactAdmin;

import Interface.InterCategorie;
import dao.daocategorie;
import ecommerce.ContactEmail;
import gui.QuizAdmin.QuizAdminController;
import gui.homeAdmin.homeadminController;
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
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import model.Contact;
import model.Historique;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Notification;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class ContactAdminController implements Initializable {

    int index = -1;
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
    private TableView<Contact> Promotiontable;
    @FXML
    private TableColumn<Contact, Integer> colid;
    @FXML
    private TableColumn<Contact, String> colnom;
    @FXML
    private TableColumn<Contact, String> colemail;
    @FXML
    private TableColumn<Contact, String> colmessage;
    @FXML
    private TextField id;
    @FXML
    private TextField txtmessage;
    @FXML
    private Button btnenvoyer;
    @FXML
    private Label validationcat;
    @FXML
    private TextField prixold;
    @FXML
    private Label nomproduit;
    @FXML
    private Label labelmessage;
    @FXML
    private ImageView image;
    @FXML
    private Label nom;
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnOverview1;
    @FXML
    private Button btnCustomers11;
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
    Connection cnx = MaConnexion.getInstance().getCnx();
    private final ObservableList<Contact> dataList = FXCollections.observableArrayList();
    @FXML
    private TextField txtemail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtmessage.setVisible(false);
        labelmessage.setVisible(false);
        btnenvoyer.setVisible(false);
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
        String tt = "SELECT * FROM `contacts`";

        Statement statement;

        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                Integer y = queryoutput.getInt("id");
                String nom = queryoutput.getString("nom");
                String email = queryoutput.getString("email");
                String message = queryoutput.getString("message");

                dataList.add(new Contact(y, nom, email, message));
            }
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colmessage.setCellValueFactory(new PropertyValueFactory<>("message"));
            colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));

            Promotiontable.setItems(dataList);
        } catch (SQLException ex) {
            Logger.getLogger(QuizAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void Promotiontable(MouseEvent event) {

    }

    @FXML
    private void envoyermessage(ActionEvent event) throws MessagingException, AddressException, IOException {

        ContactEmail mail = new ContactEmail();
        mail.setupServerProperties();
        mail.draftEmail(txtemail.getText(), txtmessage.getText());
        mail.sendEmail();
        Notification.notificationSuccess("message", "message envoyer avec succés");
        txtmessage.setVisible(false);
        labelmessage.setVisible(false);
        btnenvoyer.setVisible(false);
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
        index = Promotiontable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtemail.setText(colemail.getCellData(index));

        if (txtemail.getText().equals("")) {
            txtmessage.setVisible(false);
            labelmessage.setVisible(false);
            btnenvoyer.setVisible(false);
        } else {
            txtmessage.setVisible(true);
            labelmessage.setVisible(true);
            btnenvoyer.setVisible(true);
        }
    }

}
