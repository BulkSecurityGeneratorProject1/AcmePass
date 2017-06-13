package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class NewLongSite {
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
  public void testNewLongSite() throws Exception {
    driver.get(baseUrl + "/#/");
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("ACMEPassEdit@acme.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("ACMEPassEdit");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    driver.findElement(By.linkText("ACMEPass")).click();
    driver.findElement(By.xpath("//td[7]/div/button")).click();
    driver.findElement(By.id("field_site")).clear();
    driver.findElement(By.id("field_site")).sendKeys("test426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426seng");
    driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-primary")).click();
    try {
      assertTrue(isElementPresent(By.cssSelector("pre")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.cssSelector("button.btn.btn-default")).click();
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
