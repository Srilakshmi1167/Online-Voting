package com.klu.election.repository;

import com.klu.election.model.Nomination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NominationRepository extends JpaRepository<Nomination, Long> {
    List<Nomination> findByStatus(String status);
    boolean existsByStudentId(Long studentId);
}
