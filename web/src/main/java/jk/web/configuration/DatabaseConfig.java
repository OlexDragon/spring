package jk.web.configuration;

import jk.web.database.Dump;
import net.sourceforge.jeval.EvaluationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	//Values from application.properties file
	@Value("${jk.db.dump.path}")
	private String dumpPath;

	@Value("${jk.db.dump.time}")
	private String dumpTime;

	@Value("${jk.mysql.path}")
	private String mysqldumpApp;

	@Bean
    public Dump startDarabaseDump() throws EvaluationException{
    	Dump dump = new Dump(mysqldumpApp, dumpPath, dumpTime);
		return dump;
    }
}
