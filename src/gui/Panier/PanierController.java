/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Panier;

import Interface.InterCategorie;
import dao.daocategorie;
import ecommerce.MyPanier;
import ecommerce.main;
import gui.categorie.Categorie;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import static javax.swing.JOptionPane.showMessageDialog;
import model.Categories;
import model.Coupon;
import model.Panier;
import model.Produit;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Notification;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-006
 */
public class PanierController implements Initializable {

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
    private TableView<Panier> tableproduit;
    private TableColumn<Produit, Integer> colid;
    private TableColumn<Produit, String> colnom;
    private TableColumn<Produit, String> colimage;
    private TableColumn<Produit, Integer> colprix;
    private TableColumn<Produit, String> coldescription;
    private TableColumn<Produit, Categorie> colcategories;
    private TableColumn<Produit, Integer> colquantiter;
    @FXML
    private TextField nomproduit;
    @FXML
    private TextField quantite;
    private TextField txtimage;
    private TextField idproduit;
    @FXML
    private TextField txtcat;
    Connection cnx = MaConnexion.getInstance().getCnx();
    ObservableList<Panier> dataList = FXCollections.observableArrayList();
    ObservableList<Coupon> listcoupon = FXCollections.observableArrayList();
    @FXML
    private Spinner<Integer> number;
    int x;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    private ImageView imgproduit;
    @FXML
    private TextField txtidproduit;
    private TableColumn<Panier, Integer> colidproduit;
    @FXML
    private TextField txtid;
    private TextField txtquantite;
    private Label totalprix;
    private Label labeltotalprix;
    private Label labelcoupon;
    private ComboBox<Integer> combocoupoin;
    private Button btnpaiment;
    private Button btnajoutercoupon;
    private Label labelpaniervide;
    private Button acheterproduit;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label fruitNameLable;
    @FXML
    private Label fruitPriceLabel;
    @FXML
    private ImageView fruitImg;
    @FXML
    private TextFlow txtarea;
    private Button enstock;
    private Button horsstock;
    @FXML
    private Label showstock;
    @FXML
    private Button addcart;
    @FXML
    private TextField txtdes;
    @FXML
    private TextField prix;
    @FXML
    private GridPane grid;
    private List<Panier> fruits = new ArrayList<>();
    private MyPanier myListener;
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
    private ScrollPane scroll;
    @FXML
    private Button btnupdate1;
    @FXML
    private Button btnupdate11;
    @FXML
    private Label totaleprix;
    @FXML
    private Button makepayment;
    @FXML
    private Label showpaniervide;
    @FXML
    private Label showpaniervide1;
    @FXML
    private Button prdouit;
    @FXML
    private ComboBox<Coupon> combocoupon;
    @FXML
    private Button btncoupon;
    @FXML
    private AnchorPane hboxpanier;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    private List<Panier> getData() throws SQLException, JSONException, IOException {
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");

        String id = new Jwt(aaa).getAudience();
        List<Panier> fruits = new ArrayList<>();
        Panier fruit;
        String tt = "SELECT * FROM `panier`  where id_user ='" + id + "'";

        Statement statement;

        statement = cnx.createStatement();
        ResultSet queryoutput = statement.executeQuery(tt);
        while (queryoutput.next()) {
            fruit = new Panier();
            fruit.setNom_produit(queryoutput.getString("nom_produit"));
            fruit.setPrix(queryoutput.getString("prix"));
            fruit.setNewprix(queryoutput.getString("newprix"));
            fruit.setImage(queryoutput.getString("image"));
            fruit.setColor("B0E0E6");
            fruit.setQuantite(Integer.parseInt(queryoutput.getString("quantite")));
            fruit.setDescription(queryoutput.getString("description"));
            fruit.setId(Integer.parseInt(queryoutput.getString("id")));
            fruit.setId_produit(Integer.parseInt(queryoutput.getString("id_produit")));
            fruit.setImage(queryoutput.getString("image"));
            InterCategorie deptdao = daocategorie.getInstance();
            fruit.setCategorie(queryoutput.getString("categorie"));
            fruits.add(fruit);

        }

        return fruits;
    }

