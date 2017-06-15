package com.acme.EditPass;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class EmptyLogin extends TestCase{
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://54.202.159.200:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testEmptyLogin() throws Exception {
	driver.get(baseUrl + "/#/");
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("alice.sandhu@acme.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("princess");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    driver.findElement(By.linkText("ACMEPass")).click();
    driver.findElement(By.xpath("//td[7]/div/button")).click();
    driver.findElement(By.id("field_login")).clear();
    driver.findElement(By.id("field_login")).sendKeys("");
    // ERROR: Caught exception [ERROR: Unsupported command [isEditable | css=div.modal-footer > button.btn.btn-primary | ]]
    driver.findElement(By.cssSelector("button.btn.btn-default")).click();
    driver.findElement(By.linkText("ACMEPass")).click();
    try {
      assertEquals("test426", driver.findElement(By.xpath("//td[3]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.cssSelector("#account-menu > span > span.hidden-sm")).click();
    driver.findElement(By.xpath("//a[@id='logout']/span[2]")).click();
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
