package com.example.demo.repository;

import com.example.demo.entity.NganhHocDto;
import com.example.demo.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;
import java.util.UUID;

public interface ISinhVienRepository extends JpaRepository<SinhVien, UUID> {
    List<SinhVien> findAllByNameContaining(String name);
    @Procedure(name = "selectCountSinhVienByNganhHoc")
    List<NganhHocDto> thongKe();
}
