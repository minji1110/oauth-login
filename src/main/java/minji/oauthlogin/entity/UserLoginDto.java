package minji.oauthlogin.entity;

import lombok.Data;

@Data
public class UserLoginDto {
    private String userId;
    private String userPassword;
}
