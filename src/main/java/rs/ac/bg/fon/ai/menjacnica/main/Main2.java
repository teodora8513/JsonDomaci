package rs.ac.bg.fon.ai.menjacnica.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.menjacnica.model.Transakcija;

public class Main2 {

	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "2e4baadf5c5ae6ba436f53ae5558107f";
	private static final String SOURCE = "USD";
	private static final String CURRENCIE_EUR = "EUR";
	private static final String CURRENCIE_CHF = "CHF";
	private static final String CURRENCIE_CAD = "CAD";

	private static final BigDecimal AMMOUNT = new BigDecimal(100);

	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();

		try {
			URL url = new URL(BASE_URL + "/historical?access_key=" + API_KEY + "&date=2020-10-27" + "&source=" + SOURCE
					+ "&currencies=" + CURRENCIE_EUR + "," + CURRENCIE_CHF + "," + CURRENCIE_CAD);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			// connection.setDoInput(true);
			// connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			JsonObject result = gson.fromJson(reader, JsonObject.class);

			if (result.get("success").getAsBoolean()) {
				System.out.println(result);

				BigDecimal kurs_eur = result.get("quotes").getAsJsonObject().get("USDEUR").getAsBigDecimal();
				BigDecimal kurs_chf = result.get("quotes").getAsJsonObject().get("USDCHF").getAsBigDecimal();
				BigDecimal kurs_cad = result.get("quotes").getAsJsonObject().get("USDCAD").getAsBigDecimal();

				Transakcija transakcija_eur = new Transakcija(SOURCE, CURRENCIE_EUR, AMMOUNT,
						kurs_eur.multiply(AMMOUNT), new Date());
				Transakcija transakcija_chf = new Transakcija(SOURCE, CURRENCIE_CHF, AMMOUNT,
						kurs_eur.multiply(AMMOUNT), new Date());
				Transakcija transakcija_cad = new Transakcija(SOURCE, CURRENCIE_CAD, AMMOUNT,
						kurs_eur.multiply(AMMOUNT), new Date());

				upisiUJsonTransakcije(transakcija_eur, transakcija_cad, transakcija_chf);

			} else {

				throw new Exception("The transaction isnt successful");
			}

		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void upisiUJsonTransakcije(Transakcija... transakcija) {
		JsonArray array = new JsonArray();
		Gson gson  = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter file = new FileWriter("ostale_transakcije" + ".json")) {
			for (Transakcija trans : transakcija) {

				JsonObject obj = new JsonObject();
				obj.addProperty("source", trans.getIzvornaValuta());
				obj.addProperty("currencies", trans.getKrajnjaValuta());
				obj.addProperty("ammount", trans.getPocetniIznos());
				obj.addProperty("converted", trans.getKonvertovaniIznos());
				obj.addProperty("date", new SimpleDateFormat("dd.MMM.YYYY ss:mm:HH").format(trans.getDatum()));

				
				array.add(obj);

			}
			gson.toJson(array, file);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
