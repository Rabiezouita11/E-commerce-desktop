/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Produit;

import Interface.InterCategorie;
import Interface.interProduit;
import dao.daocategorie;
import dao.daoproduit;
import gui.homeAdmin.homeadminController;
import gui.homeClient.AccueilClientController;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
import javax.swing.JFileChooser;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Categories;
import model.Produit;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class ProduitController implements Initializable {

    int index = -1;
    String filename = null;
    File xxx = null;
    byte[] person_image = null;
    Connection cnx = MaConnexion.getInstance().getCnx();
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
    private TextField nomproduit;
    @FXML
    private Button addd11;
    @FXML
    private Button addd;
    @FXML
    private Button addd1;
    @FXML
    private Label validationcat;
    @FXML
    private TextField prix;
    @FXML
    private TextField Description;
    @FXML
    private ComboBox<Categories> categorie;
    @FXML
    private TextField Quantite;
    @FXML
    private TableView<Produit> produitTable;
    @FXML
    private TableColumn<Produit, Integer> idcol;
    @FXML
    private TableColumn<Produit, String> idnomproduit;
    @FXML
    private TableColumn<Produit, String> idimage;
    @FXML
    private TableColumn<Produit, Float> idprix;
    @FXML
    private TableColumn<Produit, String> iddescription;
    @FXML
    private TableColumn<Produit, Categories> idcategorie;
    @FXML
    private TableColumn<Produit, Integer> idquantite;
    ObservableList<Categories> dataList = FXCollections.observableArrayList();
    ObservableList<Produit> Produitlist = FXCollections.observableArrayList();
    private InputStream input;
    @FXML
    private ImageView img;
    @FXML
    private Button pp;
    @FXML
    private TextField imageold;
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

                dataList.add(new Categories(x));
                categorie.setItems(dataList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // afficahage produit
        interProduit x = daoproduit.getInstance();
        Produitlist = x.DisplayAllproduit();

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idnomproduit.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
        idimage.setCellValueFactory(new PropertyValueFactory<>("image"));
        //   idimage.setCellValueFactory(new PropertyValueFactory<>("image"));
        idprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        iddescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        idcategorie.setCellValueFactory(new PropertyValueFactory<>("cat"));
        idquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));

//
        produitTable.setItems(Produitlist);

