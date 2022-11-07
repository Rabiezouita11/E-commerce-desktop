/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Paiment;

import Interface.InterCategorie;
import dao.daocategorie;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static javax.swing.JOptionPane.showMessageDialog;
import model.Adresse;
import model.Categories;
import model.Panier;
import org.json.JSONException;
import util.Jwt;
import util.MaConnexion;
import util.Notification;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-108
 */
public class PaimentController implements Initializable {

    @FXML
    private ComboBox<Adresse> adress;
    @FXML
    private TextField txtcodepostale;
    @FXML
    private TextField txtrue;
    Connection cnx = MaConnexion.getInstance().getCnx();
    private final ObservableList<Adresse> dataList = FXCollections.observableArrayList();
    @FXML
    private TextField txtnumero;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println(Statics.current_panier.getPrix());

        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");
        try {
            String id = new Jwt(aaa).getAudience();
            //  System.out.println(id);
            String tt = "SELECT * FROM adresse where id_user='" + id + "'";

            Statement statement;

            try {
                statement = cnx.createStatement();
                ResultSet queryoutput;
                queryoutput = statement.executeQuery(tt);
                while (queryoutput.next()) {
                    String x = queryoutput.getString("ville");

                    dataList.add(new Adresse(x));
                    adress.setItems(dataList);
                }

            } catch (SQLException ex) {
                Logger.getLogger(PaimentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSONException ex) {
            Logger.getLogger(PaimentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PaimentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void adresse(ActionEvent event) throws JSONException, IOException, SQLException {
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");

        String id = new Jwt(aaa).getAudience();

        String s = adress.getSelectionModel().getSelectedItem().toString();
        String tt = "SELECT * FROM adresse where id_user='" + id + "' and ville=  '" + s + "' ";

        Statement statement;
        statement = cnx.createStatement();
        ResultSet queryoutput;
        queryoutput = statement.executeQuery(tt);
        while (queryoutput.next()) {
            String x = queryoutput.getString("code_postale");
            String y = queryoutput.getString("rue");
            String z = queryoutput.getString("numero_boite_lettre");
            txtcodepostale.setText(x);
            txtrue.setText(y);
            txtnumero.setText(z);
        }
    }

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

    @FXML
    private void Paiment(ActionEvent event) throws SQLException, JSONException, IOException {
        Preferences userPreferences = Preferences.userRoot();
        String aaa = userPreferences.get("jwt", "root");

        String id = new Jwt(aaa).getAudience();

        if (adress.getSelectionModel().isSelected(-1)) {
            Notification.notificationError("sasie votre adresse");
        } else {
            String s = adress.getSelectionModel().getSelectedItem().toString();
            PreparedStatement ps, cat;
            ResultSet rs, rs2;
            String req = "INSERT INTO `commandes`(`Produit`,`total_prix`,`id_user`,`Ville`) VALUES ( ?,?,?,?)";
            ps = cnx.prepareStatement(req);

            ps.setString(1, getData().toString());
            ps.setString(2, Statics.current_panier.getPrix());
            ps.setString(3, id);
            ps.setString(4, s);

            ps.execute();
            String yy = "delete   from  panier where id_user = '" + id + "' ";
            cat = cnx.prepareStatement(yy);
            cat.execute();
            Notification.notificationSuccess("Paiment", "Paiment avec succés");
            Stage stage = (Stage) adress.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("../../gui/homeClient/accueilClient.fxml"));

            Scene scene = new Scene(root);
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.setMaxHeight(1100);
            stage.setMaxWidth(1600);
            stage.setScene(scene);
            // stage.setFullScreen(true);

            stage.setTitle("Accueil");
            //
            stage.show();

        }

    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Stage stage = (Stage) txtcodepostale.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/Panier/Panier.fxml"));

        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setMaxHeight(1100);
        stage.setMaxWidth(1600);
        stage.setScene(scene);
        stage.setResizable(true);

        stage.setTitle("Panier");
        //
        stage.show();

    }

}
