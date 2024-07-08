package com.example.demo.service;

import com.example.demo.model.NganhHoc;
import com.example.demo.repository.INganhHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NganhHocService {
    @Autowired
    private final INganhHocRepository repo_nganhHoc;

    public NganhHocService(INganhHocRepository repo_nganhHoc){
        this.repo_nganhHoc = repo_nganhHoc;
    }


    // lấy tất cả ngành học
    public List<NganhHoc> getAllNganhHoc(){
        try{
            return repo_nganhHoc.findAll();
        }catch (Exception ex){
            throw new RuntimeException("Failed to get all Nganh Hoc! \nCause is: " + ex.getMessage());
        }
    }

    // lấy danh sách ngành học và phân trang
    public Page<NganhHoc> getAllNganhHoc(Pageable pageable) {
        try {
            return repo_nganhHoc.getNganhHocTheoPage(pageable);
        } catch (DataAccessException ex) { // Specific exception for data access errors
            throw new RuntimeException("Failed to get all Nganh Hoc! Cause is: " + ex.getMessage(), ex);
        }
    }

    // lấy ngành học theo id
    public Optional<NganhHoc> getNganhHocById(Integer id) {
        try{
            return repo_nganhHoc.findById(id);
        }catch (Exception ex){
            throw new RuntimeException("Failed to get detail Nganh Hoc by Id! \nCause is: " + ex.getMessage());
        }
    }

    // tạo mới ngành học
    public NganhHoc createNganhHoc(NganhHoc nganhHoc){
        try{
            NganhHoc nganhHocNew = new NganhHoc();
            nganhHocNew.setMaNganh(nganhHoc.getMaNganh());
            nganhHocNew.setTenNganh(nganhHoc.getTenNganh());
            nganhHocNew.setIsDeleted(nganhHoc.getIsDeleted()!=null?nganhHoc.getIsDeleted():true);

            return repo_nganhHoc.save(nganhHocNew);
        }catch (Exception ex){
            throw new RuntimeException("Failed to create Nganh Hoc! \nCause is: " + ex.getMessage() );
        }
    }

    // sửa thông tin ngành học theo id
    public Optional<NganhHoc> updateNganhHoc(Integer id, NganhHoc nganhHoc) {
        try {
            NganhHoc nganhHocUpdate = new NganhHoc();
            nganhHocUpdate.setId(id);
            nganhHocUpdate.setMaNganh(nganhHoc.getMaNganh());
            nganhHocUpdate.setTenNganh(nganhHoc.getTenNganh());
            if(nganhHoc.getIsDeleted()!=null){
                nganhHocUpdate.setIsDeleted(nganhHoc.getIsDeleted());
            }

            NganhHoc nganhHocUpdated = repo_nganhHoc.save(nganhHocUpdate);

            return Optional.of(nganhHocUpdated);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to update Nganh Hoc! \nCause is: " + ex.getMessage());
        }
    }

    // Xóa ngành học theo ID
    public boolean deleteNganhHocById(Integer id) {
        try{
            repo_nganhHoc.deleteById(id);
            return true;
        }catch (Exception ex){
            throw new RuntimeException("Failed to delete Nganh Hoc! \nCause is: " + ex.getMessage());
        }
    }

    // kiểm tra mã ngành học đã có trong database hay chưa ?
    // dùng procedure
    public int checkExistMaNganhHoc(String maNganhHoc){
        try{
            return repo_nganhHoc.checkMaNganhExists(maNganhHoc);
        }catch (Exception ex){
            throw new RuntimeException("Failed to check ma nganh có tồn tại không! \nCause is: " + ex.getMessage());
        }
    }

    // xóa mềm ngành học
    public boolean xoaMemNganhHocById(Integer id) {
        try{
            repo_nganhHoc.xoaMemNganhHocById(id);
            return true;
        }catch (Exception ex){
            throw new RuntimeException("Failed to xóa mềm Nganh Hoc! \nCause is: " + ex.getMessage());
        }
    }

}
