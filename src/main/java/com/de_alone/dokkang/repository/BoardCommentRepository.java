package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.BoardComment;
import com.de_alone.dokkang.models.BoardPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    List<BoardComment> findAllByPostId(BoardPost boardPost);
}
