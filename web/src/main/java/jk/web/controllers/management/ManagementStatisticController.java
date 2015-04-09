package jk.web.controllers.management;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jk.web.entities.statistic.StatisticEntity;
import jk.web.entities.statistic.StatisticRequestUrlEntity;
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

	@ModelAttribute("week")
	public List<StatisticEntity>  attrWeekStatistic(){
	    Date dateToday = Statistic.getDate();

	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dateToday);
	    calendar.add(Calendar.WEEK_OF_YEAR, -1);
	    Date d = new Date(calendar.getTime().getTime());

		List<StatisticEntity> entities = statisticRepository.findByStatisticDateBetween(d, dateToday);
		List<StatisticEntity> es = new ArrayList<>();
		if(entities!=null)
			for(StatisticEntity se:entities){
				if(es.contains(se)){
					StatisticEntity get = es.get(es.indexOf(se));
					List<StatisticRequestUrlEntity> sru = get.getStatisticRequestUrlEntityList();
					List<StatisticRequestUrlEntity> sruFrom = se.getStatisticRequestUrlEntityList();
					for(StatisticRequestUrlEntity sr:sruFrom){
						if(sru.contains(sr)){
							StatisticRequestUrlEntity srTmp = sru.get(sru.indexOf(sr));
							srTmp.setTimes(srTmp.getTimes()+sr.getTimes());
						}else
							sru.add(sr);
					}
				}else
					es.add(se);
			}

		return logger.exit(entities);
	}

	@RequestMapping
	public String statistic(){
		return "management/statistic";
	}
}