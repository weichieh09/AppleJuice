package tw.com.lyls.AppleJuice.domain.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Car")
public class Car {

    @Id
    private Long id;

    private String brand;

}
