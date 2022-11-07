/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Client;

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
import model.user;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Notification;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-108
 */
public class ClientController implements Initializable {

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
    private TextField id;
    @FXML
    private Button Banier;
    @FXML
    private Label validationcat;
    @FXML
    private TableView<user> Usertable;
    @FXML
    private TableColumn<user, Integer> idcol;
    @FXML
    private TableColumn<user, String> colonom;
    @FXML
    private TableColumn<user, String> coloprenom;
    @FXML
    private TableColumn<user, Integer> colocin;

    @FXML
    private TextField imageold;
    @FXML
    private Button unbanier;
    @FXML
    private TableColumn<user, String> coloemail;
    @FXML
    private TableColumn<user, String> colbanier;
    Connection cnx = MaConnexion.getInstance().getCnx();
    private final ObservableList<user> dataList = FXCollections.observableArrayList();
    int index = -1;
    @FXML
    private TextField txtbanier;
    @FXML
    private TextField txtid;
    @FXML
    private Button btnOverview1;
    @FXML
    private Button btnCustomers11;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Banier.setVisible(false);
        unbanier.setVisible(false);
        refreshdata();

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

    }

    public void refreshdata() {

        dataList.clear();
        String tt = "SELECT * FROM `users` where role ='Client' ";

        Statement statement;

        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                Integer id = Integer.valueOf(queryoutput.getString("id"));
                String nom = queryoutput.getString("nom");
                String prenom = queryoutput.getString("prenom");
                Integer cin = Integer.valueOf(queryoutput.getString("cin"));
                String email = queryoutput.getString("email");
                String banier = queryoutput.getString("banier");

                dataList.add(new user(id, nom, prenom, email, banier, cin));
            }
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            colonom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            coloprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colocin.setCellValueFactory(new PropertyValueFactory<>("cin"));
            coloemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colbanier.setCellValueFactory(new PropertyValueFactory<>("banier"));

            Usertable.setItems(dataList);
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void Banier(ActionEvent event) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        String x = "true";
        String xx = txtid.getText();
        String yy = "update   users set  banier ='" + x + "' where id = '" + xx + "' ";
        ps = cnx.prepareStatement(yy);
        ps.execute();
        refreshdata();
        Banier.setVisible(false);
        unbanier.setVisible(false);
        Notification.notificationSuccess("banier", "Client banier");
    
    }

    @FXML
    private void getSelected(MouseEvent event) {
        index = Usertable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtbanier.setText(colbanier.getCellData(index));
        txtid.setText(String.valueOf(idcol.getCellData(index)));
        String xx = colbanier.getCellData(index);
        if (xx.equals("false")) {
            Banier.setVisible(true);
            unbanier.setVisible(false);
        } else {
            Banier.setVisible(false);
            unbanier.setVisible(true);

        }
    }

    @FXML
    private void unbanier(ActionEvent event) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        String x = "false";
        String xx = txtid.getText();
        String yy = "update   users set  banier ='" + x + "' where id = '" + xx + "' ";
        ps = cnx.prepareStatement(yy);
        ps.execute();
        refreshdata();
        Banier.setVisible(false);
        unbanier.setVisible(false);
     
        Notification.notificationSuccess("banier", "Client revenir");

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

}
