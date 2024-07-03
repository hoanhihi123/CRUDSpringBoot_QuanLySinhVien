package com.example.demo.entity;

import com.example.demo.model.NganhHoc;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Table(name = "sinh_vien")
@Builder
public class SinhVien {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_nganh_hoc")
    private NganhHoc nganhHoc;
    private String name;
    private Integer yob;
    @Column(name = "phone_number")
    private String phoneNumber;
}
