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

					URL obj = new URL(URL + "specificAttributes/" + idString + "?username=" + admin.getUsername()
							+ "&password=" + admin.getPassword());
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					con.setRequestMethod("DELETE");
					int responseCode = con.getResponseCode();
					System.out.println("GET Response Code :: " + responseCode);
					category(request.getParameter("category"), admin, request, response);
					return;
				} else if ("updateCategory".equals(action)) {
					String idString = request.getParameter("category");
					String name = request.getParameter("name");
					CategoryDTO dto = new CategoryDTO();
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
					} catch (Exception e) {
						e.printStackTrace();
					}
					category(idString, admin, request, response);
					return;
				} else if ("addAttribute".equals(action)) {

					String idString = request.getParameter("category");
					String name = request.getParameter("name");
					System.out.println("adding spec attr category=" + idString + " name=" + name);
					SpecificAttributeDTO dto = new SpecificAttributeDTO();
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
					} catch (Exception e) {
						e.printStackTrace();
					}
					category(idString, admin, request, response);
					return;
				} else if ("user".equals(action)) {
					String id = request.getParameter("user");
					user(id, admin, request, response);
					return;
				} else if ("addUser".equals(action)) {
					UserDTO temp = new UserDTO();
					temp.setCity(request.getParameter("city"));
					temp.setEmail(request.getParameter("email"));
					temp.setFirstname(request.getParameter("firstname"));
					temp.setLastname(request.getParameter("lastname"));
					temp.setPassword(request.getParameter("password"));
					temp.setUsername(request.getParameter("username"));
				
					System.out.println(temp);
					if (temp.getCity() == null || temp.getUsername() == null || temp.getPassword() == null
							|| temp.getFirstname() == null || temp.getLastname() == null || temp.getEmail() == null
							|| "".equals(temp.getCity()) || "".equals(temp.getEmail()) || "".equals(temp.getUsername())
							|| "".equals(temp.getPassword()) || "".equals(temp.getFirstname())
							|| "".equals(temp.getLastname())) {
						System.out.println("\"Popunite sva polja!\"");
						request.setAttribute("note", "Popunite sva polja!");
					} else if (temp.getUsername().length() < 5)
						request.setAttribute("note", "Korisnicko ime mora imati minimalno 5 karaktera");
					else if (temp.getPassword().length() < 8)
						request.setAttribute("note", "Lozinka mora imati minimalno 8 karaktera!");
					else if (temp.getPassword().contains("$") == false && temp.getPassword().contains("#") == false
							&& temp.getPassword().contains("%") == false && temp.getPassword().contains("&") == false
							&& temp.getPassword().contains("_") == false)
						request.setAttribute("note",
								"Lozinka mora imati minimalno jedan specijalni karakter (#,$;%;&,_)");
					else if (temp.getEmail().contains("@") == false)
						request.setAttribute("note", "Unesite validan email!");
					else
						try {

							URL obj = new URL(URL + "users/register");
							HttpURLConnection con = (HttpURLConnection) obj.openConnection();
							con.setRequestMethod("POST");
							con.setRequestProperty("Content-Type", "application/json");
							con.setDoOutput(true);
							OutputStream dos = con.getOutputStream();
							Gson gson = new Gson();
							System.out.println(gson.toJson(temp));
							dos.write(gson.toJson(temp).getBytes());
							dos.flush();
							dos.close();
							int responseCode = con.getResponseCode();
							if (responseCode != HttpURLConnection.HTTP_OK) { // success
								request.setAttribute("note", "Neuspjesno kreiranje!");
							} else
								request.setAttribute("note", "Novi korisnik je uspjesno kreiran!");
							System.out.println("GET Response Code :: " + responseCode);
						} catch (Exception e) {
							e.printStackTrace();
						}
					users(admin, request);
					request.getRequestDispatcher("WEB-INF/admin-users.jsp").forward(request, response);
					return;

				} else if ("deleteUser".equals(action)) {
					String id = request.getParameter("user");

					URL obj = new URL(URL + "users/" + id + "?username=" + admin.getUsername() + "&password="
							+ admin.getPassword());
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					con.setRequestMethod("DELETE");
					int responseCode = con.getResponseCode();
					System.out.println("GET Response Code :: " + responseCode);

					users(admin, request);
					request.getRequestDispatcher("WEB-INF/admin-users.jsp").forward(request, response);
					return;

				} else if ("updateUser".equals(action)) {
					String id = request.getParameter("user");

					UserDTO temp = new UserDTO();
					temp.setCity(request.getParameter("city"));
					temp.setEmail(request.getParameter("email"));
					temp.setFirstname(request.getParameter("firstname"));
					temp.setLastname(request.getParameter("lastname"));
					temp.setPassword(request.getParameter("password"));
					temp.setId(Long.parseLong(id));
					System.out.println(temp);
					if (temp.getCity() == null || temp.getPassword() == null
							|| temp.getFirstname() == null || temp.getLastname() == null || temp.getEmail() == null
							|| "".equals(temp.getCity()) || "".equals(temp.getEmail())
							|| "".equals(temp.getPassword()) || "".equals(temp.getFirstname())
							|| "".equals(temp.getLastname())) {
						System.out.println("\"Popunite sva polja!\"");
						request.setAttribute("note", "Popunite sva polja!");
					} 
					else if (temp.getPassword().length() < 8)
						request.setAttribute("note", "Lozinka mora imati minimalno 8 karaktera!");
					else if (temp.getPassword().contains("$") == false && temp.getPassword().contains("#") == false
							&& temp.getPassword().contains("%") == false && temp.getPassword().contains("&") == false
							&& temp.getPassword().contains("_") == false)
						request.setAttribute("note",
								"Lozinka mora imati minimalno jedan specijalni karakter (#,$;%;&,_)");
					else if (temp.getEmail().contains("@") == false)
						request.setAttribute("note", "Unesite validan email!");
					else {
						try {

							URL obj = new URL(URL + "users");
							HttpURLConnection con = (HttpURLConnection) obj.openConnection();
							con.setRequestMethod("PUT");
							con.setRequestProperty("Content-Type", "application/json");
							con.setDoOutput(true);
							OutputStream dos = con.getOutputStream();
							Gson gson = new Gson();
							System.out.println(gson.toJson(temp));
							dos.write(gson.toJson(temp).getBytes());
							dos.flush();
							dos.close();
							int responseCode = con.getResponseCode();
							if (responseCode != HttpURLConnection.HTTP_OK) { // success
								request.setAttribute("note", "Neuspjesno azuriranje!");
							} else
								request.setAttribute("note", "Informacije azurirane!");
							System.out.println("GET Response Code :: " + responseCode);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (request.getAttribute("note") == null || request.getAttribute("note").equals(""))
						request.setAttribute("note", "-");
					user(id, admin, request, response);
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
		if (request.getAttribute("note") == null || request.getAttribute("note").equals(""))
			request.setAttribute("note", "-");
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

	private void user(String id, User admin, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		users(admin, request);
		System.out.println(id);
		List<UserDTO> temp = (List<UserDTO>) request.getAttribute("users");
		for (UserDTO c : temp) {
			if (Long.toString(c.getId()).equals(id)) {
				request.setAttribute("appUser", c);
				request.getRequestDispatcher("WEB-INF/user.jsp").forward(request, response);
				return;
			}
		}
		request.getRequestDispatcher("WEB-INF/admin-users.jsp").forward(request, response);
	}

}
