package etf.ip.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import etf.ip.model.User;

public class AdminDAO {
	private final static String URL = "http://localhost:8081/WebShop/admin";
	public User login(String username, String password) {
		try {
			URL obj = new URL(URL + "?username=" + username + "&password=" + password);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				// print result
				System.out.println(response.toString());
				Gson gson=new Gson();
				return gson.fromJson(response.toString(), User.class);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
