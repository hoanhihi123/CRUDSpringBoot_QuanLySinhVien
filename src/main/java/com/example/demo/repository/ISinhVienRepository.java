package com.example.demo.repository;

import com.example.demo.entity.NganhHocDto;
import com.example.demo.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ISinhVienRepository extends JpaRepository<SinhVien, UUID> {
    // ham kiểm tra sự tồn tại của ngành học có trong sinh viên hay ko
    @Query("SELECT COUNT(sv) FROM SinhVien sv WHERE sv.nganhHoc.id = :idNganhHoc")
    public int checkExistsNganhHocInTableSinhVien(@Param("idNganhHoc") Integer idNganhHoc);
    List<SinhVien> findAllByNameContaining(String name);
}
