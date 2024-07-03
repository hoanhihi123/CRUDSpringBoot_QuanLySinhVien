package com.example.demo.service;

import com.example.demo.model.NganhHoc;
import com.example.demo.repository.INganhHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Service
public class NganhHocService {
    @Autowired
    INganhHocRepository repo_nganhHoc;

    private static final Logger logger = LoggerFactory.getLogger(NganhHocService.class);


    // lấy tất cả ngành học
    public List<NganhHoc> getAllNganhHoc(){
        return repo_nganhHoc.findAll();
    }

    // lấy ngành học theo id
    public Optional<NganhHoc> getNganhHocById(Integer id) {
        if (id == null) {
            logger.error("Id truyền vào để thực hiện lấy chi tiết ngành học null!");
            return Optional.empty();
        }

        return repo_nganhHoc.findById(id);
    }

    // Tạo / Sửa ngành học
    public NganhHoc saveNganhHoc(NganhHoc nganhHoc) {
        if (nganhHoc == null) {
            logger.error("Object nganhHoc truyền vào để thực hiện tạo/sửa ngành học null");
            return null;
        }
        return repo_nganhHoc.save(nganhHoc);
    }

    // Xóa ngành học theo ID
    public boolean deleteNganhHocById(Integer id) {
        if (id == null) {
            logger.error("Id truyền vào để thực hiện xóa ngành học null!");
            return false;
        }
        if (repo_nganhHoc.existsById(id)) {
            repo_nganhHoc.deleteById(id);
            return true;
        } else {
            logger.warn("Không tìm thấy ngành học với ID: {}", id);
            return false;
        }
    }


}
