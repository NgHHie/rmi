package client;

import chunkserver.ChunkServerInterface;
import masterserver.MasterServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientTest {
    private MasterServerInterface masterServer;
    public ClientTest(String host) {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            masterServer = (MasterServerInterface) registry.lookup("MasterServer");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void readRequest(String fileName) {
        try {
            String chunkServerName = masterServer.chooseChunkServer(fileName);
        } catch (Exception e) {
            System.err.println("Read request failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ClientTest client = new ClientTest("localhost");
    }
}
