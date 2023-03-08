import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Stock {
    String tickers = "";
    double price = 0;
    public Stock(String ticker){
        tickers = ticker;
        price = priceinit(ticker);
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
JSONArray pageName = obj.getJSONObject("quoteSummary").getJSONArray("result");
for (int i = 0; i < pageName.length(); i++) {
    JSONObject post_id = pageName.getJSONObject(i).getJSONObject("price").getJSONObject("regularMarketPrice");
    price = post_id.getDouble("raw");
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
    void buy(int shares, WebDriver driver){
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

    }
    }

