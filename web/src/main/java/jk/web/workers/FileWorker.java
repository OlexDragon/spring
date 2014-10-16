package jk.web.workers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import jk.web.configuration.WebConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileWorker {

	private final Logger logger= LogManager.getLogger();

	//https://developers.google.com/maps/documentation/staticmaps/?csw=1#ImplicitPositioning
	public final static String GOOGLE_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?format=png32";
	public final static String GOOGLE_MAP_SIZE_PARAM = "&size=";
	public final static String GOOGLE_API_KEY_PARAM = "&key=";
	public final static String GOOGLE_MAP_MARKERS ="&markers=size:mid%7Ccolor:red%7C";
	public final static String MAPS_RELATIVE_PATH = File.separator+"images"+File.separator+"maps"+File.separator;

	private final String FILES_PATH;
	private final String GOOGLE_API_KEY;
	private final String GOOGLE_MAP_SIZE;
	private final String MAPS_FULL_PATH;

	public FileWorker(String filesPath, String googleApiKey, String googleMapSize) {
		logger.entry(filesPath, googleApiKey, googleMapSize);
		FILES_PATH = filesPath;
		GOOGLE_API_KEY = GOOGLE_API_KEY_PARAM+googleApiKey;
		GOOGLE_MAP_SIZE = GOOGLE_MAP_SIZE_PARAM+googleMapSize;
		MAPS_FULL_PATH = FILES_PATH+MAPS_RELATIVE_PATH;

		File file = new File(MAPS_FULL_PATH);
		if(!(file.exists() || file.isDirectory()))
				file.mkdirs();
	}

	public void saveMap(Long userId, String address, String city, String regionsCode, String country, String postalCode) {
		StringBuilder sb = new StringBuilder();
		if(address!=null && !address.isEmpty())
			sb.append(address);
		if(city!=null){
			if(sb.length()>0)
				sb.append(',');
			sb.append(city);
		}
		if(regionsCode!=null && !regionsCode.isEmpty()){
			if(sb.length()>0)
				sb.append(',');
			sb.append(regionsCode);
		}
		if(country!=null && !country.isEmpty()){
			if(sb.length()>0)
				sb.append(',');
			sb.append(country);
		}
		if(postalCode!=null && !postalCode.isEmpty()){
			if(sb.length()>0)
				sb.append(',');
			sb.append(postalCode);
		}

		if(sb.length()>0){
			sb.insert(0, GOOGLE_MAP_MARKERS);
			sb.insert(0, GOOGLE_MAP_URL);
			sb.append(GOOGLE_MAP_SIZE);
			sb.append(GOOGLE_API_KEY);
			String url = sb.toString().trim().replaceAll(" ", "+");
			logger.trace("\n\t{}", url);

			if(userId!=null){
				try {
					urlToFile(MAPS_FULL_PATH+userId+".png", url);
				} catch (IOException e) {
					logger.catching(e);
				}
			}
		}
	}

	private void urlToFile(String fileName, String url) throws IOException {
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		try(FileOutputStream fos = new FileOutputStream(fileName);){
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
	}

	public String getMapPath(Long userId){
		logger.entry(userId);
		String fileName = userId+".png";
		File f = new File(MAPS_FULL_PATH, fileName);
		String pathname;
		if(f.exists())
			pathname = WebConfig.MAPES_RESOURCE+fileName;
		else
			pathname = null;
		return logger.exit(pathname);
	}
}
