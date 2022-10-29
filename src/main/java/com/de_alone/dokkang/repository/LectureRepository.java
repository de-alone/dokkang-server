package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
