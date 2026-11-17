package com.klu.election.controller;

import com.klu.election.model.Nomination;
import com.klu.election.model.Student;
import com.klu.election.model.Vote;
import com.klu.election.repository.NominationRepository;
import com.klu.election.repository.StudentRepository;
import com.klu.election.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/election")
public class ElectionController {

    @Autowired
    private NominationRepository nominationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @PostMapping("/nominate")
    public ResponseEntity<?> nominateCandidate(@RequestBody Nomination nomination, Authentication authentication) {
        String email = authentication.getName();
        Optional<Student> studentOpt = studentRepository.findByEmail(email);

        if (!studentOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Student not found");
        }

        Student student = studentOpt.get();

        if (nominationRepository.existsByStudentId(student.getId())) {
            return ResponseEntity.badRequest().body("You have already submitted a nomination.");
        }

        nomination.setStudent(student);
        nomination.setStatus("PENDING"); 
        
        // Simulating the quiz pass validation inside the frontend logic, 
        // we can directly save the nomination.
        nominationRepository.save(nomination);

        return ResponseEntity.ok("Nomination submitted successfully!");
    }

    @GetMapping("/candidates")
    public ResponseEntity<List<Nomination>> getCandidates() {
        // Return all nominations for now (in real system, only "APPROVED" ones)
        List<Nomination> candidates = nominationRepository.findAll();
        return ResponseEntity.ok(candidates);
    }

    @PostMapping("/vote")
    public ResponseEntity<?> castVote(@RequestBody Map<String, Long> payload, Authentication authentication) {
        String email = authentication.getName();
        Student student = studentRepository.findByEmail(email).orElse(null);

        if (student == null) {
            return ResponseEntity.badRequest().body("User not authenticated.");
        }

        if (voteRepository.existsByStudentId(student.getId())) {
            return ResponseEntity.badRequest().body("You have already voted. Multiple votes are not allowed.");
        }

        Long candidateId = payload.get("candidateId");
        Optional<Nomination> candidateOpt = nominationRepository.findById(candidateId);

        if (!candidateOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Candidate not found.");
        }

        Vote vote = new Vote();
        vote.setStudent(student);
        vote.setCandidate(candidateOpt.get());
        voteRepository.save(vote);

        return ResponseEntity.ok("Vote cast successfully!");
    }
}
