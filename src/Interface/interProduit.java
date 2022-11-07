/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Categories;
import model.Produit;

/**
 *
 * @author bouden
 */
public interface interProduit {

    void insertproduit(Produit st);

    void updateProduit(Produit st);

    void deleteProduit(int id);

    ObservableList<Produit> DisplayAllproduit();
}
