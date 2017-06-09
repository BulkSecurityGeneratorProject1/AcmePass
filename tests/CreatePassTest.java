package com.company;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CreatePassTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    driver = new FirefoxDriver();
    baseUrl = "http://24.108.28.116:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
    goToAcmePass();
  }

  public void goToAcmePass() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("tester@test");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    driver.findElement(By.linkText("ACMEPass")).click();
  }

  @Test
  public void testAddNewPass() throws Exception {
    driver.get(baseUrl + "/#/acme-pass");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

    //Verify length of site & error message
    driver.findElement(By.id("field_site")).clear();
    assertEquals("This field is required.", driver.findElement(By.cssSelector("p.help-block")).getText());
    driver.findElement(By.id("field_site")).sendKeys("a");
    assertEquals("This field is required to be at least 3 characters.", driver.findElement(By.xpath("//p[2]")).getText());
    driver.findElement(By.id("field_site")).clear();
    driver.findElement(By.id("field_site")).sendKeys("ab");
    assertEquals("This field is required to be at least 3 characters.", driver.findElement(By.xpath("//p[2]")).getText());
    driver.findElement(By.id("field_site")).clear();
    driver.findElement(By.id("field_site")).sendKeys("abc");

    //Login
    assertEquals("This field is required.", driver.findElement(By.xpath("//div[3]/div/p")).getText());
    driver.findElement(By.id("field_login")).sendKeys("d");

    //Password
    assertEquals("This field is required.", driver.findElement(By.xpath("//div[2]/p")).getText());
    driver.findElement(By.id("field_password")).sendKeys("1234");

    //Save
    driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-primary")).click();
    Thread.sleep(1500);

    //Verify saved data
    driver.findElement(By.cssSelector("th")).click();
    assertEquals("abc", driver.findElement(By.xpath("//td[2]")).getText());
    assertEquals("d", driver.findElement(By.xpath("//td[3]")).getText());
    //TODO: assert Password matches
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
