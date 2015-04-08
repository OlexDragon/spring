package jk.web.controllers.management;

import java.util.List;

import jk.web.entities.statistic.StatisticEntity;
import jk.web.filters.Statistic;
import jk.web.repositories.statictic.StatisticRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/management/statistic")
public class ManagementStatisticController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private StatisticRepository statisticRepository;

	@ModelAttribute("day")
	public List<StatisticEntity>  attrDayStatistic(){
		return logger.exit(statisticRepository.findByStatisticDate(Statistic.getDate()));
	}

	@RequestMapping
	public String statistic(){
		return "management/statistic";
	}
}
