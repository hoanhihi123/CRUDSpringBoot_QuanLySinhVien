package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor @AllArgsConstructor
public class SinhVienDto {
    private UUID id;
    private Integer idNganhHoc;
    private String name;
    private Integer yob;
    private String phoneNumber;
}
