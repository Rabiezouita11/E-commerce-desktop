/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.CommandeAdmin;

import Interface.InterCategorie;
import dao.daocategorie;
import ecommerce.CommandeEmail;
import gui.QuizAdmin.QuizAdminController;
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
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import static javax.swing.JOptionPane.showMessageDialog;
import model.Historique;
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
public class CommandeAdminController implements Initializable {

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
    private TextField id;
    @FXML
    private Button Annulertxt;
    @FXML
    private Label validationcat;
    @FXML
    private TableView<Historique> Usertable;
    @FXML
    private TableColumn<Historique, Integer> idcol;
    @FXML
    private TableColumn<Historique, String> colproduit;
    @FXML
    private TableColumn<Historique, Integer> colprix;
    @FXML
    private TableColumn<Historique, String> coluser;
    @FXML
    private TableColumn<Historique, String> colville;
    @FXML
    private TableColumn<Historique, String> coletat;
    @FXML
    private TableColumn<Historique, String> coldate;
    @FXML
    private TableColumn<Historique, String> colemail;
    @FXML
    private TextField imageold;
    @FXML
    private Button validertxt;
    @FXML
    private TextField txtbanier;
    @FXML
    private TextField txtid;
    @FXML
    private Label etatcommande;
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
    private final ObservableList<Historique> dataList = FXCollections.observableArrayList();
    @FXML
    private TextField txtemail;
    @FXML
    private TextField txtetat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Annulertxt.setVisible(false);
        validertxt.setVisible(false);
        etatcommande.setVisible(false);
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
        String tt = "SELECT * FROM `commandes`";

        Statement statement;

        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                Integer y = queryoutput.getInt("id");
                String Produit = queryoutput.getString("Produit");
                Integer total_prix = queryoutput.getInt("total_prix");
                InterCategorie deptdao = daocategorie.getInstance();

                String id_user = String.valueOf(deptdao.findnomById(queryoutput.getInt("id_user")));

