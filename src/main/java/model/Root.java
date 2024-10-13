package model;

import chunkserver.ChunkServer;

import java.util.List;

public class Root {
    private List<FileInfo> files;
    private List<ChunkServerInfo> chunk_servers;

    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }

    public List<ChunkServerInfo> getChunkServers() {
        return chunk_servers;
    }

    public void setChunkServers(List<ChunkServerInfo> chunk_servers) {
        this.chunk_servers = chunk_servers;
    }
}
