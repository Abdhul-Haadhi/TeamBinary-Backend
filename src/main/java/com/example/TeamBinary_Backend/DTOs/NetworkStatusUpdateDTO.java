package com.example.TeamBinary_Backend.DTOs;

public class NetworkStatusUpdateDTO {

    private String status;

    public NetworkStatusUpdateDTO() {}
    public NetworkStatusUpdateDTO(String status) { this.status = status; }

    public String getStatus()         { return status;  }
    public void   setStatus(String s) { this.status = s; }
}
