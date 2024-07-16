package com.example.board.Controller;

import com.example.board.Entity.Comment;
import com.example.board.Entity.Notice;
import com.example.board.Entity.User;
import com.example.board.Repository.CommentRepository;
import com.example.board.Repository.UserRepository;
import com.example.board.Service.NoticeService;
import com.example.board.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
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
            int img = userService.getImgNumber(user.getIdx());
            model.addAttribute("profileImg","/image/img" + img + ".png");
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
            int img = userService.getImgNumber(user.getIdx());
            model.addAttribute("profileImg","/image/img" + img + ".png");
        }
        List<Comment> cList = noticeService.findComment(checkIdx);
        model.addAttribute("commentData",cList);
        return "noticeShow";
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
        int img = userService.getImgNumber(user.getIdx());
        model.addAttribute("profileImg","/image/img" + img + ".png");
        return "writeEdit";
    }

    @PostMapping("/editSuccess") //수정해서 게시하기 버튼 클릭
    public String editSuccess(@ModelAttribute Notice noitce, Model model){
        noticeService.noitceUpdate(noitce);
        return "redirect:/";
    }


    @PostMapping("/comment/write") //댓글 쓰기
    public String commentWrite(RedirectAttributes rttr, @RequestParam("boardid") Long boardid, @RequestParam("comment") String comment,
                               @AuthenticationPrincipal User user){
        noticeService.commentSave(boardid,comment, user);
        rttr.addFlashAttribute("result",true);
        return "redirect:/boardShow?checkIdx="+boardid;
    }

    @GetMapping("/comment/delete")
    public String commentDelete(@RequestParam("idx") Long idx, @RequestParam("boardid") Long boardid, RedirectAttributes rttr){
        noticeService.commentDelete(idx);
        rttr.addFlashAttribute("result",true);
        return "redirect:/boardShow?checkIdx="+boardid;
    }

    @RequestMapping("/multiImageUploader.do")
    public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String sFileInfo = "";
            String filename = request.getHeader("file-name");
            String filename_ext = filename.substring(filename.lastIndexOf(".") + 1);
            filename_ext = filename_ext.toLowerCase();

            // 프로젝트의 static 디렉토리 경로를 설정
            String dftFilePath = new File("target/classes/static/NoticeImg").getAbsolutePath();
            String filePath = dftFilePath + File.separator;
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String rlFileNm = filePath + filename;
            File uploadFile = new File(rlFileNm);
            int counter = 1;
            while (uploadFile.exists()) {
                rlFileNm = filePath + filename.substring(0, filename.lastIndexOf(".")) + "_" + counter + "." + filename_ext;
                uploadFile = new File(rlFileNm);
                counter++;
            }

            InputStream is = request.getInputStream();
            OutputStream os = new FileOutputStream(rlFileNm);
            int numRead;
            byte[] b = new byte[Integer.parseInt(request.getHeader("file-size"))];
            while ((numRead = is.read(b, 0, b.length)) != -1) {
                os.write(b, 0, numRead);
            }

            if (is != null) {
                is.close();
            }
            os.flush();
            os.close();

            sFileInfo += "&bNewLine=true";
            sFileInfo += "&sFileName=" + filename;
            sFileInfo += "&sFileURL=" + "/NoticeImg/" + uploadFile.getName();

            PrintWriter print = response.getWriter();
            print.print(sFileInfo);
            print.flush();
            print.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
