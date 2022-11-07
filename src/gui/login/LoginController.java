/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.login;

import static gui.register.RegisterController.getHash;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import util.MaConnexion;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import static javax.swing.JOptionPane.showMessageDialog;
import model.user;
import java.util.*;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.json.JSONException;
import org.json.JSONObject;
import util.Jwt;
import util.Notification;
import util.Statics;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class LoginController implements Initializable {

    Window window;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Button loginButton1;
    Connection cnx = MaConnexion.getInstance().getCnx();
    private ComboBox<user> combox;
    private InputStream input;
    @FXML
    private Button passoublier;
    @FXML
    private ImageView img;
    @FXML
    private Label labelshow;
    @FXML
    private CheckBox remember;
    Preferences preference;
    boolean rememberPreference;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//  RotateTransition rotate = new RotateTransition();
//  rotate.setNode(img);
//  rotate.setDuration(Duration.millis(500));
//  rotate.setCycleCount(TranslateTransition.INDEFINITE);
//  rotate.setInterpolator(Interpolator.LINEAR);
//  rotate.setByAngle(360);
//  rotate.setAxis(Rotate.Z_AXIS);
//  rotate.play();

        FadeTransition fade = new FadeTransition();
        fade.setNode(img);
        fade.setDuration(Duration.millis(3000));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        remember.setSelected(true);
        preference = Preferences.userNodeForPackage(LoginController.class);
        if (preference != null) {
            if (preference.get("username", null) != null && !preference.get("username", null).isEmpty()) {
                username.setText(preference.get("username", null));
                password.setText(preference.get("password", null));
            }
        }

    }

//    public void rememberMe() {
//
//        // Put the boolean of the rememberMe preference
//        rememberPreference = preference.getBoolean("rememberMe", Boolean.valueOf(""));
//        // Check if the check box was selected
//        if (rememberPreference) {
//            // Replace the textField by the preference User and Password who will be stock
//            username.setText(preference.get("user", ""));
//            password.setText(preference.get("password", ""));
//            remember.setSelected(rememberPreference);
//        }
//    }
    @FXML
    private void login(ActionEvent event) throws IOException, JSONException, InvalidKeyException {
        String pass = password.getText();
        if (this.isValidated()) {
            PreparedStatement ps, ps2;
            ResultSet rs;
            String query = "select * from users WHERE nom = ? and mot_de_passe = ?";
            try {
                ps = cnx.prepareStatement(query);
                ps.setString(1, username.getText());
                ps.setString(2, getHash(pass.getBytes(), "SHA-1"));

                rs = ps.executeQuery();

                if (rs.next()) {

                    if (remember.isSelected()) {
                        preference.put("username", username.getText());
                        preference.put("password", pass);

                    } else {
                        preference.put("username", "");
                        preference.put("password", "");
                    }
                    Stage xx = (Stage) remember.getScene().getWindow();
                    Parent aaa = null;
                    String s1 = rs.getString("role");
                    String s2 = rs.getString("image");
                    String s3 = rs.getString("banier");
                    if (s1.equalsIgnoreCase("Admin")) {
                        Notification.notificationSuccess("Bienvenue", rs.getString("nom"));
                        JSONObject payload = new JSONObject();

                        payload.put("sub", "Admin");
                        payload.put("aud", rs.getString("id"));
                        payload.put("exp", 10);

                        String aa = new Jwt(payload).toString();
                        Preferences userPreferences = Preferences.userRoot();

                        userPreferences.put("jwt", aa);
                        Statics.current_user.setNom(rs.getString("nom"));
                        if (s2 == null) {

                        } else {
                            String x = rs.getString("image");

                            Statics.current_user.setImage(x);

                        }

                        //   Statics.current_user.setImage(rs.getString("image"));
                        Stage stage = (Stage) password.getScene().getWindow();
                        stage.close();

                        Parent root = FXMLLoader.load(getClass().getResource("../../gui/homeAdmin/homeadmin.fxml"));

                        Scene scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        stage.setMaxHeight(576);
                        stage.setMaxWidth(1050);

                        stage.setTitle("Admin Panel");
                        stage.getIcons().add(new Image("/img/mm.png"));
                        stage.show();
                        showMessageDialog(null, "Login successfuly");

                    } else if (s1.equalsIgnoreCase("Client") ) {
                        if (s3.equalsIgnoreCase("false")){
                              Notification.notificationSuccess("Bienvenue", rs.getString("nom"));
                        JSONObject payload = new JSONObject();

                        payload.put("sub", rs.getString("role"));
                        payload.put("aud", rs.getString("id"));
                        payload.put("exp", 10);

                        String aa = new Jwt(payload).toString();
                        Preferences userPreferences = Preferences.userRoot();

                        userPreferences.put("jwt", aa);

//                        Statics.current_user.setNom(rs.getString("nom"));
//                        Statics.current_user.setId(rs.getInt("id"));
//                        Statics.current_user.setEmail(rs.getString("email"));
//                        Statics.current_user.setCin(rs.getInt("cin"));
//                        Statics.current_user.setPrenom(rs.getString("prenom"));
//                        Statics.current_user.setMot_de_passe(rs.getString("mot_de_passe"));
//                        Statics.current_user.setRole(rs.getString("role"));
//                        Statics.current_user.setImage(rs.getString("image"));
                        Stage stage = (Stage) password.getScene().getWindow();
                        stage.close();

                        Parent root = FXMLLoader.load(getClass().getResource("../../gui/homeClient/accueilClient.fxml"));

                        Scene scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.centerOnScreen();
                        stage.setMaxHeight(900);
                        stage.setMaxWidth(1600);
                        stage.setScene(scene);
                        stage.setTitle("Accueil");
                        stage.getIcons().add(new Image("/img/mm.png"));
                        stage.show();

                        }else{
                            Notification.notificationError("vous avez banier ");
                        }
                      
                    }

                } else {

                  
                     Notification.notificationError("Invalid username and password.");
                    username.requestFocus();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/register/Register.fxml"));

        Scene scene = new Scene(root);
        stage.setMaxHeight(582);
        stage.setMaxWidth(612);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("User Registration");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();
    }

    private boolean isValidated() {

        window = loginButton.getScene().getWindow();
        if (username.getText().equals("")) {

            showMessageDialog(null, "Username text field cannot be blank.");
            username.requestFocus();

        } else if (password.getText().equals("")) {

            showMessageDialog(null, "Password text field cannot be blank.");
            password.requestFocus();
//        }else if (IsEmpty(combox.SelectedItems)){
//
//            showMessageDialog(null, "erreur");
//            combox.requestFocus();

        } else {
            return true;
        }
        return false;
    }

//    public void init() {
//
//        ObservableList<user> mList = FXCollections.observableArrayList(this.afficherRole());
//        combox.setItems(mList);
//        
//        
//    }
    public List<user> afficherRole() {
        List<user> users = new ArrayList<>();
        String req = "SELECT * FROM roles";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user p = new user();
                p.setNom(rs.getString("nom"));

                users.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("erreur");
        }
        return users;
    }

    @FXML
    private void passoublier(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("mot_de_passe_oublier.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("mot de passe oublier ");
        stage.getIcons().add(new Image("/img/mm.png"));
        stage.show();

    }

    @FXML
    private void show(ActionEvent event) {
        labelshow.setText(password.getText());
    }

    @FXML
    private void add(MouseEvent event) {
    }

}
