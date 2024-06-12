package com.example.board.Controller;

import com.example.board.Entity.Comment;
import com.example.board.Entity.Notice;
import com.example.board.Entity.User;
import com.example.board.Repository.UserRepository;
import com.example.board.Service.NoticeService;
import com.example.board.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/write") //write페이지
    public String writePage(Model model, @AuthenticationPrincipal User user){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        if (user == null) {
            model.addAttribute("userName", "none");
            model.addAttribute("userEmail", "none");

        } else {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail",user.getEmail());
        }
        return "write";
    }

    @PostMapping("/writeAdd") //글쓰기
    public String writeAdd(@ModelAttribute Notice notice, @AuthenticationPrincipal User user){
        String name = user.getName();
        noticeService.noticewrite_setting(notice, name);
        return "redirect:/";
    }

    @GetMapping("/boardShow") // 글 인덱스번호로 조회
    public String boardShow(@RequestParam("checkIdx") Long checkIdx, Model model,@AuthenticationPrincipal User user){
        model.addAttribute("resNotice",noticeService.notice_search(checkIdx));
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        if (user == null) {
            model.addAttribute("userName", "none");
            model.addAttribute("userEmail", "none");
        } else {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
        }
        model.addAttribute("comment",noticeService.commentSearch(checkIdx));
        return "noticeShow";
    }
    @PostMapping("/comment/write")
    public String commentWrite(Comment comment){
        noticeService.saveComment(comment);

        return "redirect:/boardShow?checkIdx="+comment.getBoardid();
    }
    @PostMapping("/boardShow/deleteW")
    public String borderDelete(@RequestParam Long idx){
        noticeService.boardDelete(idx);
        return "redirect:/";
    }

    @PostMapping("/goNoticeEditPage") //수정하기 페이지로 이동
    public String goNoticeEditPage(@RequestParam Long idx, Model model,@AuthenticationPrincipal User user){
        Notice notice = noticeService.notice_search(idx);
        model.addAttribute("notice", notice);
        model.addAttribute("userName", user.getName());
        model.addAttribute("userEmail", user.getEmail());
        return "writeEdit";
    }

    @PostMapping("/editSuccess") //수정해서 게시하기 버튼 클릭
    public String editSuccess(@ModelAttribute Notice noitce, Model model){
        noticeService.noitceUpdate(noitce);
        return "redirect:/";
    }

}
