/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Khủng long
 */
public class WriteAck implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private boolean status;
    private int transId;

    public WriteAck(boolean status, int transId) {
        this.status = status;
        this.transId = transId;
    }

    public boolean isStatus() {
        return status;
    }

    public int getTransId() {
        return transId;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    @Override
    public String toString() {
        return "WriteAck{" + "status=" + status + ", transId=" + transId + '}';
    }
    
    
    
}
