package com.example.demo.dto;

import com.example.demo.entity.SinhVien;
import lombok.Data;

import java.util.List;
@Data
public class ResponseGetListSinhVien {
    private List<SinhVien> listSinhVien;
}
