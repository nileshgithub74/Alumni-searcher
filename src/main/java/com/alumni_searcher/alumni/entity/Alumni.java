package com.alumni_searcher.alumni.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alumni")
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 200)
    private String university;

    @Column(name = "current_designation", length = 200)
    private String currentRole;


    @Column(length = 200)
    private String location;

    private Integer passoutYear;

    @Column(name = "linkedin_url", length = 1000, unique = true)
    private String linkedinUrl;

    @Column(updatable = false)
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


}
