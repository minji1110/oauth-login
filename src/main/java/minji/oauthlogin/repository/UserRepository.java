package minji.oauthlogin.repository;

import minji.oauthlogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
     User findUserByUserId(String userId);
}
