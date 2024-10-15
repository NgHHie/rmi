/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chunkserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Request;
import model.Response;

import model.WriteAck;
import testlinhtinh.sosanh2file;

/**
 *
 * @author Khủng long
 */
public class ChunkServer implements ChunkServerInterface {
    private final String storageDir = "./filestorage";
    private ConcurrentHashMap<String, byte[]> fileStore;
    private int transId;
    private Map<Integer, List<Integer>> chunkId;
    private ConcurrentHashMap<Integer, byte[]> storeData;
  
    public ChunkServer() {
        transId = 0;
        chunkId = new HashMap<>();
        storeData = new ConcurrentHashMap<>();
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
    public Response requestReceive(Request req) throws RemoteException{
        String typeOfRequest = req.getTypeOfRequest();
        long sizeOfFile = req.getSizeOfFile();
        String typeOfFile = req.getTypeOfFile();
        int id = setTransId();
        String idOfTransaction = String.valueOf(id);
        chunkId.put(id, new ArrayList<>());
        return new Response(typeOfRequest, idOfTransaction);
    }
    @Override
    public WriteAck receiveChunk(byte[] chunkData, int idOfTransaction) throws RemoteException {
        int idOfChunk = chunkId.get(idOfTransaction).size();
        chunkId.get(idOfTransaction).add(idOfChunk);
        boolean res = false;
        String fileName = String.valueOf(idOfTransaction) + "-" + String.valueOf(idOfChunk);
        File file = new File(storageDir + File.separator + fileName);
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(chunkData);
            System.out.println("Received chunk of size: " + chunkData.length);
            res = true;
        } catch (IOException e) {
            res = false;
            throw new RemoteException("Error uploading file", e);
        }
        return new WriteAck(res, idOfTransaction, res == true ? idOfChunk : -1);
    }
    
    @Override
    public synchronized void uploadFile(String fileName, int id) throws RemoteException {
        File file = new File(storageDir + File.separator + fileName);

        try (FileOutputStream fos = new FileOutputStream(file, true)) {
        // Ghi chunk vào tệp (sử dụng 'true' để ghi thêm vào)
            System.out.println(id);
            System.out.println(storeData.get(id).length);
            fos.write(storeData.get(id));
            System.out.println("Received chunk of size: " + storeData.get(id).length);
            storeData.remove(id);
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

    private synchronized int setTransId() {
        transId ++;
        int id = transId;
        return id;
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
            ChunkServer chunkServer = new ChunkServer();
            // Xuất bản đối tượng máy chủ
            ChunkServerInterface stub = (ChunkServerInterface) UnicastRemoteObject.exportObject((Remote) chunkServer, 0);
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
