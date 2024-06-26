package com.example.board.Service;

import com.example.board.Entity.Notice;
import com.example.board.Entity.Role;
import com.example.board.Entity.User;
import com.example.board.Repository.NoticeRepository;
import com.example.board.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import javax.swing.text.html.Option;
import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> user = userRepository.findByName(username);

       if(user.isPresent()){

           User roleUser = user.get();
           //권한까지 불러오기
           User authUser = User.builder()
                   .idx(roleUser.getIdx())
                   .name(roleUser.getName())
                   .email(roleUser.getEmail())
                   .pwd(roleUser.getPwd())
                   .date(roleUser.getDate())
                   .role(roleUser.getRole())
                   .build();
           return authUser;
       }
       System.out.println(user+"error");
       return null;
    }

    //회원가입 데이터 넣기
    public User register_setting(User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        user.setDate(date); // 날짜 저장

        String encodedPassword = passwordEncoder.encode(user.getPwd());
        user.setPwd(encodedPassword); //비밀번호 저장

        user.setRole(Role.USER.getValue()); //role사용

        return userRepository.save(user); //정보 저장
    }

//회원가입 중복이름 인지아닌지
    public String username_search(String username) {
        Optional<User> userOptional = userRepository.findByName(username);
        if (userOptional.isPresent()) {
            System.out.println("yes");
            return "yes";
        } else {
            System.out.println("no");
            return "no";
        }
    }

    public List<Notice> noticeSearch(String searchKeyword, Page<Notice> pageable){
       // return noticeRepository.findByTitleContaining(searchKeyword);
        return null;
    }
}
