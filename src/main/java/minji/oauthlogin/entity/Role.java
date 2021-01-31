package minji.oauthlogin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER("사용자"),ROLE_ADMIN("관리자");
    private String roleName;
}
