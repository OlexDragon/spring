package jk.web.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class Dump extends Thread {

	private final Logger logger = LogManager.getLogger();

	public final String MYSQLDUMP_PATH;
	private final int dumpEvery;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

	@Autowired
	public Dump(String mysqldumpApp, String dumpPath, String dumpTime) throws EvaluationException {
		logger.entry(dumpPath, dumpTime);
		Evaluator evaluator = new Evaluator();

		dumpEvery = (int) Math.round(evaluator.getNumberResult(dumpTime));
		char charAt = dumpPath.charAt(dumpPath.length()-1);
		if(!(charAt=='/' || charAt=='\\'))
			dumpPath += File.separatorChar;

		MYSQLDUMP_PATH = mysqldumpApp+" -u jk -ppotomkina --all-databases > "+dumpPath+"jk-";
		logger.trace(MYSQLDUMP_PATH);

		setDaemon(true);
		setPriority(Thread.MIN_PRIORITY);
		start();
	}

	@Override
	public void run() {
		try {
			File dumpFolder = new File("C:\\jk\\db");
			if(!dumpFolder.exists() || !dumpFolder.isDirectory())
				dumpFolder.mkdir();

			while(true){
				Thread.sleep(dumpEvery);
				logger.trace("RUN");

				//Process exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c","C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysqldump "+fisier.getName()+" > C:\\"+fisier.getName()+".sql;"});

				String path = MYSQLDUMP_PATH+sdf.format(Calendar.getInstance().getTime())+".sql";
				logger.info(path);
				Process exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c", path});

				if(exec.waitFor()==0){
				    //normally terminated, a way to read the output
				    InputStream inputStream = exec.getInputStream();
				    byte[] buffer = new byte[inputStream.available()];
				    inputStream.read(buffer);

				    logger.trace(new String(buffer));
				}else{
				    // abnormally terminated, there was some problem
				                //a way to read the error during the execution of the command
				    InputStream errorStream = exec.getErrorStream();
				    byte[] buffer = new byte[errorStream.available()];
				    errorStream.read(buffer);

				   logger.error(new String(buffer));
				}
			}
		} catch (InterruptedException | IOException e) {
			logger.catching(e);
		}
	}

	public void setDumpPath(String dumpPath) {
		logger.entry(dumpPath);
	}

}
