package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
