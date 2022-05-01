package com.poginc.backendmaster.repository;

import com.poginc.backendmaster.entity.pog_Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockRepository extends JpaRepository<pog_Mock, Long> {
    List<pog_Mock> findAll();
}
