package main;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class TomcatTest extends HttpServlet
{
   static final long serialVersionUID = 1L;

   @Override
   public void doGet( HttpServletRequest requ, HttpServletResponse resp )
   throws ServletException, IOException
   {
      resp.setContentType( "text/html" );
      PrintWriter out = resp.getWriter();
      out.println( "<html>" );
      out.println( "<h3> Hallo, mein erstes Servlet meldet sich </h3>" );
      out.println( "<a href='/MeineWebAppRoot/'>zur&uuml;ck</a>" );
      out.println( "</html>" );
      out.close();
   }
}
