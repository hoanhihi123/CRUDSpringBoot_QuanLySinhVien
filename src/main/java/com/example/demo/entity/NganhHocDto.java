package com.example.demo.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class NganhHocDto {
    private String ten;
    private String ma;
    private Integer soLuong;
}
