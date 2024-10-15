/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Khủng long
 */
public class Response implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String typeOfRequest;
    private String idOfTransaction;

    public Response(String typeOfRequest, String idOfTransaction) {
        this.typeOfRequest = typeOfRequest;
        this.idOfTransaction = idOfTransaction;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTypeOfRequest() {
        return typeOfRequest;
    }

    public String getIdOfTransaction() {
        return idOfTransaction;
    }

    public void setTypeOfRequest(String typeOfRequest) {
        this.typeOfRequest = typeOfRequest;
    }

    public void setIdOfTransaction(String IdOfTransaction) {
        this.idOfTransaction = IdOfTransaction;
    }

    @Override
    public String toString() {
        return "Response{" + "typeOfRequest=" + typeOfRequest + ", idOfTransaction=" + idOfTransaction + '}';
    }
    
    
}
