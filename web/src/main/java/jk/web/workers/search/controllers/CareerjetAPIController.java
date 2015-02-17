package jk.web.workers.search.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import com.careerjet.webservices.api.Client;

@Controller
public class CareerjetAPIController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private LocaleResolver localeResolver;

	@RequestMapping("/careerjet")
	public ResponseEntity<Object> search(
									@RequestParam(value = "keywords") String keywords,
									@RequestParam(value = "location", required=false) String location,
									HttpServletRequest request){

		logger.entry(keywords, location);
//		Locale locale = localeResolver.resolveLocale(request);
		Client c = new Client("en_CA");
		Map<String, String> args = new HashMap<String, String>();
		args.put("keywords", keywords);

		if(location!=null)
			args.put("location", location);

		JSONObject results = (JSONObject) c.search(args);

		Object result;
		HttpStatus status;
		String type = (String)results.get("type");
		switch(type){
		case "JOBS":
			   result = results.get("jobs");
			   status = HttpStatus.OK;
//			   JSONArray jobs = (JSONArray) results.get("jobs");
//			   logger.trace("\n\t{}", jobs);
			   break;
		   case "LOCATIONS":
			   result = results.get("solveLocations");
			   status = HttpStatus.OK;
//		       JSONArray solvelocations = (JSONArray) results.get("solveLocations");
//			   logger.trace("\n\t{}", solvelocations);
			   break;
		   case "ERROR":
			   result = results.get("ERROR");
			   status = HttpStatus.OK;
//			   logger.error("\n\t Error: {}", results.get("ERROR"));
			   break;
		   default:
			   result = type;
			   status = HttpStatus.OK;
			   logger.trace("\n\t Type: {}", type);
		   }

		return logger.exit(new ResponseEntity<>(result,  status));
	}
}
