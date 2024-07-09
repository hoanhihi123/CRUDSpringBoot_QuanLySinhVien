package com.example.demo.repository;

import com.example.demo.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/*
 * Người tạo :
 * Mục đích  :
 *
 * */
@Repository
<<<<<<< HEAD:src/main/java/com/example/demo/repository/ISinhVienRepositoryImpl.java
public interface ISinhVienRepositoryImpl extends JpaRepository<SinhVien, UUID> {
=======
public interface ISinhVienRepository  extends JpaRepository<SinhVien, UUID> {
>>>>>>> d60cff16f6f1bca26d1d1303c8844682d8c16531:src/main/java/com/example/demo/repository/ISinhVienRepository.java
    // ham kiểm tra sự tồn tại của ngành học có trong sinh viên hay ko
    /*
     * Mục đích: Thực hiện đếm xem có bao nhiêu giá trị id ngành học được sử dụng trong bảng SinhVien
     * Input   : tham số idNganhHoc
     * Output  : số lượng idNganhHoc có trong bảng SinhVien
     *
     * */
    @Query("SELECT COUNT(sv) FROM SinhVien sv WHERE sv.nganhHoc.id = :idNganhHoc")
    public int checkExistsNganhHocInTableSinhVien(@Param("idNganhHoc") Integer idNganhHoc);
<<<<<<< HEAD:src/main/java/com/example/demo/repository/ISinhVienRepositoryImpl.java

}
=======
//    @Query("select sv.id,sv.name,sv.yob,sv.phoneNumber,sv.nganhHoc from SinhVien sv where sv.name like CONCAT('%',:name,'%')")
    List<SinhVien> findAllByNameContaining(@Param("name") String name);
}
>>>>>>> d60cff16f6f1bca26d1d1303c8844682d8c16531:src/main/java/com/example/demo/repository/ISinhVienRepository.java
