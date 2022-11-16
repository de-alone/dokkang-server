package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.StudyGroupParticipation;
import com.de_alone.dokkang.models.StudyGroupPost;
import com.de_alone.dokkang.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupParticipationRepository extends JpaRepository<StudyGroupParticipation, Long> {
    List<StudyGroupParticipation> findAllByStudyGroupId(StudyGroupPost studyGroupId);

    Boolean existsByStudyGroupId(StudyGroupPost studyGroupId);

    List<StudyGroupParticipation> findByStudyGroupIdAndUserId(StudyGroupPost studyGroupId, User userId);
}
