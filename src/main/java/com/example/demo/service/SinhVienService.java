package com.example.demo.service;

import com.example.demo.dto.RequestAddSinhVien;
import com.example.demo.dto.RequestDeleteSinhVien;
import com.example.demo.dto.RequestGetListSinhVien;
import com.example.demo.dto.RequestUpdateSinhVien;
import com.example.demo.dto.ResponseAddSinhVien;
import com.example.demo.dto.ResponseDeleteSinhVien;
import com.example.demo.dto.ResponseGetListSinhVien;
import com.example.demo.dto.ResponseUpdateSinhVien;
import com.example.demo.dto.SinhVienDto;
import com.example.demo.entity.SinhVien;
import com.example.demo.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;
    public ResponseGetListSinhVien getList(RequestGetListSinhVien req) {
        List<SinhVien> list = sinhVienRepository.findAll();
        ResponseGetListSinhVien res = new ResponseGetListSinhVien();
        res.setListSinhVien(list);
        return res;
    }

    public ResponseAddSinhVien add(RequestAddSinhVien req) {
        SinhVienDto sv = req.getSinhVienDto();

        sinhVienRepository.save(SinhVien.builder().name(sv.getName()).yob(sv.getYob()).phoneNumber(sv.getPhoneNumber()).build());
        return new ResponseAddSinhVien();
    }

    public ResponseDeleteSinhVien delete(RequestDeleteSinhVien req) {
        sinhVienRepository.deleteById(req.getIdSinhVien());
        return new ResponseDeleteSinhVien();
    }

    public ResponseUpdateSinhVien update(RequestUpdateSinhVien req) {
//        Optional<SinhVien> o = sinhVienRepository.findById(req.getIdSinhVien());
//        if(o.isPresent()){
//
//        }else {
//
//        }
        return new ResponseUpdateSinhVien();
    }
}
