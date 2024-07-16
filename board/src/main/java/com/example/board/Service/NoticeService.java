package com.example.board.Service;

import com.example.board.Entity.Comment;
import com.example.board.Entity.Notice;
import com.example.board.Entity.User;
import com.example.board.Repository.CommentRepository;
import com.example.board.Repository.NoticeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findComment(Long boardid) {
        return commentRepository.findByBoardid(boardid);
    }


    public Notice noticewrite_setting(Notice notice, String name) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        notice.setDate(timestamp);
        notice.setName(name);

        return noticeRepository.save(notice);
    }

    public Notice notice_search(Long idx) {
        System.out.println("notice_search (noticeService)");
        Optional<Notice> noticeOptional = noticeRepository.findByIdx(idx);
        if (noticeOptional.isPresent()) {
            Notice roleNotice = noticeOptional.get();

            Notice buildNotice = Notice.builder()
                    .idx(roleNotice.getIdx())
                    .name(roleNotice.getName())
                    .title(roleNotice.getTitle())
                    .content(roleNotice.getContent())
                    .date(roleNotice.getDate())
                    .build();


            return buildNotice;
        }
        return null;
    }

    public void boardDelete(Long idx) {
        noticeRepository.deleteById(idx);
    }

    public void noitceUpdate(Notice notice) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        Notice data = notice_search(notice.getIdx());
        notice.setName(data.getName());
        notice.setDate(timestamp);

        noticeRepository.save(notice);
    }

    public Page<Notice> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 12;

        return noticeRepository.findAllByOrderByIdxDesc(PageRequest.of(page, pageLimit));
    }

    public Page<Notice> noticeSearch(String searchKeyword, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 12;
        return noticeRepository.findByTitleContaining(searchKeyword, PageRequest.of(page, pageLimit));
    }

    public Comment commentSave(Long boardid, String comment, User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        Comment c = Comment.builder()
                .boardid(boardid)
                .username(user.getUsername())
                .comment(comment)
                .commentdate(timestamp)
                .profile_num(user.getProfile_num())
                .build();
        return commentRepository.save(c);
    }

    public void commentDelete(Long idx) {
        Comment comment = commentRepository.findById(idx).orElse(null);
        if(comment != null){
            commentRepository.delete(comment);
        }else{
            return;
        }
    }
}