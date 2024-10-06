/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testlinhtinh;

/**
 *
 * @author Khủng long
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sosanh2file {

    public static String calculateFileHash(String filePath) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[1024*100];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        // Chuyển đổi byte[] sang chuỗi hex
        StringBuilder sb = new StringBuilder();
        for (byte b : digest.digest()) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static boolean areFilesEqual(String filePath1, String filePath2) {
        try {
            String hash1 = calculateFileHash(filePath1);
            String hash2 = calculateFileHash(filePath2);
            return hash1.equals(hash2);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        String filePath1 = "D:/Downloads/ubuntu-24.04.1-desktop-amd64.iso";
        String filePath2 = "D:/Documents/NetBeansProjects/testRMI/filestorage/testchunk2.iso";

        if (areFilesEqual(filePath1, filePath2)) {
            System.out.println("Files are equal.");
        } else {
            System.out.println("Files are not equal.");
        }
    }
}
