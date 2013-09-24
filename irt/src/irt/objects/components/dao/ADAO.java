package irt.objects.components.dao;

import irt.tools.table.OrderBy;
import irt.tools.table.Row;
import irt.tools.table.Table;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class ADAO<T> implements IDAO<T> {

	protected JdbcTemplate jdbcTemplate;
	private OrderBy orderBy = new OrderBy();

	@Autowired
	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<T> getList(String query, RowMapper<T> rowMapper){
		return jdbcTemplate.query(query+orderBy, rowMapper);
	}

	protected Table getTable(String dbName, String TableName, RowMapper<Row> rowMapper, final boolean showRowCount, String href, Row titleRow) throws SQLException {

		List<Row> rows = jdbcTemplate.query("SELECT*FROM`"+dbName+"`.`"+TableName+"`"+orderBy, rowMapper);

		//Add Titles
		rows.add(0, titleRow);

		Table table = new Table();
		table.setRows(rows);

		return table;
	}

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy.setOrderBy(orderBy);
	}
}
