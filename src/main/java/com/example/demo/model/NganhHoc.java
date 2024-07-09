package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
* Người tạo: Hoan
* Mục đích class: Mapping Class NganhHoc với Table NganhHoc trong database
* */
@Entity
@Table(name="nganh_hoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NganhHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message = "Vui lòng không để trống mã ngành học")
    @Pattern(regexp ="^MN[0-9]{1,48}$"
            , message = "Vui lòng nhập mã ngành học theo định dạng MN + số bất kỳ (0-9)")
    @Size( max = 50, message = "Mã ngành học chỉ nhận tối đa 50 ký tự")
    @Column(name = "ma_nganh_hoc")
    private String maNganh;

    @NotBlank(message = "Vui lòng không để trống tên ngành học")
    @Size( max = 300, message = "Tên ngành học chỉ nhận tối đa 300 ký tự")
    @Column(name = "ten_nganh_hoc")
    private String tenNganh;

    @Column(name = "isdeleted")
    private Boolean isDeleted;

}