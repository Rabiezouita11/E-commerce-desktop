/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Adresse;

import ecommerce.MyListener;
import ecommerce.main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Adresse;
import model.Produit;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-108
 */
public class ItemAdresseController implements Initializable {

    private Label nameLabel;
    @FXML
    private Label quantite;
    @FXML
    private TextFlow xx;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void click(MouseEvent event) {
        Adressse.onClickListener(adresse);
    }

    public void setData(Adresse adreess, ecommerce.Adresse myListener) {
        this.adresse = adreess;
        this.Adressse = myListener;
       
        //   priceLable.setText(fruit.getPrix()+main.CURRENCY);
        xx.getChildren().clear();
        Text t1 = new Text(adreess.getRue());
        xx.getChildren().add(t1);
    }

    private Adresse adresse;
    private ecommerce.Adresse Adressse;

}
