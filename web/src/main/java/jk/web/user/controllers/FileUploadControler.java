package jk.web.user.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;

import javax.imageio.ImageIO;

import jk.web.user.entities.LoginEntity;
import jk.web.user.repository.LoginRepository;
import jk.web.workers.FileWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@RequestMapping("/uploadProfileImage")
	public String uploadProfileImage(@RequestParam("fileSelect") MultipartFile file, Principal principal) {
		if (!file.isEmpty()) {
			ByteArrayInputStream bis;
			try {
				bis = new ByteArrayInputStream(file.getBytes());
				BufferedImage bufferedImage = Scalr.resize(ImageIO.read(bis), 180);

				String username = principal.getName();

				if(username!=null){
					LoginEntity loginEntity = loginRepository.findByUsername(username);
					fileWorker.saveProfileImage(loginEntity.getId(), bufferedImage);
				}
			} catch (IOException e) {
				logger.catching(e);
			}
			String name = file.getOriginalFilename();
			long size = file.getSize();
			logger.trace("\n\t{}\n\t{}", size, name);
		}
		return "redirect:/profile/edit";

	}
}
