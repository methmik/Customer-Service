package com;
import model.Customer;

import java.sql.Date;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;


@Path("/Customers")
public class CustomerService {
	Customer customerObj = new Customer();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)

	public String readItems()
	{
	return customerObj.readCustomers();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCustomer(@FormParam("cusAccount") int cusAccount,
			@FormParam("cusName") String cusName,
			@FormParam("cusEmail") String cusEmail,
			@FormParam("cusAddress") String cusAddress,
			@FormParam("cusCity") String cusCity	,
			@FormParam("cusMobile") String cusMobile,
			@FormParam("regDate") Date regDate	)
	{
	String output = customerObj.insertCustomer(cusAccount, cusName, cusEmail, cusAddress,cusCity,cusMobile, regDate	);
	return output;
	}
	

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(String customerData)
	{
	//Convert the input string to a JSON object
	JsonObject customerObject = new JsonParser().parse(customerData).getAsJsonObject();
	
	//Read the values from the JSON object
	String cusID = customerObject.get("cusID").getAsString();
	String cusAccount = customerObject.get("cusAccount").getAsString();
	String cusName = customerObject.get("cusName").getAsString();
	String cusEmail = customerObject.get("cusEmail").getAsString();
	String cusAddress = customerObject.get("cusAddress").getAsString();
	String cusCity = customerObject.get("cusCity").getAsString();
	String cusMobile = customerObject.get("cusMobile").getAsString();
	String regDate = customerObject.get("regDate").getAsString();
	String output = customerObj.updateCustomer(cusID, cusAccount, cusName, cusEmail, cusAddress, cusCity, cusMobile, regDate);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(String customerData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(customerData, "", Parser.xmlParser());

	//Read the value from the element <customerID>
	String cusID = doc.select("cusID").text();
	String output = customerObj.deleteCustomer(cusID);
	return output;
	}

}
	
	