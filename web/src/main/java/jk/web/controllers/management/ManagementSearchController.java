package jk.web.controllers.management;

import java.util.List;

import jk.web.beans.view.management.SearchCategoryView;
import jk.web.entities.workers.search.SearchCatgoryEntity;
import jk.web.entities.workers.search.SearchCatgoryEntity.CategoryStatus;
import jk.web.repositories.workers.search.SearchCatgoriesRepository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/management/search")
public class ManagementSearchController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private SearchCatgoriesRepository searchCatgoriesRepository;

	@ModelAttribute("letters")
	public List<String>  attrBomView(){
		return searchCatgoriesRepository.findAvailableFirstCharacters();
	}

	@RequestMapping(value="categories/**", method=RequestMethod.GET)
	public String serchCategories(SearchCategoryView searchCategoryView, Model model){
		logger.entry();
		return "management/categories";
	}

	@RequestMapping(value="/categories/edit", method=RequestMethod.POST)
	public String editCategory(SearchCategoryView searchCategoryView){
		logger.entry(searchCategoryView);

		String name = validateCategory(searchCategoryView.getName());
		if(name!=null){
			SearchCatgoryEntity catgoryEntity = searchCatgoriesRepository.findOneByCategoryName(name);
			if(catgoryEntity==null){
				Long id = searchCategoryView.getId();
				if(id==null || (catgoryEntity = searchCatgoriesRepository.findOne(id))==null){

					catgoryEntity = new SearchCatgoryEntity();
					catgoryEntity.setCategoryName(name);
					catgoryEntity.setStatus(searchCategoryView.isShow() ? CategoryStatus.SHOW : CategoryStatus.DO_NOT_SHOW);
					searchCatgoriesRepository.save(catgoryEntity);

					String msg = "A new category " + name + " has been saved";
					logger.info(msg);
					searchCategoryView.setMsg(msg);
					searchCategoryView.setMsgStatus(Level.INFO);

				}else{
					catgoryEntity.setCategoryName(name);
					catgoryEntity.setStatus(searchCategoryView.isShow() ? CategoryStatus.SHOW : CategoryStatus.DO_NOT_SHOW);
					searchCatgoriesRepository.save(catgoryEntity);

					String msg = "The category " + name + " status " + catgoryEntity.getStatus()+ " has been saved";
					logger.info(msg);
					searchCategoryView.setMsg(msg);
					searchCategoryView.setMsgStatus(Level.INFO);
				}

			}else if((catgoryEntity.getStatus()==CategoryStatus.SHOW) != searchCategoryView.isShow()){
				catgoryEntity.setStatus(searchCategoryView.isShow() ? CategoryStatus.SHOW : CategoryStatus.DO_NOT_SHOW);
				searchCatgoriesRepository.save(catgoryEntity);

				String msg = "The category " + name + " status " + catgoryEntity.getStatus()+ " has been saved";
				logger.info(msg);
				searchCategoryView.setMsg(msg);
				searchCategoryView.setMsgStatus(Level.INFO);
			}else{
				String msg = "the category " + name + " has not been changed";
				logger.warn(msg);
				searchCategoryView.setMsg(msg);
				searchCategoryView.setMsgStatus(Level.WARN);
			}
		}else{
			String msg = "The category field is empry";
			logger.error(msg);
			searchCategoryView.setMsg(msg);
			searchCategoryView.setMsgStatus(Level.ERROR);
		}
		return "management/categories";
	}

	@RequestMapping(value="categories/{startWith}", method = RequestMethod.POST)
	public ResponseEntity<Page<SearchCatgoryEntity>> searchByFirstLetter(@PathVariable String startWith){
		logger.entry(startWith);

		Page<SearchCatgoryEntity> categories;
		try{
			categories = searchCatgoriesRepository.findFirst2ByCategoryNameStartingWith(startWith, new PageRequest(0, 2, new Sort(Direction.ASC, "categoryName")));
		}catch(Exception ex){
			logger.catching(ex);
			categories = null;
		}
		logger.trace("\n\t{}", categories);

		return logger.exit(new ResponseEntity<>(categories,  HttpStatus.OK));
	}

	private String validateCategory(String name) {
		return name;
	}

	@RequestMapping(value="categories", method=RequestMethod.POST, params = "submitSearch")
	public String categoriesContains(SearchCategoryView searchCategoryView, @RequestParam("search") String searchFor, Model model){
		logger.entry(searchFor);

		if(searchFor!=null && !(searchFor=searchFor.trim()).isEmpty())
			categoriesContains(searchCategoryView, searchFor, 0, model);

		return "management/categories";
	}

	@RequestMapping(value="categories/{contains}/{pageNumber}", method=RequestMethod.GET)
	public String categoriesContains(SearchCategoryView searchCategoryView, @PathVariable String contains, @PathVariable int pageNumber, Model model){
		logger.entry(contains, pageNumber);

		Page<SearchCatgoryEntity> page = searchCatgoriesRepository.findFirst50ByCategoryNameContaining(contains, new PageRequest(pageNumber, 50, new Sort(Direction.ASC, "categoryName")));
			if(page!=null && page.getTotalElements()>0){
				model.addAttribute("page", page);
				model.addAttribute("title", contains);
			}
		return "management/categories";
	}
}
