package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "ma_nganh_hoc")
    private String maNganh;


    @Column(name = "ten_nganh_hoc")
    private String tenNganh;

}