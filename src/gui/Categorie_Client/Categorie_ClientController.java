/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Categorie_Client;

import Interface.InterCategorie;
import dao.daocategorie;
import ecommerce.MyListener;

import ecommerce.main;
import gui.Panier.PanierController;
import gui.Produit.ProduitController;
import gui.Profile.ProfileController;
import gui.categorie.Categorie;
import gui.homeClient.AccueilClientController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import static javax.swing.JOptionPane.showMessageDialog;
import model.Categories;
import model.Produit;
import util.MaConnexion;
import java.util.prefs.Preferences;

import util.Statics;
import java.util.prefs.BackingStoreException;
import javafx.application.Platform;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import util.Jwt;
import javafx.scene.text.TextFlow;
import org.json.JSONException;
import util.Notification;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-006
 */
public class Categorie_ClientController implements Initializable {

    int index = -1;
    @FXML
    private ImageView image;
    @FXML
    private Label labelnom;
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
    private ComboBox<Categories> combocategorie;
    private TableView<Produit> tableproduit;
    private TableColumn<Produit, Integer> colid;
    private TableColumn<Produit, String> colnom;
    private TableColumn<Produit, String> colimage;
    private TableColumn<Produit, Integer> colprix;
    private TableColumn<Produit, String> coldescription;
    private TableColumn<Produit, Integer> colquantiter;
    private TableColumn<Produit, Categorie> colcategories;
    Connection cnx = MaConnexion.getInstance().getCnx();
    ObservableList<Categories> dataList = FXCollections.observableArrayList();
    ObservableList<Produit> Produitlist = FXCollections.observableArrayList();
    private ImageView imgproduit;
    @FXML
    private TextField nomproduit;
    private Label prixproduit;
    @FXML
    private Button enstock;
    private Label description;
    @FXML
    private Button horsstock;
    private Label cat;
    @FXML
    private TextField quantite;
    private Button ajouterpanierr;
    @FXML
    private TextField txtimage;
    @FXML
    private TextField idproduit;
    @FXML
    private TextField txtcat;
    private Label y;
    private Label x;
    private Label z;
    @FXML
    private Label showstock;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label fruitNameLable;
    @FXML
    private Label fruitPriceLabel;
    @FXML
    private ImageView fruitImg;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    private List<Produit> fruits = new ArrayList<>();
    private List<Produit> xx = new ArrayList<>();
    private String rabie;
    private MyListener myListener;
    @FXML
    private Button btnAboutus;
    @FXML
    private Button btnContact;
    @FXML
    private Button idcat;
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
    @FXML
    private Button btnAccueil;
    @FXML
    private Button addcart;
    @FXML
    private TextFlow txtarea;
    @FXML
    private TextField txtdes;
    @FXML
    private TextField prix;
    @FXML
    private ComboBox<Categories> combocat;
    @FXML
    private TextField prixmin;
    @FXML
    private TextField prixmax;
    @FXML
    private Button horsstock1;
    @FXML
    private RadioButton noter1;
    @FXML
    private ToggleGroup produit;
    @FXML
    private RadioButton noter2;
    @FXML
    private RadioButton noter3;
    @FXML
    private RadioButton noter4;
    @FXML
    private RadioButton noter5;
    @FXML
    private Label countnote;

    @FXML
    private Label lesnoter;
    @FXML
    private Label promotions;
    private volatile boolean stop = false;
    @FXML
    private Label date;

