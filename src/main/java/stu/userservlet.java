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

public class userservlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String branch = request.getParameter("branch");
        int year = Integer.parseInt(request.getParameter("year"));

        try (Connection con = DriverManager.getConnection(
                System.getenv("postgresql://sample_4ly3_user:9fuwvu9p4v9QewqblhatanvQkQGAE6B9@dpg-d63nej94tr6s73a04jkg-a.singapore-postgres.render.com/sample_4ly3"))) {

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

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
