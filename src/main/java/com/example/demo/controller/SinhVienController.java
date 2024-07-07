package com.example.demo.controller;

import com.example.demo.entity.NganhHocDto;
import com.example.demo.entity.SinhVien;
import com.example.demo.model.NganhHoc;
import com.example.demo.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;
    @GetMapping("")
    public ResponseEntity<List<SinhVien>> getList(@RequestParam(required = false) String name){
        try {
            List<SinhVien> list = new ArrayList<>();
            if(name==null){
                list = sinhVienService.getListSinhVien();
            }else {
                list = sinhVienService.getListSinhVienByNameContaining(name);
            }
            if(list.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<SinhVien> getSinhVienById(@PathVariable("id") UUID id){
        Optional<SinhVien> o = sinhVienService.getSinhVienById(id);
        if(o.isPresent()) {
            return new ResponseEntity<>(o.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("")
    public ResponseEntity<SinhVien> add(@RequestBody SinhVien sinhVien){
        try {
            SinhVien sv = sinhVienService.createSinhVien(sinhVien);
            return new ResponseEntity<>(sv,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") UUID id){
        try {
            sinhVienService.deleteSinhVienById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<SinhVien> update(@PathVariable("id") UUID id,@RequestBody SinhVien sinhVien){
        Optional<SinhVien> o = sinhVienService.getSinhVienById(id);
        if(o.isPresent()){
            SinhVien sv = o.get();
            sv.setName(sinhVien.getName());
            sv.setNganhHoc(sinhVien.getNganhHoc());
            sv.setPhoneNumber(sinhVien.getPhoneNumber());
            sv.setYob(sinhVien.getYob());
            return new ResponseEntity<>(sinhVienService.updateSinhVien(sv),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/thong_ke")
    public ResponseEntity<List<NganhHocDto>> thongKe(){
        try {
            List<NganhHocDto> list = sinhVienService.thongKe();
            return new ResponseEntity<>(list,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
