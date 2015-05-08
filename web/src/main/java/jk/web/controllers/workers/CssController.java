package jk.web.controllers.workers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import jk.web.entities.BackgroundImgEntity;
import jk.web.entities.BackgroundImgEntity.BackgroundImgStatus;
import jk.web.entities.repositories.BackgroundImgRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("css")
public class CssController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private BackgroundImgRepository backgroundImgRepository;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

	private volatile byte[] fpfCss;
	private volatile List<BackgroundImgEntity> backgroundImgs;
	private volatile BackgroundImgEntity backgroundInUse;

	@RequestMapping("fpf.css")
	public ResponseEntity<byte[]> getFpfCss(ZoneId zoneId){
		logger.entry();

		setBackgroundPath();

		if(setBackground(zoneId) || fpfCss==null){
			InputStream is = CssController.class.getResourceAsStream("/static/css/fpf.css");
			if(backgroundInUse!=null)
				is = editFpfCssFile(is);
			try(ByteArrayOutputStream os = new ByteArrayOutputStream();){

				StreamUtils.copy(is, os);
				fpfCss = os.toByteArray();
			} catch (IOException e) {
				logger.catching(e);
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text","css"));

		return new ResponseEntity<byte[]>(fpfCss, headers, HttpStatus.OK);
	}

	private InputStream editFpfCssFile(InputStream is) {
		StringBuffer sb = new StringBuffer();
		try(Scanner sc = new Scanner(is);){
			while(sc.hasNextLine()){
				sb.append(sc.nextLine().trim());
			}
		}

		String startStr = "background:url('";
		int start = sb.indexOf(startStr, sb.indexOf("body"));
		String endStr = "') no-repeat center center fixed;";
		int end = sb.indexOf(endStr, start);
		if(start>0 && end>0){
			sb.replace(start+startStr.length(), end, backgroundInUse.getPath());
			logger.trace("Image Path has been canged.");
		}
		return new ByteArrayInputStream(sb.toString().getBytes());
	}

	private boolean setBackground(ZoneId zoneId) {

		Clock clock = Clock.system(zoneId);
		Instant inst = Instant.now(clock);
		String format = simpleDateFormat.format(Time.from(inst));
		boolean chaged = false;

		try {
			Time time = new Time(simpleDateFormat.parse(format).getTime());

			int index;
			if(backgroundInUse==null || backgroundInUse.getStartToShowAt().after(time)){
				BackgroundImgEntity bi = backgroundImgs.get(backgroundImgs.size()-1);
				if(bi!=backgroundInUse){
					backgroundInUse = bi;
					chaged = true;
				}
				index = 0;
			}else
				index = backgroundImgs.indexOf(backgroundInUse)+1;

			for(; index<backgroundImgs.size(); index++)
				if(backgroundImgs.get(index).getStartToShowAt().before(time)){
					backgroundInUse = backgroundImgs.get(index);
					chaged = true;
				}else{
					break;
				}

			logger.trace("\n\t"
					+ "time:\t{}\n\t"
					+ "canget:\t{}\n\t"
					+ "backgroundInUse:\t{}",
					time,
					chaged,
					backgroundInUse);

		} catch (ParseException e) {
			logger.catching(e);
		}

		return chaged;
	}

	private void setBackgroundPath() {
		if(backgroundImgs==null){
			backgroundImgs = backgroundImgRepository.findByStatus(BackgroundImgStatus.USE);
			Collections.sort(backgroundImgs);
			logger.trace("\n\t{}", backgroundImgs);
		}
	}

	public void reset(){
		backgroundImgs = null;
	}
}
