package com.iluxa.selenium.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class ChromeDriverTest {

    private WebDriver driver;
    public final String expectedResult = "la-la-lo-la";

    @Before
    public void prepare() throws Exception {

        String osName = System.getProperty("os.name");
        if (osName.contains("Mac OS X"))
            System.setProperty(
                    "webdriver.chrome.driver",
                    "webdriver/chromedriver");
        else if (osName.contains("Linux"))
            System.setProperty(
                    "webdriver.chrome.driver",
                    "webdriver/chromedriver-linux");
        else throw new Exception("no webdriver for: " + osName);

        final ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setHeadless(true);

        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    public void testGoogleInput() throws InterruptedException {
        String url = "https://google.com";
        String elementLocator = "input[name=q]";
        driver.get(url);
        WebElement element = driver.findElement(By.cssSelector(elementLocator));
        test(element);
        assertTrue("input value is as expected",
                (new WebDriverWait(driver, 5))
                        .until((ExpectedCondition<Boolean>) d -> element.getAttribute("value").contains(expectedResult))
        );
    }

    @Test
    public void testDraftJs() throws InterruptedException {
        String url = "https://draftjs.org/";
        String elementLocator = "[contentEditable=true]";
        driver.get(url);
        driver.manage().window().setSize(new Dimension(1100, 400));
        WebElement element = driver.findElement(By.cssSelector(elementLocator));
        test(element);
        assertTrue("input value is as expected",
                (new WebDriverWait(driver, 5))
                        .until((ExpectedCondition<Boolean>) d -> element.getText().contains(expectedResult))
        );
    }


    @Test
    public void testContentEditable() throws InterruptedException {
        String url = "https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_global_contenteditable";
        String elementLocator = "[contenteditable=true]";
        driver.get(url);
        driver.manage().window().setSize(new Dimension(1100, 400));
        driver.switchTo().frame(driver.findElement(By.cssSelector("#iframeResult")));
        WebElement element = driver.findElement(By.cssSelector(elementLocator));
        deleteText(element);
        test(element);
        assertTrue("input value is as expected",
                (new WebDriverWait(driver, 5))
                        .until((ExpectedCondition<Boolean>) d -> element.getText().contains(expectedResult))
        );
    }

    @Test
    public void testProseMirror() throws InterruptedException {
        String url = "https://prosemirror.net/examples/basic/";
        String elementLocator = "[contentEditable=true]";
        driver.get(url);
        WebElement element = driver.findElement(By.cssSelector(elementLocator));

        deleteText(element);

        test(element);

        assertTrue("input value is as expected",
                (new WebDriverWait(driver, 5))
                        .until((ExpectedCondition<Boolean>) d -> element.getText().contains(expectedResult))
        );
    }

    private void deleteText(WebElement element) {
        element.click();
//        element.sendKeys(Keys.META, "a", Keys.DELETE);
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL);
        actions.sendKeys(Keys.DELETE);
        actions.perform();
    }

    private void test(WebElement input) throws InterruptedException {
        Actions actions = new Actions(driver);
        input.click();
        input.sendKeys("la-la-la");
        Collections.nCopies(3, 1)
                .forEach(i ->
                        actions.sendKeys(Keys.ARROW_LEFT).perform());
//                        input.sendKeys(Keys.ARROW_LEFT));

//        input.sendKeys("-lo");
//        actions.sendKeys("-lo").perform();
        actions.sendKeys("-").perform();
        Thread.sleep(100);
        actions.sendKeys("l").perform();
        Thread.sleep(100);
        actions.sendKeys("o").perform();
    }

    @After
    public void teardown() {
        driver.quit();
    }

}
