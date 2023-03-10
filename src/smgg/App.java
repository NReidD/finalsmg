package smgg;
import org.json.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Driver;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {ChromeOptions option = new ChromeOptions();
       // option.addArguments("--headless");
        System.setProperty("webdriver.chrome.driver","./resources/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver(option);
        driver.get("https://www.stockmarketgame.org/login.html");
        WebElement user = driver.findElement(By.xpath("/html/body/div/div/section/section/div/form/p[1]/input"));
        user.sendKeys("DE_78_A497");
        WebElement pass = driver.findElement(By.xpath("/html/body/div/div/section/section/div/form/p[2]/input[1]"));
        pass.sendKeys("XBYWVFZY");
        WebElement enter = driver.findElement(By.xpath("/html/body/div/div/section/section/div/form/p[3]/input"));
        enter.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int x=0;
        while (x==0) {
            driver.get("https://www.stockmarketgame.org/enterstock.htm");
            Stock[] buyableStocks = new Stock[4];
                    stockinits(buyableStocks);
                    for (Stock stock : buyableStocks) {
                        System.out.println("running");
                        if (profitable(stock)) {
                            stock.buy(100, driver);
                        }
                        if (profited(stock)) {
                            stock.sell(stock.shares, driver);
                        }
                    }
        try {
            Thread.sleep(90000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }            
        }

}

    private static boolean profited(Stock stock) {
        if (stock.shares > 0) {
            double boughtAt = stock.price;
            Stock nowprice = new Stock(stock.tickers);
            double nowpriced = nowprice.price;
            if (boughtAt-nowpriced > 5) {
                return true;

            }
        }
        return false;
    }

    private static boolean profitable(Stock stock) {
        System.out.println(stock.price+"Avg:"+stock.average);
        if (stock.price < stock.average) {
            return true;
        }
        return false;
    }

    private static void stockinits(Stock[] buyable) {
        buyable[0] = new Stock("MSFT");
        buyable[1] = new Stock("AAPL");
        buyable[2] = new Stock("META");
        buyable[3] = new Stock("AMZN");
        
    }

    private static void trybuy(String string) {
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://telescope-stocks-options-price-charts.p.rapidapi.com/stocks/"+string+"?modules=assetProfile%2CsummaryProfile%2Cprice"))
		.header("X-RapidAPI-Key", "3ee02d7a37msha72732d998c4464p1ff34bjsn5959dec5c451")
		.header("X-RapidAPI-Host", "telescope-stocks-options-price-charts.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
HttpResponse<String> response;
try {
    response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    String jsonString =response.body();  
    JSONObject obj = new JSONObject(jsonString);
JSONArray pageName = obj.getJSONObject("quoteSummary").getJSONArray("result");
for (int i = 0; i < pageName.length(); i++) {
    JSONObject post_id = pageName.getJSONObject(i).getJSONObject("price").getJSONObject("regularMarketPrice");
    System.out.println(post_id.getDouble("raw"));
}

} catch (IOException | InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
    }
}

