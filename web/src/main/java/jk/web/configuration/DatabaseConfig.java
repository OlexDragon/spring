package jk.web.configuration;

import jk.web.database.Dump;
import net.sourceforge.jeval.EvaluationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	//Values from application.properties file
	@Value("${dump_path}")
	private String dumpPath;

	@Value("${dump_time}")
	private String dumpTime;

	@Value("${dump_mysql_app}")
	private String mysqldumpApp;

	@Bean
    public Dump startDarabaseDump() throws EvaluationException{
    	Dump dump = new Dump(mysqldumpApp, dumpPath, dumpTime);
		return dump;
    }
}