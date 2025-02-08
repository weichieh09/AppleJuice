package tw.com.lyls.AppleJuice.repository.mssql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.lyls.AppleJuice.domain.mssql.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
