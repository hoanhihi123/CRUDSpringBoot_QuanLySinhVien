package com.example.demo.controller;

import com.example.demo.model.NganhHoc;
import com.example.demo.service.NganhHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nganhHoc")
public class NganhHocRestController {
    @Autowired
    NganhHocService nganhHocService;

    // Lấy tất cả ngành học
    @GetMapping
    public ResponseEntity<List<NganhHoc>> getAllNganhHoc() {
        List<NganhHoc> nganhHocList = nganhHocService.getAllNganhHoc();
        if (!nganhHocList.isEmpty()) {
            return ResponseEntity.ok(nganhHocList);
        } else {
            return ResponseEntity.noContent().build();  // phản hồi mã 204: Không có nội dung nào
        }
    }

    // Tạo / Sửa ngành học
    @PostMapping
    public ResponseEntity<NganhHoc> saveNganhHoc(@RequestBody NganhHoc nganhHoc) {
        NganhHoc savedNganhHoc = nganhHocService.saveNganhHoc(nganhHoc);
        if (savedNganhHoc != null) {
            return ResponseEntity.ok(savedNganhHoc);
        } else {
            return ResponseEntity.badRequest().build();  // 400 Bad Request: Không có nội dung nào
        }
    }

    // Xóa ngành học theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNganhHocById(@PathVariable Integer id) {
        boolean deleted = nganhHocService.deleteNganhHocById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found: không có nội dung nào
        }
    }

}
