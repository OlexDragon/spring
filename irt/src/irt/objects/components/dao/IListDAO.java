package irt.objects.components.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public interface IListDAO<T> {

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	public List<T> getList(boolean newList);
}
