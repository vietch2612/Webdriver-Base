package me.hoaiviet.webdriver.base.common.core.drivers.browsers;

import me.hoaiviet.webdriver.base.common.core.BaseWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import me.hoaiviet.webdriver.base.common.core.drivers.BrowserAbstract;
import me.hoaiviet.webdriver.base.common.core.helper.ExtensionHelper;

import java.io.File;

/**
 * Created by hoaiviet on 12/6/16.
 * Google Chrome browser
 */
public class ChromeBrowser extends BrowserAbstract {

    private static final String CHROME_DIVER_PATH_FORMAT = "ChromeDriver/chromedriver_%s";
    private static final String CHROME_DRIVER_PATH_MAC = String.format(CHROME_DIVER_PATH_FORMAT, "mac32/chromedriver");
    private static final String CHROME_DRIVER_PATH_WINDOWS = String.format(CHROME_DIVER_PATH_FORMAT, "win32/chromedriver");

    private final ChromeOptions chromeOptions = new ChromeOptions();

    @Override
    public void setOptions() {
        String chromeBinaryPath;
        String osName = System.getProperty("os.name").toUpperCase();

        if (osName.contains("WINDOWS")) {
            chromeBinaryPath = CHROME_DRIVER_PATH_WINDOWS;
        } else {
            chromeBinaryPath = CHROME_DRIVER_PATH_MAC;
        }

        File chromeDriver = new File(ClassLoader.getSystemResource(chromeBinaryPath).getPath());

        //noinspection ResultOfMethodCallIgnored
        chromeDriver.setExecutable(true);

        System.setProperty("webdriver.chrome.driver", chromeDriver.getPath());

        chromeOptions.addArguments("start-maximized");
    }

    @Override
    public BaseWebDriver create() {
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return new BaseWebDriver(new ChromeDriver(capabilities));
    }

    @Override
    public void addExtension(String extensionName) {
        chromeOptions.addExtensions(ExtensionHelper.findExtension(extensionName, "crx"));
    }
}
