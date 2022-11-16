package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.Lecture;
import com.de_alone.dokkang.models.StudyGroupPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroupPost, Long> {
    List<StudyGroupPost> findAllByLectureId(Lecture lecture);
}
