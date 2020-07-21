package rego.springframework.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class CowServiceIm implements CowService {

	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<String> atriskcows(Date rescued) {
		String sql = "SELECT * FROM cow WHERE rescued < '"+ rescued+"' AND vaccinated = '0'";
		List<String> cowList = new ArrayList<String>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor<List>() {
			
			public List extractData(ResultSet rs) throws SQLException {				
				
				while (rs.next()) {
					String name = rs.getString("name");
					cowList.add(name);
				}
				
				return cowList;
			}
			
		});
		
		System.out.println("cowlist");
		return cowList;
	}

	@Override
	public void addCow(String name, Date rescued, Boolean vaccinated) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("INSERT INTO cow(name, rescued,vaccinated) VALUES(?,?,?)", name, rescued, vaccinated);
				
	}

	@Override
	public void deleteCow(String name, Long id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("DELETE FROM cow WHERE name = '"+name+"' AND id = '"+id+"'");
		
	}
	
	
	
}
