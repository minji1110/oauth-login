package minji.oauthlogin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauthService principalOauthService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity.csrf().disable()
              .exceptionHandling()

              .and()
              .authorizeRequests()
              .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
              .antMatchers("/","/join","/login","/joinForm","/loginForm").permitAll()
              .anyRequest().authenticated()

              .and()
              .formLogin()
              .loginPage("/loginForm")
              .loginProcessingUrl("/login")
              .defaultSuccessUrl("/")

              .and()
              .oauth2Login()
              //google 로그인 후 처리? 엑세스토큰 + 사용자 프로필 정보를 받을 수 있음
              // ->이 정보 토대로 회원가입 시킬 수 있음
              .loginPage("/loginForm")
              .userInfoEndpoint()
              .userService(principalOauthService);
    }

}
