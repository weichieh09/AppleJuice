package tw.com.lyls.AppleJuice.domain.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 角色名稱，如 ROLE_USER、ROLE_ADMIN 等
    @Column(name = "name")
    private String name;

}
