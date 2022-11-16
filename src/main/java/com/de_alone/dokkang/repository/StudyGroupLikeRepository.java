package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.StudyGroupLike;
import com.de_alone.dokkang.models.StudyGroupPost;
import com.de_alone.dokkang.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupLikeRepository extends JpaRepository<StudyGroupLike, Long> {
    List<StudyGroupLike> findAllByPostId(StudyGroupPost studyGroupPost);

    Boolean existsByPostId(StudyGroupPost studyGroupPost);

    List<StudyGroupLike> findByPostIdAndUserId(StudyGroupPost studygroup_id, User userId);
}
