package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

/**
 * @author hai
 * đây là entity sinh viên map với bảng 'sinh_vien' trong db
 */
@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "sinh_vien")
@Builder
@ToString
public class SinhVien {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_nganh_hoc")
    private NganhHoc nganhHoc;
    // tên sinh viên
    private String name;
    // năm sinh
    private Integer yob;
    @Column(name = "phone_number")
    private String phoneNumber;

    public void setNganhHoc(NganhHoc nganhHoc) {
        this.nganhHoc = nganhHoc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYob(Integer yob) {
        this.yob = yob;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
