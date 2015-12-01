package com.spark;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mortbay.util.ajax.JSON;
import org.json.JSONObject;


/**
 * Servlet implementation class ProcessTweets
 */
@WebServlet("/ProcessTweets")
public class ProcessTweets extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ProcessTweets() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String query = request.getParameter("query");//"Time analysis";"hashtaganalysis";
		String result;
		result= sparkProcess.main(query);
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers","Content-Type");
		response.setHeader("Access-Control-Max-Age","86400");
		if(result!= null ){
			response.getWriter().write(result.replaceAll("\"", "\\\""));
		}
		else
		{
			response.getWriter().write("No data found");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}
