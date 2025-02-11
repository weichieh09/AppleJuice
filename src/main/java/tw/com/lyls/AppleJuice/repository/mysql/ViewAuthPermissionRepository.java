package tw.com.lyls.AppleJuice.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.lyls.AppleJuice.domain.mysql.ViewAuthPermission;

@Repository
public interface ViewAuthPermissionRepository extends JpaRepository<ViewAuthPermission, Long> {
    // 利用物件關係中的 auth.role 來查詢
    boolean existsByAuthRoleAndPermissionResourceAndPermissionOperation(String authRole, String permissionResource, String permissionOperation);
}
