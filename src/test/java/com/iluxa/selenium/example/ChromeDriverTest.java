package com.iluxa.selenium.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class ChromeDriverTest {

    private String url;
    private WebDriver driver;

    @Before
    public void prepare() throws MalformedURLException {
        System.setProperty(
                "webdriver.chrome.driver",
                "webdriver/chromedriver");

        url = "https://google.com";

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);

//        driver = new ChromeDriver(chromeOptions);
        driver = new RemoteWebDriver(new URL("http://circlet:circlet@ggr.labs.intellij.net:4444/wd/hub"), chromeOptions);
        driver.get(url);
    }

    @Test
    public void testInput() throws IOException {

        List<WebElement> elements = driver
                .findElements(By.cssSelector("input[name=q]"));

        WebElement input = elements.get(0);
        input.sendKeys("la-la-la");
        Collections.nCopies(3, 1)
                .stream()
                .forEach(i ->
                        elements.get(0).sendKeys(Keys.ARROW_LEFT));

        input.sendKeys("-lo");

        assertTrue("input value is as expected",
                (new WebDriverWait(driver, 5))
                        .until(new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver d) {
                                return input.getAttribute("value").contains("la-la-lo-la");
                            }
                        })
        );

    }

    @After
    public void teardown() throws IOException {
        driver.quit();
    }

}
