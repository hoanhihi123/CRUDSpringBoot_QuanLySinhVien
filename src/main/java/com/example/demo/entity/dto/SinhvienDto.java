package com.example.demo.entity.dto;

import com.example.demo.entity.NganhHoc;
import lombok.Data;

@Data
public class SinhvienDto {
    private Integer idNganhHoc;
    private String name;
    private String phoneNumber;
    private Integer yob;
}
