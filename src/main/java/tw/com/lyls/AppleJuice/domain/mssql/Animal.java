package tw.com.lyls.AppleJuice.domain.mssql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Animal")
public class Animal {
    @Id
    private Long id;

    private String name;
}
