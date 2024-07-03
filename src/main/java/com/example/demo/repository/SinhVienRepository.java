package com.example.demo.repository;

import com.example.demo.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SinhVienRepository extends JpaRepository<SinhVien, UUID> {
}
