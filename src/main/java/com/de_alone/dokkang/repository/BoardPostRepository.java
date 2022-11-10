package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.BoardPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {
}
