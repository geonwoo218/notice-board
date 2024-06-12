package com.example.board.Repository;

import com.example.board.Entity.Notice;
import com.example.board.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByIdx(Long idx);
    Page<Notice> findAllByOrderByIdxDesc(PageRequest pageRequest);
}
