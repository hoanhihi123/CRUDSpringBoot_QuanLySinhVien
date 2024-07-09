package com.example.demo.entity.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hai
 * entity đối tượng dùng để thống kê số lượng sinh viên của ngành học
 */
@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
public class NganhHocDto {
    @Column(name = "ten")
    private String ten;
    @Id
    @Column(name = "ma")
    private String ma;
    @Column(name = "so_luong")
    private Integer soLuong;
}
