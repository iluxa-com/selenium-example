# selenium-example
An example project of selenium using ChromeWebDriver


## Prerequisites ##

* Have [java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed
* Have [maven](http://maven.apache.org/) installed


## Execute automation tests ##

Provide system property `webdriver.chrome.driver` for webdriver.

`Chromedriver` for browser version 83.0.4103.97 can be found in **webdriver** directory (mac and linux).
Otherwise visit https://chromedriver.chromium.org/downloads

To run tests, execute

```bash
mvn clean test
```

The result for `contenteditable` looks as following:

![](docs/prosemirror_screenshot.png)

![](./docs/img/autotests.gif)



## LICENSE ##

[MIT License](https://raw.githubusercontent.com/leftstick/selenium-example/master/LICENSE)
