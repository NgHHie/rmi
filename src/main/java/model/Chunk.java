package model;

import java.util.List;

public class Chunk {
    private int chunk_id;
    private int chunk_size;
    private List<String> replica_locations;
    private String status;

    // Getters and Setters
    public int getChunkId() {
        return chunk_id;
    }

    public void setChunkId(int chunk_id) {
        this.chunk_id = chunk_id;
    }

    public int getChunkSize() {
        return chunk_size;
    }

    public void setChunkSize(int chunk_size) {
        this.chunk_size = chunk_size;
    }

    public List<String> getReplicaLocations() {
        return replica_locations;
    }

    public void setReplicaLocations(List<String> replica_locations) {
        this.replica_locations = replica_locations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}