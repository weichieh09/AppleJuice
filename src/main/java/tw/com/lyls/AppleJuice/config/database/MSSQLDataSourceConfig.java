package tw.com.lyls.AppleJuice.config.database;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * MSSQL 的範例資料來源配置類別，注意 @Configuration、@EnableJpaRepositories、@Primary 與相關路徑的使用。
 * 設定 MSSQL 連線資訊，並註冊對應的 EntityManagerFactory 與 TransactionManager。
 */
//@Configuration
@Setter
@ConfigurationProperties(prefix = "spring.datasource.mssql")
//@EnableJpaRepositories(
//        basePackages = "tw.com.lyls.AppleJuice.repository.mssql",
//        entityManagerFactoryRef = "mssqlEntityManagerFactory",
//        transactionManagerRef = "mssqlTransactionManager"
//)
public class MSSQLDataSourceConfig extends AbstractDataSourceConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    /**
     * 設定 MSSQL 資料來源。
     */
//    @Primary
    @Bean(name = "mssqlDataSource")
    public DataSource mssqlDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    /**
     * 設定 MSSQL 的 EntityManagerFactory。
     */
//    @Primary
    @Bean(name = "mssqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mssqlEntityManagerFactory(
            @Qualifier("mssqlDataSource") DataSource dataSource) {
        return createEntityManagerFactory(dataSource, "tw.com.lyls.AppleJuice.domain.mssql");
    }

    /**
     * 設定 MSSQL 的 TransactionManager。
     */
//    @Primary
    @Bean(name = "mssqlTransactionManager")
    public JpaTransactionManager mssqlTransactionManager(
            @Qualifier("mssqlEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}