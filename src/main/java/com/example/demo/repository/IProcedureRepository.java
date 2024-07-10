package com.example.demo.repository;

import com.example.demo.dto.response.NganhHocDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProcedureRepository extends JpaRepository<NganhHocDto,String> {
    @Query(value = "exec thongKe",nativeQuery = true)
    List<NganhHocDto> thongKe();
}
