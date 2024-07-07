package com.example.demo.service;

import com.example.demo.model.NganhHoc;
import com.example.demo.repository.INganhHocRepository;
import org.hibernate.validator.constraints.ru.INN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NganhHocProcedureService {
    @Autowired
    INganhHocRepository repo_nganhHoc;

    public List<NganhHoc> layDanhSachNganhHoc(){
        return repo_nganhHoc.layDanhSachNganhHoc();
    }

    public void suaNganhHoc(NganhHoc nganhHoc, Integer id){
        repo_nganhHoc.updateNganhHoc(nganhHoc.getMaNganh(), nganhHoc.getTenNganh(), nganhHoc.getIsDeleted(),id);
    }

    public void xoaNganhHoc( Integer id){
        repo_nganhHoc.xoaNganhHoc(id);
    }

    public void themNganhHoc(NganhHoc nganhHoc){
        repo_nganhHoc.taoNganhHoc(nganhHoc.getMaNganh(), nganhHoc.getTenNganh());
    }

}
