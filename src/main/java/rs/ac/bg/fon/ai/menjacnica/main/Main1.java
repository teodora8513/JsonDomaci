package rs.ac.bg.fon.ai.menjacnica.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.menjacnica.model.Transakcija;

public class Main1 {
	
	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "2e4baadf5c5ae6ba436f53ae5558107f";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES = "CAD";
	private static final BigDecimal AMMOUNT = new BigDecimal(18);

	public static void main(String[] args) {
		Gson gson = new Gson();
		
		try {
			URL url = new URL(BASE_URL + "/live?access_key="+API_KEY+
					"&source="+SOURCE + "&currencies="+CURRENCIES);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			JsonObject result = gson.fromJson(reader, JsonObject.class);
			
			if(result.get("success").getAsBoolean()) {
			System.out.println(result);
			
			BigDecimal kurs = result.get("quotes").getAsJsonObject().get("USDCAD").getAsBigDecimal();
			
			
			System.out.println("Kurs je " + kurs);
			Transakcija transakcija = new Transakcija(SOURCE,CURRENCIES,AMMOUNT, kurs.multiply(AMMOUNT), 
					new Date());
			
			System.out.println(transakcija);
			
			upisiUJsonTransakciju(transakcija);
			}
			else {
				
			}
				
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void upisiUJsonTransakciju(Transakcija transakcija) {
		
		try(FileWriter file = new FileWriter("prva_transakcija.json")){
			JsonObject obj = new JsonObject();
			obj.addProperty("source", transakcija.getIzvornaValuta());
			obj.addProperty("currencies", transakcija.getKrajnjaValuta());
			obj.addProperty("ammount", transakcija.getPocetniIznos());
			obj.addProperty("converted", transakcija.getKonvertovaniIznos());
			obj.addProperty("date", new SimpleDateFormat("dd.MMM.YYYY ss:mm:HH").format(transakcija.getDatum()));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(obj, file);
		}catch(Exception e) {
			
		}
		
	}

	
}
