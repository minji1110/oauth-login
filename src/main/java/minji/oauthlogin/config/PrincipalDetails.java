package minji.oauthlogin.config;

import minji.oauthlogin.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//시큐리티가 /login을 낚아채서 로그인 진행->세션을 만들 것
//이 때 Authentication 객체 필요-> UserDetails객체 필요
//이를 변환해주기 위한 클래스
public class PrincipalDetails implements UserDetails {

    private User user;
    public PrincipalDetails(User user){
        this.user=user;
    }

    //해당 user의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection=new ArrayList<>();
        collection.add((GrantedAuthority) () -> user.getUserRole().toString());
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
