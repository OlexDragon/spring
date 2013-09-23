package irt.objects.components.dao;

import irt.objects.components.Manufacture;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class ManufactureDAO extends AListDAO<Manufacture>{

	@Override
	protected RowMapper<Manufacture> getRowMapper() {
		return new RowMapper<Manufacture>(){

			@Override
			public Manufacture mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				Manufacture manufacture = new Manufacture();

				manufacture.setId(resultSet.getString("id"));
				manufacture.setName(resultSet.getString("name"));
				manufacture.setLink(resultSet.getString("link"));

				return manufacture;
			}
			
		};
	}

	@Override
	protected String getQuery() {
		return "SELECT*FROM`irt`.`manufacture";
	}

}
