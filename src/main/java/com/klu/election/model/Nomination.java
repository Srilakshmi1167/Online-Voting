package com.klu.election.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nominations")
public class Nomination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String department;

    @Column(name = "study_year", nullable = false)
    private String year;

    @Column(nullable = false)
    private String nominationName;

    @Column(nullable = false)
    private String status; // e.g., "SELECTED", "REJECTED", "PENDING"

    public Nomination() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getNominationName() { return nominationName; }
    public void setNominationName(String nominationName) { this.nominationName = nominationName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
