package com.example.demo.service;


import com.example.demo.entity.NganhHoc;
import com.example.demo.dto.response.NganhHocDto;
import com.example.demo.entity.SinhVien;
import com.example.demo.dto.request.SinhvienDTO;
import com.example.demo.repository.INganhHocRepository;
import com.example.demo.repository.IProcedureRepository;

import com.example.demo.repository.ISinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author hai
 *
 */
@Service
public class SinhVienService {
    @Autowired
    private ISinhVienRepository iSinhVienRepository;
    @Autowired
    private IProcedureRepository iProcedureRepository;
    @Autowired
    private INganhHocRepository iNganhHocRepository;
    /**
     * function lấy danh sách sinh viên
     * @return danh sách sinh viên
     */
    public List<SinhVien> getListSinhVien() {
        return iSinhVienRepository.findAll();
    }

    /**
     * function lấy danh sách sinh viên theo từ khoá gợi ý của tên sinh viên
     * @param name từ khoá gợi ý của tên sinh viên
     * @return danh sách sinh viên
     */
    public List<SinhVien> getListSinhVienByNameContaining(String name) {
        return iSinhVienRepository.findAllByNameContaining(name);
    }

    /**
     * function lấy đối tượng sinh viện theo id sinh viên
     * @param id id sinh viên
     * @return đối tượng sinh viên
     */
    public Optional<SinhVien> getSinhVienById(UUID id) {
        return iSinhVienRepository.findById(id);
    }

    /**
     * function tạo mới một sinh viên
     * @param sinhVien đối tượng sinh viên tạo mới
     * @return đối tượng sinh viên được tạo trong db
     */
    public SinhVien createSinhVien(SinhvienDTO sinhVien) {
        Optional<NganhHoc> o = iNganhHocRepository.findById(sinhVien.getNganhHoc().getId());
        NganhHoc nganhHoc ;
        if(o.isPresent()){
            nganhHoc = o.get();
        }else {
            throw new NullPointerException("ko tồn tại ngành học này");
        }
        return iSinhVienRepository.save(SinhVien.builder().nganhHoc(nganhHoc).name(sinhVien.getName()).yob(sinhVien.getYob()).phoneNumber(sinhVien.getPhoneNumber()).build());
    }

    /**
     * function update sinh viên
     * @param sv đối tượng sinh viên cần update
     * @return đối tượng sinh viên đã được update
     */
    public SinhVien updateSinhVien(SinhVien sv) {
        return iSinhVienRepository.save(sv);
    }

    /**
     * function xoá sinh viên theo id sinh viên
     * @param id id sinh viên
     */
    public void deleteSinhVienById(UUID id) {
        iSinhVienRepository.deleteById(id);
    }

    /**
     * function thống kê số lượng sinh viên từ trước đến nay theo ngành học
     * @return danh sách thống kê bao gồm thông tin mã ngành học, tên ngành học , số lượng sinh viên
     */
    public List<NganhHocDto> thongKe() {
        return iProcedureRepository.thongKe();
    }

    //
    /*
     * Mục đích: đếm số lượng id ngành học có trong bảng sinh viên
     * Input   : tham số id ngành học
     * Output  : tổng số lượng id ngành học đếm được trong bảng sinh viên
     *
     * */
    public int checkExistIdNganhHocIntableSinhVien(Integer idNganhHoc){
        return iSinhVienRepository.checkExistsNganhHocInTableSinhVien(idNganhHoc);
    }
}
