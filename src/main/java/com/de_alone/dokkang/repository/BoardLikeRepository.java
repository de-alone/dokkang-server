package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.BoardLike;
import com.de_alone.dokkang.models.BoardPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    List<BoardLike> findAllByPostId(BoardPost boardPost);
}
