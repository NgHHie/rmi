package masterserver;

import chunkserver.ChunkServerInterface;

import java.rmi.Remote;

public interface MasterServerInterface extends Remote {
    void registerChunkServer(ChunkServerInterface chunkServer) throws Exception;
    void unregisterChunkServer(ChunkServerInterface chunkServer) throws Exception;
    void chooseChunkServer(int chunkId) throws Exception;
    void updateChunkMetadata() throws Exception;
}
