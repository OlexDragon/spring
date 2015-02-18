package jk.web.workers;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import jk.web.data.beans.VideoProperties;
import jk.web.user.Address.AddressType;
import jk.web.user.User.Gender;
import jk.web.user.entities.FileEntity;
import jk.web.user.entities.ImageDetailsEntity;
import jk.web.user.entities.ImageDetailsEntity.ImageMaxSize;
import jk.web.user.entities.UserEntity;
import jk.web.user.repository.FileRepositiry;
import jk.web.workers.executors.ffmpeg.FFMpegExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.ImageSize;
import org.springframework.util.StreamUtils;
import org.springframework.web.accept.MappingMediaTypeFileExtensionResolver;
import org.springframework.web.multipart.MultipartFile;

public class FileWorker {

	private class CopyMaker implements Runnable {

		private long fileID;
		private File fileFrom;
		private Dimension resolution;

		public CopyMaker(long fileID, File fileFrom, Dimension resolution){
			logger.entry( fileID, fileFrom, resolution);
			this.fileID = fileID;
			this.fileFrom =fileFrom;
			this.resolution = resolution;
		}

		@Override
		public void run() {
			Path path = Paths.get(RESOURCES_PATH, ImageSize.BIGGER.name(), fileID+".mp4");
			File vf = path.toFile();
			logger.info("\n\t Copy file: {} to\n\t{}", fileFrom, vf);
			double factor = getFactor(resolution, largeSize);
			try {
				FFMpegExecutor.copyVideo(fileFrom.getAbsolutePath(), vf.getAbsolutePath(), (int)(resolution.width*factor), (int)(resolution.height*factor));
			} catch (IOException | InterruptedException e) {
				logger.catching(e);
			}
			logger.info("DONE");
		}

	}

	private final Logger logger= LogManager.getLogger();

	@Value("${jk.image.size.small}")
	private int smallSize;
	@Value("${jk.image.size.medium}")
	private int mediumSize;
	@Value("${jk.image.size.large}")
	private int largeSize;
	@Value("${jk.image.profile.size}")
	private int profileImgSize;

	@Autowired
	private FileRepositiry fileRepositiry;
	@Autowired
	private UserWorker userWorker;

	public final static String RESOURCE_HANDLER = "/res";

	//https://developers.google.com/maps/documentation/staticmaps/?csw=1#ImplicitPositioning
	public final static String GOOGLE_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?format=png32";
	public final static String GOOGLE_MAP_SIZE_PARAM = "&size=";
	public final static String GOOGLE_API_KEY_PARAM = "&key=";
	public final static String GOOGLE_MAP_MARKERS ="&markers=size:mid%7Ccolor:red%7C";
	public final static String MAPS_URL = "/maps/";
	public final static String  PROFILE_URL = "/profiles/";

	private final String RESOURCES_PATH;
	private final String GOOGLE_API_KEY;
	private final String GOOGLE_MAP_SIZE;
	private final String MAPS_FULL_PATH;
	private final String PROFILE_FULL_PATH;

	private static final MappingMediaTypeFileExtensionResolver fileResolver;
	public static final String DEFAULT_IMAGE_FORMAT_EXTENSION = ".png";
	public static final String DEFAULT_VIDEO_FORMAT_EXTENSION = ".mp4";

	public static final MediaType DEFAULT_IMAGE_MEDIA_TYPE = MediaType.IMAGE_PNG;
	public static final MediaType DEFAULT_VIDEO_IMAGE_MEDIA_TYPE = new MediaType("video", "mp4");

	static {
		HashMap<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
		mediaTypes.put(".jpg", MediaType.IMAGE_JPEG);
		mediaTypes.put(".png", MediaType.IMAGE_PNG);
		mediaTypes.put(".gif", MediaType.IMAGE_GIF);
		mediaTypes.put(".mp4", DEFAULT_VIDEO_IMAGE_MEDIA_TYPE);
		mediaTypes.put(".wmv", new MediaType("video","x-ms-wmv"));
		mediaTypes.put(".m2ts", new MediaType("video","vnd.dlna.mpeg-tts"));
		fileResolver = new MappingMediaTypeFileExtensionResolver(mediaTypes);
	}

