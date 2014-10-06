//************************************************************
//	Once the files are downloaded and extracted from the zips, unjar mysql-connector-mxj-gpl-5-0-12-db-files.jar and add a line to platform-map.properties file inside:
//	Windows_7-amd64=Win-x86
// source: http://stackoverflow.com/questions/9520536/missingresourceexception-running-mxj-for-mysql
// http://literatitech.blogspot.ca/2011/04/embedded-mysql-server-for-junit-testing.html
//************************************************************

package jk.web.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.management.MysqldResourceI;
import com.mysql.management.driverlaunched.ServerLauncherSocketFactory;

public class EmbeddedMysqlDataSource extends MysqlDataSource{
	private static final long serialVersionUID = 6002111388136790533L;
private String port;
  private String socket;
  private String url;
  private File basedir;
  private File datadir;
  private Connection connection;

  private static Logger logger = LogManager.getLogger();

  public static EmbeddedMysqlDataSource getInstance()
  {
    EmbeddedMysqlDataSource dataSource = null;
    try {
      dataSource = new EmbeddedMysqlDataSource("4000");
      dataSource.setUrl( dataSource.getEmbeddedUrl() );
      dataSource.setUser( "root" );
      dataSource.setPassword( "" );
    } catch( Exception e2 ) {
      dataSource = null;
      logger.info( "Could not create embedded server.  Skipping tests. (%s)", e2.getMessage() );
      e2.printStackTrace();
    }
    return dataSource;
  }

  public static void shutdown( EmbeddedMysqlDataSource ds )
  {
    try {
      ds.shutdown();
    } catch( IOException e ) {
      logger.info( "Could not shutdown embedded server. (%s)", e.getMessage() );
      e.printStackTrace();
    }
  }

  public EmbeddedMysqlDataSource( String port ) throws IOException{
	  super();
	  logger.entry(port);
    this.port = port;
    socket = "sock" + System.currentTimeMillis();

    // We need to set our own base/data dirs as we must
    // pass those values to the shutdown() method later
    basedir = File.createTempFile( "mysqld-base", null );
    datadir = File.createTempFile( "mysqld-data", null );

    // Wish there was a better way to make temp folders!
    basedir.delete();
    datadir.delete();
    basedir.mkdir();
    datadir.mkdir();
    basedir.deleteOnExit();
    datadir.deleteOnExit();

    StringBuilder sb = new StringBuilder();
    sb.append( String.format( "jdbc:mysql:mxj://localhost:%s/test", port ));
    sb.append( "?createDatabaseIfNotExist=true" );
    sb.append( "&server.basedir=" ).append( basedir.getPath() );
    sb.append( "&sessionVariables=wait_timeout=" ).append( 28000 );
    url = sb.toString();
  }

  public String getEmbeddedUrl(){
    return url;
  }

  @Override
  protected java.sql.Connection getConnection( Properties props ) throws SQLException {
	  logger.entry(props);
    if( connection == null || connection.isClosed()) {
      props.put( MysqldResourceI.PORT, port);
      props.put( MysqldResourceI.SOCKET, socket );
      props.put( MysqldResourceI.BASEDIR, basedir.getPath() );
      props.put( MysqldResourceI.DATADIR, datadir.getPath() );
       connection = super.getConnection( props );
    }
    logger.trace("\n\t{}\n\t{}",props, connection);
   return connection;
  }

  public void shutdown() throws IOException{
	  logger.entry();
    ServerLauncherSocketFactory.shutdown( basedir, datadir );
  }
}
