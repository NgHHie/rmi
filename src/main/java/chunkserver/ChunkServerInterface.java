/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chunkserver;

import model.WriteAck;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Khủng long
 */
public interface ChunkServerInterface extends Remote{
    WriteAck receive(byte[] data, String checkSum) throws RemoteException;
    void uploadFile(String fileName, int transId) throws RemoteException;
    byte[] downloadFile(String fileName) throws RemoteException;
}
