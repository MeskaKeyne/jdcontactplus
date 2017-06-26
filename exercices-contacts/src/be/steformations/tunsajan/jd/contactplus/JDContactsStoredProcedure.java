package be.steformations.tunsajan.jd.contactplus;

import java.sql.Connection;

import be.steformations.java_data.contacts.interfaces.jdbc.ContactJdbcManager;

public class JDContactsStoredProcedure implements ContactJdbcManager {

	private String url ="jdbc:postgresql://prim2014-14.fapse.priv:5432/contact";
	//private String url ="jdbc:postgresql://localhost:5432/contact";
	private String user = "postgres";
	private String pwd = "postgres";
	private Connection con;
	

	@Override
	public String getEmailByContact(String firstname, String name) {
		
		String email = null;
		
		if ( firstname != null && name != null ) {

			String sql = "{ ? = call JdbcGetEmailByContact(?, ?) }";
			
			try (
				java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, pwd);
				java.sql.CallableStatement query = connection.prepareCall(sql);
			) {
			
				query.registerOutParameter(1, java.sql.Types.VARCHAR);
				query.setString(2, firstname);
				query.setString(3, name);
				query.execute();
				
				email = query.getString(1);
				
			} catch(java.sql.SQLException ex) {
				ex.printStackTrace();
			}
			
		}
		
		return email;
	}

	@Override
	public java.util.List<String> getEmailsByCountry(String abbreviation) {
		
		java.util.List<String> emails = new java.util.ArrayList<String>();
		
		if ( abbreviation != null ) {

			String sql = "{ call JdbcGetEmailsByAbreviation(?) }";
			
			try (
				java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, pwd);
				java.sql.CallableStatement query = connection.prepareCall(sql);
			) {
				query.setString(1, abbreviation);
				
				try (
					java.sql.ResultSet rs = query.executeQuery();
				) {
					while ( rs.next() ) {
						emails.add(rs.getString(1));
					}
				}
				
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		} 
		
		return emails;
	}

	@Override
	public java.util.List<String> getTagsByContact(String firstname, String name) {
		
		java.util.List<String> tags = new java.util.ArrayList<String>();
		
		if ( firstname != null && name != null ) {
		
			String sql = "{ call JdbcGetTagsByContact(?, ?) }";
			
			try (
				java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, pwd);
				java.sql.CallableStatement query = connection.prepareCall(sql);
			) {				
				query.setString(1, firstname);
				query.setString(2, name);
				
				try (
					java.sql.ResultSet rs = query.executeQuery();		
				) {
					while ( rs.next() ) {
						tags.add(rs.getString(1));	
					}
				} 
				
			} catch(java.sql.SQLException e) {
				e.printStackTrace();

			}			
		} 
		
		return tags;
	}

	@Override
	public int createAndSaveContact(String firstname, String name,
			String email, String abbreviation, String[] tagsValues) {
		
		int contactId = 0;
		
		if ( firstname != null && name != null && email != null ) {
			
			String sqlInsertContact = "{ ? = call JdbcCreateContact(?, ?, ?, ?) }";
			String sqlInsertTag = "{ call JdbcAddTagToContact(?, ?) }";
		
			try (
				java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, pwd);
				java.sql.CallableStatement insertContactQuery = connection.prepareCall(sqlInsertContact);
				java.sql.CallableStatement insertTagQuery = connection.prepareCall(sqlInsertTag)
			) {
				connection.setAutoCommit(false);
				try {
					insertContactQuery.registerOutParameter(1, java.sql.Types.INTEGER);
					insertContactQuery.setString(2, firstname);
					insertContactQuery.setString(3, name);
					insertContactQuery.setString(4, email);
					if ( abbreviation == null ) {
						insertContactQuery.setNull(5, java.sql.Types.VARCHAR);
					} else {
						insertContactQuery.setString(5, abbreviation);
					}
					insertContactQuery.execute();
					contactId = insertContactQuery.getInt(1); 
					
					if ( contactId != 0 && tagsValues != null ) {
						insertTagQuery.setInt(1, contactId);
						for (String tagValue : tagsValues) {
							if ( tagValue != null ) {
								insertTagQuery.setString(2, tagValue);
								insertTagQuery.execute();
							}
						}
					}				
					connection.commit();
				} catch(java.sql.SQLException sqle) {
					connection.rollback();
					throw sqle;
				}
			} catch(java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		
		return contactId;
	}
	
	@Override
	public void removeContact(int id) {

		String sql = "{ call JdbcRemoveContact(?) }";
		
		try(
			java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, pwd);
			java.sql.CallableStatement query = connection.prepareCall(sql);	
		) {
			connection.setAutoCommit(true);
			query.setInt(1, id);
			query.execute();
			
		} catch(java.sql.SQLException e) {
			e.printStackTrace();
		}
	}
}

