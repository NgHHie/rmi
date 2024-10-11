/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testrmi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.cert.LDAPCertStoreParameters;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import testlinhtinh.sosanh2file;

/**
 *
 * @author Khủng long
 */
public class Server implements FileServerInterface{
    private final String storageDir = "./filestorage";
    private ConcurrentHashMap<String, byte[]> fileStore;
    private int clientID = 0;
    private ConcurrentHashMap<Integer, byte[]> storeData;
  
    public Server() {
        fileStore = new ConcurrentHashMap<>();
        File dir = new File(storageDir);
        if(!dir.exists()) {
            dir.mkdir();
        }
        
//        for (File file : dir.listFiles()) {
//            try {
//                byte[] data = readFileToByteArray(file);
//                fileStore.put(file.getName(), data);
//            } catch (IOException e) {
//                System.err.println("Error loading file: " + file.getName());
//            }
//        }
    }
    @Override
    public boolean receive(byte[] chunkData, String checkSum, int id) {
        System.out.println("cliend id: " + id);
        String hash = "";
        try {
            hash = sosanh2file.calculateChunkHash(chunkData);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
//        for(int i=0; i< Integer.MAX_VALUE; i++) {
//            try {
//                hash = sosanh2file.calculateChunkHash(chunkData);
//            } catch (IOException ex) {
//                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (NoSuchAlgorithmException ex) {
//                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        boolean res = hash.equals(checkSum);
        if(res == true) {
            storeData.put(clientID ++, chunkData);
        }
        return res;
    }
    
    @Override
    public synchronized void uploadFile(String fileName, byte[] data) throws RemoteException {
        File file = new File(storageDir + File.separator + fileName);

        try (FileOutputStream fos = new FileOutputStream(file, true)) {
        // Ghi chunk vào tệp (sử dụng 'true' để ghi thêm vào)
            fos.write(data);
            System.out.println("Received chunk of size: " + data.length);
        } catch (IOException e) {
            throw new RemoteException("Error uploading file", e);
        }
    }

    @Override
    public byte[] downloadFile(String fileName) throws RemoteException {
        byte[] data = fileStore.get(fileName);
        if (data == null) {
            throw new RemoteException("File not found: " + fileName);
        }
        System.out.println("File downloaded: " + fileName);
        return data;
    }

    private byte[] readFileToByteArray(File file) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(file)) {
             
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }

    public static void main(String[] args) {
        try {
            // Tạo đối tượng máy chủ
            Server server = new Server();
            // Xuất bản đối tượng máy chủ
            FileServerInterface stub = (FileServerInterface) UnicastRemoteObject.exportObject((Remote) server, 0);
            // Tạo hoặc lấy Registry trên cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            // Đăng ký đối tượng máy chủ với tên "FileServer"
            registry.rebind("FileServer", (Remote) stub);
            System.out.println("File Server is ready.");
        } catch (Exception e) {
            System.err.println("File Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
