package com.example.demo.service;

<<<<<<< HEAD
import com.example.demo.model.NganhHoc;
import com.example.demo.repository.INganhHocRepositoryImpl;
=======
import com.example.demo.entity.NganhHoc;
import com.example.demo.repository.INganhHocRepository;
>>>>>>> d60cff16f6f1bca26d1d1303c8844682d8c16531
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Người tạo : Hoan
 * Mục đích  : Tạo lớp Service xử lý nghiệp vụ Ngành Học bằng execute procedure
 *
 * */
@Service
public class NganhHocProcedureService {
    @Autowired
    INganhHocRepositoryImpl repo_nganhHoc;

    /*
     * Mục đích: lấy danh sách ngành học bằng việc execute procedure
     * Input   : không có
     * Output  : danh sách các ngành học được tạo trước đó trong database
     *
     * */
    public List<NganhHoc> layDanhSachNganhHoc(){
        return repo_nganhHoc.sp_layDanhSachNganhHoc();
    }



}


//    public void suaNganhHoc(NganhHoc nganhHoc, Integer id){
//        repo_nganhHoc.updateNganhHoc(nganhHoc.getMaNganh(), nganhHoc.getTenNganh(), nganhHoc.getIsDeleted(),id);
//    }
//
//    public void xoaNganhHoc( Integer id){
//        repo_nganhHoc.xoaNganhHoc(id);
//    }
//
//    public void themNganhHoc(NganhHoc nganhHoc){
//        repo_nganhHoc.taoNganhHoc(nganhHoc.getMaNganh(), nganhHoc.getTenNganh());
//    }