package tw.com.lyls.AppleJuice.domain.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "vw_permission_role")
public class VwPermissionRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "permission_resource")
    private String permissionResource;

    @Column(name = "permission_operation")
    private String permissionOperation;

}
