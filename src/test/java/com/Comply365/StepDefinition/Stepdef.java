package com.Comply365.StepDefinition;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.ClientApiException;

import com.Comply365.Engine.BaseClass;
import com.Comply365.Engine.CommonMethod;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class Stepdef extends BaseClass  {
		
		@After
		public void addScreenshot(Scenario scenario) throws IOException {
			if (scenario.isFailed()) {
				final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png", "image");
			}
//			driver.quit(); 
			
			System.out.println("EndTest");
		}

	@Given("User is on Hireprous login page")
	public void user_is_on_hireprous_login_page() throws InterruptedException, IOException, ClientApiException {
		setup();
	}
	@When("User enters username and password")
	public void user_enters_username_and_password() throws IOException, InterruptedException  {
		CommonMethod.WaitUntilPresence("UserName", 120);
		CommonMethod.sendKeys("UserName", data.getCellData("Login", "UserName", 2));
		CommonMethod.sendKeys("Password", data.getCellData("Login", "Password", 2));

	}
	@And("User clicks on Log IN button")
	public void user_clicks_on_log_in_button() throws InterruptedException, IOException {
		CommonMethod.RobustclickElementVisible("loginBtn","SuccessfulLogin");

	}
	@Then("User login must be successful")
	public void user_login_must_be_successful() throws InterruptedException, IOException {
		CommonMethod.WaitUntilVisibility("SuccessfulLogin", 300);
	}

	//////////////////////Create Customer/////////////////////////////////////////////
	@Given("User navigate to the Dashboard page by logging in to nopCommerce")
	public void user_navigate_to_the_dashboard_page_by_logging_in_to_nopCommerce() throws IOException {

		CommonMethod.WaitUntilPresence("CustomersTab", 120);

	}
	@When("User clicks on Customers tab")
	public void user_clicks_on_customers_tab() throws IOException, InterruptedException {
		CommonMethod.click("CustomersTab");
		CommonMethod.RobustclickElementVisible("CustomersButton","CustomerAddnewButton");
	}
	@And("User clicks on Add new button")
	public void user_clicks_on_add_new_button() throws IOException, InterruptedException {
		CommonMethod.RobustclickElementVisible("CustomerAddnewButton","CustomerEmail");

	}
	@And("User enters data to the Customer info fields")
	public void user_enters_data_to_the_customer_info_fields() throws IOException {
		String Email = USfaker.internet().emailAddress();
		String Password = USfaker.internet().password();
		String Firstname = USfaker.address().firstName();
		String lastname = USfaker.address().lastName();
		String Dateofbirth = data.getCellData("customer", "DateOfBirth", 2);
		String Companyname = USfaker.address().lastName();
		String Admincomment = data.getCellData("customer", "AdminComment", 2);
		CommonMethod.data.setCellData("customer","Email",2, Email);
		CommonMethod.sendKeys("CustomerEmail", Email);
		CommonMethod.sendKeys("CustomerPassword", Password);
		CommonMethod.sendKeys("CustomerFirstName", Firstname);
		CommonMethod.sendKeys("CustomerLastName", lastname);
		CommonMethod.click("CustomerGender");
		CommonMethod.sendKeys("CustomerDateOfBirth", Dateofbirth);
		CommonMethod.sendKeys("CustomerCompanyName", Companyname);
		CommonMethod.sendKeys("CustomerAdminComment", Admincomment);

	}
	@And("User clicks on save button")
	public void user_clicks_on_save_button() throws IOException, InterruptedException {
		CommonMethod.RobustclickElementVisible("CustomerSaveButton","CustomersTab");

	}
	@Then("User will be redirected to Customers list page successfully")
	public void user_will_be_redirected_to_customers_list_page_successfully() throws IOException {
		CommonMethod.scrollDown();
		CommonMethod.WaitUntilPresence("CustomerListTable", 60);
	}
	
	@And("User verifies created customer in Customers list")
	public void user_verifies_created_customer_in_customers_list() throws IOException {    
		List<String> val = CommonMethod.fetchTableData("CustomerListTable");
		CommonMethod.assertExpectedContainsActual(val.get(1),data.getCellData("customer", "Email", 2),"Email doesn't match");

	}

	/////////////////Logout///////////////////////////////////////////////
	@When("User clicks on Logout button")
	public void user_clicks_on_logout_button() throws IOException, InterruptedException {
		CommonMethod.RobustclickElementVisible("CustomerLogoutButton","loginBtn");

	}
	@Then("User will be redirected to Login page successfully")
	public void user_will_be_redirected_to_login_page_successfully() throws IOException, InterruptedException {
		CommonMethod.WaitUntilPresence("loginBtn", 120);

	}

}






