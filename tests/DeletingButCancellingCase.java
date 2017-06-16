package com.acme;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class DeletingButCancellingCase extends TestCase {
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
  public void testDeletingButCancellingCase() throws Exception {
    driver.get(baseUrl + "/#/");
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("delete@test.test");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("test");
    driver.findElement(By.id("rememberMe")).click();
    driver.findElement(By.cssSelector("span")).click();
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    driver.findElement(By.linkText("ACMEPass")).click();
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    driver.findElement(By.id("field_site")).clear();
    driver.findElement(By.id("field_site")).sendKeys("deletion test");
    driver.findElement(By.id("field_login")).clear();
    driver.findElement(By.id("field_login")).sendKeys("deletion test");
    driver.findElement(By.id("field_password")).clear();
    driver.findElement(By.id("field_password")).sendKeys("deletiontest");
    driver.findElement(By.xpath("//button[2]")).click();

    driver.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes-alt")).click();
    try {
      assertEquals("deletion test", driver.findElement(By.xpath("//td[2]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.cssSelector("button.btn.btn-danger")).click();
    driver.findElement(By.cssSelector("button.btn.btn-default")).click();
    assertEquals("deletion test", driver.findElement(By.xpath("//td[2]")).getText());
    driver.findElement(By.cssSelector("#account-menu > span")).click();
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
