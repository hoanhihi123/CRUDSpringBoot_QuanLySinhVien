package com.example.demo.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class RequestDeleteSinhVien extends BaseRequest{
    private UUID idSinhVien;
}
