package com.example.board.Controller;

import com.example.board.Entity.Notice;
import com.example.board.Entity.User;
import com.example.board.Repository.NoticeRepository;
import com.example.board.Repository.UserRepository;
import com.example.board.Service.NoticeService;
import com.example.board.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/register") //회원가입
    public String register() {
        return "register";
    }

    @PostMapping("/submit_registration")
    public String submit(@ModelAttribute User user) {
        userService.register_setting(user);
        return "redirect:/";
    }

    @GetMapping("/login") //로그인
    public String login() {
        return "login";
    }

    @GetMapping("/loginError") //로그인 실패
    public String loginError(Model errorChecker){
        errorChecker.addAttribute("error","true");
        return "login";
    }

    @PostMapping("/submit_username")
    @ResponseBody
    public String submit_username(@RequestBody String username) {
        System.out.println(username);
        String data = userService.username_search(username);
        return data;
    }


    @GetMapping("/") // 유저이름 보내기
    public String mainPage(Model model, @AuthenticationPrincipal User user, @PageableDefault(page = 1) Pageable pageable) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        if (user == null) {
            model.addAttribute("userName", "none");
            model.addAttribute("userEmail", "none");

        } else {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail",user.getEmail());
        }

        Page<Notice> noticePages = noticeService.paging(pageable);

        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), noticePages.getTotalPages());
        model.addAttribute("endPage",endPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("noticeData", noticePages);
        System.out.println("noticePtages:::" + noticePages);
        return "main";

    }

    @GetMapping("/search")
    public String search(@RequestParam String title, Model model,
                         @AuthenticationPrincipal User user,
                         @PageableDefault(page = 1) Pageable pageable){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        if (user == null) {
            model.addAttribute("userName", "none");
            model.addAttribute("userEmail", "none");

        } else {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail",user.getEmail());
        }
        System.out.println("searchTitle: "+title);
        Page<Notice> noticePages = noticeService.noticeSearch(title,pageable);

        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), noticePages.getTotalPages());
        model.addAttribute("endPage",endPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("noticeData", noticePages);
        System.out.println("noticePages "+noticePages);
        return "main";
    }
}

