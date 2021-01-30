package minji.oauthlogin.config;

import minji.oauthlogin.entity.Role;
import minji.oauthlogin.entity.User;
import minji.oauthlogin.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauthService extends DefaultOAuth2UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    //순환참조 막기 위함
    public PrincipalOauthService(@Lazy PasswordEncoder passwordEncoder,UserRepository userRepository){
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }

    //google 로그인 후 불려지는 함수 -> google 로부터 받은 유저 데이터 이용
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User=super.loadUser(userRequest);

        String provider=userRequest.getClientRegistration().getRegistrationId();  //google
        String providerId=oAuth2User.getAttribute("sub");             //google 의 primary key
        String userId=provider+"_"+providerId;                              //google_12345 형태
        String userEmail=oAuth2User.getAttribute("email");
        String userPassword=passwordEncoder.encode("NotNeeded");
        String familyName=oAuth2User.getAttribute("family_name");
        String givenName=oAuth2User.getAttribute("given_name");
        String userName=familyName+givenName;
        Role userRole=Role.ROLE_USER;

        //가져온 정보 토대로 이미 있는 회원인지 확인-> 있다면 로그인, 없다면 회원가입
        User user=userRepository.findUserByUserId(userId);
        if(user==null){
            user=User.builder()
                    .userId(userId)
                    .userEmail(userEmail)
                    .userRole(userRole)
                    .userPassword(userPassword)
                    .userName(userName)
                    .build();

            userRepository.save(user);
        }
        return new PrincipalDetails(user,oAuth2User.getAttributes());
    }
}
