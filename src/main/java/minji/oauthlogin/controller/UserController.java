package minji.oauthlogin.controller;

import lombok.RequiredArgsConstructor;
import minji.oauthlogin.entity.Role;
import minji.oauthlogin.entity.User;
import minji.oauthlogin.entity.UserJoinDto;
import minji.oauthlogin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @PostMapping("/join")
    public String join(UserJoinDto userJoinDto){
        User user=User.builder()
                .userId(userJoinDto.getUserId())
                .userEmail(userJoinDto.getUserEmail())
                .userName(userJoinDto.getUserName())
                .userPassword(passwordEncoder.encode(userJoinDto.getUserPassword()))
                .userRole(Role.ROLE_USER)
                .build();

        userRepository.save(user);
        return "redirect:loginForm";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin만 접속 가능";
    }
}
