package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/*
 * Người tạo class: Hoan
 * Mục đích class : Mapping thuộc tính của NganhHoc với Table NganhHoc trong database
 *
 * */
@Entity
@Table(name="NganhHoc")
@Getter
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
    @Column(name = "maNganhHoc")
    private String maNganh;

    @NotBlank(message = "Vui lòng không để trống tên ngành học")
    @Size( max = 300, message = "Tên ngành học chỉ nhận tối đa 300 ký tự")
    @Column(name = "tenNganhHoc")
    private String tenNganh;

    @Column(name = "isdeleted")
    private Boolean isDeleted;

    /*
     * Mục đích: set lại giá trị cho trường id
     * Input   : tham số id của ngành học
     * Output  : không có
     *
    * */
    private void setId(int id) {
        this.id = id;
    }

    /*
     * Mục đích: set lại giá trị cho trường isDeleted
     * Input   : tham số deleted của ngành học
     * Output  : không có
     *
     * */
    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    /*
     * Mục đích: set lại giá trị cho trường maNganh
     * Input   : tham số maNganh của ngành học
     * Output  : không có
     *
     * */
    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    /*
     * Mục đích: set lại giá trị cho trường tenNganh
     * Input   : tham số tenNganh của ngành học
     * Output  : không có
     *
     * */
    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }
}