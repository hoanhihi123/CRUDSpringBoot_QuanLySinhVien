package com.example.demo.repository;

import com.example.demo.model.NganhHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INganhHocRepository extends JpaRepository<NganhHoc, Integer> {

}
