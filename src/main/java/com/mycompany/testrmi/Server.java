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
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Khủng long
 */
public class Server implements FileServerInterface{
    private final String storageDir = "./filestorage";
    private ConcurrentHashMap<String, byte[]> fileStore;
    
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
