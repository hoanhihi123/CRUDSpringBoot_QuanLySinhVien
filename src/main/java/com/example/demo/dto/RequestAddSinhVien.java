package com.example.demo.dto;

import com.example.demo.entity.SinhVien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class RequestAddSinhVien extends BaseRequest{
    private SinhVien sinhVien;
}
