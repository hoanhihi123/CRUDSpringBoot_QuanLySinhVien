package com.example.demo.service;

import com.example.demo.entity.NganhHocDto;
import com.example.demo.entity.SinhVien;
import com.example.demo.model.NganhHoc;
import com.example.demo.repository.IProcedureRepository;
import com.example.demo.repository.ISinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SinhVienService {
    @Autowired
    private ISinhVienRepository iSinhVienRepository;
    @Autowired
    private NganhHocService nganhHocService;
    @Autowired
    private IProcedureRepository iProcedureRepository;
    public List<SinhVien> getListSinhVien() {
        return iSinhVienRepository.findAll();
    }

    public List<SinhVien> getListSinhVienByNameContaining(String name) {
        return iSinhVienRepository.findAllByNameContaining(name);
    }

    public Optional<SinhVien> getSinhVienById(UUID id) {
        return iSinhVienRepository.findById(id);
    }

    public SinhVien createSinhVien(SinhVien sinhVien) {
        return iSinhVienRepository.save(SinhVien.builder().name(sinhVien.getName()).yob(sinhVien.getYob()).phoneNumber(sinhVien.getPhoneNumber()).nganhHoc(sinhVien.getNganhHoc()).build());
    }

    public SinhVien updateSinhVien(SinhVien sv) {
        return iSinhVienRepository.save(sv);
    }

    public void deleteSinhVienById(UUID id) {
        iSinhVienRepository.deleteById(id);
    }

    public List<NganhHocDto> thongKe() {
        return iProcedureRepository.thongKe();
    }
}