    /**
     * Initializes the controller class.
     */
    private List<Produit> getData() throws SQLException {
        List<Produit> fruits = new ArrayList<>();
        Produit fruit;
        String tt = "SELECT * FROM `produits`";
        Statement statement;

        statement = cnx.createStatement();
        ResultSet queryoutput = statement.executeQuery(tt);

        while (queryoutput.next()) {

            fruit = new Produit();
            fruit.setNom_produit(queryoutput.getString("nom"));
            fruit.setPrix(Integer.parseInt(queryoutput.getString("prix")));
            fruit.setImage(queryoutput.getString("image"));
            fruit.setColor("B0E0E6");
            fruit.setNumberpromotion(queryoutput.getString("numberpromotion"));
            fruit.setQuantite(Integer.parseInt(queryoutput.getString("quantite")));
            fruit.setDescription(queryoutput.getString("Description"));
            fruit.setId(Integer.parseInt(queryoutput.getString("id")));
            fruit.setImage(queryoutput.getString("image"));
            fruit.setPromotion(queryoutput.getString("promotion"));
            InterCategorie deptdao = daocategorie.getInstance();
            fruit.setCat(deptdao.findcatById(queryoutput.getInt("categorie")));
            fruit.setDate(queryoutput.getString("date_exp"));
            fruit.setPrixold(queryoutput.getInt("prixold"));
            fruits.add(fruit);

        }

        return fruits;
    }

//     private List<Produit> removedata() throws SQLException {
//        List<Produit> fruits = new ArrayList<>();
//        Produit fruit;
//     
//          fruits.removeAll(fruits);
//        
//
//        return fruits;
//    }
    private void setChosenFruit(Produit fruit) {

        LocalDate currentdDate1 = LocalDate.now();

        if (fruit.getDate() == null) {

        } else {
            // System.out.println(fruit.getDate());
            if (fruit.getDate().equals(currentdDate1.toString())) {
                try {
                    PreparedStatement ps;

                    // String xx = id.getText();
                    String yy = "update   produits set  prix ='" + fruit.getPrixold() + "' , promotion ='false' , date_exp= '" + "aa" + "'  where id = '" + fruit.getId() + "' ";
                    ps = cnx.prepareStatement(yy);

                    ps.execute();
                    grid.getChildren().clear();
                    fruits.clear();
                    try {
                        fruits.addAll(getData());
                        if (fruits.size() > 0) {
                            setChosenFruit(fruits.get(0));

                            myListener = this::setChosenFruit;

                        }
                        int column = 0;
                        int row = 1;
                        try {
                            for (int i = 0; i < fruits.size(); i++) {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("../../gui/Categorie_Client/item.fxml"));
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
                    } catch (SQLException ex) {
                        Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //showMessageDialog(null, "categorie modifier avec succes");
                } catch (SQLException ex) {
                    Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("aa");
            }
        }

        if ("false".equals(fruit.getPromotion())) {
            promotions.setVisible(false);
            date.setVisible(false);

        } else {

            promotions.setVisible(true);
            date.setVisible(true);
            promotions.setText("En promotion" + " " + fruit.getNumberpromotion() + "%");
            date.setText("jusqu'a" + " " + fruit.getDate());

        }
        fruitNameLable.setText(fruit.getNom_produit());
        fruitPriceLabel.setText(fruit.getPrix() + main.CURRENCY);
        quantite.setText(String.valueOf(fruit.getQuantite()));
        String path;
        txtimage.setText(fruit.getImage());
        //  txtarea.setTextAlignment(TextAlignment.valueOf(fruit.getDescription()));
        txtarea.getChildren().clear();
        Text t1 = new Text("Description : " + fruit.getDescription());
        txtarea.getChildren().add(t1);

        idproduit.setText(String.valueOf(fruit.getId()));
        txtcat.setText(String.valueOf(fruit.getCat()));
        nomproduit.setText(fruit.getNom_produit());
        txtdes.setText(fruit.getDescription());
        prix.setText(String.valueOf(fruit.getPrix()));
        try {
            System.out.println("nombre avis" + countClientnoter(fruit.getId()));
            lesnoter.setText(countClientnoter(fruit.getId()) + " " + "Avis Clients vérifiés");
        } catch (SQLException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            countnoter(fruit.getId());
            float x = countnoter(fruit.getId());
            float xx = x / 5;
            System.out.println(xx);
            countnote.setText(String.valueOf(xx) + "/5");
        } catch (SQLException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // verifiernoter(fruit.getId());
            System.out.println(verifiernoter(fruit.getId()));

            if ("deja existe".equals(verifiernoter(fruit.getId()))) {
                noter1.setVisible(false);
                noter2.setVisible(false);
                noter3.setVisible(false);
                noter4.setVisible(false);
                noter5.setVisible(false);
                horsstock1.setText("deja noter");
                horsstock1.setDisable(true);

            } else {
                noter1.setVisible(true);
                noter2.setVisible(true);
                noter3.setVisible(true);
                noter4.setVisible(true);
                noter5.setVisible(true);
                horsstock1.setText("noter produit");
                horsstock1.setDisable(false);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  System.out.println(quantite.getText());
        if (quantite.getText().equals("0")) {
            horsstock.setVisible(true);

            enstock.setVisible(false);
            addcart.setVisible(false);
            showstock.setVisible(false);
            //    ajouterpanierr.setVisible(false);
        } else {
            enstock.setVisible(true);

            addcart.setVisible(true);
            showstock.setVisible(true);
            showstock.setText("il reste " + quantite.getText() + " produits");
            horsstock.setVisible(false);

            //   ajouterpanierr.setVisible(true);
        }

        //   this.img.setImage(image);
        path = fruit.getImage();
        Image aa = new Image("file:" + path);
        // System.out.println(image);
        fruitImg.setImage(aa);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n"
                + "    -fx-background-radius: 30;");
    }

    public Integer countClientnoter(Integer produit) throws SQLException, JSONException, IOException {
        Integer a = null;

        int x = 0;
        Statement stmt = cnx.createStatement();
        String query = "select count(*) from rate where id_produit='" + produit + "'";

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        a = rs.getInt(1);
        
 
        return a;
       

    }

    public Integer countnoter(Integer produit) throws SQLException, JSONException, IOException {
        Integer a = null;
       
    
        int x = 0;
        Statement stmt = cnx.createStatement();
        String query = "select SUM(noter) from rate where id_produit='" + produit + "'";

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        a = rs.getInt(1);
        return a;

      

    }

    public String verifiernoter(Integer id_produit) throws SQLException, JSONException, IOException {
        String x = "";
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        
        if(aaa.equals("root")){
            
        }else{
             String id = new Jwt(aaa).getAudience();
        PreparedStatement ps, ps2;
        ResultSet rs;
        String query = "select * from rate WHERE id_user = ? and id_produit = ?";
        ps = cnx.prepareStatement(query);
        ps.setString(1, id);
        ps.setInt(2, id_produit);

        rs = ps.executeQuery();

        if (rs.next()) {
            return x = "deja existe";
        }
        return x;
        }
        return x;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // noter1.setText(String.valueOf(10));

        String xxxx = "SELECT * FROM `categories`";

        Statement aa;
        try {
            aa = cnx.createStatement();
            ResultSet queryoutput = aa.executeQuery(xxxx);
            while (queryoutput.next()) {
                String x = queryoutput.getString("name");

                dataList.add(new Categories(x));
                combocat.setItems(dataList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // System.out.println("aaaaaaaaaaaaaaa"+quantite.getText());
        try {
            fruits.addAll(getData());
            if (fruits.size() > 0) {
                setChosenFruit(fruits.get(0));

                myListener = this::setChosenFruit;

            }
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < fruits.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../../gui/Categorie_Client/item.fxml"));
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
        } catch (SQLException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        
        try {
            totalpanier.setText(String.valueOf(refreshpanier()));
            System.out.println(refreshpanier());
        } catch (SQLException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Categorie_ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        ajouterpanierr.setVisible(false);
//        showcombocat();
        LocalDate currentdDate1 = LocalDate.now();

        PreparedStatement ps, ps2;
        ResultSet rs;
        String query = "select * from users WHERE id = ? ";

        try {
            ps = cnx.prepareStatement(query);
            ps.setInt(1, Statics.current_user.getId());

            rs = ps.executeQuery();

            if (rs.next()) {
                String s1 = rs.getString("date_supprimer_compte");
                if (s1 == null) {

                } else if ("null".equals(s1)) {

                } else if (s1.equals(currentdDate1.toString())) {

                    PreparedStatement psxx;
                    ResultSet rsxx;

                    String yy = "delete   from  users where id = '" + Statics.current_user.getId() + "' ";
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
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

        FadeTransition xx = new FadeTransition();
        xx.setNode(promotions);
        xx.setDuration(Duration.millis(1500));
        xx.setCycleCount(TranslateTransition.INDEFINITE);
        xx.setInterpolator(Interpolator.LINEAR);
        xx.setFromValue(0);
        xx.setToValue(1);
        xx.play();

        FadeTransition fade = new FadeTransition();
        fade.setNode(logo);
        fade.setDuration(Duration.millis(3000));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

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

//        if (Statics.current_user.getNom() == null) {
//            labelnom.setVisible(false);
//            btnSignout.setVisible(false);
//            btnprofile.setVisible(false);
//            historique.setVisible(false);
//            quiz.setVisible(false);
//            avis.setVisible(false);
//            avishbox.setVisible(false);
//            profilehbox.setVisible(false);
//            histbox.setVisible(false);
//            quizhbox.setVisible(false);
//            adresshbox.setVisible(false);
//
//            btnadresse.setVisible(false);
//        } else {
//
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
//        }
    }

//    public Integer showtotalepanier() throws SQLException {
//        int count = 0;
//        Statement stmt = cnx.createStatement();
//        String x = String.valueOf(Statics.current_user.getId());
//        String query = "select count(*) from panier where  id_user ='" + x + "' ";
//        ResultSet rs = stmt.executeQuery(query);
//        rs.next();
//        count = rs.getInt(1);
//        return count;
//
//    }
    public Integer refreshpanier() throws SQLException, JSONException, IOException {
        Integer a = 0;
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

    public ObservableList<Produit> rechercherCat(Integer nom) {

        String requete = "select * from produits where categorie=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setInt(1, nom);
            ResultSet resultat = ps.executeQuery();
            while (resultat.next()) {
                Produit cat = new Produit();
                Integer id = resultat.getInt("id");
                String des = resultat.getString("Description");
                InterCategorie deptdao = daocategorie.getInstance();
                Categories nomcat = deptdao.findcatById(resultat.getInt("categorie"));
                String image = resultat.getString("image");
                String nomproduit = resultat.getString("nom");
                Integer prix = resultat.getInt("prix");
                Integer quantiter = resultat.getInt("quantite");
                Produitlist.add(new Produit(id, quantiter, des, nomproduit, prix, image, nomcat));

                //System.out.println(Produitlist);
            }
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colnom.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
            colimage.setCellValueFactory(new PropertyValueFactory<>("image"));

            colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            coldescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
            colcategories.setCellValueFactory(new PropertyValueFactory<>("cat"));
            colquantiter.setCellValueFactory(new PropertyValueFactory<>("quantite"));

            tableproduit.setItems(Produitlist);
            return Produitlist;

        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la recherche du depot " + ex.getMessage());
            return null;
        }

    }

    public void showcombocat() {

        String tt = "SELECT * FROM `categories`";

        Statement statement;
        try {
            statement = cnx.createStatement();
            ResultSet queryoutput = statement.executeQuery(tt);
            while (queryoutput.next()) {
                String x = queryoutput.getString("name");

                dataList.add(new Categories(x));
                combocategorie.setItems(dataList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    private void panier(ActionEvent event) throws IOException {
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
    private void totalpanier(ActionEvent event) {
    }

    private List<Produit> getbynom(String nom) throws SQLException {
        fruits.removeAll(fruits);

        List<Produit> xx = new ArrayList<>();
        //  fruits.removeAll(fruits);

        Produit fruit;
        String tt = "SELECT * FROM `produits` where categorie='" + nom + "'";

        Statement statement;

        statement = cnx.createStatement();
        ResultSet queryoutput = statement.executeQuery(tt);
        while (queryoutput.next()) {

            fruit = new Produit();
            fruit.setNom_produit(queryoutput.getString("nom"));
            fruit.setPrix(Integer.parseInt(queryoutput.getString("prix")));
            fruit.setImage(queryoutput.getString("image"));
            fruit.setColor("B0E0E6");
            fruit.setNumberpromotion(queryoutput.getString("numberpromotion"));
            fruit.setQuantite(Integer.parseInt(queryoutput.getString("quantite")));
            fruit.setDescription(queryoutput.getString("Description"));
            fruit.setId(Integer.parseInt(queryoutput.getString("id")));
            fruit.setImage(queryoutput.getString("image"));
            fruit.setPromotion(queryoutput.getString("promotion"));
            InterCategorie deptdao = daocategorie.getInstance();
            fruit.setCat(deptdao.findcatById(queryoutput.getInt("categorie")));
            fruit.setDate(queryoutput.getString("date_exp"));
            fruit.setPrixold(queryoutput.getInt("prixold"));
            fruits.add(fruit);

            xx.add(fruit);

        }

        return xx;
    }

    public void delete() {

    }

    @FXML
    private void rechercher(ActionEvent event) throws SQLException {

        if (combocat.getSelectionModel().isSelected(-1)) {

            Notification.notificationError("sasie votre categorie");
            combocat.requestFocus();

        } else {

            PreparedStatement ps, cat;
            ResultSet rs, rs2;
            String s = combocat.getSelectionModel().getSelectedItem().toString();
            String yy = "SELECT * FROM categories WHERE name ='" + s + "'";
            ps = cnx.prepareStatement(yy);

            rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");

                //   getrecherchebycat(id);
                try {
                    fruits.removeAll(fruits);
                    grid.getChildren().clear();
                    xx.clear();

                    xx.addAll(getbynom(id));
                    if (xx.size() > 0) {
                        setChosenFruit(fruits.get(0));

                        myListener = this::setChosenFruit;

                    }
                    int column = 0;
                    int row = 1;

                    try {
                        for (int i = 0; i < xx.size(); i++) {

                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("../../gui/Categorie_Client/item.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();

                            ItemController itemController = fxmlLoader.getController();
                            itemController.setData(xx.get(i), myListener);

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
                }
            }
        }
////        Produitlist.clear();
////        if (this.isValidated()) {
////
////            String s = combocategorie.getSelectionModel().getSelectedItem().toString();
////            PreparedStatement ps, ps2;
////            ResultSet rs;
////            String query = "select * from categories WHERE name = ? ";
////
////            ps = cnx.prepareStatement(query);
////            ps.setString(1, s);
////
////            rs = ps.executeQuery();
////
////            if (rs.next()) {
////                Integer s1 = rs.getInt("id");
////
////                Produit cat = new Produit();
////
////                rechercherCat(s1);
////                this.cat.setText(s);
////            }
    }

    private boolean isValidated() {
//        String s = categorie.getSelectionModel().getSelectedItem().toString();
        String number = "[0-9]+";
        Pattern x = Pattern.compile(number);

        if (combocategorie.getSelectionModel().isSelected(-1)) {
            showMessageDialog(null, "categorie  must be selected to search");
            combocategorie.requestFocus();
        } else {
            return true;
        }
        return false;
    }

//    private void getselected(MouseEvent event) {
//        index = tableproduit.getSelectionModel().getSelectedIndex();
//        if (index <= -1) {
//            return;
//        }
//
//        x.setVisible(true);
//        y.setVisible(true);
//        z.setVisible(true);
//        nomproduit.setText(String.valueOf(colnom.getCellData(index)));
//        prixproduit.setText(String.valueOf(colprix.getCellData(index)));
//        description.setText(String.valueOf(coldescription.getCellData(index)));
//        txtimage.setText(colimage.getCellData(index));
//        idproduit.setText(String.valueOf(colid.getCellData(index)));
//        quantite.setText(String.valueOf(colquantiter.getCellData(index)));
//        txtcat.setText(String.valueOf(colcategories.getCellData(index)));
//
//        if (quantite.getText().equals("0")) {
//            horsstock.setVisible(true);
//            enstock.setVisible(false);
//            ajouterpanierr.setVisible(false);
//        } else {
//            showstock.setText("il reste " + String.valueOf(colquantiter.getCellData(index)) + " produits");
//            horsstock.setVisible(false);
//            enstock.setVisible(true);
//            ajouterpanierr.setVisible(true);
//
//        }
//        String path = colimage.getCellData(index);
//        Image image = new Image("file:" + path);
//        this.imgproduit.setImage(image);
//
//    }
    @FXML
    private void ajouterpanier(ActionEvent event) throws IOException, SQLException {

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
            String id = new Jwt(aaa).getAudience();
            PreparedStatement ps, psx;
            ResultSet rs, rsx;
            String id_user = String.valueOf(Statics.current_user.getId());
            String produit = nomproduit.getText();
            String desc = txtdes.getText();
            String prix = this.prix.getText();
            String image = txtimage.getText();
            String id_produit = idproduit.getText();
            Integer quantiter = 1;
            String categorie = txtcat.getText();
            //  String xx = id.getText();
            // String yy = "delete   from  categories where name = '" + nom + "' ";
            String yy = "INSERT INTO panier(nom_produit,image,prix,description,categorie,quantite,id_user,id_produit,newprix) VALUES ( ?,?,?,?,?,?,?,?,?)";

            // String req = "INSERT INTO `categories`(`name`) VALUES ( ?)";
            String yyy = "SELECT * FROM panier WHERE id_user ='" + id + "'  and  nom_produit='" + produit + "'  ";
            psx = cnx.prepareStatement(yyy);

            rsx = psx.executeQuery();
            if (rsx.next()) {

           
                Notification.notificationError("produit déja ajouter au panier");
            } else {

                ps = cnx.prepareStatement(yy);
                ps.setString(1, produit);
                ps.setString(2, image);
                ps.setString(3, prix);
                ps.setString(4, desc);
                ps.setString(5, categorie);
                ps.setInt(6, quantiter);
                ps.setString(7, id);
                ps.setString(8, id_produit);
                ps.setString(9, prix);
                ps.execute();

                PreparedStatement ps4, ps5;
                ResultSet rs5;

                String xxx = "update produits set quantite =quantite-? where id =? ";

                ps5 = cnx.prepareStatement(xxx);
                ps5.setInt(1, 1);

                ps5.setString(2, id_produit);
                System.out.println(idproduit.getText());
                ps5.execute();

                showMessageDialog(null, "produit  ajouter avec succes au panier");
                Notification.notificationSuccess("ajouter produit", "produit  ajouter avec succés au panier");
                try {
                    totalpanier.setText(String.valueOf(refreshpanier()));
                    System.out.println(refreshpanier());
                } catch (SQLException ex) {
                    Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                }
//                Stage stage = (Stage) register.getScene().getWindow();
//                stage.close();
//
//                Parent root = FXMLLoader.load(getClass().getResource("../../gui/Categorie_Client/Categorie_Client.fxml"));
//
//                Scene scene = new Scene(root);
//                stage.setMaxHeight(1000);
//                stage.setMaxWidth(1500);
//                stage.setScene(scene);
//                stage.setResizable(true);
//
//                stage.setTitle("Login");
//                //
//                stage.show();
            }
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

    private List<Produit> getprix(Integer prixmin, Integer prixmax) throws SQLException {
        fruits.removeAll(fruits);

        List<Produit> xx = new ArrayList<>();
        //  fruits.removeAll(fruits);

        Produit fruit;
        String tt = "SELECT * FROM `produits` where  prix  BETWEEN '" + prixmin + "' AND '" + prixmax + "'";

        Statement statement;

        statement = cnx.createStatement();
        ResultSet queryoutput = statement.executeQuery(tt);
        while (queryoutput.next()) {
            fruit = new Produit();
           fruit.setNom_produit(queryoutput.getString("nom"));
            fruit.setPrix(Integer.parseInt(queryoutput.getString("prix")));
            fruit.setImage(queryoutput.getString("image"));
            fruit.setColor("B0E0E6");
            fruit.setNumberpromotion(queryoutput.getString("numberpromotion"));
            fruit.setQuantite(Integer.parseInt(queryoutput.getString("quantite")));
            fruit.setDescription(queryoutput.getString("Description"));
            fruit.setId(Integer.parseInt(queryoutput.getString("id")));
            fruit.setImage(queryoutput.getString("image"));
            fruit.setPromotion(queryoutput.getString("promotion"));
            InterCategorie deptdao = daocategorie.getInstance();
            fruit.setCat(deptdao.findcatById(queryoutput.getInt("categorie")));
            fruit.setDate(queryoutput.getString("date_exp"));
            fruit.setPrixold(queryoutput.getInt("prixold"));

            xx.add(fruit);

        }

        return xx;
    }

    @FXML
    private void rechercherprix(ActionEvent event) throws SQLException, SQLException {
        if (this.valid()) {
            Integer a = Integer.valueOf(prixmax.getText());
            Integer b = Integer.valueOf(prixmin.getText());
            grid.getChildren().clear();

            PreparedStatement ps, cat;
            ResultSet rs, rs2;

            //   getrecherchebycat(id);
            fruits.removeAll(fruits);

            fruits.clear();
            xx.clear();

            xx.addAll(getprix(b, a));
            if (xx.size() > 0) {
                setChosenFruit(xx.get(0));

                myListener = this::setChosenFruit;

            }
            int column = 0;
            int row = 1;

            try {
                for (int i = 0; i < xx.size(); i++) {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../../gui/Categorie_Client/item.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(xx.get(i), myListener);

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

        }

    }

    private boolean valid() {
//        String s = categorie.getSelectionModel().getSelectedItem().toString();
        String number = "[0-9]+";
        Pattern x = Pattern.compile(number);

//        Integer xx = Integer.valueOf(prixmax.getText());
//        Integer yy = Integer.valueOf(prixmin.getText());
        if (prixmax.getText().equals("")) {

            showMessageDialog(null, "prix max  text field cannot be blank.");
            prixmax.requestFocus();
        } else if (prixmin.getText().equals("")) {
            showMessageDialog(null, "prix min text field cannot be blank.");
            prixmin.requestFocus();
        } else if (!x.matcher(prixmax.getText()).matches()) {
            showMessageDialog(null, "prix max contains only number.");
            prixmax.requestFocus();
        } else if (!x.matcher(prixmin.getText()).matches()) {
            showMessageDialog(null, "prix min max contains only number.");
            prixmin.requestFocus();

        } else if (Integer.valueOf(prixmin.getText()) > Integer.valueOf(prixmax.getText())) {
            System.out.println(Integer.valueOf(prixmax.getText()));
            System.out.println(Integer.valueOf(prixmin.getText()));
            showMessageDialog(null, "le prix maximum doit dépasser le prix minimum. ");
            prixmin.requestFocus();

        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void noterproduit(ActionEvent event) {
    }

    @FXML
    private void noter(ActionEvent event) throws JSONException, IOException, SQLException {

        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        if (aaa.equals("root")){
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
        }else{
            
       
        
        
        if (noter1.isSelected() || noter2.isSelected() || noter3.isSelected() || noter4.isSelected() || noter5.isSelected()) {
            String id = new Jwt(aaa).getAudience();
            PreparedStatement ps, psx;
            ResultSet rs, rsx;
            String id_user = String.valueOf(Statics.current_user.getId());

            String id_produit = idproduit.getText();

            //  String xx = id.getText();
            // String yy = "delete   from  categories where name = '" + nom + "' ";
            String yy = "INSERT INTO rate(id_user,id_produit,noter) VALUES ( ?,?,?)";

            // String req = "INSERT INTO `categories`(`name`) VALUES ( ?)";
            ps = cnx.prepareStatement(yy);
            ps.setString(1, id);
            ps.setString(2, id_produit);
            if (noter1.isSelected()) {
                ps.setString(3, noter1.getText());
            } else if (noter2.isSelected()) {
                ps.setString(3, noter2.getText());
            } else if (noter3.isSelected()) {
                ps.setString(3, noter3.getText());
            } else if (noter4.isSelected()) {
                ps.setString(3, noter4.getText());
            } else if (noter5.isSelected()) {
                ps.setString(3, noter5.getText());
            }

            ps.execute();
           
            Notification.notificationSuccess("noter produit", "noter produit avec succés");

            fruits.clear();
            grid.getChildren().clear();
            fruits.addAll(getData());
            if (fruits.size() > 0) {
                setChosenFruit(fruits.get(0));

                myListener = this::setChosenFruit;

            }
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < fruits.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../../gui/Categorie_Client/item.fxml"));
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
          
            Notification.notificationError("please noter le produit");
        }

    }
 }
}
