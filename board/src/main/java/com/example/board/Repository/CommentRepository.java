package com.example.board.Repository;

import com.example.board.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByBoardidOrderByCommentsortDesc(Long idx);

    @Modifying
    @Query("UPDATE Comment c set c.commentsort = c.commentsort+1 where c.commentsort > :commentsort")
    Long CommentUpdate(Long commentsort);

    @Query("SELECT MAX(c.commentsort) FROM Comment c WHERE c.boardid = :boardid")
    Comment findByBoardId(Long boardid);
}
