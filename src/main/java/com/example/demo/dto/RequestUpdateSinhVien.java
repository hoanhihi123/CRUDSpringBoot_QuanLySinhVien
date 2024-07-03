package com.example.demo.dto;

import com.example.demo.entity.SinhVien;
import lombok.Data;

import java.util.UUID;
@Data
public class RequestUpdateSinhVien extends BaseRequest{
    private UUID idSinhVien;
    private SinhVienUpdateDto sinhVienUpdateDto;
}
