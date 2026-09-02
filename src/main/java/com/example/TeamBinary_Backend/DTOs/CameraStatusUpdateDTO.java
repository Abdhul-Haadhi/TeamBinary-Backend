package com.example.TeamBinary_Backend.DTOs;


public class CameraStatusUpdateDTO {
    private String status;  // "ACTIVE" | "INACTIVE" | "OFFLINE"

    public CameraStatusUpdateDTO() {}
    public CameraStatusUpdateDTO(String status) { this.status = status; }

    public String getStatus()            { return status;  }
    public void   setStatus(String s)    { this.status = s; }
}
