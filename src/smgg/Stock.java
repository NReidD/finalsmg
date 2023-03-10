package smgg;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Stock {
    String tickers = "";
    double price = 0;
    int marketcap = 0;
    int shares = 0;
    double average;
    
    public Stock(String ticker){
        tickers = ticker;
        price = priceinit(ticker);
        average = average(ticker);
    }
    private double average(String ticker2){
return getSMA(ticker2,"weekly");
        
    }
    private double getSMA(String ticker2, String string) {
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?time_period=60&interval="+string+"&series_type=close&function="+"SMA"+"&symbol="+ticker2+"&datatype=json"))
		.header("X-RapidAPI-Key", "3ee02d7a37msha72732d998c4464p1ff34bjsn5959dec5c451")
		.header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
HttpResponse<String> response;
try {
    response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.body());
    String re = response.body();
    JSONObject jsonObject = new JSONObject(re);
    Iterator<String> keys = jsonObject.keys();
String key = keys.next();
System.out.println(key);

    JSONObject jArray= jsonObject.getJSONObject(key);
    Iterator<String> keysw = jArray.keys();
    String check = keysw.next();
    System.out.println(check);
JSONObject totake = jArray.getJSONObject(check);
System.out.println(totake.getString("SMA"));
    average = Double.parseDouble(totake.getString("SMA"));
    System.out.println(average);
return average;


} catch (IOException | InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
return average;
    }
    private double priceinit(String ticker2) {
        System.out.println("Running");

        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://telescope-stocks-options-price-charts.p.rapidapi.com/stocks/"+ticker2+"?modules=assetProfile%2CsummaryProfile%2Cprice"))
		.header("X-RapidAPI-Key", "3ee02d7a37msha72732d998c4464p1ff34bjsn5959dec5c451")
		.header("X-RapidAPI-Host", "telescope-stocks-options-price-charts.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
HttpResponse<String> response;
try {
    response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    String jsonString =response.body();  
    JSONObject obj = new JSONObject(jsonString);
    System.out.println(jsonString);
JSONArray pageName = obj.getJSONObject("quoteSummary").getJSONArray("result");
for (int i = 0; i < pageName.length(); i++) {
    JSONObject post_id = pageName.getJSONObject(i).getJSONObject("price").getJSONObject("marketCap");
    JSONObject post_id2 = pageName.getJSONObject(i).getJSONObject("price").getJSONObject("regularMarketPrice");
    marketcap = post_id.getInt("raw");
    price = post_id2.getDouble("raw");
    System.out.println("price is "+price);
    return price;
}

} catch (IOException | InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
return price;
    }
    private double[] fivemin(){

        return null;
        
    }
    void buy(int shares, WebDriver driver) throws InterruptedException{
        System.out.println("Running");
                WebElement setbuy = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[1]/div[2]/div[1]/form/div/input[1]"));
                setbuy.click();
                WebElement tick = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[1]/div[2]/div[1]/form/p[1]/input"));
                tick.sendKeys(this.tickers);
                WebElement share = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[1]/div[2]/div[1]/form/p[3]/input"));
                share.sendKeys(String.valueOf(shares));
                WebElement submit = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[1]/div[2]/div[1]/form/p[5]/input[2]"));
                submit.click();
                if (priceinit(this.tickers)==this.price) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    WebElement pass = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[2]/div[2]/div[14]/input"));
                    pass.sendKeys("XBYWVFZY");
                    WebElement submit2 = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[2]/div[2]/div[14]/div[2]/input[1]"));
                submit2.click();
                System.out.println("Executed.");
                this.shares = shares;
                driver.get("https://www.stockmarketgame.org/enterstock.htm");
Thread.sleep(50000);
                }

    }
    void sell(int shares, WebDriver driver){
        System.out.println("Running");
                WebElement setbuy = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[1]/div[2]/div[1]/form/div/input[2]"));
                setbuy.click();
                WebElement tick = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[1]/div[2]/div[1]/form/p[1]/input"));
                tick.sendKeys(this.tickers);
                WebElement share = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[1]/div[2]/div[1]/form/p[3]/input"));
                share.sendKeys(String.valueOf(shares));
                WebElement submit = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[1]/div[2]/div[1]/form/p[5]/input[2]"));
                submit.click();
                if (priceinit(this.tickers)==this.price) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    WebElement pass = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[2]/div[2]/div[14]/input"));
                    pass.sendKeys("XBYWVFZY");
                    WebElement submit2 = driver.findElement(By.xpath("/html/body/div/div[1]/section/section/div/div[2]/div[2]/div[14]/div[2]/input[1]"));
                submit2.click();
                }
                System.out.println("Executed.");
                this.shares = this.shares-shares;
    }
    }

