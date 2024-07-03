package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="NganhHoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NganhHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "maNganhHoc")
    private String maNganh;


    @Column(name = "tenNganhHoc")
    private String tenNganh;

}