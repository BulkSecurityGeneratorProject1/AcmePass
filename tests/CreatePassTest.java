package com.acme;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class CreatePassTest extends TestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    driver = new FirefoxDriver();
    baseUrl = "http://54.202.159.200:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
    goToAcmePass();
  }

  public void goToAcmePass() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("princess");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("alice.sandhu@acme.com");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    driver.findElement(By.linkText("ACMEPass")).click();
  }

  @Test
  public void testAddNewPassword() throws Exception {
    testAddNewPass("google", "user", "password");
    verifyLastSavedPassword("google", "user", "password");
  }

  @Test
  public void testAddNewPasswordWithUnicode() throws Exception {
    testAddNewPass("你好好", "বাংলা", "こんにちは");
    try {
      assertTrue(isElementPresent(By.cssSelector("pre")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  public void testAddNewPass(String site, String login, String password) throws Exception {
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
    driver.findElement(By.id("field_site")).sendKeys(site);

    //Login
    assertEquals("This field is required.", driver.findElement(By.xpath("//div[3]/div/p")).getText());
    driver.findElement(By.id("field_login")).sendKeys(login);

    //Password
    assertEquals("This field is required.", driver.findElement(By.xpath("//div[2]/p")).getText());
    driver.findElement(By.id("field_password")).sendKeys(password);
    driver.findElement(By.cssSelector("span.glyphicon.glyphicon-eye-open")).click();
    assertEquals(password, driver.findElement(By.id("field_password")).getAttribute("value"));

    //Save
    driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-primary")).click();

  }

  public void verifyLastSavedPassword(String site, String login, String password) throws Exception {
    driver.findElement(By.cssSelector("th")).click();
    assertEquals(site, driver.findElement(By.xpath("//td[2]")).getText());
    assertEquals(login, driver.findElement(By.xpath("//td[3]")).getText());
    driver.findElement(By.cssSelector("span.glyphicon.glyphicon-eye-open")).click();
    assertEquals(password, driver.findElement(By.xpath("//input[@type='text']")).getAttribute("value"));
  }


  @Test
  public void testTooLongSite() throws Exception {
    testAddNewPass("atest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426seng", "user", "123456");

      try {
          assertTrue(isElementPresent(By.cssSelector("pre")));
      } catch (Error e) {
          verificationErrors.append(e.toString());
      }
  }

  @Test
  public void testTooLongLogin() throws Exception {
    testAddNewPass("website", "uatest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengser", "123456");

    try {
      assertTrue(isElementPresent(By.cssSelector("pre")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @Test
  public void testTooLongPassword() throws Exception {
    testAddNewPass("website", "user", "1atest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426sengtest426seng23456");

    try {
      assertTrue(isElementPresent(By.cssSelector("pre")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @Test
  public void testDeletePassword() throws Exception {
    String lastID = driver.findElement(By.cssSelector("td")).getText();
    driver.findElement(By.xpath("//button[2]")).click();
    driver.findElement(By.cssSelector("button.btn.btn-danger")).click();
    assertNotSame(lastID, driver.findElement(By.cssSelector("td")).getText());
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
