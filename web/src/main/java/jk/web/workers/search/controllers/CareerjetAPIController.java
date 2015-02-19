package jk.web.workers.search.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CareerjetAPIController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private LocaleResolver localeResolver;

	@RequestMapping("/careerjet")
	public ResponseEntity<JSONObject> search(
									@RequestParam(value = "keywords") String keywords,
									@RequestParam(value = "location", required=false) String location,
									@RequestParam(value = "page", required=false) String page,
									HttpServletRequest request,
									HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{

		logger.entry(keywords, location);
//		Locale locale = localeResolver.resolveLocale(request);
		Client c = new Client("en_CA");
		Map<String, String> args = new HashMap<String, String>();
		args.put("keywords", keywords);

		if(location!=null){
			args.put("location", location);
			response.addCookie(new Cookie("location", location));
		}

		JSONObject results = (JSONObject) c.search(args);
		logger.trace(results);

//		HashMap<?, ?> readValue = new ObjectMapper().readValue(
//			"{"
//				+ "\"hits\":219,"
//				+ "\"pages\":11,"
//				+ "\"jobs\":[{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.bestjobsca.com\","
//							+ "\"description\":\"A newly created position for an <b>Art<\\/b> Director is needed for an established retailer.   Responsibilities:  Monitor... through asset production for online, social, retail stores, and PR touch points  Conceptualize and <b>Art<\\/b> Direct photo shoots...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Prestige Recruitment\","
//							+ "\"title\":\"Art Director (PRA 539)\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-4c1c414c5f104f0a4e0645001c0e1c650149060d0a0745593c1919525b\\/8d26873f688648f92db91b2431ccab98.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.bullhornreach.com\","
//							+ "\"description\":\"A newly created position for an <b>Art<\\/b> Director is needed for an established retailer.   Responsibilities:  Monitor... through asset production for online, social, retail stores, and PR touch points  Conceptualize and <b>Art<\\/b> Direct photo shoots...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"\","
//							+ "\"title\":\"Art Director (PRA 539) Prestige Recruitment Montreal, QC Canada\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-1c4f415f48071d1b4e004d0606156c150043061d0007474e53590d00061c4217540c1a630f15110010011b16495f525f2f00061c4217540c1a605a535314\\/959b5afcc141754a652a8000af548fcd.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.genivar.com\","
//							+ "\"description\":\"Concepteur - Ouvrage d'<b>art<\\/b> Activity Sector : Transportation Reference : 20-0362 Job Type : Full Time - Permanent... Mobility : No Income : Selon expérience City : Montreal Concepteur - Ouvrage d'<b>art<\\/b> Principales responsabilités Participer...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Génivar\","
//							+ "\"title\":\"Concepteur - Ouvrage d'art\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-131d412c1950595a\\/1417d1ebd2bfdb66e28a75ba4e709254.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.jobbank.gc.ca\","
//							+ "\"description\":\"Juste pour rire   View the full job posting   Important Notice: This job advertisement has been provided by an external employer. The Government of Canada is not responsible for the accuracy, authenticity or reliability of the content.   Da...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Juste pour rire\","
//							+ "\"title\":\"scenic painter - visual arts\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-1d49415b44171a0f4b7641111c126c110c53010905534b59495e2f140e074900451169555a5151\\/7850b638d10ec47814062b4cb4375e49.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.jobbank.gc.ca\","
//							+ "\"description\":\"Behaviour Interactif Inc.   View the full job posting   Important Notice: This job advertisement has been provided by an external employer. The Government of Canada is not responsible for the accuracy, authenticity or reliability of the con...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Behaviour Interactif Inc.\","
//							+ "\"title\":\"artist, visual arts\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-4c19415b44171a0f4b7641111c126c0617541d1b1d715c424e584c084f0f550053611e081d12044c54091b074358492c1950595a\\/0f3f63783bee569dcff154c51beaaf69.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.jobbank.gc.ca\","
//							+ "\"description\":\"Behaviour Interactif Inc.   View the full job posting   Important Notice: This job advertisement has been provided by an external employer. The Government of Canada is not responsible for the accuracy, authenticity or reliability of the con...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Behaviour Interactif Inc.\","
//							+ "\"title\":\"artist, visual arts\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-4c19415b44171a0f4b7641111c126c0617541d1b1d715c424e584c084f0f550053611e081d12044c54091b074358492c1950595a\\/4d41f1a7d5294c5b3b9d9b7331c59738.html\""
//							+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.jobbank.gc.ca\","
//							+ "\"description\":\"Behaviour Interactif Inc.   View the full job posting   Important Notice: This job advertisement has been provided by an external employer. The Government of Canada is not responsible for the accuracy, authenticity or reliability of the con...\","
//							+ "\"locations\":\"Montreal, QC\",\"company\":\"Behaviour Interactif Inc.\","
//							+ "\"title\":\"technical producer - motion pictures, broadcasting and performing arts\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-481e414f5f0b0e0a44155317010f09650452001b6b034f595b425f090600405441111c126c17174f101d0a16582949484e0c010744154c625c555853\\/bf253af6ea7a6acb6d87421dbe04e6a1.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.jobbank.gc.ca\","
//							+ "\"description\":\"Carpet <b>Art<\\/b> Deco (2964-3277 QUEBEC INC.)   View the full job posting   Important Notice: This job advertisement has been...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Carpet Art Deco (2964-3277 QUEBEC INC.)\","
//							+ "\"title\":\"accountants supervisor\",\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-131a414c4e07001b4900410d1c126c141050111a1f1a59444f2c1950595a\\/1ed1838282fa439a37c3e4958e45f1ec.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.jobbank.gc.ca\","
//							+ "\"description\":\"L'<b>art<\\/b> du hamburger   View the full job posting   Important Notice: This job advertisement has been provided...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"L'art du hamburger\","
//							+ "\"title\":\"cook\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-494e414e420b046f13401657\\/d1264979cd3b21ce2237b3a5835dd253.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.jobbank.gc.ca\","
//							+ "\"salary_min\":\"10.35\","
//							+ "\"description\":\"L'<b>art<\\/b> du hamburger   View the full job posting   Important Notice: This job advertisement has been provided...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"salary_max\":\"10.35\","
//							+ "\"company\":\"L'art du hamburger\","
//							+ "\"salary_type\":\"H\","
//							+ "\"salary\":\"$10.35 per hour\","
//							+ "\"title\":\"dishwasher\","
//							+ "\"salary_currency_code\":\"CAD\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-4919414944170719460748061a605a535314\\/7e6e210f806bc52e1a3cf2d667d9da68.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.mmm.ca\","
//							+ "\"description\":\"Directeur de projet senior (en surveillance d'ouvrages d'<b>art<\\/b>)   Montréal, QC   Le Groupe MMM est passionné de faire... d\u2019<b>art<\\/b>, il planifiera, organisera et coordonnera des équipes de projet pour des mandats de surveillance de projet de grande...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"\","
//							+ "\"title\":\"Directeur de projet senior (en surveillance d'ouvrages d'art)\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-1b12414944160a0d5311551148050b4715521b020c070a585843440b1d6c54114e0a07134e17174f1e0d1d71455e4b5f4c030a1d071007021a156c030c52110b1d165f591d4c5f106d0a4e0645001c041b15454411481901454158592c505b5813\\/7abfa56d59f70aa9c001542b93e067ec.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.rconstructionjobs.com\","
//							+ "\"description\":\"Req Number  NA03169   Sector of Activity  Infrastructure Engineering   Job Type  Regular   Country  Canada   State\\/Province  Quebec   City  Montréal   Area of Expertise  Engineering   Présentation de l'Entreprise  Fondée en 1911, SNC-Lavali...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"SNC-Lavalin\","
//							+ "\"title\":\"Ingénieur intérmédiaire - surveillance de chantier Ponts et Ouvrages d\u2019art\","
//							+ "\"salary\":\"\",\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-1d1a415e5816190b4e184c0206020b470145540b0112445f54485f66001b510641040d124e034241061c6b1c5f5d4f4c4a011c4e435341111c41070902e3dd0600165f593f444303acc7491d45161a411d1217561101051f4b455e482f0d0109e4dd4e0a0d141c470145540b0112445f54485f655b5a1140\\/ca7982c0ad179d2019b6012adee07c2e.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.rengineeringjobs.com\","
//							+ "\"description\":\"Req Number  NA03169   Sector of Activity  Infrastructure Engineering   Job Type  Regular   Country  Canada   State\\/Province  Quebec   City  Montréal   Area of Expertise  Engineering   Présentation de l'Entreprise  Fondée en 1911, SNC-Lavali...\","
//							+ "\"locations\":\"Montreal, QC\",\"company\":\"SNC-Lavalin Inc.\",\"title\":\"Ingénieur intérmédiaire - surveillance de chantier Ponts et Ouvrages d\u2019art\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-1d1a415e5816190b4e184c0206020b470145540b0112445f54485f66001b510641040d124e034241061c6b1c5f5d4f4c4a011c4e435341111c41070902e3dd0600165f593f444303acc7491d45161a411d1217561101051f4b455e482f0d0109e4dd4e0a0d141c470145540b0112445f54485f655b5a1140\\/4d2094eeb32a407122628cd110a6ecff.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.workcenter.ca\","
//							+ "\"description\":\"), which are capable of producing in excess of one billion bars annually. The Company also operates a state-of-the-<b>art<\\/b> powder manufacturing...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Nellson Nutraceutical Canada\","
//							+ "\"title\":\"Quality Control Supervisor\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-1919415c58050307530d00101d110b15134907071b715b5e5c414410164e441b4e171a0e02651655040d1b054358525f2f151a0f4b1d541a4802010911521b0449005f5b585f5b0d1c0155765116090d07131c21405c5f47\\/ce9eadc92a98d0aca5db02a3465c42a8.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.loreal.com\","
//							+ "\"description\":\" includes L'Oréal Professionnel, Redken, Kérastase, Matrix, PureOlogy, Shu Uemura <b>Art<\\/b> of Hair, L'Oréal Paris, Garnier, Essie...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"L'Oreal\","
//							+ "\"title\":\"New Graduates' opportunities - Commercial Field\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-4f13414348134f095515441609150b1467431b0504165848544c4166081c461055021c041d65034911040d721e1f0b19\\/be01324103929a9aece7b51fe56a3fb7.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"jobs.cisco.com\","
//							+ "\"description\":\" self-development with regard to fast-paced and changing technology to ensure state-of-the-<b>art<\\/b> readiness to apply diverse...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Cisco Systems, Inc.\","
//							+ "\"title\":\"SEM - Quebec - (FR)\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-131d412c1950595a\\/1047c01579e5f59a2d65673d292dd685.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.talentnow.com\","
//							+ "\"salary_min\":\"1200\","
//							+ "\"description\":\", waitress, hostess, clerks, clerical, online surveys, graphic design, project management, secretary, design, call center, <b>art<\\/b>...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"salary_max\":\"1200\","
//							+ "\"company\":\"Home Online Pro\","
//							+ "\"salary_type\":\"W\","
//							+ "\"salary\":\"$1200 per week\","
//							+ "\"title\":\"Data Entry Reps Wanted \\/ Work At Home\","
//							+ "\"salary_currency_code\":\"CAD\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-4c4941494c100e4e4f1b4d066a050f13040011061d015329594c59056d06481945611f0e1c0c45410048011c474e1d4843101d17264014555c\\/9800ebeb153c53ffb55b47eee4a321ab.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.achcr.net\","
//							+ "\"description\":\" to our community on an outpatient basis, including a $7-million state-of-the-<b>art<\\/b> consolidated cancer center on our main campus...\","
//							+ "\"locations\":\"Montreal, QC - Ottawa, ON\","
//							+ "\"company\":\"American Coalition of Health Care Recruiters\","
//							+ "\"title\":\"Cardiology-NP\","
//							+ "\"salary\":\"\",\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-1d4f414e4c160b0748184f0411605a535314765c59441f\\/80ec96c587ecad6d3c6ae352a7f8ece2.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"jobs.cisco.com\","
//							+ "\"description\":\" self-development with regard to fast-paced and changing technology to ensure state-of-the-<b>art<\\/b> readiness to apply diverse...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Cisco Systems, Inc.\","
//							+ "\"title\":\"SEM - Quebec\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-131d412c1950595a\\/18de06be53a0c3478ed3580f3b2a97e9.html\""
//				+ "},{"
//							+ "\"date\":\"Tue, 17 Feb 2015 22:57:31 GMT\","
//							+ "\"site\":\"www.careerbuilder.ca\","
//							+ "\"description\":\"Schedule: Full-time Come join a dynamic team using the latest state of the <b>art<\\/b> technologies, responsible for improving...\","
//							+ "\"locations\":\"Montreal, QC\","
//							+ "\"company\":\"Accenture\","
//							+ "\"title\":\"C++ \\/ JAVA Software Developer Analyst\","
//							+ "\"salary\":\"\","
//							+ "\"url\":\"http:\\/\\/jobviewtrack.com\\/en-ca\\/job-1a1f415e42021b19460645430c041802094f040d1b53404a4b4c0d05010f4b0d531748026c140a46001f08014f0b574c5b054f0d2517001007071a10045211480d165c4e51425d011d6c541b46171f001c024544111e0c1f455b585f2f0e0e184654530c0e1519061745540c0c054f47525d48166e5a134214\\/08cdb4f4b3e951242a921306bcf5be89.html\""
//				+ "}"
//			+ "],"
//			+ "\"response_time\":0.0331060886383057,"
//			+ "\"type\":\"JOBS\"}", HashMap.class);
//		JSONObject results = new JSONObject(readValue);

//		Object result;
//		HttpStatus status;
//		String type = (String)results.get("type");
//		switch(type){
//		case "JOBS":
//			   result = results.get("jobs");
//			   status = HttpStatus.OK;
//			   JSONArray jobs = (JSONArray) results.get("jobs");
//			   logger.trace("\n\t{}", jobs);
//			   break;
//		   case "LOCATIONS":
//			   result = results.get("solveLocations");
//			   status = HttpStatus.OK;
//		       JSONArray solvelocations = (JSONArray) results.get("solveLocations");
//			   logger.trace("\n\t{}", solvelocations);
//			   break;
//		   case "ERROR":
//			   result = results.get("ERROR");
//			   status = HttpStatus.OK;
//			   logger.error("\n\t Error: {}", results.get("ERROR"));
//			   break;
//		   default:
//			   result = type;
//			   status = HttpStatus.OK;
//			   logger.trace("\n\t Type: {}", type);
//		   }

		return logger.exit(new ResponseEntity<>(results,  HttpStatus.OK));
	}
}
