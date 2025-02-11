package tw.com.lyls.AppleJuice.domain.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "permission")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 資源類型，例如 "PRODUCT"、"ORDER" 等
    @Column(name = "resource")
    private String resource;

    // 操作類型，例如 "CREATE"、"EDIT"、"VIEW"、"DELETE"
    @Column(name = "operation")
    private String operation;

}
