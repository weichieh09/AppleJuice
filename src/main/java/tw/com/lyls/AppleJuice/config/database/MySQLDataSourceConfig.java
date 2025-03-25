package tw.com.lyls.AppleJuice.config.database;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * MySQL 的範例資料來源配置類別，注意 @Configuration、@EnableJpaRepositories、@Primary 與相關路徑的使用。
 * 設定 MySQL 連線資訊，並註冊對應的 EntityManagerFactory 與 TransactionManager。
 */
@Configuration
@Setter
@ConfigurationProperties(prefix = "spring.datasource.mysql")
@EnableJpaRepositories(
        basePackages = "tw.com.lyls.AppleJuice.repository.mysql",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
public class MySQLDataSourceConfig extends AbstractDataSourceConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    /**
     * 設定 MySQL 為主要資料來源，並加入 @RefreshScope 以支援動態刷新。
     */
    @Primary
    @RefreshScope
    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    /**
     * 設定 MySQL 的 EntityManagerFactory，並標記為主要 EntityManagerFactory。
     */
    @Primary
    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            @Qualifier("mysqlDataSource") DataSource dataSource) {
        return createEntityManagerFactory(dataSource, "tw.com.lyls.AppleJuice.domain.mysql");
    }

    /**
     * 設定 MySQL 的 TransactionManager，並標記為主要 TransactionManager。
     */
    @Primary
    @Bean(name = "mysqlTransactionManager")
    public JpaTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}