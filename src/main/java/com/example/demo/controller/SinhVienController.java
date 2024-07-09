package com.example.demo.controller;

import com.example.demo.entity.NganhHocDto;
import com.example.demo.entity.SinhVien;
import com.example.demo.service.SinhVienService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author hai
 * đây là controller quản lý api /students
 */
@RestController
@RequestMapping("/students")
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;
    /**
     * api lấy danh sách tất cả các sinh viên (có thể theo tên gợi ý)
     *
     * @param name từ khoá gợi ý theo tên sinh viên
     * @return dánh sách sinh viên cần tìm,nếu danh sách trống trả về null và mã thông váo 204
     */
    @GetMapping("")
    @Operation(summary = "lấy danh sách sinh viên", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<List<SinhVien>> getList(@RequestParam(required = false) String name){
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
    }

    /**
     * api trả về sinh viên theo id sinh viên
     *
     * @param id id của sinh viên
     * @return đối tượng sinh viên cần tìm, nếu không có trả về null và mã code 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<SinhVien> getSinhVienById(@PathVariable("id") UUID id){
        Optional<SinhVien> o = sinhVienService.getSinhVienById(id);
        if(o.isPresent()) {
            return new ResponseEntity<>(o.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * api thêm mới một sinh viên
     *
     * @param sinhVien đối tượng sinh viên truyền vào
     * @return đối tượng sinh viên được thêm vào db
     */
    @PostMapping("")
    public ResponseEntity<SinhVien> add(@RequestBody SinhVien sinhVien){
        SinhVien sv = sinhVienService.createSinhVien(sinhVien);
        return new ResponseEntity<>(sv,HttpStatus.CREATED);
    }

    /**
     * api xoá một sinh viên
     *
     * @param id id đối tượng sinh viên cần xoá
     * @return mã code thông báo kết quả hành động
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") UUID id){
            sinhVienService.deleteSinhVienById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * api update sinh viên
     *
     * @param id id của đối tượng sinh viên cần update
     * @param sinhVien object sinh viên mang thông tin cần update
     * @return đói tương vừa được upate
     */
    @PutMapping("/{id}")
    public ResponseEntity<SinhVien> update(@PathVariable("id") UUID id,@RequestBody SinhVien sinhVien){
        if(Objects.isNull(sinhVien)){
            throw new NullPointerException("sinh viên nhập vào đang null");
        }
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

    /**
     * api thống kê số lượng sinh viên theo ngành học
     *
     * @return List danh sách ngành học gồm tên ngành , mã ngành, số lượng sinh viên của ngành học này từ trước tới nay
     */
    @GetMapping("/thong_ke")
    public ResponseEntity<List<NganhHocDto>> thongKe(){
            List<NganhHocDto> list = sinhVienService.thongKe();
            return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
