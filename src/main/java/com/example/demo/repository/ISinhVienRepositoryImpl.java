package com.example.demo.repository;

import com.example.demo.model.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*
 * Người tạo :
 * Mục đích  :
 *
 * */
@Repository
public interface ISinhVienRepositoryImpl extends JpaRepository<SinhVien, UUID> {
    // ham kiểm tra sự tồn tại của ngành học có trong sinh viên hay ko
    /*
     * Mục đích: Thực hiện đếm xem có bao nhiêu giá trị id ngành học được sử dụng trong bảng SinhVien
     * Input   : tham số idNganhHoc
     * Output  : số lượng idNganhHoc có trong bảng SinhVien
     *
     * */
    @Query("SELECT COUNT(sv) FROM SinhVien sv WHERE sv.nganhHoc.id = :idNganhHoc")
    public int checkExistsNganhHocInTableSinhVien(@Param("idNganhHoc") Integer idNganhHoc);

}
