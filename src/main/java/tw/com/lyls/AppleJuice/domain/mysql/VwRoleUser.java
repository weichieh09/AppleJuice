package tw.com.lyls.AppleJuice.domain.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "vw_role_user")
public class VwRoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_username")
    private String userUsername;

    @Column(name = "user_password")
    private String userPassword;

}
