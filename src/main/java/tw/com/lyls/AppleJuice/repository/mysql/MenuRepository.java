package tw.com.lyls.AppleJuice.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.lyls.AppleJuice.domain.mysql.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
