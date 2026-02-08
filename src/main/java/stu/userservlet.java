package stu;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Userservlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.getWriter().println("<h2>UserServlet is working (GET)</h2>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String branch = request.getParameter("branch");
        int year = Integer.parseInt(request.getParameter("year"));

        try {
            // ✅ get DB url from Render env variable
            String dbUrl = System.getenv("DATABASE_URL");

            Connection con = DriverManager.getConnection(dbUrl);

            String sql = "INSERT INTO students(name,email,branch,year) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, branch);
            ps.setInt(4, year);

            ps.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Student Registered Successfully</h2>");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
