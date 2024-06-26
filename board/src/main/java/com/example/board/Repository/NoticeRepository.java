package com.example.board.Repository;

import com.example.board.Entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByIdx(Long idx);
    Page<Notice> findAllByOrderByIdxDesc(PageRequest pageRequest);

    Page<Notice> findByTitleContaining(String title, PageRequest pageRequest);

}
