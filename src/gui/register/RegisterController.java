/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.register;

import cn.hutool.crypto.digest.BCrypt;
import ecommerce.BienvenueEmail;
import static gui.Produit.ProduitController.randomStringforimage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.DatatypeConverter;
import util.MaConnexion;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * FXML Controller class
 *
 * @author bouden
 */
public class RegisterController implements Initializable {

    String filename = null;
    File xxx = null;

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    private PasswordField password;
    @FXML
    private Button registerButton;

    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private TextField cin;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private Button registerButton1;
    @FXML
    private PasswordField password1;

    private FileInputStream fis;
    private FileChooser fileChooser;
    @FXML
    private Button uploid;
    private ImageIcon format = null;
    @FXML
    private TextField email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void uploidimage(ActionEvent event) throws FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fd = new FileNameExtensionFilter("PNG JPG", "png", "jpg");
        chooser.addChoosableFileFilter(fd);

        int response = chooser.showOpenDialog(null); //select file to open
        //int response = fileChooser.showSaveDialog(null); //select file to save

        if (response == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (fd.accept(f)) {
                filename = f.getAbsolutePath();

                String newpath = "uploids/profile/";
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

    @FXML
    private void register(ActionEvent event) throws SQLException, IOException, MessagingException, MessagingException {

//          FileInputStream fis = null ;
        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;

            String yy = "SELECT * FROM users WHERE cin ='" + cin.getText() + "'";
            String req = "INSERT INTO `users`(`nom`, `prenom`, `mot_de_passe`,`cin`,`role`,`image`,`email`) VALUES ( ?,?, ?, ?,?,?,?)";

            ps = cnx.prepareStatement(yy);

            rs = ps.executeQuery();

            if (rs.next()) {
                showMessageDialog(null, "deja existe");
            } else {
                String nom = firstName.getText();
                String prenom = lastName.getText();
                String pass = password1.getText();
                String email = this.email.getText();
                PreparedStatement x = cnx.prepareStatement(req);
                x.setString(1, nom);
                x.setString(2, prenom);
                 String salt = BCrypt.gensalt(10);
              String hashedPassword = BCrypt.hashpw(String.valueOf(password), salt);
                x.setString(3, hashedPassword);
                x.setString(4, cin.getText());
                x.setString(5, "user");
                x.setString(6, String.valueOf(xxx));

                x.setString(7, email);

                //  fis = new FileInputStream(file);
                x.executeUpdate();
                System.out.println("PS : compte ajoutée avec succés!");
//                SendEmail a = new SendEmail();
//                a.sendemailwelcom(email);
                BienvenueEmail mail = new BienvenueEmail();
                mail.setupServerProperties();
                mail.draftEmail(email, firstName.getText());
                mail.sendEmail();
                showMessageDialog(null, "compte creer avec succes");
                firstName.clear();
                lastName.clear();
                password1.clear();
                confirmpassword.clear();
                cin.clear();
                this.email.clear();
            }
        }
    }

    public static String getHash(byte[] inputBytes, String algorithme) {
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithme);
            messageDigest.update(inputBytes);
            byte[] digesteBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digesteBytes).toLowerCase();

        } catch (Exception e) {

        }
        return hashValue;
    }

    private boolean isValidated() {

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        String number = "[0-9]+";
        Pattern x = Pattern.compile(number);

        if (firstName.getText().equals("")) {

            showMessageDialog(null, "firstName text field cannot be blank.");
            firstName.requestFocus();

        } else if (firstName.getText().length() < 5 || firstName.getText().length() > 25) {

            showMessageDialog(null, "Error , Username text field cannot be less than 5 and greator than 25 characters.");
            firstName.requestFocus();

        } else if (!password1.getText().equals(confirmpassword.getText())) {

            showMessageDialog(null, "erreur confirm password  ");
            password1.requestFocus();

        } else if (lastName.getText().equals("")) {

            showMessageDialog(null, "lastName text field cannot be blank.");
            lastName.requestFocus();
        } else if (email.getText().equals("")) {

            showMessageDialog(null, "email text field cannot be blank.");
            email.requestFocus();

        } else if (!pattern.matcher(email.getText()).matches()) {

            showMessageDialog(null, "email invalid");
            email.requestFocus();
        } else if (confirmpassword.getText().equals("")) {

            showMessageDialog(null, "confirm password text field cannot be blank.");
            confirmpassword.requestFocus();
        } else if (lastName.getText().length() < 5 || lastName.getText().length() > 25) {

            showMessageDialog(null, "Error , lastName text field cannot be less than 5 and greator than 25 characters.");
            lastName.requestFocus();

        } else if (cin.getText().length() < 8 || cin.getText().length() > 8) {

            showMessageDialog(null, "Error , cin text field cannot be less than 8 and greator than 8 characters.");
            cin.requestFocus();
        } else if (!x.matcher(cin.getText()).matches()) {
            showMessageDialog(null, "cin contains only number.");
            cin.requestFocus();
        } else if (password1.getText().equals("")) {

            showMessageDialog(null, "Password text field cannot be blank.");
            password1.requestFocus();
        } else if (filename == null) {
            showMessageDialog(null, "image required");
            uploid.requestFocus();
        } else if (password1.getText().length() < 5 || password1.getText().length() > 25) {

            showMessageDialog(null, "Password text field cannot be less than 5 and greator than 25 characters.");
            password1.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../../gui/login/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Login");
        //
        stage.show();
    }

}