//        
//        String bb = "SELECT * FROM `produits`";
//  String query = "select * from categories WHERE id = ?";
//          PreparedStatement cat = null;
//        Statement st;
//        try {
//            st = cnx.createStatement();
//             cat = cnx.prepareStatement(query);
//            ResultSet c = st.executeQuery(bb);
//            while (c.next()) {
//                Integer id = c.getInt("id");
//                String nom_produit = c.getString("nom");
//                  input = c.getBinaryStream("image");
//              
//                       Image imge = new Image(input);
//                Integer quantite = c.getInt("quantite");
//                float prix = c.getInt("prix");
//                String Description = c.getString("Description");
//
//                Integer categorie_id = c.getInt("categorie");
//
//                   cat.setInt(1, categorie_id);
//                 ResultSet rs2 = cat.executeQuery();
//                if (rs2.next()) {
//                    String s1 = rs2.getString("name");
//                    System.out.println(s1);
//                  
//                }
//                System.out.println(rs2.toString());
//                Produitlist.add(new Produit(id, quantite, nom_produit, Description, prix, categorie_id));
//
//            }
//            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
//            idnomproduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
//
//            idimage.setCellValueFactory(new PropertyValueFactory<>("image"));
//
//            idprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
//            iddescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
//            
//           idcategorie.setCellValueFactory(new Callback<CellDataFeatures<Produit,String>,ObservableValue<String>>(){
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Produit, String> param) {
//                    return new SimpleStringProperty(param.getValue().getCat().getName());
//                }
//
//            });
//            idquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
//
//            produitTable.setItems(Produitlist);
        FilteredList<Produit> filteredData = new FilteredList<>(Produitlist, b -> true);
        rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((pr) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (pr.getNom_produit().toLowerCase().contains(lowerCaseFilter)) {
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
        sortedData.comparatorProperty().bind(produitTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        produitTable.setItems(sortedData);

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

        if (id.getText().equals("")) {
            showMessageDialog(null, "you must select produit");
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Supprimer");
            alert.setHeaderText("You're about to delete product!");
            alert.setContentText("Do you want to delete ");
            if (alert.showAndWait().get() == ButtonType.OK) {

                PreparedStatement ps;
                ResultSet rs;
                Integer nom = Integer.parseInt(id.getText());
                String xx = id.getText();
                String yy = "delete   from  produits where id = '" + nom + "' ";
                ps = cnx.prepareStatement(yy);
                ps.execute();

                showMessageDialog(null, "produits supprimer avec succes");
                nomproduit.clear();
                prix.clear();
                Description.clear();
                Quantite.clear();
                refreshTable();
                this.img.setVisible(false);
            }
        }
    }

    @FXML
    private void add(ActionEvent event) throws SQLException {

        if (categorie.getSelectionModel().isSelected(-1)) {
            showMessageDialog(null, "categorie  must be selected");
            categorie.requestFocus();
            this.img.setVisible(false);
        } else {

            if (this.isValidated()) {
                this.img.setVisible(false);
                String s = categorie.getSelectionModel().getSelectedItem().toString();
                PreparedStatement ps, cat;
                ResultSet rs, rs2;
                String req = "INSERT INTO `produits`(`nom`,`image`,`prix`,`Description`,`categorie`,`quantite`) VALUES ( ?,?,?,?,?,?)";
                String yy = "SELECT * FROM produits WHERE nom ='" + nomproduit.getText() + "'";
                //   String query = "select * from categories WHERE name = ?";
                ps = cnx.prepareStatement(yy);
                // cat = cnx.prepareStatement(query);
                //  cat.setString(1, s);
                // rs2 = cat.executeQuery();
                rs = ps.executeQuery();
                if (rs.next()) {
                    showMessageDialog(null, "deja existe");
                    nomproduit.requestFocus();
                } else {
                    Produit produit = new Produit();
                    produit.setNom_produit(nomproduit.getText());
                    produit.setImage(String.valueOf(xxx));
                    produit.setPrix(Integer.parseInt(prix.getText()));
                    produit.setDescription(Description.getText());
                    InterCategorie deptdao = daocategorie.getInstance();
                    produit.setCat(deptdao.findcatBynom(s));
                    produit.setQuantite(Integer.parseInt(Quantite.getText()));
                    interProduit x = daoproduit.getInstance();
                    x.insertproduit(produit);
                    this.img.setVisible(false);

                    System.out.println("PS : produit ajoutée avec succés!");
                    nomproduit.clear();
                    prix.clear();
                    Description.clear();
                    Quantite.clear();
                    refreshTable();
                    this.img.setVisible(false);
                    showMessageDialog(null, "produit ajouter avec succes");

                }

            }
        }
    }

    private boolean isValidated() {
//        String s = categorie.getSelectionModel().getSelectedItem().toString();
        String number = "[0-9]+";
        Pattern x = Pattern.compile(number);
        if (nomproduit.getText().equals("")) {

            showMessageDialog(null, "nom produit text field cannot be blank.");
            nomproduit.requestFocus();
        } else if (prix.getText().equals("")) {
            showMessageDialog(null, "prix text field cannot be blank.");
            prix.requestFocus();
        } else if (xxx == null) {
            showMessageDialog(null, "image required");
            pp.requestFocus();
        } else if (Description.getText().equals("")) {
            showMessageDialog(null, "Description text field cannot be blank.");
            Description.requestFocus();
        } else if (Quantite.getText().equals("") && Quantite.equals("[a-zA-Z_]+")) {
            showMessageDialog(null, "Quantite text field cannot be blank.");
            Quantite.requestFocus();
        } else if (!x.matcher(Quantite.getText()).matches()) {
            showMessageDialog(null, "Quantite contains only number.");
            Quantite.requestFocus();
        } else if (!x.matcher(prix.getText()).matches()) {
            showMessageDialog(null, "prix contains only number.");
            prix.requestFocus();
        } else if (categorie.getSelectionModel().isSelected(-1)) {
            showMessageDialog(null, "categorie  must be selected");
            categorie.requestFocus();
        } else {
            return true;
        }
        return false;
    }

//!name.matches("[a-zA-Z_]+")
    @FXML

    private void update(ActionEvent event) throws SQLException {

        if (id.getText().equals("")) {
            showMessageDialog(null, "you must select produit");
        } else {

            PreparedStatement cat;
            ResultSet rs2;
            String s = categorie.getSelectionModel().getSelectedItem().toString();
            InterCategorie x = daocategorie.getInstance();
            String query = "select * from categories WHERE name = ?";
            cat = cnx.prepareStatement(query);
            cat.setString(1, s);
            rs2 = cat.executeQuery();
            if (rs2.next()) {

                String s1 = rs2.getString("id");
                PreparedStatement ps;
                ResultSet rs;
                String nom = nomproduit.getText();
                String xx = id.getText();

                String yy = "update   produits set  nom ='" + nom + "' , image =? , prix ='" + prix.getText() + "', Description ='" + Description.getText() + "' , quantite ='" + Quantite.getText() + "' , categorie ='" + s1 + "'  where id = '" + xx + "' ";

                ps = cnx.prepareStatement(yy);
                if (xxx == null) {
                    String z = imageold.getText();
                    ps.setString(1, z);
                } else {
                    ps.setString(1, String.valueOf(xxx));
                }

                ps.execute();

                showMessageDialog(null, "produit modifier avec succes");
                refreshTable();
            }
        }
        // cat = cnx.prepareStatement(query);
        //  cat.setString(1, s);
        // rs2 = cat.executeQuery();

    }

    @FXML
    private void refreshTable() {
        dataList.clear();
        interProduit x = daoproduit.getInstance();
        Produitlist = x.DisplayAllproduit();
        produitTable.setItems(Produitlist);
        String tt = "SELECT * FROM `categories`";

        Statement statement;
        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                String xx = queryoutput.getString("name");

                dataList.add(new Categories(xx));
                categorie.setItems(dataList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void uploid(ActionEvent event) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fd = new FileNameExtensionFilter("PNG JPG", "png", "jpg");
        chooser.addChoosableFileFilter(fd);

        int response = chooser.showOpenDialog(null); //select file to open
        //int response = fileChooser.showSaveDialog(null); //select file to save

        if (response == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (fd.accept(f)) {
                filename = f.getAbsolutePath();

                String newpath = "uploids/produit/";
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

    public static String randomStringforimage() {
        //   String  randomString  =null;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 10;

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }
        String randomString = sb.toString();

        return randomString;
    }

    @FXML
    void getSelected(MouseEvent event) {

        index = produitTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id.setText(String.valueOf(idcol.getCellData(index)));
        nomproduit.setText(idnomproduit.getCellData(index));
        prix.setText(String.valueOf(idprix.getCellData(index)));
        Description.setText(iddescription.getCellData(index));
        Quantite.setText(String.valueOf(idquantite.getCellData(index)));

        imageold.setText(idimage.getCellData(index));
        Categories xx = idcategorie.getCellData(index);
        categorie.getSelectionModel().select(xx);
        this.img.setVisible(true);
        String path = idimage.getCellData(index);
        Image image = new Image("file:" + path);
        this.img.setImage(image);

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
