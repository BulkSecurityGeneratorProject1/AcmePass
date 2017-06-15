package com.acme.GeneratePassword;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GeneratePasswordTest extends TestCase {

	private String baseUrl = "http://54.202.159.200:8080";
	private WebDriver driver;

	private String loginId = "alice.sandhu@acme.com";
	private String password = "princess";

	private int[] lengths = { 8, 25, 100, 1000 };
	private int passwordsToGenerate = 10;
	private String[] genOptions = { "lower", "upper", "digits", "special", "repetition" };

	@Before
	public void setUp() throws Exception {
//		System.setProperty(
//				"webdriver.gecko.driver",
//				"C:/Users/Owner/Downloads/geckodriver-v0.16.1-win64/geckodriver.exe"
//		);
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(10,  TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);

		driver.get(baseUrl);

		driver.findElement(By.linkText("Sign in")).click();
		driver.findElement(By.id("username")).sendKeys(loginId);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//button[text()='Sign in'][1]")).click();
		driver.get(baseUrl + "/#/acme-pass/new");
		driver.findElement(By.xpath("//button[@ng-click='vm.openPwdGenModal()']")).click();

	}

	/**
	 * Unchecks all generation options on the generation screen (lowercase, uppercase, etc.).
	 */
	private void uncheckAllOptions() throws Exception {
		for (String option : genOptions) {
			String htmlClass = driver.findElement(By.xpath("//input[@id='field_" + option + "']")).getAttribute("class");
			if (htmlClass.contains("ng-not-empty")) {
				driver.findElement(By.xpath("//input[@id='field_" + option + "']")).click();
			}
		}
	}
	/**
	 * Verifies that a String s contains only unique characters.
	 * @param s	The input string.
	 * @return 'true' if the string contains only unique characters, 'false' otherwise.
	 */
	private boolean verifyUniqueness(String s) {
		Set<Character> set = new HashSet<Character>();
		for(char c : s.toCharArray()) {
			if(!set.add(c)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Converts a string of characters in to a Set<Character>.
	 * @param s	The string to convert.
	 * @return	The Set<Character> containing the characters in the String.
	 */
	private Set<Character> stringToSet(String s) {
		Set<Character> set = new HashSet<Character>();
		for (char c : s.toCharArray()) set.add(c);
		return set;
	}

	@Test
	/**
	 * Tests if a warning is displayed to the user when the "Length" field of the
	 * password generation page is left blank, and that the generation button is disabled.
	 */
	public void testFieldRequired() throws Exception {
		String disabled = driver.findElement(By.xpath("//button[@ng-click='vm.generate()']")).getAttribute("disabled");
		String hidden = driver.findElement(By.xpath("//p[@ng-show='pdwGenForm.length.$error.required']"))
				.getAttribute("aria-hidden");
		assert(disabled == null);
		assert(hidden.equals("true"));

		driver.findElement(By.xpath("//input[@id='field_length'][1]")).sendKeys(Keys.BACK_SPACE);

		disabled = driver.findElement(By.xpath("//button[@ng-click='vm.generate()']")).getAttribute("disabled");
		hidden = driver.findElement(By.xpath("//p[@ng-show='pdwGenForm.length.$error.required']"))
				.getAttribute("aria-hidden");
		assert(disabled.equals("true"));
		assert(hidden.equals("false"));
	}

	@Test
	/**
	 * Tests that the generate button is disabled when the password length is negative.
	 */
	public void testInvalidLengthInput() throws Exception {
		String disabled = driver.findElement(By.xpath("//button[@ng-click='vm.generate()']")).getAttribute("disabled");
		assert(disabled == null);

		driver.findElement(By.xpath("//input[@id='field_length'][1]")).sendKeys("abc");

		disabled = driver.findElement(By.xpath("//button[@ng-click='vm.generate()']")).getAttribute("disabled");
		assert(disabled.equals("true"));
	}

	@Test
	/**
	 * Tests if the user is unable to generate a password with negative length.
	 */
	public void testNegativeLength() throws Exception {
		String disabled = driver.findElement(By.xpath("//button[@ng-click='vm.generate()']")).getAttribute("disabled");
		assert(disabled == null);
		driver.findElement(By.xpath("//input[@id='field_length'][1]")).sendKeys(
				Keys.BACK_SPACE + "-1"
		);
		disabled = driver.findElement(By.xpath("//button[@ng-click='vm.generate()']")).getAttribute("disabled");
		assert(disabled.equals("true"));
	}

	@Test
	/**
	 * Tests if the user is able to generate a password with all generation option
	 * checkboxes unchecked.
	 */
	public void testNoBoxesChecked() throws Exception {
		uncheckAllOptions();
		driver.findElement(By.xpath("//button[@ng-click='vm.generate()']")).click();
		String text = driver.findElement(By.xpath("//input[@ng-model='vm.password']")).getAttribute("value");
		assert(text.equals(""));
	}
	/**
	 * Test all of the current combinations (one of lowercase, uppercase, digits, and special characters).
	 * @param combination	The StringBuffer containing any of L, U, D, or S.
	 */
	private void testCombination(StringBuffer combination) throws Exception {
		uncheckAllOptions();
		Set<Character> set = new HashSet<Character>();
		// Lowercase
		if (combination.indexOf("L") >= 0) {
			set.addAll(stringToSet("abcdefghijklmnopqrstuvwxyz"));
			driver.findElement(By.xpath("//input[@id='field_lower']")).click();
		}
		// Uppercase
		if (combination.indexOf("U") >= 0) {
			set.addAll(stringToSet("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			driver.findElement(By.xpath("//input[@id='field_upper']")).click();
		}
		// Digits
		if (combination.indexOf("D") >= 0) {
			set.addAll(stringToSet("0123456789"));
			driver.findElement(By.xpath("//input[@id='field_digits']")).click();
		}
		// Special characters
		if (combination.indexOf("S") >= 0) {
			set.addAll(stringToSet("!@#$%-_"));
			driver.findElement(By.xpath("//input[@id='field_special']")).click();
		}
		// Repetitions allowed
		for (int len : lengths) {
			for (int i = 0; i < passwordsToGenerate; i++) {
				driver.findElement(By.xpath("//input[@id='field_length'][1]")).clear();
				driver.findElement(By.xpath("//input[@id='field_length'][1]")).sendKeys(Integer.toString(len));
				driver.findElement(By.xpath("//button[@ng-click='vm.generate()']")).click();
				String text = driver.findElement(By.xpath("//input[@ng-model='vm.password']")).getAttribute("value");
				assert(text.length() == len);
				assert(set.containsAll(stringToSet(text)));
			}
		}

		driver.findElement(By.xpath("//input[@id='field_repetition']")).click();

		int[] repLengths = { set.size() / 3, set.size() / 2, set.size() };
		// Repetitions not allowed
		for (int len : repLengths) {
			for (int i =0; i < passwordsToGenerate; i++) {
				driver.findElement(By.xpath("//input[@id='field_length'][1]")).clear();
				driver.findElement(By.xpath("//input[@id='field_length'][1]")).sendKeys(Integer.toString(len));
				driver.findElement(By.xpath("//button[@ng-click='vm.generate()']")).click();
				String text = driver.findElement(By.xpath("//input[@ng-model='vm.password']")).getAttribute("value");
				assert(text.length() == len);
				assert(verifyUniqueness(text));
				assert(set.containsAll(stringToSet(text)));
			}
		}
	}
	/**
	 * Recursively generate all combinations of the characters in a given String.
	 * @param in	The input String containing the characters for generation.
	 * @param out	The output StringBuffer to which the combinations will be written.
	 * @param index	The current index of the input String, past which will be used to generate combinations.
	 */
	private void generateCombinations(String in, StringBuffer out, int index) throws Exception {
		for (int i = index; i < in.length(); i++) {
			out.append(in.charAt(i));
			testCombination(out);
			generateCombinations(in, out, i + 1);
			out.deleteCharAt(out.length() - 1);
		}
	}

	@Test
	/**
	 * Tests all combinations of lowercase, uppercase, numeric, and special
	 * character password generations with repetitions both on and off.
	 * Generates multiple passwords of different lengths per combination,
	 * and verifies that the lengths are consistent and the non-repetitive
	 * passwords are unique.
	 */
	public void testAllCombinations() throws Exception {
		generateCombinations("LUDS", new StringBuffer(), 0);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}