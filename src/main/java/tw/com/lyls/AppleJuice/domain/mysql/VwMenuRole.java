package tw.com.lyls.AppleJuice.domain.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "vw_menu_role")
public class VwMenuRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_parent_id")
    private Long menuParentId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_weight")
    private Long menuWeight;

}
