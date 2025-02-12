package tw.com.lyls.AppleJuice.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.lyls.AppleJuice.domain.mysql.VwPermissionRole;

@Repository
public interface VwPermissionRoleRepository extends JpaRepository<VwPermissionRole, Long> {
    // 利用物件關係中的 auth.role 來查詢
    boolean existsByRoleNameAndPermissionResourceAndPermissionOperation(String roleName, String permissionResource, String permissionOperation);
}
