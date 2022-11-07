/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.categorie;

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
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class Categorie implements Initializable {

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
    private TextField id;
    @FXML
    private TableColumn<Categories, Integer> idCol;
    @FXML
    private TableColumn<Categories, String> cat;
    @FXML
    private TextField nomcategorie;
    ObservableList<Categories> listM;
    ObservableList<Categories> data;
    private final ObservableList<Categories> dataList = FXCollections.observableArrayList();

    Connection cnx = MaConnexion.getInstance().getCnx();
    private final ObservableList<Categories> catlist = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Categories student = null;
    @FXML
    private TableView<Categories> studentsTable;
    @FXML
    private Label validationcat;
    @FXML
    private Button addd11;
    @FXML
    private Button addd;
    @FXML
    private Button addd1;
    @FXML
    private Button btnOverview1;
    @FXML
    private Button btnCustomers11;

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
        String tt = "SELECT * FROM `categories`";

        Statement statement;

        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                String x = queryoutput.getString("name");
                Integer y = queryoutput.getInt("id");
                dataList.add(new Categories(y, x));
            }
            cat.setCellValueFactory(new PropertyValueFactory<>("name"));
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            studentsTable.setItems(dataList);
            FilteredList<Categories> filteredData = new FilteredList<>(dataList, b -> true);
            rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate((Categories) -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Categories.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.

                    } else {
                        return false; // Does not match.
                    }
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Categories> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(studentsTable.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            studentsTable.setItems(sortedData);

        } catch (SQLException ex) {
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadDate() {
        refreshTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cat.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    private void Accueil(ActionEvent event) throws IOException {
        Stage stage = (Stage) pnlCustomer.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/homeAdmin/homeadmin.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Admin Panel");
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
    private void delete(ActionEvent event) throws SQLException {
        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            String nom = nomcategorie.getText();
            String xx = id.getText();
            String yy = "delete   from  categories where name = '" + nom + "' ";
            ps = cnx.prepareStatement(yy);
            ps.execute();

            showMessageDialog(null, "categorie supprimer avec succes");
            nomcategorie.clear();
            id.clear();
        }
    }

    @FXML
    private void add(ActionEvent event) throws IOException, SQLException {

        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            // String req = "INSERT INTO `categories`(`name`) VALUES ( ?)";
            String yy = "SELECT * FROM categories WHERE name ='" + nomcategorie.getText() + "'";
            ps = cnx.prepareStatement(yy);

            rs = ps.executeQuery();
            if (rs.next()) {
                showMessageDialog(null, "deja existe");
            } else {

                Categories cat = new Categories();
                cat.setName(nomcategorie.getText());
                InterCategorie deptdao = daocategorie.getInstance();
                deptdao.insertcat(cat);
                showMessageDialog(null, "categorie ajouter avec succes");
                nomcategorie.clear();
                id.clear();
                refreshTable();
                search();

            }
        }
    }

    private boolean isValidated() {

        if (nomcategorie.getText().equals("")) {
            validationcat.setText("nom categorie text field cannot be blank.");

            nomcategorie.requestFocus();

        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void update(ActionEvent event) throws SQLException {
        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            String nom = nomcategorie.getText();
            String xx = id.getText();
            String yy = "update   categories set  name ='" + nom + "' where id = '" + xx + "' ";
            ps = cnx.prepareStatement(yy);
            ps.execute();

            showMessageDialog(null, "categorie modifier avec succes");
        }
    }

    private void search() {

        dataList.clear();
        String tt = "SELECT * FROM `categories`";

        Statement statement;
        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                String x = queryoutput.getString("name");
                Integer y = queryoutput.getInt("id");
                dataList.add(new Categories(y, x));
            }
            cat.setCellValueFactory(new PropertyValueFactory<>("name"));
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            studentsTable.setItems(dataList);
            FilteredList<Categories> filteredData = new FilteredList<>(dataList, b -> true);
            rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate((Categories) -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Categories.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.

                    } else {
                        return false; // Does not match.
                    }
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Categories> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(studentsTable.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            studentsTable.setItems(sortedData);

        } catch (SQLException ex) {
            Logger.getLogger(Categorie.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void refreshTable() {
        dataList.clear();
        try {

            String query = "SELECT * FROM `categories`";
            preparedStatement = cnx.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataList.add(new Categories(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
                studentsTable.setItems(dataList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Categorie.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void getSelected(MouseEvent event) {

        index = studentsTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id.setText(idCol.getCellData(index).toString());
        nomcategorie.setText(cat.getCellData(index));

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
