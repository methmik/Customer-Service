package model;
import java.sql.*;

public class Customer {
	//A common method to connect to the DB
	private Connection connect()
	{
	Connection con = null;
	try
	{
	Class.forName("com.mysql.jdbc.Driver");

	//Provide the correct details: DBServer/DBName, username, password
	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electro_grid", "root", "");
	}
	catch (Exception e) 
	{e.printStackTrace();}
	return con;
	}

	public String readCustomers()
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for reading."; }
	// Prepare the html table to be displayed
	output = "<table border='1'><tr><th>Customer Code</th><th>Customer Name</th>" +
	"<th>Customer Email</th>" +
	"<th>Customer Address</th>" +
	"<th>Update</th><th>Remove</th></tr>";

	String query = "select * from customers";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	// iterate through the rows in the result set
	while (rs.next())
	{
	String cusID  = Integer.toString(rs.getInt("cusID"));
	String cusAccount = rs.getString("cusAccount");
	String cusName = rs.getString("cusName");
	String cusEmail = rs.getString("cusEmail");
	String cusAddress = rs.getString("cusAddress");
	String cusCity = rs.getString("cusCity");
	String cusMobile = rs.getString("cusMobile");
	String regDate = rs.getString("regDate");
	
	// Add into the html table
	output += "<tr><td>" + cusAccount + "</td>";
	output += "<td>" + cusName + "</td>";
	output += "<td>" + cusEmail + "</td>";
	output += "<td>" + cusAddress + "</td>";
	output += "<td>" + cusCity + "</td>";
	output += "<td>" + cusMobile + "</td>";
	output += "<td>" + regDate + "</td>";
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	+ "<td><form method='post' action='items.jsp'>"
	+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
	+ "<input name='cusID' type='hidden' value='" + cusID
	+ "'>" + "</form></td></tr>";
	}
	con.close();
	// Complete the html table
	output += "</table>";
	}
	catch (Exception e)
	{
	output = "Error while reading the customers.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	
	public String insertCustomer(int cusAccount, String cusName, String cusEmail,String cusAddress, String cusCity, String cusMobile, Date regDate)
	{
	String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for inserting."; }
		// create a prepared statement
		String query = " insert into customers (`cusID`,`cusAccount`,`cusName`,`cusEmail`,`cusAddress`,`cusCity`,`cusMobile`,`regDate`)"
		+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setInt(2, cusAccount);
		preparedStmt.setString(3, cusName);
		preparedStmt.setString(4, cusEmail);
		preparedStmt.setString(5, cusAddress);
		preparedStmt.setString(6, cusCity);
		preparedStmt.setString(7, cusMobile);
		preparedStmt.setDate(8, regDate);
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Inserted successfully";
		}
		catch (Exception e)
		{
		output = "Error while inserting the customer.";
		System.err.println(e.getMessage());
		}
	
		return output;
	}
	
	public String updateCustomer(String cusID, String cusAccount, String cusName, String cusEmail, String cusAddress, String cusCity, String cusMobile, String regDate)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for updating."; }
	// create a prepared statement
	String query = "UPDATE customers SET cusAccount=?,cusName=?,cusEmail=?, cusAddress=? ,cusCity=? ,cusMobile=? ,regDate=? WHERE cusID=?";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values

	preparedStmt.setString(1, cusAccount);
	preparedStmt.setString(2, cusName);
	preparedStmt.setString(3, cusEmail);
	preparedStmt.setString(4, cusAddress);
	preparedStmt.setString(5, cusCity);
	preparedStmt.setString(6, cusMobile);
	preparedStmt.setString(7, regDate);
	preparedStmt.setInt(8, Integer.parseInt(cusID));
	// execute the statement
	preparedStmt.execute();
	con.close();
	output = "Updated successfully";
	}
	catch (Exception e)
	{
	output = "Error while updating the customer.";
	System.err.println(e.getMessage());
	}
	return output;
	}


	public String deleteCustomer(String cusID)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for deleting."; }
	// create a prepared statement
	String query = "delete from customers where cusID=?";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setInt(1, Integer.parseInt(cusID));
	// execute the statement
	preparedStmt.execute();
	con.close();
	output = "Deleted successfully";
	}
	catch (Exception e)
	{
	output = "Error while deleting the customer.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	
	
}
	
