package jk.web.user.controllers;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class FileUploadControler {

	private final Logger logger = LogManager.getLogger();

	@RequestMapping("/uploadProfileImage")
	public @ResponseBody Path uploadProfileImage(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			String name = file.getOriginalFilename();
			long size = file.getSize();
			logger.trace("\n\t{}\n\t{}", size, name);
		}
		return null;
	}
}
