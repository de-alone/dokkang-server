package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.models.UserLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserLectureRepository extends JpaRepository<UserLecture, Long> {
    List<UserLecture> findByUserId(Optional<User> userId);

    @Query(value = "select ul.lectureId.id " +
            "from UserLecture ul " +
            "where ul.userId.id = :user_id")
    List<Long> findLectureById(@Param("user_id") Long user_id);
    @Modifying
    @Transactional
    @Query(value = "delete " +
            "from UserLecture ul " +
            "where ul.userId.id = :user_id")
    Integer deleteLectureById(@Param("user_id") Long user_id);
}
