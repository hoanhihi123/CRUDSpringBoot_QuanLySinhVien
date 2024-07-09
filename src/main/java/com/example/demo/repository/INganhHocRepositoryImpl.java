package com.example.demo.repository;

import com.example.demo.model.NganhHoc;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Người tạo interface: Hoan
 * Mục đích : thực hiện xử lý các nghiệp vụ liên quan tới database như truy vấn, chỉnh sửa, thêm mới dữ liệu
 *
 * */
@Repository
public interface INganhHocRepositoryImpl extends JpaRepository<NganhHoc, Integer> {

    /*
    * Mục đích: đếm xem mã ngành học có trong bảng Ngành Học xuất hiện bao nhiêu lần theo mã ngành học truyền vào phương thức
    * Input   : tham số truyền vào maNganhHoc = mã ngành học
    * Output  : số lượng bản ghi được tìm thấy theo mã ngành học truyền vào
    * */
    @Query("SELECT COUNT(n) FROM NganhHoc n WHERE n.maNganh = :maNganhHoc")
    public int demSoLuongNganhHoc_theoMaNganhHoc(@Param("maNganhHoc") String maNganhHoc);

    /*
     * Mục đích: Cập nhật trạng thái xóa cho bản ghi Ngành Học theo id truyền vào
     * Input   : tham số truyền vào idNganhHoc = id ngành học
     * Output  :
     *              trả về 1 nếu dữ liệu được cập nhật thành công
     *              trả về 0 nếu không có id ngành học phù hợp trong database
     * */
    @Transactional
    @Modifying
    @Query("UPDATE NganhHoc SET isDeleted=false where id = :idNganhHoc")
    public int xoaMemNganhHoc_theoId(@Param("idNganhHoc") Integer idNganhHoc);


    /*
     * Mục đích: Lấy danh sách Ngành Học theo số trang truyền vào
     * Input   : tham số truyền vào pageable chứa số lượng bản ghi trong 1 trang, số trang muốn hiển thị
     * Output  : lấy được các danh sách bản ghi theo số trang muốn hiển thị truyền vào pageable
     *              trả về: danh sách bản ghi nếu số lượng trang phù hợp
     *              trả về: không có gì khi số lượng trang vượt quá tổng số lượng trang
    * */
    @Query(value = "select * from NganhHoc ",
            countQuery = "select count(*) from NganhHoc"
            , nativeQuery = true)
    public Page<NganhHoc> layDanhSachNganhHocVaPhanTrang(Pageable pageable);


    /*
    * Mục đích: lấy danh sách ngành học bằng thủ tục Stored Procedure
    * Input: không có
    * Output:
    *         hiển thị danh sách ngành học có trong database nếu có dữ liệu bản ghi ngành học
    *         không hiển thị kết quả gì nếu không có dữ liệu bản ghi ngành học nào được tạo
    *
    * */
    @Query(value = "EXEC sp_layDanhSachNganhHoc", nativeQuery = true)
    public List<NganhHoc> sp_layDanhSachNganhHoc();


}
