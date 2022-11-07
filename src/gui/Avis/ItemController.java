package gui.Avis;

import ecommerce.MyAvis;
import ecommerce.MyListener;
import ecommerce.main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Avis;

import model.Produit;
import util.MaConnexion;

public class ItemController {

    @FXML
    private Label nameLabel;


    @FXML
    private ImageView img;
    @FXML
    private Label quantite;
    Connection cnx = MaConnexion.getInstance().getCnx();

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(avis);
    }

    private Avis avis;
    private MyAvis myListener;

    public void setData(Avis avis, MyAvis myListener) throws SQLException {
        this.avis = avis;
        this.myListener = myListener;
        System.out.println( avis.getId_user());
        String tt = "SELECT * FROM `users` where id ='" + avis.getId_user() + "' ";

        Statement statement;

        statement = cnx.createStatement();
        ResultSet queryoutput = statement.executeQuery(tt);
        while (queryoutput.next()) {
            String a = queryoutput.getString("prenom");
            String x = queryoutput.getString("nom");
            String y = queryoutput.getString("image");
            String z = queryoutput.getString("cin");
            String e = queryoutput.getString("email");
            nameLabel.setText(x);
            Image aa = new Image("file:" + y);

            img.setImage(aa);
        }

     
    }
}
