package mod.notification;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PublishNotificationServlet_v1 extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private Producer producer = null;

	@Override
	public void destroy()
	{

	}

	@Override
	public void init() throws ServletException
	{

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		AsyncContext async = request.startAsync(request, response);
		producer = new Producer(async, request.getParameter("type"), request.getParameter("lastUpdateTime"));
		async.start(producer);
		System.out.println();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		// TODO Auto-generated method stub
	}


	private class Producer implements Runnable
	{

		private String type;
		private AsyncContext context;
		private String lastUpdateTime;

		public Producer(AsyncContext context, String type, String lastUpdateTime)
		{
			this.context = context;
			this.type = type;
			if (lastUpdateTime.equals("undefined"))
			{
				this.lastUpdateTime = "0";
			}
			else
			{
				this.lastUpdateTime = lastUpdateTime;
			}

		}

		public void run()
		{
			try
			{
				do
				{

				} while (!Message.messages.containsKey(type));

				/*do
				{

				} while (Message.messages.containsKey(type));
				*/
				PrintWriter writer = context.getResponse().getWriter();

				boolean newMsg = true;
				do
				{
					writer = context.getResponse().getWriter();
					ConcurrentHashMap<String, String> map = Message_v1.messages.get(type);
					Iterator<String> iter = map.keySet().iterator();

					writer.print("{\"response\": {\"messages\": [");
					String key;
					boolean firstTime = true;
					while (iter.hasNext())
					{
						key = iter.next();
						if (Long.valueOf(key).longValue() > Long.valueOf(lastUpdateTime))
						{
							if (!firstTime)
							{
								writer.print(",");								
							}
							writer.print("{\"message\":\"");
							writer.print(map.get(key));
							writer.print("\"}");
							firstTime = false;
							newMsg = false;
						}

					}
				} while (newMsg);

				writer.print("],\"timestamp\": \"" + new Date().getTime() + "\"}}");
				writer.flush();
				writer.close();
				context.complete();

			}
			catch (Exception e)
			{
				// just eat it, eat it
			}

		}

		private String entryToHtml(String entry)
		{
			StringBuilder html = new StringBuilder("<h3>");
			html.append(entry);
			html.append("</h3>");
			return html.toString();
		}
	}

}
