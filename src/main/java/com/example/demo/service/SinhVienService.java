package com.example.demo.service;

import com.example.demo.repository.ISinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SinhVienService {
    @Autowired
    ISinhVienRepository repo_sinhVien;

    // kiểm tra sự tồn tại của ngành học
    public int checkExistIdNganhHocIntableSinhVien(Integer idNganhHoc){
        return repo_sinhVien.checkExistsNganhHocInTableSinhVien(idNganhHoc);
    }

}
