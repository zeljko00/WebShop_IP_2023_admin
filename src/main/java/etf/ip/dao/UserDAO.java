package etf.ip.dao;

import etf.ip.model.User;
import etf.ip.mysql.ConnectionPool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.Gson;

public class UserDAO {
	private final static String GET_BY_USERNAME = "SELECT * FROM support_ac WHERE username=?";
	private final static String URL = "http://localhost:8081/WebShop/support";
	private final ConnectionPool pool = ConnectionPool.getConnectionPool();

//    public User getByUsername(String username){
//        Connection connection=null;
//        try{
//            connection=pool.checkOut();
//            PreparedStatement preparedStatement=Util.prepareStatement(connection,GET_BY_USERNAME,false,username);
//            ResultSet resultSet=preparedStatement.executeQuery();
//            if(resultSet.next()){
//                User user=new User();
//                user.setUsername(resultSet.getString("username"));
//                user.setPassword(resultSet.getString("password"));
//                user.setFirstname(resultSet.getString("firstname"));
//                user.setLastname(resultSet.getString("lastname"));
//                return user;
//            }
//            else return
//             null;
//        }catch(Exception e){
//            e.printStackTrace();
//            pool.checkIn(connection);
//            return null;
//        }
//    }
	public User login(String username, String password) {
		try {
			URL obj = new URL(URL + "?username=" + username + "&password=" + password);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
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
