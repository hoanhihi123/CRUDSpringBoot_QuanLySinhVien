package com.example.demo.service;

import com.example.demo.repository.ISinhVienRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Người tạo : Hoan
 * Mục đích  : Xử lý nghiệp vụ liên quan tới đối tượng SinhVien
 *
 * */
@Service
public class SinhVienService {
    @Autowired
    ISinhVienRepositoryImpl repo_sinhVien;

    //
    /*
     * Mục đích: đếm số lượng id ngành học có trong bảng sinh viên
     * Input   : tham số id ngành học
     * Output  : tổng số lượng id ngành học đếm được trong bảng sinh viên
     *
     * */
    public int checkExistIdNganhHocIntableSinhVien(Integer idNganhHoc){
        return repo_sinhVien.checkExistsNganhHocInTableSinhVien(idNganhHoc);
    }

}
