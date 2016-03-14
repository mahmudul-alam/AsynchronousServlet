package mod.notification;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NotificationServlet
 */
public class PostNotificationServlet_v1 extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PostNotificationServlet_v1()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub

		if (Message_v1.messages.containsKey(request.getParameter("type")))
		{
			Message_v1.messages.get(request.getParameter("type")).put(request.getParameter("lastUpdateTime"),
					request.getParameter("message"));
		}
		else
		{
			ConcurrentHashMap map = new ConcurrentHashMap<String, String>();

			map.put(request.getParameter("lastUpdateTime"), request.getParameter("message"));
			Message_v1.messages.put(request.getParameter("type"), map);
		}

		request.getRequestDispatcher("/mod/createNotification").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		doGet(request, response);
		// TODO Auto-generated method stub
	}

}
