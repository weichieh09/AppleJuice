package tw.com.lyls.AppleJuice.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.lyls.AppleJuice.domain.mysql.VwRoleUser;

import java.util.List;

@Repository
public interface VwRoleUserRepository extends JpaRepository<VwRoleUser, Long> {
    List<VwRoleUser> findByUserId(Long userId);
}
