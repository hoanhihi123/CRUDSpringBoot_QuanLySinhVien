package com.example.demo.service;

import com.example.demo.model.NganhHoc;
import com.example.demo.repository.INganhHocRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Người tạo : Hoan
 * Mục đích  : Tạo lớp Service xử lý nghiệp vụ Ngành Học như CRUD sử dụng các method mà JPARepository hỗ trợ
 *
 * */
@Service
public class NganhHocService {
    @Autowired
    private final INganhHocRepositoryImpl repo_nganhHoc;

    public NganhHocService(INganhHocRepositoryImpl repo_nganhHoc){
        this.repo_nganhHoc = repo_nganhHoc;
    }

    /*
     * Mục đích: lấy danh sách ngành học và phân trang
     * Input   : tham số pageable chứa thông tin số trang hiện tại và số lượng bản ghi trong 1 trang
     * Output  : trả về danh sách bản ghi của ngành học tương ứng với số trang hiện tại được set trong tham số pageable
     *           trả về lỗi nếu xảy ra lỗi trong quá trình runtime
     *
     * */
    public Page<NganhHoc> layDanhSachNganhHocVaPhanTrang(Pageable pageable) {
        try {
            return repo_nganhHoc.layDanhSachNganhHocVaPhanTrang(pageable);
        } catch (DataAccessException ex) { // Specific exception for data access errors
            ex.printStackTrace();
            throw new RuntimeException("Failed to get all Nganh Hoc! Cause is: " + ex.getMessage(), ex);
        }
    }

    /*
     * Mục đích: lấy ngành học theo id
     * Input   : tham số idNganhHoc
     * Output  : Object NganhHoc tương ứng với id ngành học truyền vào
     *           Trả về lỗi nếu xảy ra lỗi trong quá trình Runtime
     *
     * */
    public Optional<NganhHoc> layNganhHoc_theoId(Integer idNganhHoc) {
        try{
            return repo_nganhHoc.findById(idNganhHoc);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to get detail Nganh Hoc by Id! \nCause is: " + ex.getMessage());
        }
    }

    /*
     * Mục đích: tạo mới ngành học
     * Input   : tham số Object nganhHoc
     * Output  : Object NganhHoc sau khi tạo thành công
     *           hoặc trả về lỗi nếu xảy ra lỗi trong quá trình Runtime
     *
     * */
    public NganhHoc taoMoiNganhHoc(NganhHoc nganhHoc){
        try{
            NganhHoc nganhHocNew = new NganhHoc();
            nganhHocNew.setMaNganh(nganhHoc.getMaNganh());
            nganhHocNew.setTenNganh(nganhHoc.getTenNganh());
            nganhHocNew.setIsDeleted(nganhHoc.getIsDeleted()!=null?nganhHoc.getIsDeleted():true);

            return repo_nganhHoc.save(nganhHocNew);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to create Nganh Hoc! \nCause is: " + ex.getMessage() );
        }
    }

    //
    /*
     * Mục đích: sửa thông tin ngành học theo id
     * Input   : Object NganhHoc
     * Output  : Object NganhHoc sau khi cập nhật thông tin thành công
     *           hoặc trả về lỗi nếu xảy ra lỗi trong quá trình Runtime
     * */
    public Optional<NganhHoc> capNhatNganhHoc(NganhHoc nganhHoc) {
        try {

            NganhHoc nganhHocUpdated = repo_nganhHoc.save(nganhHoc);

            return Optional.of(nganhHocUpdated);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to update Nganh Hoc! \nCause is: " + ex.getMessage());
        }
    }

    /*
     * Mục đích: Xóa ngành học theo ID
     * Input   : id ngành học
     * Output  : trả về true nếu ngành học được xóa khỏi database
     *           hoặc trả về lỗi nếu xảy ra lỗi trong quá trình Runtime
     * */
    public boolean xoaNganhHocBangId(Integer id) {
        try{
            repo_nganhHoc.deleteById(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to delete Nganh Hoc! \nCause is: " + ex.getMessage());
        }
    }

    /*
     * Mục đích: Đếm số lượng ngành học theo mã ngành học truyền vào
     * Input   : mã ngành học
     * Output  : trả về tổng số lượng ngành học tìm thấy trong database hợp lệ với mã ngành học truyền vào
     *           hoặc trả về lỗi nếu xảy ra lỗi trong quá trình Runtime
     * */
    public int demSoLuongNganhHoc_theoMaNganhHoc(String maNganhHoc){
        try{
            return repo_nganhHoc.demSoLuongNganhHoc_theoMaNganhHoc(maNganhHoc);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to check ma nganh có tồn tại không! \nCause is: " + ex.getMessage());
        }
    }

    /*
     * Mục đích: xóa mềm ngành học theo id truyền vào
     * Input   : id ngành học
     * Output  : trả về true nếu ngành học được cập nhật trạng thái xóa là false trong database
     *           hoặc trả về lỗi nếu xảy ra lỗi trong quá trình Runtime
     *
     */
    public boolean xoaMemNganhHoc_theoId(Integer id) {
        try{
            repo_nganhHoc.xoaMemNganhHoc_theoId(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to xóa mềm Nganh Hoc! \nCause is: " + ex.getMessage());
        }
    }

}
