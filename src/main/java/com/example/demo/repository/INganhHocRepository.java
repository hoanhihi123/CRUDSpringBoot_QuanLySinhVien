package com.example.demo.repository;

import com.example.demo.model.NganhHoc;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INganhHocRepository extends JpaRepository<NganhHoc, Integer> {

    @Query("SELECT COUNT(n) FROM NganhHoc n WHERE n.maNganh = :maNganhHoc")
    public int checkMaNganhExists(@Param("maNganhHoc") String maNganhHoc);

    @Transactional
    @Modifying
    @Query("UPDATE NganhHoc SET isDeleted=false where id = :idNganhHoc")
    public int xoaMemNganhHocById(@Param("idNganhHoc") Integer idNganhHoc);

    @Query(value = "select * from NganhHoc ",
            countQuery = "select count(*) from NganhHoc", nativeQuery = true)
    public Page<NganhHoc> getNganhHocTheoPage(Pageable pageable);

    // hàm thực hiện lấy danh sách sinh viên bằng procedure
    @Procedure(name = "sp_layDanhSachNganhHoc")
    public List<NganhHoc> layDanhSachNganhHoc();

    @Procedure(name = "sp_updateNganhHoc")
    void updateNganhHoc(
            @Param("maNganh") String maNganh,
            @Param("tenNganh") String tenNganh,
            @Param("isDeleted") Boolean isDeleted,
            @Param("idNganhHoc") Integer idNganhHocUpdate
    );

    @Procedure(name = "sp_insertNganhHoc")
    void taoNganhHoc(
            @Param("maNganh") String maNganh,
            @Param("tenNganh") String tenNganh
    );

    @Procedure(name = "sp_xoaNganhHoc")
    void xoaNganhHoc(
            @Param("idNganhHoc") int idNganhHocDeleted
    );


}
