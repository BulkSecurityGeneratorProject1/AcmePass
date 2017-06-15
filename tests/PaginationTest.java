package PaginationSuite;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.regex.Pattern;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PaginationTest {
	

	 private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
	    driver = new FirefoxDriver();
	    baseUrl = "http://24.108.28.116:8080";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testCompare() throws Exception {
	    driver.get(baseUrl + "/#/");
	    driver.findElement(By.id("login")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("PaginationTest@acme.com");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("PaginationTest");
	    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
	    driver.findElement(By.linkText("ACMEPass")).click();
	    
	    //populate the ACMEPass page with over 20(30) values
	    for(int i = 0; i < 30; i++){
	    	
	        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
	        driver.findElement(By.id("field_site")).clear();
	        driver.findElement(By.id("field_site")).sendKeys("test426");
	        driver.findElement(By.id("field_login")).clear();
	        driver.findElement(By.id("field_login")).sendKeys("test426");
	        driver.findElement(By.id("field_password")).clear();
	        driver.findElement(By.id("field_password")).sendKeys("test426");
	        driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-primary")).click();
            Thread.sleep(1500);

	    }
	    
	    //Get the first listed element from each page and compare them, fail if they are the same.
	    String first = driver.findElement(By.cssSelector("td")).getText();
	    driver.findElement(By.linkText("»")).click();
	    String second = driver.findElement(By.cssSelector("td")).getText();
	    try {
	    	assertThat(first, is(not(second)));
		      //assertEquals(first, second);
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		}
	    
	    //Navigate back to the first page
	    driver.findElement(By.linkText("«")).click();

	    
	    
	    //delete the test values
	    for(int i = 0; i<30;i++){
	        driver.findElement(By.xpath("//button[2]")).click();
	        driver.findElement(By.cssSelector("button.btn.btn-danger")).click();
            Thread.sleep(2000);

	    }
	  }

	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
		}
	

	


