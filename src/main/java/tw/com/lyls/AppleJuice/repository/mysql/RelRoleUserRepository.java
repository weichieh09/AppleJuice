package tw.com.lyls.AppleJuice.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.lyls.AppleJuice.domain.mysql.RelRoleUser;

@Repository
public interface RelRoleUserRepository extends JpaRepository<RelRoleUser, Long> {
}
