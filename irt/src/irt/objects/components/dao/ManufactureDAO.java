package irt.objects.components.dao;

import irt.objects.components.Manufacture;
import irt.tools.table.Field;
import irt.tools.table.Row;
import irt.tools.table.Table;
import irt.tools.table.Title;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;


public class ManufactureDAO extends ADAO<Manufacture>{

	private RowMapper<Manufacture> rowMapper = new RowMapper<Manufacture>(){

		@Override
		public Manufacture mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Manufacture manufacture = new Manufacture();

			manufacture.setId(resultSet.getString("id"));
			manufacture.setName(resultSet.getString("name"));
			manufacture.setLink(resultSet.getString("link"));

			return manufacture;
		}	
	};

	public Manufacture getManufacture(String id){

		List<Manufacture> manufactures = getList("SELECT*FROM`irt`.`manufacture`WHERE`id`='"+id+"'", rowMapper);
		Manufacture manufacture = null;

		if(!manufactures.isEmpty())
			manufacture = manufactures.get(0);

		return manufacture;
	}

	public List<Manufacture> getManufacturs(){
		return getList("SELECT*FROM`irt`.`manufacture`", rowMapper);
	}

	public Table getTable(boolean showRowCount, String href) throws SQLException {
		RowMapper<Row> rowMapper = new RowMapper<Row>() {
			@Override
			public Row mapRow(ResultSet rs, int rowNum) throws SQLException {

				//Fill rows
				Row row = new Row();
				row.add(new Field(rowNum+1, null, null));
				row.add(new Field(rs.getString("id"), null, null));
				row.add(new Field(rs.getString("name"), null, rs.getString("link")));
				row.setClassName(rowNum%2==1 ? "even" : "odd");

				return row;
			}
		};

		Row titles = new Row();
		titles.setClassName("title");
		titles.add(new Title("", null, null));
		titles.add(new Title("ID", null, href!=null ? href+"/id" : null));
		titles.add(new Title("Name", null, href!=null ? href+"/name" : null));

		Table table = super.getTable("irt", "manufacture", rowMapper, showRowCount, href, titles);
		table.setId("manufacture");
		return table;

	}
}