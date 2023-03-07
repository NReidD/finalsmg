
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;


public class Stock {
    private String ticker = "";
    private double price = 0;
    public Stock(String ticker){
        ticker = ticker;
        price = priceinit(ticker);
    }
    private double priceinit(String ticker2) {
        
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
    }

