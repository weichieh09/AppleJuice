package tw.com.lyls.AppleJuice.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.lyls.AppleJuice.domain.mysql.ViewAuthUser;

import java.util.List;

@Repository
public interface ViewAuthUserRepository extends JpaRepository<ViewAuthUser, Long> {
    List<ViewAuthUser> findByUserId(Long userId);
}
