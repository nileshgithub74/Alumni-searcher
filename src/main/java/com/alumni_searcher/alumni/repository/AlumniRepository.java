package com.alumni_searcher.alumni.repository;

import com.alumni_searcher.alumni.entity.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumniRepository extends JpaRepository<Alumni, Long> {
   boolean  existsByLinkedinUrl(String linkedinUrl);
}
