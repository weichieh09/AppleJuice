package tw.com.lyls.AppleJuice.domain.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "view_auth_user")
public class ViewAuthUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "auth_id")
    private Long authId;

    @Column(name = "auth_role")
    private String authRole;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_username")
    private String userUsername;

    @Column(name = "user_password")
    private String userPassword;

}
