package masterserver;


import chunkserver.ChunkServer;
import chunkserver.ChunkServerInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Chunk;
import model.ChunkServerInfo;
import model.FileInfo;
import model.Root;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MasterServer implements MasterServerInterface  {
    @Override
    public void registerChunkServer(ChunkServerInterface chunkServer) throws Exception {

    }

    @Override
    public void unregisterChunkServer(ChunkServerInterface chunkServer) throws Exception {

    }

    @Override
    public String chooseChunkServer(String fileName) throws Exception {
        return "";
    }

    @Override
    public void updateChunkMetadata() throws Exception {

    }

    public MasterServer() {
        readMetadata();

    }

    public static void main(String[] args) {

        try {
            MasterServer masterServer = new MasterServer();
            MasterServerInterface stub = (MasterServerInterface) UnicastRemoteObject.exportObject((Remote) masterServer, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("MasterServer", (Remote) stub);
            System.out.println("File Server is ready.");
        } catch (Exception e) {
            System.err.println("File Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    private static void readMetadata() {
        Gson gson = new GsonBuilder().create();

        // Tạo đối tượng Gson
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/masterserver/metadata.json"))) {
            // Đọc file JSON và ánh xạ vào class Root
            Root root = gson.fromJson(br, Root.class);

            // Truy cập dữ liệu
            for (FileInfo file : root.getFiles()) {
                System.out.println("File Name: " + file.getFileName());
                System.out.println("File Size: " + file.getFileSize());
                System.out.println("Created At: " + file.getCreatedAt());
                System.out.println("Modified At: " + file.getModifiedAt());
                System.out.println("Hash: " + file.getHash());
                // Truy cập các chunk
                for (Chunk chunk : file.getChunks()) {
                    System.out.println("Chunk ID: " + chunk.getChunkId());
                    System.out.println("Chunk Size: " + chunk.getChunkSize());
                    System.out.println("Replica Locations: " + chunk.getReplicaLocations());
                    System.out.println("Status: " + chunk.getStatus());
                }
                System.out.println("Read Permission: " + file.getPermissions().isRead());
                System.out.println("Write Permission: " + file.getPermissions().isWrite());
            }

            // Tương tự cho chunk_servers
            for (ChunkServerInfo server : root.getChunkServers()) {
                System.out.println("Chunk Server ID: " + server.getServerId());
                System.out.println("Status: " + server.getStatus());
                System.out.println("Remaining Capacity: " + server.getRemainingCapacity());
                System.out.println("Stored Chunks: " + server.getStoredChunks());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
