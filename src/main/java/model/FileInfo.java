package model;

import model.Permissions;
import java.util.List;

public class FileInfo {
    private String file_name;
    private int file_size;
    private String created_at;
    private String modified_at;
    private String hash;
    private List<Chunk> chunks;
    private Permissions permissions;

    public String getFileName() {
        return file_name;
    }

    public void setFileName(String file_name) {
        this.file_name = file_name;
    }

    public int getFileSize() {
        return file_size;
    }

    public void setFileSize(int file_size) {
        this.file_size = file_size;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getModifiedAt() {
        return modified_at;
    }

    public void setModifiedAt(String modified_at) {
        this.modified_at = modified_at;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }
}
