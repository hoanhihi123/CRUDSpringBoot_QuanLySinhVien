package com.example.demo.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class RequestUpdateSinhVien extends BaseRequest{
    private SinhVienDto sinhVienDto;
}
