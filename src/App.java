import org.json.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    public static void main( String[] args )
    {ChromeOptions option = new ChromeOptions();
        option.addArguments("--headless");
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
driver.get("https://www.stockmarketgame.org/enterstock.htm");
System.out.println("Connection to Base made");
System.out.println("Enter Stock");
Scanner reader = new Scanner(System.in);
String stockpick = reader.nextLine();
Stock picked = new Stock(stockpick);
System.out.println("Ticker: "+picked.tickers+"\nPrice: "+picked.price);
System.out.println("Want to buy(b) or sell(s)?");
String buyOrSell = reader.nextLine();
if (buyOrSell.toLowerCase().equals("b")) {
    System.out.println("How many shares");
    Scanner numread = new Scanner(System.in);
    int sharess = numread.nextInt();
    if (sharess >= 10) {
        picked.buy(sharess,driver);

    } else {
     System.out.println("needs at least 10 shares");   
    }
} 
else if(buyOrSell.toLowerCase().equals("s")) {
    
}
Stock[] buyable = new Stock[4];
stockinits(buyable);
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
