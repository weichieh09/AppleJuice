package tw.com.lyls.AppleJuice.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.lyls.AppleJuice.domain.mysql.VwMenuRole;

import java.util.List;

@Repository
public interface VwMenuRoleRepository extends JpaRepository<VwMenuRole, Long> {
    List<VwMenuRole> findAllByRoleNameIn(List<String> roles);
}
