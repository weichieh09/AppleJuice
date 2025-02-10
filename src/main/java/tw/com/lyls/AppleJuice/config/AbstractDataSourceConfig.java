package tw.com.lyls.AppleJuice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象資料來源配置類別，提供通用的 JPA 設定與 EntityManagerFactory 建立方法。
 */
public abstract class AbstractDataSourceConfig {

    @Value("${spring.jpa.show-sql}")
    private boolean SHOW_SQL; // 是否顯示 SQL 語句

    /**
     * 設定 JPA 相關屬性，例如 Hibernate 自動建表策略與 SQL 顯示選項。
     */
    protected Map<String, Object> jpaProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", SHOW_SQL);
        return properties;
    }

    /**
     * 建立 EntityManagerFactory，設定 DataSource 與 JPA 屬性。
     *
     * @param dataSource     資料來源
     * @param packagesToScan 需掃描的 JPA 實體類別路徑
     * @return LocalContainerEntityManagerFactoryBean 實體工廠
     */
    protected LocalContainerEntityManagerFactoryBean createEntityManagerFactory(
            DataSource dataSource, String packagesToScan) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(packagesToScan);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaPropertyMap(jpaProperties());
        return factoryBean;
    }
}