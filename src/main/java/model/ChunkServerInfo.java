package model;

import java.util.List;

public class ChunkServerInfo {
    private int server_id;
    private String status;
    private int remaining_capacity;
    private List<Integer> stored_chunks;

    public int getServerId() {
        return server_id;
    }

    public void setServerId(int server_id) {
        this.server_id = server_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRemainingCapacity() {
        return remaining_capacity;
    }

    public void setRemainingCapacity(int remaining_capacity) {
        this.remaining_capacity = remaining_capacity;
    }

    public List<Integer> getStoredChunks() {
        return stored_chunks;
    }

    public void setStoredChunks(List<Integer> stored_chunks) {
        this.stored_chunks = stored_chunks;
    }
}
