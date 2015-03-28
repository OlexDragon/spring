package jk.web.workers.rest.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;

import reactor.event.Event;
import reactor.function.Function;

public class Thumbnailer implements Function<Event<Path>, Path> {

	private final Logger logger = LogManager.getLogger();

	private int maxImageSide;

	public Thumbnailer(int maxImageSide){
		this.maxImageSide = maxImageSide;
	}

	@Override
	public Path apply(Event<Path> event) {
		Path thumbnailPath;
		try {
			Path srcPath = event.getData();
			BufferedImage imgIn = ImageIO.read(srcPath.toFile());

			BufferedImage resizedImage = Scalr.resize(imgIn, maxImageSide, Scalr.OP_ANTIALIAS);

			thumbnailPath = Files.createTempFile("thumbnail", ".jpg").toAbsolutePath();
			ImageIO.write(resizedImage, "jpeg", thumbnailPath.toFile());

		} catch (IOException e) {
			logger.catching(e);
			thumbnailPath = null;
		}

		return thumbnailPath;
	}
}
