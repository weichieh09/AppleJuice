package tw.com.lyls.AppleJuice.domain.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "view_auth_permission")
public class ViewAuthPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "auth_id")
    private Long authId;

    @Column(name = "auth_role")
    private String authRole;

    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "permission_resource")
    private String permissionResource;

    @Column(name = "permission_operation")
    private String permissionOperation;

}
