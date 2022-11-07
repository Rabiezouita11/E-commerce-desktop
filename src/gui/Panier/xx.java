/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Panier;

import ecommerce.MyPanier;
import ecommerce.main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Panier;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-108
 */
public class xx  {

    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLable;
    @FXML
    private ImageView img;
    @FXML
    private Label quantite;

    /**
     * Initializes the controller class.
     */
    @FXML
  private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(fruit);
    }

    private Panier fruit;
    private MyPanier myListener;

    public void setData(Panier fruit, MyPanier myListener) {
        this.fruit = fruit;
        this.myListener = myListener;
        nameLabel.setText(fruit.getNom_produit());
        priceLable.setText(fruit.getNewprix()+main.CURRENCY);
        quantite.setText(String.valueOf("Quantiter"+" "+fruit.getQuantite()));
        String path = fruit.getImage();
        Image aa = new Image("file:" + path);

        img.setImage(aa);
    }
    
}
