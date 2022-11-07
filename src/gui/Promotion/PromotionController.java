/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Promotion;

import gui.homeAdmin.homeadminController;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
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
import model.Produit;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Notification;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-108
 */
public class PromotionController implements Initializable {

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
    private TextField rechercher;
    @FXML
    private Label welcome;
    @FXML
    private TableColumn<Produit, Integer> idCol;
    @FXML
    private TextField id;
    @FXML
    private Label validationcat;
    @FXML
    private Button btnCustomers11;
    @FXML
    private TableView<Produit> Promotiontable;
    @FXML
    private TableColumn<Produit, String> nomCol;
    @FXML
    private TableColumn<Produit, Integer> PrixCol;
    @FXML
    private TextField prixproduit;
    @FXML
    private Button btnpromotion;
    Connection cnx = MaConnexion.getInstance().getCnx();
    private final ObservableList<Produit> dataList = FXCollections.observableArrayList();
    @FXML
    private TextField prixold;
    @FXML
    private Label shownomproduit;
    @FXML
    private Label nomproduit;
    @FXML
    private DatePicker date;
    @FXML
    private Label pourcentage;
    @FXML
    private Label labeldate;
    @FXML
    private Button btnOverview1;

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
        shownomproduit.setVisible(false);
        prixproduit.setVisible(false);
        btnpromotion.setVisible(false);
        date.setVisible(false);
        pourcentage.setVisible(false);
        labeldate.setVisible(false);
        try {
            refresh();
            FilteredList<Produit> filteredData = new FilteredList<>(dataList, b -> true);
            rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate((Produit) -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Produit.getNom_produit().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.

                    } else {
                        return false; // Does not match.
                    }
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Produit> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(Promotiontable.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            Promotiontable.setItems(sortedData);
        } catch (SQLException ex) {
            Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
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
        index = Promotiontable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id.setText(idCol.getCellData(index).toString());
        prixold.setText(PrixCol.getCellData(index).toString());
        nomproduit.setText(nomCol.getCellData(index));
        prixproduit.setVisible(true);
        btnpromotion.setVisible(true);
        shownomproduit.setVisible(true);
        date.setVisible(true);
        pourcentage.setVisible(true);
        labeldate.setVisible(true);

    }

    @FXML
    private void promotion(ActionEvent event) throws SQLException {

        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;

            String xx = id.getText();
            String yy = "update   produits set  prix =? , promotion ='true' ,  numberpromotion=? ,prixold=? , date_exp=?   where id = '" + xx + "' ";
            ps = cnx.prepareStatement(yy);
            float a = Float.parseFloat(prixold.getText());
            float b = Float.parseFloat(prixproduit.getText());

            Float xxx = a * (1 - (b / 100));
            ps.setFloat(1, xxx);
            ps.setFloat(2, b);
            ps.setString(3, prixold.getText());
            ps.setString(4, String.valueOf(date.getValue()));
            ps.execute();
            refresh();
            //showMessageDialog(null, "categorie modifier avec succes");
            Notification.notificationSuccess("Promotion ajouter avec succeés", "");
            prixproduit.clear();
            date.setValue(null);
        }

    }

    public void refresh() throws SQLException {
        dataList.clear();
        String tt = "SELECT * FROM `produits`";

        Statement statement;

        statement = cnx.createStatement();
        ResultSet queryoutput = statement.executeQuery(tt);
        while (queryoutput.next()) {
            String x = queryoutput.getString("nom");
            Integer y = queryoutput.getInt("prix");
            Integer z = queryoutput.getInt("id");
            dataList.add(new Produit(z, x, y));
        }
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
        PrixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        Promotiontable.setItems(dataList);
    }

    private boolean isValidated() {
        String number = "[0-9]+";
        Pattern x = Pattern.compile(number);
        LocalDate currentdDate1 = LocalDate.now();

        if (prixproduit.getText().equals("")) {

            Notification.notificationError("saisir la promotion a ajouter.");
            prixproduit.requestFocus();
        } else if (date.getValue() == null) {

            Notification.notificationError("saisir la date a ajouter.");
            date.requestFocus();
        } else if (!x.matcher(prixproduit.getText()).matches()) {

            Notification.notificationError("promotion contient seulement des chiffres");
            prixproduit.requestFocus();
        } else if (date.getValue().equals(currentdDate1)) {

            Notification.notificationError("impossible d'ajouter date actuelle");
            date.requestFocus();
        } else if (date.getValue().isBefore(currentdDate1)) {

            Notification.notificationError("impossible d'ajouter date ancien");
            date.requestFocus();

        } else {
            return true;
        }
        return false;
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
