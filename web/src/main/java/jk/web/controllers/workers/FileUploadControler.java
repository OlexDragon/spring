package jk.web.controllers.workers;

import java.io.IOException;
import java.security.Principal;

import jk.web.entities.user.LoginEntity;
import jk.web.repositories.user.LoginRepository;
import jk.web.workers.FileWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class FileUploadControler {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private FileWorker fileWorker;

	@RequestMapping(value="/files")
	public String get() {
		return "redirect:/home";
	}

	@RequestMapping(value="/files", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public String uploaImages(@RequestParam("fileSelect") MultipartFile[] files, Principal principal) {
		logger.entry();
		if (files!=null && files.length>0) {
			try {
				String username = principal.getName();

				if(username!=null){
					LoginEntity loginEntity = loginRepository.findByUsername(username);
					for(int i =0 ;i< files.length; i++)
						fileWorker.saveFile(loginEntity.getId(), files[i]);
				}
			} catch (IOException e) {
				logger.catching(e);
			}
		}
		return "redirect:/profile/edit";
	}
}
