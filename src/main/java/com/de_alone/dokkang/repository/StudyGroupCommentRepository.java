package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.StudyGroupComment;
import com.de_alone.dokkang.models.StudyGroupPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupCommentRepository extends JpaRepository<StudyGroupComment, Long> {
    List<StudyGroupComment> findAllByStudyGroupId(StudyGroupPost studyGroupId);
}
