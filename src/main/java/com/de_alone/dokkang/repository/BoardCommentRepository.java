package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
}
