package etf.ip.controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import etf.ip.dao.AdminDAO;
import etf.ip.model.CategoryDTO;
import etf.ip.model.SpecificAttributeDTO;
import etf.ip.model.User;
import etf.ip.model.UserDTO;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final AdminDAO adminDAO = new AdminDAO();
	private final static String URL = "http://localhost:8081/WebShop/";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("hit");
		String action = request.getParameter("action");
		if (action != null && "".equals(action) == false) {
			System.out.println(action);
			if ("login".equals(action)) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");

				User temp = adminDAO.login(username, password);
				if (temp != null) {
					request.getSession().setAttribute("admin", temp);
					statistics(temp, request);
					request.getRequestDispatcher("WEB-INF/admin-statistics.jsp").forward(request, response);
					return;
				} else {
					request.setAttribute("invalid", true);
					request.getRequestDispatcher("WEB-INF/admin-login.jsp").forward(request, response);
					return;
				}
			} else if (request.getSession().getAttribute("admin") != null) {
				User admin = (User) request.getSession().getAttribute("admin");
				if ("statistics".equals(action)) {

					statistics(admin, request);
					request.getRequestDispatcher("WEB-INF/admin-statistics.jsp").forward(request, response);
					return;

				} else if ("categories".equals(action)) {

					categories(admin, request);
					request.getRequestDispatcher("WEB-INF/admin-categories.jsp").forward(request, response);
					return;

				} else if ("users".equals(action)) {
					users(admin, request);
					request.getRequestDispatcher("WEB-INF/admin-users.jsp").forward(request, response);
					return;
				} else if ("addCategory".equals(action)) {
					System.out.println("adding");
					CategoryDTO cat = new CategoryDTO();
					cat.setName(request.getParameter("category"));

					try {
						URL obj = new URL(URL + "categories?username=" + admin.getUsername() + "&password="
								+ admin.getPassword());
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						con.setRequestMethod("POST");
						con.setRequestProperty("Content-Type", "application/json");
						con.setDoOutput(true);
						OutputStream dos = con.getOutputStream();
						Gson gson = new Gson();
						System.out.println(gson.toJson(cat));
						dos.write(gson.toJson(cat).getBytes());
						dos.flush();
						dos.close();
						int responseCode = con.getResponseCode();
						System.out.println("GET Response Code :: " + responseCode);
						if (responseCode == HttpURLConnection.HTTP_OK) { // success
							System.out.println("success");
						}
					} catch (Exception e) {
						System.out.println("exception");
						e.printStackTrace();
					}

					System.out.println("getting categories");
					categories(admin, request);
					request.getRequestDispatcher("WEB-INF/admin-categories.jsp").forward(request, response);
					return;
				} else if ("category".equals(action)) {

					String id = request.getParameter("category");
					category(id, admin, request, response);
					return;
				} else if ("deleteAttribute".equals(action)) {
					String idString = request.getParameter("attribute");

					URL obj = new URL(URL + "specificAttributes/" + idString + "?username=" + admin.getUsername() + "&password="
							+ admin.getPassword());
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					con.setRequestMethod("DELETE");
					int responseCode = con.getResponseCode();
					System.out.println("GET Response Code :: " + responseCode);
					category(request.getParameter("category"),admin, request,response);
					return;
				}else if("updateCategory".equals(action)) {
					String idString=request.getParameter("category");
					String name=request.getParameter("name");
					CategoryDTO dto=new CategoryDTO();
					try {
						dto.setId(Long.parseLong(idString));
						dto.setName(name);
						
						URL obj = new URL(URL + "categories?username=" + admin.getUsername() + "&password="
								+ admin.getPassword());
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						con.setRequestMethod("PUT");
						con.setRequestProperty("Content-Type", "application/json");
						con.setDoOutput(true);
						OutputStream dos = con.getOutputStream();
						Gson gson = new Gson();
						System.out.println(gson.toJson(dto));
						dos.write(gson.toJson(dto).getBytes());
						dos.flush();
						dos.close();
						int responseCode = con.getResponseCode();
						System.out.println("GET Response Code :: " + responseCode);
					}catch(Exception e) {
						e.printStackTrace();
					}
					category(idString,admin, request,response);
					return;
				}
				else if("addAttribute".equals(action)) {
					
					String idString=request.getParameter("category");
					String name=request.getParameter("name");
					System.out.println("adding spec attr category="+idString+" name="+name);
					SpecificAttributeDTO dto=new SpecificAttributeDTO();
					try {
						dto.setCategory(Long.parseLong(idString));
						dto.setName(name);
						
						URL obj = new URL(URL + "specificAttributes?username=" + admin.getUsername() + "&password="
								+ admin.getPassword());
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						con.setRequestMethod("POST");
						con.setRequestProperty("Content-Type", "application/json");
						con.setDoOutput(true);
						OutputStream dos = con.getOutputStream();
						Gson gson = new Gson();
						System.out.println(gson.toJson(dto));
						dos.write(gson.toJson(dto).getBytes());
						dos.flush();
						dos.close();
						int responseCode = con.getResponseCode();
						System.out.println("GET Response Code :: " + responseCode);
					}catch(Exception e) {
						e.printStackTrace();
					}
					category(idString,admin, request,response);
					return;
				}
			}
		}
		request.setAttribute("invalid", false);
		request.getRequestDispatcher("WEB-INF/admin-login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void statistics(User admin, HttpServletRequest request) {
		try {
			URL obj = new URL(URL + "statistics?username=" + admin.getUsername() + "&password=" + admin.getPassword());
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer buffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					buffer.append(inputLine);
				}
				in.close();

				System.out.println(buffer.toString());
				Gson gson = new Gson();
				List<String> list = Arrays.asList(gson.fromJson(buffer.toString(), String[].class));
				Collections.reverse(list);
				request.setAttribute("statistics", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("statistics", new ArrayList<String>());
		}

	}

	private void categories(User admin, HttpServletRequest request) {
		try {
			URL obj = new URL(URL + "categories?username=" + admin.getUsername() + "&password=" + admin.getPassword());
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer buffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					buffer.append(inputLine);
				}
				in.close();

				System.out.println(buffer.toString());
				Gson gson = new Gson();
				List<CategoryDTO> list = Arrays.asList(gson.fromJson(buffer.toString(), CategoryDTO[].class));

				request.setAttribute("categories", list);
			} else
				request.setAttribute("categories", new ArrayList<String>());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("categories", new ArrayList<String>());
		}
	}

	private void users(User admin, HttpServletRequest request) {
		try {
			URL obj = new URL(URL + "users?username=" + admin.getUsername() + "&password=" + admin.getPassword());
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer buffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					buffer.append(inputLine);
				}
				in.close();

				System.out.println(buffer.toString());
				Gson gson = new Gson();
				List<UserDTO> list = Arrays.asList(gson.fromJson(buffer.toString(), UserDTO[].class));

				request.setAttribute("users", list);
			} else
				request.setAttribute("users", new ArrayList<String>());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("users", new ArrayList<String>());
		}
	}

	private void category(String id, User admin, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		categories(admin, request);
		System.out.println(id);
		List<CategoryDTO> temp = (List<CategoryDTO>) request.getAttribute("categories");
		for (CategoryDTO c : temp) {
			if (Long.toString(c.getId()).equals(id)) {
				request.setAttribute("category", c);
				request.getRequestDispatcher("WEB-INF/category.jsp").forward(request, response);
				return;
			}
		}
		request.getRequestDispatcher("WEB-INF/admin-categories.jsp").forward(request, response);
	}

}
