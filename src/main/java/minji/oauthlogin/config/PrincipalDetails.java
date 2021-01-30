package minji.oauthlogin.config;

import lombok.Getter;
import minji.oauthlogin.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//시큐리티가 /login을 낚아채서 로그인 진행->세션을 만들 것
//이 때 Authentication 객체 필요-> UserDetails 객체 필요-> 이를 변환해주기 위한 클래스
//즉 PrincipalDetails 가 세션정보로 들어감

@Getter
public class PrincipalDetails implements UserDetails , OAuth2User {

    private User user;
    private Map<String,Object> attributes;

    //일반 유저 생성자
    public PrincipalDetails(User user){
        this.user=user;
    }
    //OAuth 유저 생성자
    public PrincipalDetails(User user,Map<String,Object> attributes){
        this.user=user;
        this.attributes=attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection=new ArrayList<>();
        collection.add((GrantedAuthority) () -> user.getUserRole().toString());
        //role을 리턴해주므로 role 기준으로 권한을 판단하는 것!
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
