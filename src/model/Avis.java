/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author bouden
 */
public class Avis {

    private int id;
    private String message;
    private int id_user;

    public Avis() {
    }

    public Avis(int id, String message, int id_user) {
        this.id = id;
        this.message = message;
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Avis{" + "id=" + id + ", message=" + message + ", id_user=" + id_user + '}';
    }
    
    
    
    
    
}
