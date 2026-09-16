package org.example;

// Crucial web imports needed to fix the errors
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/records")
public class display extends HttpServlet {

    @Override
    // 1. Fixed error: added 'throws IOException' because res.getWriter() demands it
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // 2. Fixed error: added the missing semicolon at the end of the line
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connection details
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/t4",
                    "root",
                    "navyasri"
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from emp");

            res.setContentType("text/html");
            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<body>");
            out.println("<table border=1>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>name</th>");
            out.println("<th>marks</th>");
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getInt("marks") + "</td>");
                out.println("</tr>");
            }

            // 3. Fixed error: completed the missing '>' brackets for closing tags
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

            // Clean up database connections
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