    private void setChosenFruit(Panier fruit) {
        fruitNameLable.setText(fruit.getNom_produit());
        fruitPriceLabel.setText(fruit.getNewprix() + main.CURRENCY);
        quantite.setText(String.valueOf(fruit.getNewprix()));
        String path;
//        txtimage.setText(fruit.getImage());
        //  txtarea.setTextAlignment(TextAlignment.valueOf(fruit.getDescription()));
        txtarea.getChildren().clear();
        Text t1 = new Text("Description : " + fruit.getDescription());
        txtarea.getChildren().add(t1);

        txtidproduit.setText(String.valueOf(fruit.getId_produit()));
//        idproduit.setText(String.valueOf(fruit.getId()));
        txtid.setText(String.valueOf(fruit.getId()));
        txtcat.setText(String.valueOf(fruit.getCategorie()));
        nomproduit.setText(fruit.getNom_produit());
        txtdes.setText(fruit.getDescription());
        prix.setText(String.valueOf(fruit.getPrix()));
        //  System.out.println(quantite.getText());
//        if (quantite.getText().equals("0")) {
//            horsstock.setVisible(true);
//
//            enstock.setVisible(false);
//            addcart.setVisible(false);
//            showstock.setVisible(false);
//            //    ajouterpanierr.setVisible(false);
//        } else {
//            enstock.setVisible(true);
//
//            addcart.setVisible(true);
//            showstock.setVisible(true);
//            showstock.setText("il reste " + quantite.getText() + " produits");
//            horsstock.setVisible(false);
//
//            //   ajouterpanierr.setVisible(true);
//        }
        //   this.img.setImage(image);
        path = fruit.getImage();
        Image aa = new Image("file:" + path);
        // System.out.println(image);
        fruitImg.setImage(aa);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n"
                + "    -fx-background-radius: 30;");
    }

    public Integer showcoupon() throws SQLException, JSONException, IOException {
        LocalDate currentdDate1 = LocalDate.now();
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        String aaaa = "en cours";
        String idd = new Jwt(aaa).getAudience();
        int count = 0;
        Statement stmt = cnx.createStatement();
        String xxxx = "SELECT  count(*) FROM `coupon` where id_user='" + idd + "' and  date_expiration > '" + currentdDate1 + "' and etat='" + aaaa + "' ";
        ResultSet rs = stmt.executeQuery(xxxx);
        rs.next();
        count = rs.getInt(1);
        return count;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Notification.notificationInformation("coupon", "vous avez " + showcoupon() + " " + "coupons non utiliser");
        } catch (SQLException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }

        hboxpanier.setVisible(false);
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        try {
            String idd = new Jwt(aaa).getAudience();
            try {
                totaleprix.setText(String.valueOf(totalprix()));
                Statics.current_panier.setPrix(totalprix().toString());
                if (totalprix() == 0) {
                    makepayment.setVisible(false);
                    chosenFruitCard.setVisible(false);
                    showpaniervide.setText("votre panier et vide !!");

                    hboxpanier.setVisible(false);
                } else {
                    hboxpanier.setVisible(true);
                    btncoupon.setVisible(true);
                    LocalDate currentdDate1 = LocalDate.now();
                    // xxx= date.getValue().isBefore(currentdDate1);
                    String aaaa = "en cours";
                    String xxxx = "SELECT * FROM `coupon` where id_user='" + idd + "' and  date_expiration > '" + currentdDate1 + "' and etat='" + aaaa + "' ";

                    Statement statementa;

                    try {
                        statementa = cnx.createStatement();
                        ResultSet queryoutput = statementa.executeQuery(xxxx);
                        while (queryoutput.next()) {
                            Integer id = queryoutput.getInt("id");

                            String date_expiration = queryoutput.getString("date_expiration");
                            Integer prix = queryoutput.getInt("prix");

                            listcoupon.add(new Coupon(id, date_expiration, prix));
                            combocoupon.setItems(listcoupon);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    makepayment.setVisible(true);
                    prdouit.setVisible(false);
                }

            } catch (SQLException ex) {
                Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSONException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addcart.setVisible(false);

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
                        fxmlLoader.setLocation(getClass().getResource("../../gui/Panier/item.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();

                        xx aa = fxmlLoader.getController();
                        aa.setData(fruits.get(i), myListener);

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

            }

        } catch (SQLException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //   System.out.println("aaaa" + txtid.getText());
        if (txtid.getText().equals("")) {

        }
//        
//        try {
//            totalprix.setText(String.valueOf(totalprix()));
//        } catch (SQLException ex) {
//            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            totalpanier.setText(String.valueOf(refreshpanier()));
            System.out.println(refreshpanier());
        } catch (SQLException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        valueFactory.setValue(1);
        number.setValueFactory(valueFactory);

        // btnupdate.setVisible(false);
        //  btndelete.setVisible(false);
        // number.setVisible(false);
//        if (Statics.current_user.getNom() == null) {
//            labelnom.setVisible(false);
//            btnSignout.setVisible(false);
//            btnprofile.setVisible(false);
//            historique.setVisible(false);
//            quiz.setVisible(false);
//            avis.setVisible(false);
//            panier.setVisible(false);
//        } else {
////            panier.setVisible(true);
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
                    labelnom.setText("welcome" + x);

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

    public List<Panier> refreshtableproduit() throws SQLException {

        List<Panier> fruits = new ArrayList<>();
        Panier fruit;
        String tt = "SELECT * FROM `panier`  where id_user ='" + Statics.current_user.getId() + "'";

        Statement statement;

        statement = cnx.createStatement();
        ResultSet queryoutput = statement.executeQuery(tt);
        while (queryoutput.next()) {
            fruit = new Panier();
            fruit.setNom_produit(queryoutput.getString("nom_produit"));
            fruit.setPrix(queryoutput.getString("prix"));
            fruit.setImage(queryoutput.getString("image"));
            fruit.setColor("B0E0E6");
            fruit.setQuantite(Integer.parseInt(queryoutput.getString("quantite")));
            fruit.setDescription(queryoutput.getString("description"));
            fruit.setId(Integer.parseInt(queryoutput.getString("id")));
            fruit.setId_produit(Integer.parseInt(queryoutput.getString("id_produit")));
            fruit.setImage(queryoutput.getString("image"));
            InterCategorie deptdao = daocategorie.getInstance();
            fruit.setCategorie(queryoutput.getString("categorie"));
            fruits.add(fruit);

        }

        return fruits;

    }

    public Integer refreshpanier() throws SQLException, JSONException, IOException {

        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");

        String id = new Jwt(aaa).getAudience();
        int x = 0;
        Statement stmt = cnx.createStatement();
        String query = "select SUM(quantite) from panier where id_user='" + id + "'";

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        x = rs.getInt(1);
        return x;

    }

    public Integer totalprix() throws SQLException, JSONException, IOException {
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");

        String id = new Jwt(aaa).getAudience();
        int z = 0;
        Statement stmt = cnx.createStatement();
        String query = "select SUM(prix) from panier where id_user='" + id + "'";

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        z = rs.getInt(1);
        return z;

    }

    public Integer showtotalepanier() throws SQLException {
        int count = 0;
        Statement stmt = cnx.createStatement();
        String x = String.valueOf(Statics.current_user.getId());
        String query = "select count(*) from panier where  id_user ='" + x + "' ";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        count = rs.getInt(1);
        return count;

    }

    @FXML
    private void categorie(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Categorie_Client/Categorie_Client.fxml"));

        Scene scene = new Scene(root);
        stage.setMaxHeight(1000);
        stage.setMaxWidth(1500);
        stage.setScene(scene);
        stage.setResizable(true);

        stage.setTitle("Login");
        //
        stage.show();
    }

    private void getselected(MouseEvent event) {
        index = tableproduit.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtquantite.setText(String.valueOf(colquantiter.getCellData(index)));
        txtimage.setText(colimage.getCellData(index));
        txtid.setText(String.valueOf(colid.getCellData(index)));

        quantite.setText(String.valueOf(colprix.getCellData(index)));
        //  System.out.println(colquantiter.getCellData(index));
        txtidproduit.setText(String.valueOf(colidproduit.getCellData(index)));
        if (!txtid.getText().equals("")) {
            btnupdate.setVisible(true);
            btndelete.setVisible(true);
            number.setVisible(true);
        } else {

        }
        String path = colimage.getCellData(index);
        Image image = new Image("file:" + path);
        this.imgproduit.setImage(image);

    }

    @FXML
    private void updateproduit(ActionEvent event) throws SQLException, JSONException, IOException {
        PreparedStatement ps, ps2, ps3, ps4;
        ResultSet rs, rs2 = null;

        String y = "select * from panier WHERE id = ?";
        ps4 = cnx.prepareStatement(y);
        ps4.setString(1, txtid.getText());
        rs2 = ps4.executeQuery();
        while (rs2.next()) {
            Integer s1 = rs2.getInt("quantite");
            Integer x = number.getValue() + s1;
            Integer yy = number.getValue();
            String query = "select * from produits WHERE id = ?";

            ps = cnx.prepareStatement(query);
            ps.setString(1, txtidproduit.getText());

            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("quantite") >= yy) {
                    System.out.println("update succes");
                    String xx = "update produits set quantite = quantite - ? where id =? ";
                    ps2 = cnx.prepareStatement(xx);
                    ps2.setInt(1, yy);
                    ps2.setString(2, txtidproduit.getText());
                    ps2.execute();
                    String xxx = "update panier set quantite =?  , prix=? * ?  where id =? and id_user =?";
                    ps3 = cnx.prepareStatement(xxx);
                    ps3.setInt(1, x);
                    ps3.setInt(2, x);
                    ps3.setString(3, quantite.getText());
                    ps3.setString(4, txtid.getText());
                    Preferences userPreferences = Preferences.userRoot();
                    String aaa = userPreferences.get("jwt", "root");

                    String id = new Jwt(aaa).getAudience();
                    ps3.setString(5, id);
                    ps3.execute();
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
                                    fxmlLoader.setLocation(getClass().getResource("../../gui/Panier/item.fxml"));
                                    AnchorPane anchorPane = fxmlLoader.load();

                                    xx aa = fxmlLoader.getController();
                                    aa.setData(fruits.get(i), myListener);

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

                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    totalpanier.setText(String.valueOf(refreshpanier()));
                    totaleprix.setText(String.valueOf(totalprix()));
                    try {
                        System.out.println(totalprix());
                    } catch (SQLException ex) {
                        Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    try {
//                        totalprix.setText(String.valueOf(totalprix()));
//                    } catch (SQLException ex) {
//                        Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    refreshtableproduit();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Produit");

                    alert.setContentText("produit hors stock ");
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        System.out.println("produit hors stock");
                    }

                }
            }

        }

//        PreparedStatement ps;
//        ResultSet rs;
//        String nom = nomcategorie.getText();
//        String xx = id.getText();
//        String yy = "update   categories set  name ='" + nom + "' where id = '" + xx + "' ";
//        ps = cnx.prepareStatement(yy);
//        ps.execute();
//
//        showMessageDialog(null, "categorie modifier avec succes");
    }

    @FXML
    private void deleteproduit(ActionEvent event) throws SQLException, IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(" produit");

        alert.setContentText("delete ");
        if (alert.showAndWait().get() == ButtonType.OK) {

            PreparedStatement ps2, ps3, ps4;
            ResultSet rs, rs2 = null;
            String y = "select * from panier WHERE id = ?";

            ps4 = cnx.prepareStatement(y);
            ps4.setString(1, txtid.getText());
            rs2 = ps4.executeQuery();
            while (rs2.next()) {
                Integer s1 = rs2.getInt("quantite");
                System.out.println(s1);

                String xxx = "update produits set quantite =quantite+? where id =? ";

                ps2 = cnx.prepareStatement(xxx);
                ps2.setInt(1, s1);

                ps2.setString(2, txtidproduit.getText());
                ps2.execute();

            }

            PreparedStatement ps;
            //   ResultSet rs;
            String id = txtid.getText();

            String yy = "delete   from  panier where id = '" + id + "' ";
            ps = cnx.prepareStatement(yy);
            ps.execute();
            Notification.notificationSuccess("Suppression", "produit supprimer avec sucées");
            totalpanier.setText(String.valueOf(refreshpanier()));
            totaleprix.setText(String.valueOf(totalprix()));
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
                            fxmlLoader.setLocation(getClass().getResource("../../gui/Panier/item.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();

                            xx aa = fxmlLoader.getController();
                            aa.setData(fruits.get(i), myListener);

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

                }

            } catch (SQLException ex) {
                Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
            }

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
//        
            totalpanier.setText(String.valueOf(refreshpanier()));
//            try {
//                if (refreshpanier() == 0) {
//                    btnpaiment.setVisible(false);
//                    labeltotalprix.setVisible(false);
//                    totalprix.setVisible(false);
//                    labelcoupon.setVisible(false);
//                    combocoupoin.setVisible(false);
//                    btnpaiment.setVisible(false);
//                    btnajoutercoupon.setVisible(false);
//                    labelpaniervide.setVisible(true);
//
//                    acheterproduit.setVisible(true);
//
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }

    }

    private void acheterproduits(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Categorie_Client/Categorie_Client.fxml"));

        Scene scene = new Scene(root);
        stage.setMaxHeight(1000);
        stage.setMaxWidth(1500);
        stage.setScene(scene);
        stage.setResizable(true);

        stage.setTitle("Login");
        //
        stage.show();
    }

    @FXML
    private void ajouterpanier(ActionEvent event) {
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
    private void payement(ActionEvent event) throws SQLException, JSONException, IOException {

        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Paiment/Paiment.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setMaxHeight(700);
        stage.setMaxWidth(754);

        stage.setTitle("Paiment");
        //
        stage.show();

//        String requete = "insert into commandes (Produit) values (?)";
//        try {
//            PreparedStatement ps = cnx.prepareStatement(requete);
//            ps.setString(1, getData().toString());
//            ps.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(daocategorie.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void produit(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Categorie_Client/Categorie_Client.fxml"));

        Scene scene = new Scene(root);
        stage.setMaxHeight(1000);
        stage.setMaxWidth(1500);
        stage.setScene(scene);
        stage.setResizable(true);

        stage.setTitle("Login");
        //
        stage.show();
    }

    @FXML
    private void ajoutercoupon(ActionEvent event) throws SQLException, JSONException, IOException {

        if (this.isValidated()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");

            alert.setContentText("si tu choisi coupon vous ne pouvez pas changer  la quantiter du achats ");
            if (alert.showAndWait().get() == ButtonType.OK) {

                btnupdate.setVisible(false);
                Preferences userPreferences = Preferences.userRoot();
                String aaa = userPreferences.get("jwt", "root");

                String id = new Jwt(aaa).getAudience();
                int z = 0;
                Statement stmt = cnx.createStatement();
                String query = "select SUM(prix) from panier where id_user='" + id + "'";

                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                z = rs.getInt(1);

                Integer s = Integer.parseInt(combocoupon.getSelectionModel().getSelectedItem().toString());

                PreparedStatement ps;

                String yy = "delete   from  coupon where prix= '" + s + "' ";

                ps = cnx.prepareStatement(yy);
                ps.execute();
                LocalDate currentdDate1 = LocalDate.now();
                // xxx= date.getValue().isBefore(currentdDate1);
                String aaaa = "en cours";
                String xxxx = "SELECT * FROM `coupon` where id_user='" + id + "' and  date_expiration > '" + currentdDate1 + "' and etat='" + aaaa + "' ";

                Statement statementa;
                listcoupon.clear();

                try {
                    statementa = cnx.createStatement();
                    ResultSet queryoutput = statementa.executeQuery(xxxx);
                    while (queryoutput.next()) {
                        Integer xx = queryoutput.getInt("id");

                        String date_expiration = queryoutput.getString("date_expiration");
                        Integer prix = queryoutput.getInt("prix");

                        listcoupon.add(new Coupon(xx, date_expiration, prix));
                        combocoupon.setItems(listcoupon);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(s);

                if (s > totalprix()) {

                    int aa = 0;
                    totaleprix.setText(String.valueOf(aa));
                    Statics.current_panier.setPrix(String.valueOf(aa));
                } else {
                    int x = totalprix() - Integer.valueOf(s);
                    System.out.println(x);

                    totaleprix.setText(String.valueOf(x));
                    Statics.current_panier.setPrix(String.valueOf(x));
                }

//            String yy = "update   panier set  prix ='" + nom + "' where id = '" + xx + "' ";
//            ps = cnx.prepareStatement(yy);
//            ps.execute();
                //System.out.println(z);
            }
        } else {

        }
    }

    private boolean isValidated() {
//        String s = categorie.getSelectionModel().getSelectedItem().toString();

        if (combocoupon.getSelectionModel().isSelected(-1)) {
            //showMessageDialog(null, "categorie  must be selected to search");
            Notification.notificationError("coupon  must be selected ");
            combocoupon.requestFocus();
        } else {
            return true;
        }
        return false;
    }

}
