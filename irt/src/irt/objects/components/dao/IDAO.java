package irt.objects.components.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public interface IDAO<T> {

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	public List<T> getList(String query, RowMapper<T> rowMapper);
}