                String email = String.valueOf(deptdao.findemailById(queryoutput.getInt("id_user")));
//                 System.out.println(deptdao.findnomById(Integer.parseInt(id_user)));
                String Ville = queryoutput.getString("Ville");
                String created = queryoutput.getString("created");
                String etat_commande = queryoutput.getString("etat_commande");
                System.out.println(created);
                dataList.add(new Historique(y, Produit, total_prix, Ville, etat_commande, created, id_user, email));
            }
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            colproduit.setCellValueFactory(new PropertyValueFactory<>("Produit"));
            colprix.setCellValueFactory(new PropertyValueFactory<>("total_prix"));
            coluser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            colville.setCellValueFactory(new PropertyValueFactory<>("Ville"));
            coletat.setCellValueFactory(new PropertyValueFactory<>("etat_commande"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("email"));

            Usertable.setItems(dataList);
        } catch (SQLException ex) {
            Logger.getLogger(QuizAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void Annuler(ActionEvent event) throws SQLException, MessagingException, AddressException, IOException {
        PreparedStatement ps;
        ResultSet rs;
        String x = "true";
        String xx = txtid.getText();
        String a = "Annuler";
        String yy = "update   commandes set  etat_commande ='" + a + "' where id = '" + xx + "' ";
        ps = cnx.prepareStatement(yy);
        ps.execute();
        CommandeEmail mail = new CommandeEmail();
        mail.setupServerProperties();
        mail.draftEmail(txtemail.getText(), Integer.valueOf(txtid.getText()), a);
        mail.sendEmail();

        dataList.clear();
        String tt = "SELECT * FROM `commandes`";

        Statement statement;

        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                Integer y = queryoutput.getInt("id");
                String Produit = queryoutput.getString("Produit");
                Integer total_prix = queryoutput.getInt("total_prix");
                InterCategorie deptdao = daocategorie.getInstance();

                String id_user = String.valueOf(deptdao.findnomById(queryoutput.getInt("id_user")));

                String email = String.valueOf(deptdao.findemailById(queryoutput.getInt("id_user")));
//                 System.out.println(deptdao.findnomById(Integer.parseInt(id_user)));
                String Ville = queryoutput.getString("Ville");
                String created = queryoutput.getString("created");
                String etat_commande = queryoutput.getString("etat_commande");
                System.out.println(created);
                dataList.add(new Historique(y, Produit, total_prix, Ville, etat_commande, created, id_user, email));
            }
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            colproduit.setCellValueFactory(new PropertyValueFactory<>("Produit"));
            colprix.setCellValueFactory(new PropertyValueFactory<>("total_prix"));
            coluser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            colville.setCellValueFactory(new PropertyValueFactory<>("Ville"));
            coletat.setCellValueFactory(new PropertyValueFactory<>("etat_commande"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("email"));

            Usertable.setItems(dataList);
        } catch (SQLException ex) {
            Logger.getLogger(QuizAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Notification.notificationError("commande est Annuler");
        Annulertxt.setVisible(false);
        validertxt.setVisible(false);
    }

    @FXML
    private void getSelected(MouseEvent event) {
        index = Usertable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        txtetat.setText(coletat.getCellData(index));
        txtid.setText(String.valueOf(idcol.getCellData(index)));
        txtemail.setText(colemail.getCellData(index));

        if (txtetat.getText().equals("En cours")) {
            Annulertxt.setVisible(true);
            validertxt.setVisible(true);
            etatcommande.setVisible(false);
        } else {
            Annulertxt.setVisible(false);
            validertxt.setVisible(false);
            etatcommande.setVisible(true);
            etatcommande.setText(" commande est " + " " + coletat.getCellData(index));
        }

    }

    @FXML
    private void Valider(ActionEvent event) throws SQLException, MessagingException, AddressException, IOException {
        PreparedStatement ps;
        ResultSet rs;
        String x = "true";
        String xx = txtid.getText();
        String a = "valider";
        String yy = "update   commandes set  etat_commande ='" + a + "' where id = '" + xx + "' ";
        ps = cnx.prepareStatement(yy);
        ps.execute();
        CommandeEmail mail = new CommandeEmail();
        mail.setupServerProperties();
        mail.draftEmail(txtemail.getText(), Integer.valueOf(txtid.getText()), a);
        mail.sendEmail();

        dataList.clear();
        String tt = "SELECT * FROM `commandes`";

        Statement statement;

        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                Integer y = queryoutput.getInt("id");
                String Produit = queryoutput.getString("Produit");
                Integer total_prix = queryoutput.getInt("total_prix");
                InterCategorie deptdao = daocategorie.getInstance();

                String id_user = String.valueOf(deptdao.findnomById(queryoutput.getInt("id_user")));

                String email = String.valueOf(deptdao.findemailById(queryoutput.getInt("id_user")));
//                 System.out.println(deptdao.findnomById(Integer.parseInt(id_user)));
                String Ville = queryoutput.getString("Ville");
                String created = queryoutput.getString("created");
                String etat_commande = queryoutput.getString("etat_commande");
                System.out.println(created);
                dataList.add(new Historique(y, Produit, total_prix, Ville, etat_commande, created, id_user, email));
            }
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            colproduit.setCellValueFactory(new PropertyValueFactory<>("Produit"));
            colprix.setCellValueFactory(new PropertyValueFactory<>("total_prix"));
            coluser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            colville.setCellValueFactory(new PropertyValueFactory<>("Ville"));
            coletat.setCellValueFactory(new PropertyValueFactory<>("etat_commande"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("email"));

            Usertable.setItems(dataList);
        } catch (SQLException ex) {
            Logger.getLogger(QuizAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Notification.notificationSuccess("Validation", "commande est valider");
        Annulertxt.setVisible(false);
        validertxt.setVisible(false);
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

}
