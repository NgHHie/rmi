/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author Khủng long
 */
// FileClient.java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import chunkserver.ChunkServerInterface;
import java.rmi.RemoteException;
import model.Request;
import model.Response;
import model.WriteAck;
import testlinhtinh.sosanh2file;

public class Client {
    private ChunkServerInterface fileServer;

    public Client(String host) {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            fileServer = (ChunkServerInterface) registry.lookup("FileServer");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
 
    public void upload(String localFilePath, String remoteFileName) throws NoSuchAlgorithmException, RemoteException {
        int chunkSize = 1024 * 1024;
        File file = new File(localFilePath);
        long fileSize = file.length();
        int totalChunks = (int) Math.ceil((double) fileSize / chunkSize);
        String typeOfFile = localFilePath.substring(localFilePath.lastIndexOf(".") + 1);
        Request req = new Request("write", fileSize, typeOfFile);
        Response res = fileServer.requestReceive(req);
        System.out.println(res);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[chunkSize];
            int bytesRead;

            for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
                // Đọc một chunk từ tệp
                bytesRead = bis.read(buffer);
                if (bytesRead == -1) break; // Kiểm tra xem đã đến cuối tệp chưa
                
                byte[] chunkData = new byte[bytesRead];
                System.arraycopy(buffer, 0, chunkData, 0, bytesRead);

                int idOfTransaction = Integer.parseInt(res.getIdOfTransaction());
                WriteAck w_ack = fileServer.receiveChunk(chunkData, idOfTransaction);
                while(w_ack.isStatus() == false) {
                    w_ack = fileServer.receiveChunk(chunkData, idOfTransaction);
                }
                System.out.println(w_ack);
                System.out.println("Uploaded chunk " + (chunkIndex) + " of " + totalChunks + "(" + chunkData.length + " bytes)");
                
            }
        } catch (IOException e) {
            System.err.println("Upload failed: " + e.getMessage());
        }
    }

    public void download(String remoteFileName, String localFilePath) {
        try {
            byte[] data = fileServer.downloadFile(remoteFileName);
            try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
                fos.write(data);
            }
            System.out.println("Downloaded file to: " + localFilePath);
        } catch (Exception e) {
            System.err.println("Download failed: " + e.getMessage());
        }
    }

    private byte[] readFileToByteArray(File file) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                System.out.println("number of bytes: " + String.valueOf(baos.size()));
                return baos.toByteArray();
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, RemoteException {
        // Nhập địa chỉ máy chủ từ console
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server host: ");
        String serverHost = scanner.nextLine();

        Client client = new Client(serverHost);
        String command;

        System.out.println("File Client Started. Enter commands (upload/download/exit):");

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine();
            String[] tokens = command.split(" ");

            if (tokens[0].equalsIgnoreCase("upload")) {
                if (tokens.length != 3) {
                    System.out.println("Usage: upload <local_file_path> <remote_file_name>");
                    continue;
                }
                client.upload(tokens[1], tokens[2]);
            } else if (tokens[0].equalsIgnoreCase("download")) {
                if (tokens.length != 3) {
                    System.out.println("Usage: download <remote_file_name> <local_file_path>");
                    continue;
                }
                client.download(tokens[1], tokens[2]);
            } else if (tokens[0].equalsIgnoreCase("exit")) {
                System.out.println("Exiting.");
                break;
            } else {
                System.out.println("Unknown command. Available commands: upload, download, exit.");
            }
        }

        scanner.close();
    }
}
