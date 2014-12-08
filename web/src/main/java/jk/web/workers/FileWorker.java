package jk.web.workers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.imageio.ImageIO;

import jk.web.user.Address.AddressType;
import jk.web.user.User.Gender;
import jk.web.user.entities.UserEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class FileWorker {

	private final Logger logger= LogManager.getLogger();

	public final static long maxFileSize = 10485760;// 1kB=1024B 1mb=1024kB=1048576B

	public final static String RESOURCE_HANDLER = "/res";

	//https://developers.google.com/maps/documentation/staticmaps/?csw=1#ImplicitPositioning
	public final static String GOOGLE_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?format=png32";
	public final static String GOOGLE_MAP_SIZE_PARAM = "&size=";
	public final static String GOOGLE_API_KEY_PARAM = "&key=";
	public final static String GOOGLE_MAP_MARKERS ="&markers=size:mid%7Ccolor:red%7C";
	public final static String IMAGES_UPL = "/images";
	public final static String MAPS_URL = IMAGES_UPL + "/maps/";
	public final static String  PROFILE_URL = IMAGES_UPL + "/profiles/";

	private final String FILES_PATH;
	private final String GOOGLE_API_KEY;
	private final String GOOGLE_MAP_SIZE;
	private final String MAPS_FULL_PATH;
	private final String PROFILE_IMAGES_FULL_PATH;


	private static final String IMAGE_FORMAT_NAME = "png";

	@Autowired
	private UserWorker userWorker;

	public FileWorker(String filesPath, String googleApiKey, String googleMapSize) {
		logger.entry(filesPath, googleApiKey, googleMapSize);
		FILES_PATH = filesPath;
		GOOGLE_API_KEY = GOOGLE_API_KEY_PARAM + googleApiKey;
		GOOGLE_MAP_SIZE = GOOGLE_MAP_SIZE_PARAM + googleMapSize;
		MAPS_FULL_PATH = FILES_PATH + MAPS_URL;
		PROFILE_IMAGES_FULL_PATH = FILES_PATH + PROFILE_URL;

		File file = new File(MAPS_FULL_PATH);
		if(!(file.exists() || file.isDirectory()))
				file.mkdirs();
	}

	public Thread saveMap(File file, String address, String city, String regionsCode, String country, String postalCode) {
		logger.entry(file, address, city, regionsCode, country, postalCode);
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				logger.entry();
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
					logger.trace("\n\t{}\n\t{}", url, file);

					if(file!=null){
						try {
							urlToFile(file, url);
						} catch (IOException e) {
							logger.catching(e);
						}
					}
				}
				logger.exit();
			}
		});
		t.setDaemon(true);
		int priority = t.getPriority();
		if(priority>Thread.MIN_PRIORITY)
			t.setPriority(priority-1);
		t.start();
		logger.exit();
		return t;
	}

	private void urlToFile(File file, String url) throws IOException {
		logger.trace("\n\t"
				+ "fileName -\t{}",
				file);
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		try(FileOutputStream fos = new FileOutputStream(file);){
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
	}

	public File getMapFile(Long userId, AddressType addressType){
		return new File(MAPS_FULL_PATH, getMapFileName(addressType, userId));
	}

	public String getMapFileUrl(AddressType addressType, Long userId) {
		File mapFile = getMapFile(userId, addressType);
		String url;
		if(mapFile.exists())
			url = RESOURCE_HANDLER + MAPS_URL+getMapFileName(addressType, userId);
		else
			url = null;
		return url;
	}

	public String getMapFileName(AddressType addressType, Long userId) {
		return "map" + addressType+userId + ".png";
	}

	public String getProfilePath(Long id) {
		return logger.exit(PROFILE_IMAGES_FULL_PATH + id + "." + IMAGE_FORMAT_NAME);
	}

	public void saveProfileImage(Long userID, BufferedImage bufferedImage) throws IOException {
		File outputDir = new File(PROFILE_IMAGES_FULL_PATH);
		if(!outputDir.exists())
			outputDir.mkdirs();
		ImageIO.write(bufferedImage, IMAGE_FORMAT_NAME, new File(outputDir, userID+"."+IMAGE_FORMAT_NAME));
	}

	public String getProfileImagge(String username) throws URISyntaxException, MalformedURLException {
		logger.entry(username);
		UserEntity userEntity = userWorker.getUserEntity(username);
		String imageURL;
		if(userEntity!=null){
			logger.trace(userEntity);
			Long id = userEntity.getLoginEntity().getId();
			File file = new File(getProfilePath(id));
			if(file.exists()){
				imageURL = RESOURCE_HANDLER + PROFILE_URL + file.getName();
			}else{

				Gender sex = userEntity.getGender();
				if(sex==null)
					imageURL = IMAGES_UPL + "/default/profile.png";
				else{
					switch(sex){
					case MALE:
						imageURL = IMAGES_UPL + "/default/profile_b.png";
						break;
					default:
						imageURL = IMAGES_UPL + "/default/profile_g.png";
					}
				}
			}
		}else{
			//Default image
			imageURL = IMAGES_UPL + "/default/profile.png";
		}
		return logger.exit(imageURL);
	}
}
