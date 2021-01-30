package minji.oauthlogin.config;

import lombok.RequiredArgsConstructor;
import minji.oauthlogin.entity.User;
import minji.oauthlogin.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티가 /login 요청이 오면 자동으로 UserDetailsService 타입으로 ioc 되어있는 loadUserByUsername 함수가 호출됨
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findUserByUserId(username);
        if(user!=null){
            //시큐리티 session(Authentication(UserDetails) 여기서 유저 디테일에 들어감
            return new PrincipalDetails(user);
        }
        return null;
    }
}
