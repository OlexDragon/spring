package jk.web.workers.executors.ffmpeg;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jk.web.data.beans.VideoProperties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class FFMpegExecutor {

	private static final Logger logger = LogManager.getLogger();

	@Value("${jk.process.timeout}")
	private static long timeout;

	private static final String EXE_FILE = "ffmpeg.exe";

	private static File f = new File(EXE_FILE);
	static{
		URL url = FFMpegExecutor.class.getResource(EXE_FILE);
		try {
			long lastModified = url.openConnection().getLastModified();
			if(!f.exists() || f.lastModified()<lastModified){
				try(InputStream is = FFMpegExecutor.class.getResourceAsStream(EXE_FILE)){
					Files.copy(is, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
					logger.info("File '{}' copied from jar file.", EXE_FILE);
				}
			}
		} catch (IOException e) {
			logger.catching(e);
		}
	}

	public static void getThumb(String sourceVideoFilename, String thumbFilename, long sec) throws IOException, InterruptedException{
		logger.trace("\n\t sec:{}", sec);

		ProcessBuilder processBuilder = new ProcessBuilder(EXE_FILE, "-y","-ss",  Long.toString(sec), "-i", sourceVideoFilename, "-vframes", "1", "-f", "mjpeg", thumbFilename);
		Process process = processBuilder.start();

		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer err = new StringBuffer();
		String line;
		final String lineSeparator = System.getProperty("line.separator");
		while ((line = br.readLine()) != null){
			err.append(line);
			err.append(lineSeparator);
		}
		if(err.length()>0)
			logger.info(err);

		process.waitFor(timeout, TimeUnit.MINUTES);


	}

	public static VideoProperties getVideoProperties(String videoFilename) throws IOException, InterruptedException{
		String tmpFile = videoFilename + ".tmp.png";
		//-y (global): Overwrite output files without asking.
		//-n (global): Do not overwrite output files, and exit immediately if a specified output file already exists. 
		//-i filename (input): input file name
		//-vframes number: Set the number of video frames to output. This is an alias for -frames:v. 
		//-ss position (input/output): position may be either in seconds or in hh:mm:ss[.xxx] form. 
		//-f fmt (input/output):Force input or output file format. The format is normally auto detected for input files and guessed from the file extension for output files, so this option is not needed in most cases.
		ProcessBuilder processBuilder = new ProcessBuilder(EXE_FILE, "-y", "-i", videoFilename, "-vframes", "1", "-ss", "0", "-an", "-vcodec", "png", "-f", "rawvideo", "-s", "100*100", tmpFile);
		Process process = processBuilder.start();
		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);

		StringBuffer err = new StringBuffer();
		String line;
			final String lineSeparator = System.getProperty("line.separator");
			while ((line = br.readLine()) != null){
				err.append(line);
				err.append(lineSeparator);
			}
			if(err.length()>0)
				logger.info(err);

			new File(tmpFile).delete();
			Pattern pattern = Pattern.compile("Duration: (.*?),");

			Matcher matcher = pattern.matcher(err);

			LocalTime calcTime = null;
			if (matcher.find()) {
				String time = matcher.group(1);
				calcTime = calcTime(time);
			}

			pattern = Pattern.compile(", (\\d+x\\d+)");
			matcher = pattern.matcher(err);
			Dimension resolution;

			if (matcher.find()) {
				String[] group = matcher.group().split("x");
				resolution = new Dimension(Integer.parseInt(group[0].replaceAll("\\D", "")), Integer.parseInt(group[1].replaceAll("\\D", "")));
			}else
				resolution = null;

			process.waitFor(timeout, TimeUnit.MINUTES);

			VideoProperties videoProperties = new VideoProperties();
			videoProperties.setDuration(calcTime);
			videoProperties.setResolution(resolution);

			return logger.exit(videoProperties);
	}

	public static Process copyVideo(String from, String to, int width, int height) throws IOException, InterruptedException {
		logger.trace("\n\t from: {}\n\t to: {}\n\t width: {}\n\t height: {}", from, to, width, height);

		if(width%2==1) width++;
		if(height%2==1) height++;

		Process process = new ProcessBuilder(EXE_FILE, "-y", "-i", from, "-vf", "scale="+width+":"+height, to).start();

		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		if(logger.getLevel().compareTo(Level.TRACE)>=0){
			String line;
			while ((line = br.readLine()) != null) {
				logger.info("\n\t{}", line);
			}
		}
		process.waitFor(timeout, TimeUnit.MINUTES);

		return process;
	}

	private static LocalTime calcTime(String time) {
		logger.entry(time);

		String[] parts = time.split(":");
		int hours = Integer.parseInt(parts[0]);
		int minutes = Integer.parseInt(parts[1]);
		String[] split = parts[2].split("\\.");
		int seconds = Integer.parseInt(split[0]);
		int nanoOfSecond = Integer.parseInt(split[1])*10000000;
		LocalTime localTime = LocalTime.of(hours, minutes, seconds, nanoOfSecond);
		return logger.exit(localTime);
	}
}