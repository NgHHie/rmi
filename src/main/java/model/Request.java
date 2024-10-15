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
public class Request implements Serializable{
    private static final long serialVersionUID = 1L;

    private String typeOfRequest;
    private long sizeOfFile;
    private String typeOfFile;

    public Request(String typeOfRequest, long sizeOfFile, String typeOfFile) {
        this.typeOfRequest = typeOfRequest;
        this.sizeOfFile = sizeOfFile;
        this.typeOfFile = typeOfFile;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTypeOfRequest() {
        return typeOfRequest;
    }

    public long getSizeOfFile() {
        return sizeOfFile;
    }

    public String getTypeOfFile() {
        return typeOfFile;
    }

    public void setTypeOfRequest(String typeOfRequest) {
        this.typeOfRequest = typeOfRequest;
    }

    public void setLsizeOfFile(long sizeOfFile) {
        this.sizeOfFile = sizeOfFile;
    }

    public void setTypeOfFile(String typeOfFile) {
        this.typeOfFile = typeOfFile;
    }

    @Override
    public String toString() {
        return "Request{" + "typeOfRequest=" + typeOfRequest + ", sizeOfFile=" + sizeOfFile + ", typeOfFile=" + typeOfFile + '}';
    }
    
    
}
