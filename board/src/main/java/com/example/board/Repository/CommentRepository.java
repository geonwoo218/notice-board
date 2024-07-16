package com.example.board.Repository;

import com.example.board.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@EnableJpaRepositories
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByBoardid(Long boardid);
}
