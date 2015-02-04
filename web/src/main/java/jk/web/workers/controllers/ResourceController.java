package jk.web.workers.controllers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Arrays;

import jk.web.user.entities.LoginEntity;
import jk.web.user.repository.LoginRepository;
import jk.web.workers.FileWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.ImageSize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("res")
public class ResourceController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private FileWorker fileWorker;

	@RequestMapping(value="{imageSize}/{userID}/{imageID}.*", method = RequestMethod.GET)
	public ResponseEntity<byte[]> get(	Principal principal,
										@PathVariable ImageSize imageSize,
										@PathVariable long imageID,
										@PathVariable long userID){

		logger.entry( imageSize, imageID, userID);

		String username = principal.getName();
		LoginEntity loginEntity = loginRepository.findByUsername(username);

		ResponseEntity<byte[]> responseEntity = null;

		try {
			responseEntity = fileWorker.getResponseEntity(loginEntity.getId(), imageSize, imageID, userID);
		} catch (IOException e) {
			logger.catching(e);
		}
		return responseEntity!=null ? responseEntity : null;
	}

	@RequestMapping(value="{imageSize}/{userID}/{imageID}.*", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(	Principal principal,
											@PathVariable long imageID,
											@PathVariable long userID){

		logger.entry(imageID, userID);
		Boolean deleted;

		String username = principal.getName();
		LoginEntity loginEntity = loginRepository.findByUsername(username);
		if(loginEntity!=null && loginEntity.getId()==userID)
			deleted =fileWorker.delete( imageID, userID);
		else
			deleted = false;
		return new ResponseEntity<Boolean>(deleted, HttpStatus.OK);
	}

	@RequestMapping(value="{imageSize}/{userID}/{imageID}.*", method = RequestMethod.POST)
	public ResponseEntity<String> saveProfileImage(	Principal principal,
													@PathVariable long imageID,
													@PathVariable long userID,
													@RequestParam int x,
													@RequestParam int y,
													@RequestParam int w,
													@RequestParam int h,
													@RequestParam boolean isAll){
		logger.entry(imageID, userID, x, y , w, h, isAll);

		String username = principal.getName();

		boolean set = false;
		if(username!=null){
			LoginEntity loginEntity = loginRepository.findByUsername(username);
			if(loginEntity!=null && loginEntity.getId()==userID)
				try {
					if(isAll)
						set = fileWorker.setProfileImage(userID, imageID);
					else
						set = fileWorker.setProfileImage(userID, imageID, x, y, w, h);
				} catch (IOException e) {
					logger.catching(e);
				}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
		return new ResponseEntity<String>(Boolean.toString(set),  headers, HttpStatus.CREATED);
	}

	@RequestMapping(value="/ajax/{userID}/{imageID}/{show}", method = RequestMethod.POST)
	public ResponseEntity<String> setShow(	Principal principal, @PathVariable long userID, @PathVariable long imageID, @PathVariable boolean show){
		logger.entry( userID, imageID, show);

		String username = principal.getName();
		
		if(username!=null){
			LoginEntity loginEntity = loginRepository.findByUsername(username);
			if(loginEntity!=null && loginEntity.getId()==userID)
				fileWorker.setShowToAll(userID, imageID, show);
		}

		return new ResponseEntity<String>(Boolean.toString(true),  HttpStatus.CREATED);
	}
}
