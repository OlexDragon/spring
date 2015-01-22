package jk.web.workers.executors.ffmpeg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import jk.web.data.beans.VideoProperties;
import jk.web.workers.FileWorker;

import org.junit.Test;

public class FFMpegExecutorTest {

	private static final String RESULT1 = "00:03:52.053";
	private final String VIDEO1 = "C:/Users/Alex/Videos/Sweet Memories Sasha & Ira.wmv";
	private final String VIDEO = "C:/Users/Alex/Videos/20141207162603.m2ts";
	private final String toFile = "thumb";

	@Test
	public void getInfoTest() throws IOException, InterruptedException {
		VideoProperties properties = FFMpegExecutor.getVideoProperties(VIDEO1);
		System.out.println(properties);
		assertEquals(RESULT1, properties.getDuration().toString());
	}


	@Test
	public void getImageTest() throws IOException, InterruptedException {

		VideoProperties properties = FFMpegExecutor.getVideoProperties(VIDEO);
		Random r = new Random();

		File file = File.createTempFile(toFile, ".png");
		String absolutePath = file.getAbsolutePath();
		System.out.print("absolutePath:"+absolutePath);
		FFMpegExecutor.getThumb(VIDEO, absolutePath, r.nextInt(properties.getDuration().getSecond()));

		Process p = new ProcessBuilder("cmd" , "/c", "start", absolutePath).start();
		p.waitFor();

		assertTrue(file.exists());
	}

	@Test
	public void copyTest() throws IOException, InterruptedException {
		File file = File.createTempFile(toFile, ".mp4");
		String absolutePath = file.getAbsolutePath();
		VideoProperties videoProperties = FFMpegExecutor.getVideoProperties(VIDEO);
		Dimension resolution = videoProperties.getResolution();
		final int maxSize = 603;
		double factor = FileWorker.getFactor(resolution, maxSize);

		System.out.println("factor:"+factor);
		FFMpegExecutor.copyVideo(VIDEO, absolutePath, (int)Math.round(resolution.width*factor), (int)Math.round(resolution.height*factor));

		Process p = new ProcessBuilder("cmd" , "/c", "start", absolutePath).start();
		p.waitFor();

		assertTrue(file.exists());
	}
}
