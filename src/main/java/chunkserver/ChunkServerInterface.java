/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chunkserver;

import model.WriteAck;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Request;
import model.Response;

/**
 *
 * @author Khủng long
 */
public interface ChunkServerInterface extends Remote{
    Response requestReceive(Request req) throws RemoteException; 
    WriteAck receiveChunk(byte[] data, int idOfTransaction) throws RemoteException;
    void uploadFile(String fileName, int transId) throws RemoteException;
    byte[] downloadFile(String fileName) throws RemoteException;
}
