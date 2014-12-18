package jk.web.workers;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import jk.web.user.Address.AddressType;
import jk.web.user.User.Gender;
import jk.web.user.entities.FileEntity;
import jk.web.user.entities.ImageDetailsEntity;
import jk.web.user.entities.ImageDetailsEntity.ImageMaxSize;
import jk.web.user.entities.UserEntity;
import jk.web.user.repository.FileRepositiry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.accept.MappingMediaTypeFileExtensionResolver;
import org.springframework.web.multipart.MultipartFile;

public class FileWorker {

	private final Logger logger= LogManager.getLogger();

	@Value("${jk.image.size.small}")
	private int smallSize;
	@Value("${jk.image.size.medium}")
	private int mediumSize;
	@Value("${jk.image.size.large}")
	private int largeSize;

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
	private final String IMAGES_FULL_PATH;

	private static final MappingMediaTypeFileExtensionResolver fileResolver;
	public static final String IMAGE_FORMAT_NAME = "png";

	static {
		HashMap<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
		mediaTypes.put("jpg", MediaType.IMAGE_JPEG);
		mediaTypes.put("png", MediaType.IMAGE_PNG);
		mediaTypes.put("gif", MediaType.IMAGE_GIF);
		fileResolver = new MappingMediaTypeFileExtensionResolver(mediaTypes);
	}

	@Autowired
	private FileRepositiry fileRepositiry;
	@Autowired
	private UserWorker userWorker;

	public FileWorker(String filesPath, String googleApiKey, String googleMapSize) {
		logger.entry(filesPath, googleApiKey, googleMapSize);
		FILES_PATH = filesPath;
		GOOGLE_API_KEY = GOOGLE_API_KEY_PARAM + googleApiKey;
		GOOGLE_MAP_SIZE = GOOGLE_MAP_SIZE_PARAM + googleMapSize;
		MAPS_FULL_PATH = FILES_PATH + MAPS_URL;
		IMAGES_FULL_PATH = FILES_PATH + IMAGES_UPL;

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
		return logger.exit(IMAGES_FULL_PATH + id + "." + IMAGE_FORMAT_NAME);
	}

	public void saveFile(long userId, MultipartFile file) throws IOException {

		String contentType = file.getContentType();

		 switch(contentType){
		 case MediaType.IMAGE_GIF_VALUE:
		 case MediaType.IMAGE_JPEG_VALUE:
		 case MediaType.IMAGE_PNG_VALUE:
			 saveImage(userId, file);
		 }
	}

	private void saveImage(long userId, MultipartFile multipartFile) throws IllegalStateException, IOException {

		FileEntity filesEntity = newFileEntity(userId, multipartFile);
		filesEntity = fileRepositiry.save(filesEntity);

		BufferedImage image = ImageIO.read(multipartFile.getInputStream());

		ImageDetailsEntity imageDetailsEntity = new ImageDetailsEntity();
		imageDetailsEntity.setFileID(filesEntity.getFileID());
		imageDetailsEntity.setHight(image.getHeight());
		imageDetailsEntity.setWidth(image.getWidth());


		FileSystem fileSystemDefault = FileSystems.getDefault();
		Path mainPath = fileSystemDefault.getPath(IMAGES_FULL_PATH, Long.toString(userId));

		//Save Original Image
		File file = new File(mainPath.toFile(), "original");
		if(!(file.exists() && file.isDirectory()))
			file.mkdirs();
		MediaType mediaType = MediaType.parseMediaType(multipartFile.getContentType());
		List<String> extensions = fileResolver.resolveFileExtensions(mediaType);
		file = new File(file, filesEntity.getFileID()+"."+extensions.get(0));
		multipartFile.transferTo(file);

		ExecutorService threadPool = Executors.newFixedThreadPool(3);

		if(imageDetailsEntity.getHight()>smallSize || imageDetailsEntity.getWidth()>smallSize){
			//Save Small Image
			threadPool.execute(new SaveImage( new File(mainPath.toFile(), "small"), filesEntity.getFileID() + ".png", image, smallSize));

			if(imageDetailsEntity.getHight()>mediumSize || imageDetailsEntity.getWidth()>mediumSize){
			//Save Medium Image
				threadPool.execute(new SaveImage( new File(mainPath.toFile(), "medium"), filesEntity.getFileID() + ".png", image, mediumSize));

				if(imageDetailsEntity.getHight()>largeSize || imageDetailsEntity.getWidth()>largeSize){
				//Save Large Image
					threadPool.execute(new SaveImage( new File(mainPath.toFile(), "large"), filesEntity.getFileID() + ".png", image, largeSize));
					imageDetailsEntity.setImageMaxSize(ImageMaxSize.LARGE);
				}else
					imageDetailsEntity.setImageMaxSize(ImageMaxSize.MEDIUM);
			}else
				imageDetailsEntity.setImageMaxSize(ImageMaxSize.SMALL);
		}else
			imageDetailsEntity.setImageMaxSize(ImageMaxSize.ORIGINAL);

		filesEntity.setImageDetailsEntity(imageDetailsEntity);
		filesEntity = fileRepositiry.save(filesEntity);

		threadPool.shutdown();
		try {
			threadPool.awaitTermination(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.catching(e);
		}
		logger.trace("\n\t{}", filesEntity);
	}

	public FileEntity newFileEntity(long userId, MultipartFile file) {
		String fn = new File(file.getOriginalFilename()).getName();
		FileEntity filesEntity = new FileEntity();
		filesEntity.setUserID(userId);
		filesEntity.setContentType(file.getContentType());
		filesEntity.setFileName(fn);
		filesEntity.setSize(file.getSize());
		return logger.exit(filesEntity);
	}

	public String getProfileImage(String username){
		logger.entry(username);
		UserEntity userEntity = userWorker.getUserEntity(username);
		String imageURL;
		imageURL = getProfileImage(userEntity);
		return logger.exit(imageURL);
	}

	public String getProfileImage(UserEntity userEntity) {
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
		return imageURL;
	}

	private class SaveImage implements Runnable{

		private File file;
		private String iageName;
		private BufferedImage src;
		private int maxSize;

		public SaveImage( File file, String iageName, BufferedImage src, int maxSize){
			this.file = file;
			this.iageName = iageName;
			this.src = src;
			this.maxSize = maxSize;
		}
		@Override
		public void run() {
			if (!(file.exists() && file.isDirectory()))
				file.mkdirs();
			file = new File(file, iageName);
			try {
				ImageIO.write(Scalr.resize(src, maxSize), "png", file);
			} catch (IllegalArgumentException | ImagingOpException | IOException e) {
				logger.catching(e);
			}
		}
		
	}
}
