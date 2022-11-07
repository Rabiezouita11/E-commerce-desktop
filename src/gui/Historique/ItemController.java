/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Historique;

import ecommerce.MyHistorique;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Historique;

/**
 * FXML Controller class
 *
 * @author Elife-Kef-108
 */
public class ItemController implements Initializable {

    private Label nameLabel;
    @FXML
    private Label quantite;
    @FXML
    private TextFlow xx;
    @FXML
    private Label hist;

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

    public void setData(Historique aa, MyHistorique myListener) {
        this.adresse = aa;
        this.Adressse = myListener;
hist.setText("Historique n°"+aa.getId());
        //   priceLable.setText(fruit.getPrix()+main.CURRENCY);
        xx.getChildren().clear();
        Text t1 = new Text(String.valueOf(aa.getDate()));
          t1.setFont(Font.font("Helvetica", FontPosture.ITALIC, 20));
        xx.getChildren().add(t1);
    }

    private Historique adresse;
    private MyHistorique Adressse;

}
