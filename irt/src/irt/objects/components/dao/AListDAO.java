package irt.objects.components.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class AListDAO<T> implements IListDAO<T> {

	private JdbcTemplate jdbcTemplate;
	protected List<T> list;
	private final RowMapper<T> rowMapper = getRowMapper();

	protected abstract RowMapper<T> getRowMapper();
	protected abstract String getQuery();

	@Autowired
	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<T> getList(boolean newList){
		if(list==null || newList){
			list = jdbcTemplate.query(getQuery(), rowMapper);
		}
		return list;
	}
}
