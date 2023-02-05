package etf.ip.dao;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import etf.ip.model.Message;
import etf.ip.model.User;
import etf.ip.mysql.ConnectionPool;
public class MessageDAO {
	
	private final static String GET_ALL="SELECT * FROM message";
	private final static String GET_USER_BY_ID="SELECT * FROM user WHERE id=?";
	private final ConnectionPool pool=ConnectionPool.getConnectionPool();
	
	private final static String URL="http://localhost:8081/WebShop/messages";
	
//	public List<Message> getMsgs(){
//		Connection connection=null;
//		List<Message> msgs=new ArrayList<Message>();
//		try{
//            connection=pool.checkOut();
//            PreparedStatement preparedStatement=Util.prepareStatement(connection,GET_ALL,false);
//            ResultSet resultSet=preparedStatement.executeQuery();
//            while(resultSet.next()){
//               Message msg=new Message();
//               msg.setContent(resultSet.getString("content"));
//               msg.setId(resultSet.getLong("id"));
//               msg.setUnread(resultSet.getBoolean("unread"));
//               long userID=resultSet.getLong("user");
//               System.out.println(userID);
//               msg.setUser("-"); 
//                Connection connection2=null;
//                try{
//                    connection2=pool.checkOut();
//                    PreparedStatement preparedStatement2=Util.prepareStatement(connection,GET_USER_BY_ID,false,userID);
//                    ResultSet resultSet2=preparedStatement2.executeQuery();
//                    if(resultSet2.next()){
//                        msg.setUser(resultSet2.getString("firstname")+" "+resultSet2.getString("lastname"));
//                    }
//                    else return
//                     null;
//                }catch(Exception e){
//                    e.printStackTrace();
//                    pool.checkIn(connection2);
//                }
//                msgs.add(msg);
//            }
// 
//        }catch(Exception e){
//            e.printStackTrace();
//            pool.checkIn(connection);
//        }
//		System.out.println(msgs.size());
//		return msgs;
//	}
	  public List<Message> getMsgs(){
		  List<Message> msgs=new ArrayList<Message>();
		  try {
		  URL obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				System.out.println(response);
				Gson gson=new Gson();
				msgs=Arrays.asList(gson.fromJson(response.toString(), Message[].class));
			} else System.out.println(responseCode);}catch(Exception e) {
				e.printStackTrace();
			}
			return msgs;
	  }
	  public List<Message> getMsgs(String key){
		  List<Message> msgs=new ArrayList<Message>();
		  try {
		  URL obj = new URL(URL+"/filtered?key="+key);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				System.out.println(response);
				Gson gson=new Gson();
				msgs=Arrays.asList(gson.fromJson(response.toString(), Message[].class));
			} else System.out.println(responseCode);}catch(Exception e) {
				e.printStackTrace();
			}
			return msgs;
	  }

}
