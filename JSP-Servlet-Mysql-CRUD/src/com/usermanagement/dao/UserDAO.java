package com.usermanagement.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.usermanagement.model.User;

public class UserDAO {
	
	private String url = "jdbc:mysql://localhost:3306/usermanagement";
	private String username ="root";
	private String password ="root";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users " + " (name , email , country) VALUES " + "(?,?,?)";
	private static final String SELECT_USER_BY_ID = "select id, name , email, country from users where id= ?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id =?";
	private static final String UPDATE_USERS_SQL = "update users set name = ? , email = ? , country = ? where id =?";
	
	protected Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url , username , password);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	//insert method
	
	public void insertUser(User user) throws SQLException {
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(INSERT_USERS_SQL)) {
				ps.setString(1, user.getName());
				ps.setString(2, user.getEmail());
				ps.setString(3, user.getCountry());
				ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// update method
	
	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated;
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(UPDATE_USERS_SQL)){
			
				ps.setString(1, user.getName());
				ps.setString(2, user.getEmail());
				ps.setString(3, user.getCountry());
				ps.setInt(4, user.getId());
				
				rowUpdated = ps.executeUpdate() >0 ;	
		}
		return rowUpdated;
	}
	
	// select user by id 
	
	public User selectUser(int id ) throws SQLException {
		User user = null;
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID)){
			ps.setInt(1, id);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String name  = rs.getString("name");
				String email  = rs.getString("email");
				String country  = rs.getString("country");
				user = new User(id ,name, email, country);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	// select all user menthod
	
	public List<User> selectAllUsers() throws SQLException {
		List<User> users = new ArrayList<User>();
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_ALL_USERS)){
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String name  = rs.getString("name");
				String email  = rs.getString("email");
				String country  = rs.getString("country");
				users.add( new User(id ,name, email, country));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(DELETE_USERS_SQL)){
			ps.setInt(1, id);
			rowDeleted = ps.executeUpdate() >0 ;
		}
		return rowDeleted;
	}
	
	
}
