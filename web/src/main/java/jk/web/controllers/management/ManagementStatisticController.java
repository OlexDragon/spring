package jk.web.controllers.management;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

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

		return findBetween(dateToday, new Date(calendar.getTime().getTime()));
	}

	@ModelAttribute("year")
	public List<StatisticEntity>  attrYearStatistic(){
	    Date dateToday = Statistic.getDate();

	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dateToday);
	    calendar.add(Calendar.YEAR, -1);

		return findBetween(dateToday, new Date(calendar.getTime().getTime()));
	}

	private List<StatisticEntity> findBetween(Date dateToday, Date dateBefor) {
		final List<StatisticEntity> es = new ArrayList<>();
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				final List<StatisticEntity> entities = statisticRepository.findByStatisticDateBetween(dateBefor, dateToday);
				if(entities!=null)
					for(StatisticEntity se:entities){
						int indexOf = es.indexOf(se);
						if(indexOf>=0){
							final StatisticEntity get = es.get(indexOf);
							final List<StatisticRequestUrlEntity> sruFrom = se.getStatisticRequestUrlEntityList();
							executorService.execute(new Runnable() {
								
								@Override
								public void run() {
									final List<StatisticRequestUrlEntity> sru = get.getStatisticRequestUrlEntityList();
									for(StatisticRequestUrlEntity sr:sruFrom){
										int iof = sru.indexOf(sr);
										if(iof>=0){
											final StatisticRequestUrlEntity srTmp = sru.get(iof);
											srTmp.setTimes(srTmp.getTimes()+sr.getTimes());
										}else
											sru.add(sr);
									}
								}
							});
						}else
							es.add(se);
					}
			}
		});
		return es;
	}

	@RequestMapping
	public String statistic(){
		try {
			executorService.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.catching(e);
			e.printStackTrace();
		}
		return "management/statistic";
	}
}
