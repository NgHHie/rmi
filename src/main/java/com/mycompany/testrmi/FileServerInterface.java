/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.testrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Khủng long
 */
public interface FileServerInterface extends Remote{
    void uploadFile(String fileName, byte[] data) throws RemoteException;
    byte[] downloadFile(String fileName) throws RemoteException;
}
