package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.BoardPost;
import com.de_alone.dokkang.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {
    List<BoardPost> findAllByLectureId(Lecture lecture);
}