	public FileWorker(String resourcesPath, String googleApiKey, String googleMapSize) {
		logger.entry(resourcesPath, googleApiKey, googleMapSize);
		RESOURCES_PATH = resourcesPath;
		GOOGLE_API_KEY = GOOGLE_API_KEY_PARAM + googleApiKey;
		GOOGLE_MAP_SIZE = GOOGLE_MAP_SIZE_PARAM + googleMapSize;
		MAPS_FULL_PATH = RESOURCES_PATH + MAPS_URL;
		PROFILE_FULL_PATH = RESOURCES_PATH+PROFILE_URL;

		File file = new File(MAPS_FULL_PATH);
		if(!(file.exists() || file.isDirectory()))
				file.mkdirs();
		file = new File(PROFILE_FULL_PATH);
		if(!(file.exists() || file.isDirectory()))
				file.mkdirs();
		file = new File(RESOURCES_PATH, ImageSize.ORIGINAL.name());
		if(!(file.exists() && file.isDirectory()))
			file.mkdirs();
		file = new File(RESOURCES_PATH, ImageSize.NORMAL.name());
		if(!(file.exists() && file.isDirectory()))
			file.mkdirs();
		file = new File(RESOURCES_PATH, ImageSize.BIGGER.name());
		if(!(file.exists() && file.isDirectory()))
			file.mkdirs();
		file = new File(RESOURCES_PATH, ImageSize.MINI.name());
		if(!(file.exists() && file.isDirectory()))
			file.mkdirs();
	}

	public Thread saveMap(final File file, final String address, final String city, final String regionsCode, final String country, final String postalCode) {
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
		return "map" + addressType+userId + DEFAULT_IMAGE_FORMAT_EXTENSION;
	}

	public File getFile(ImageSize imageSize, long imageID) {
		return new File(RESOURCES_PATH, imageSize + "/" + imageID + DEFAULT_IMAGE_FORMAT_EXTENSION);
	}

	public void saveFile(long userId, MultipartFile file) throws IOException {

		String contentType = file.getContentType();
		logger.trace("\n\t{}", contentType);

		if(contentType.startsWith("image/"))
			saveImage(userId, file);
		else if(contentType.startsWith("video/"))
			saveVideos(userId, file);
	}

