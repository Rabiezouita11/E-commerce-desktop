/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import model.Categories;
import model.user;

/**
 *
 * @author bouden
 */
public interface InterCategorie {

    void insertcat(Categories st);

    void updatecat(Categories st);

    void deletecat(int id);

    Categories findcatById(int id);

    user findnomById(int id);

    user findemailById(int id);

    Categories findcatBynom(String nom);

    List<Categories> DisplayAllcat();
}
