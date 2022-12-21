package autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

@AutoConfiguration
@ConditionalOnProperty(prefix = "spring.datasource", name = "url")
@ConditionalOnClass(name = "org.h2.Driver")
public class BasicDataSourceConfiguration {
    static Logger logger = LoggerFactory.getLogger(BasicDataSourceConfiguration.class);

    @Bean
    @ConditionalOnProperty(prefix = "commons.dbcp", name = "datasource", havingValue = "true")

    //conditioner l'exécution de la méthode à la présence de la propriete
    //commons.dbcp.datasource à la valeur true
    public DataSource myDataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username:sa}") String username,
            @Value("${spring.datasource.password:}") String password) {
        //System.err.println("*** myDataSource() ***");
        logger.info("BasicDataSource url:{}", url);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver"); //h2
        dataSource.setUrl(url); //spring.datasource.url
        dataSource.setUsername(username); //spring.datasource.username
        dataSource.setPassword(password); //spring.datasource.password
        return dataSource;
    }
}