	private void saveVideos(long userId, MultipartFile multipartFile) throws IllegalStateException, IOException {
		logger.entry();
		FileEntity filesEntity = newFileEntity(userId, multipartFile);
		filesEntity = fileRepositiry.save(filesEntity);
		
		Long fileID = filesEntity.getFileID();
		Path path = Paths.get(RESOURCES_PATH, ImageSize.ORIGINAL.name(), fileID+getFileExtension(multipartFile.getOriginalFilename(), true));
		File file = path.toFile();
		multipartFile.transferTo(file);

		try {
			//get video duration
			VideoProperties videoProperties = FFMpegExecutor.getVideoProperties(file.getAbsolutePath());
			Dimension resolution = videoProperties.getResolution();

			if(resolution!=null){

				ImageDetailsEntity imageDetailsEntity = new ImageDetailsEntity();
				imageDetailsEntity.setFileID(filesEntity.getFileID());
				imageDetailsEntity.setHight(resolution.height);
				imageDetailsEntity.setWidth(resolution.width);
				filesEntity = fileRepositiry.save(filesEntity);

				if(resolution.height>largeSize || resolution.width>largeSize){
					logger.trace("Copy the file({})", file);
					Thread t = new Thread(new CopyMaker(fileID, file, resolution));
					int priority = t.getPriority();
					if(priority>Thread.MIN_PRIORITY)
						t.setPriority(priority-1);
					t.start();
				}else
					logger.trace("Video resolution is too small({})", file);
			}
		} catch (InterruptedException e) {
			logger.catching(e);
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

		//Save Original Image
		File file = new File(RESOURCES_PATH, ImageSize.ORIGINAL.name());
		MediaType mediaType = MediaType.parseMediaType(multipartFile.getContentType());
		List<String> extensions = fileResolver.resolveFileExtensions(mediaType);
		file = new File(file, filesEntity.getFileID()+extensions.get(0));
		multipartFile.transferTo(file);

		ExecutorService threadPool = Executors.newFixedThreadPool(3);

		if(imageDetailsEntity.getHight()>smallSize || imageDetailsEntity.getWidth()>smallSize){
			//Save Small Image
			threadPool.execute(new SaveImage( new File(RESOURCES_PATH, ImageSize.MINI.name()), filesEntity.getFileID() + DEFAULT_IMAGE_FORMAT_EXTENSION, image, smallSize));

			if(imageDetailsEntity.getHight()>mediumSize || imageDetailsEntity.getWidth()>mediumSize){
			//Save Medium Image
				threadPool.execute(new SaveImage( new File(RESOURCES_PATH, ImageSize.NORMAL.name()), filesEntity.getFileID() + DEFAULT_IMAGE_FORMAT_EXTENSION, image, mediumSize));

				if(imageDetailsEntity.getHight()>largeSize || imageDetailsEntity.getWidth()>largeSize){
				//Save Large Image
					threadPool.execute(new SaveImage( new File(RESOURCES_PATH, ImageSize.BIGGER.name()), filesEntity.getFileID() + DEFAULT_IMAGE_FORMAT_EXTENSION, image, largeSize));
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
		String fe;
		int lastIndexOf = fn.lastIndexOf(".");
		if(lastIndexOf>0){
			fe = fn.substring(lastIndexOf+1);
			fn = fn.substring(0, lastIndexOf);
		}else
			fe = null;
		FileEntity filesEntity = new FileEntity();
		filesEntity.setUserID(userId);
		filesEntity.setContentType(file.getContentType());
		filesEntity.setFileName(fn);
		filesEntity.setFileExtension(fe);
		filesEntity.setSize(file.getSize());
		return logger.exit(filesEntity);
	}

	public String getProfilePath(Long id) {
		return logger.exit(PROFILE_FULL_PATH + id + DEFAULT_IMAGE_FORMAT_EXTENSION);
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
					imageURL = "/images/default/profile.png";
				else{
					switch(sex){
					case MALE:
						imageURL = "/images/default/profile_b.png";
						break;
					default:
						imageURL = "/images/default/profile_g.png";
					}
				}
			}
		}else{
			//Default image
			imageURL = "/images/default/profile.png";
		}
		return imageURL;
	}

	public boolean setProfileImage(long userID, long imageID) throws IOException {
		boolean saved = false;
		FileEntity fileEntity = fileRepositiry.findOne(imageID);
		if(fileEntity!=null && fileEntity.getUserID().equals(userID)){
			File f = Paths.get(RESOURCES_PATH, ImageSize.BIGGER.name(), imageID + DEFAULT_IMAGE_FORMAT_EXTENSION).toFile();
			if(f.exists()){
				BufferedImage bufferedImg = ImageIO.read(f);
				bufferedImg = Scalr.resize(bufferedImg, profileImgSize);
				f = new File(PROFILE_FULL_PATH, userID+DEFAULT_IMAGE_FORMAT_EXTENSION);
				ImageIO.write(bufferedImg, "png", f);
				saved = true;
			}
		}
		return saved;
	}

	public boolean setProfileImage(long userID, long imageID, int x, int y, int w, int h) throws IOException {
		boolean saved = false;
		FileEntity fileEntity = fileRepositiry.findOne(imageID);
		if(fileEntity!=null && fileEntity.getUserID().equals(userID)){
			File f = new File(RESOURCES_PATH, ImageSize.BIGGER + "/" + imageID + DEFAULT_IMAGE_FORMAT_EXTENSION);
			if(f.exists()){
				BufferedImage bufferedImg = ImageIO.read(f);
				bufferedImg = Scalr.resize(bufferedImg.getSubimage(x, y, w, h), profileImgSize);
				f = new File(PROFILE_FULL_PATH, userID+".png");
				ImageIO.write(bufferedImg, "png", f);
				saved = true;
			}
		}
		return saved;
	}

	public ResponseEntity<byte[]> getResponseEntity(long principalID, ImageSize imageSize, long imageID, long userID) throws IOException {

		FileEntity fileEntity = fileRepositiry.findOne(imageID);
		logger.trace("\n\timageSiz={}\n\tuserID={}\n\tfileRepositiry.findOne({})={}", imageSize, userID, imageID, fileEntity);

		String fileExtension;
		MediaType mediaType;
		File file;
		String contentType = fileEntity.getContentType();


		HttpHeaders headers = new HttpHeaders();
		InputStream is;
		if(fileEntity!=null && (principalID==userID || fileEntity.isShowToAll()) && fileEntity.getUserID().equals(userID)){

			if(imageSize == ImageSize.ORIGINAL){

				mediaType = MediaType.parseMediaType(contentType);
				fileExtension = "."+fileEntity.getFileExtension();

			}else if(contentType.startsWith("video/") && imageSize==ImageSize.BIGGER){

				mediaType = DEFAULT_VIDEO_IMAGE_MEDIA_TYPE;
				fileExtension = DEFAULT_VIDEO_FORMAT_EXTENSION;

			}else{

				mediaType = DEFAULT_IMAGE_MEDIA_TYPE;
				fileExtension = DEFAULT_IMAGE_FORMAT_EXTENSION;
			}

			file = Paths.get(RESOURCES_PATH, imageSize.name(), imageID + fileExtension).toFile();
			if(file.exists()){
				headers.setContentLength(file.length());
				is = new FileInputStream(file);
//				headers.set("Content-Disposition", "attachment");
//				headers.add("Content-Disposition", "filename=\"" + file.getName() + '\"');
			}else{
				is = FileWorker.class.getResourceAsStream("/static/images/cannotaccess.png");
				mediaType = DEFAULT_IMAGE_MEDIA_TYPE;
				fileExtension = DEFAULT_IMAGE_FORMAT_EXTENSION;
			}
		}else{
			is = FileWorker.class.getResourceAsStream("/static/images/cannotaccess.png");
			mediaType = DEFAULT_IMAGE_MEDIA_TYPE;
			fileExtension = DEFAULT_IMAGE_FORMAT_EXTENSION;
		}

		headers.set("Content-Disposition", "filename=\"" + fileEntity.getFileName() + fileExtension + '\"');
		headers.setContentType(mediaType);

		logger.trace("\n\t {}\n\t {}", headers, fileExtension);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
			StreamUtils.copy(is, os);

			return new ResponseEntity<byte[]>(os.toByteArray(), headers, HttpStatus.OK);
	}

	public boolean delete(long imageID, long userID) {
		String str = "%s/%s%s";
		boolean deleted = false;
		
		FileEntity fileEntity = fileRepositiry.findOne(imageID);
		if(fileEntity!=null && fileEntity.getUserID().equals(userID)){
			fileRepositiry.delete(fileEntity);
			File file = new File(RESOURCES_PATH);
			String fileExtension;
			for(ImageSize is:ImageSize.values()){
				switch(is){
				case ORIGINAL:
					String contentType = fileEntity.getContentType();
					List<String> fileExtensions = fileResolver.resolveFileExtensions(MediaType.parseMediaType(contentType));
					fileExtension = fileExtensions.get(0);
					break;
				default:
					fileExtension = DEFAULT_IMAGE_FORMAT_EXTENSION;
				}

				File f = new File(file, String.format(str, is.name(), imageID, fileExtension));
				try{
					f.delete();
				}catch(SecurityException e){
					logger.catching(e);
				}
				deleted = true;
			}
		}

		return deleted;
	}

	//******************** Class SaveImage ******************************************
	private class SaveImage implements Runnable{

		private File file;
		private String iageName;
		private BufferedImage src;
		private int maxSize;

		public SaveImage( File destination, String iageName, BufferedImage src, int maxSize){
			this.file = destination;
			this.iageName = iageName;
			this.src = src;
			this.maxSize = maxSize;
		}

		@Override
		public void run() {
			file = new File(file, iageName);
			try {
				ImageIO.write(Scalr.resize(src, maxSize), "png", file);
			} catch (IllegalArgumentException | ImagingOpException | IOException e) {
				logger.catching(e);
			}
		}
		
	}

	private String getFileExtension(String fileName, boolean withDot){
		String ext = "";
		if(fileName!=null){
			int lastIndexOf = fileName.lastIndexOf(".");
			if(lastIndexOf>0)
				if(withDot)
					ext = fileName.substring(lastIndexOf);
				else
					ext = fileName.substring(lastIndexOf+1);
		}
		return ext;
	}

	public static double getFactor(Dimension resolution, final int maxSize) {
		double factor;
		if(resolution.height > resolution.width)
			factor = (double)maxSize/resolution.height;
		else
			factor = (double)maxSize/resolution.width;
		return factor;
	}

	public void setShowToAll(long userID, long imageID, boolean show) {

		FileEntity fileEntity = fileRepositiry.findOne(imageID);
		if(fileEntity!=null && fileEntity.getUserID().equals(userID)){
			fileEntity.setShowToAll(show);
			fileEntity = fileRepositiry.save(fileEntity);
			logger.trace(fileEntity);
		}
	}
}
