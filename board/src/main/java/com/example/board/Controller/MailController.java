package com.example.board.Controller;

import com.example.board.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MailController {
    @Autowired
    private MailService mailService;

    private int number;

    @PostMapping("/mailSend")
    @ResponseBody
    public String mailSend(@RequestBody String mail) {
        System.out.println("mailSend start");
        String failSuccess = "Fail";
        try {
            number = mailService.sendMail(mail);
            String num = String.valueOf(number);
            System.out.println("인증번호: "+num);
           failSuccess = "Success";
        } catch (Exception e) {
            failSuccess = "Fail";
        }
        return  failSuccess;
    }

    @PostMapping("/mailCheck")
    @ResponseBody
    public String mailCheck(@RequestBody String userNumber) {
        System.out.println("컨트롤 mailCheck: "+userNumber);
        boolean isMatch = userNumber.equals(String.valueOf(number));
        String TF = String.valueOf(isMatch);

        return TF;
    }
}
