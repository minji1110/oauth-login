package minji.oauthlogin.entity;

import lombok.Data;

@Data
public class UserJoinDto {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPassword;
}
