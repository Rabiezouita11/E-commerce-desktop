package gui.Quiz;

import ecommerce.Myquiz;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import model.Quiz;

public class ItemController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;
    @FXML
    private Label quantite;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(fruit);
    }

    private Quiz fruit;
    private Myquiz myListener;

    public void setData(Quiz fruit, Myquiz myListener) {
        this.fruit = fruit;
        this.myListener = myListener;
        nameLabel.setText("Quiz n° " +" "+ String.valueOf(fruit.getId()));

    }
}
