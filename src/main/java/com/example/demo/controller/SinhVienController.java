package com.example.demo.controller;

import com.example.demo.dto.RequestAddSinhVien;
import com.example.demo.dto.RequestDeleteSinhVien;
import com.example.demo.dto.RequestGetListSinhVien;
import com.example.demo.dto.RequestUpdateSinhVien;
import com.example.demo.dto.ResponseAddSinhVien;
import com.example.demo.dto.ResponseDeleteSinhVien;
import com.example.demo.dto.ResponseGetListSinhVien;
import com.example.demo.dto.ResponseUpdateSinhVien;
import com.example.demo.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sinh_vien")
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;
    @GetMapping("/views")
    public ResponseGetListSinhVien getList(@RequestBody RequestGetListSinhVien req){
        return sinhVienService.getList(req);
    }
    @PostMapping("/add")
    public ResponseAddSinhVien add(@RequestBody RequestAddSinhVien req){
        return sinhVienService.add(req);
    }
    @DeleteMapping("/delete")
    public ResponseDeleteSinhVien delete(@RequestBody RequestDeleteSinhVien req){
        return sinhVienService.delete(req);
    }
    @PutMapping("/update")
    public ResponseUpdateSinhVien update(@RequestBody RequestUpdateSinhVien req){
        return sinhVienService.update(req);
    }
}
