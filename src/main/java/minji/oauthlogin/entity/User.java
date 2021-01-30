package minji.oauthlogin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userId;
    @Column
    private String userName;
    @Column
    private String userEmail;
    @Column
    private String userPassword;
    @Column
    @Enumerated(EnumType.STRING)
    private Role userRole;

}
