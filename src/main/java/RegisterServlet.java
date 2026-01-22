package com.studentapp;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Form data get karna
        String name = request.getParameter("name");
        String rollNo = request.getParameter("roll_no");
        int marks = Integer.parseInt(request.getParameter("marks"));
        
        try {
            // 1. MySQL driver load karo
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Database connection
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb", 
                "root", 
                "password"  // Apna password dalo
            );
            
            // 3. SQL query
            String sql = "INSERT INTO students (name, roll_no, marks) VALUES (?, ?, ?)";
            
            // 4. Prepared statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, rollNo);
            pstmt.setInt(3, marks);
            
            // 5. Execute query
            int result = pstmt.executeUpdate();
            
            // 6. Result show karna
            out.println("<html><body style='text-align:center; padding:50px;'>");
            
            if(result > 0) {
                out.println("<h2 style='color:green;'>✅ Registration Successful!</h2>");
                out.println("<p>Name: " + name + "</p>");
                out.println("<p>Roll No: " + rollNo + "</p>");
                out.println("<p>Marks: " + marks + "</p>");
            } else {
                out.println("<h2 style='color:red;'>❌ Registration Failed</h2>");
            }
            
            // Buttons
            out.println("<br><br>");
            out.println("<button style='padding:10px 20px; margin:5px; background:green; color:white; border:none;' onclick=\"location.href='register.html'\">➕ Add Another</button>");
            out.println("<button style='padding:10px 20px; margin:5px; background:blue; color:white; border:none;' onclick=\"location.href='view-students.html'\">👀 View All</button>");
            
            out.println("</body></html>");
            
            // 7. Connection close
            con.close();
            
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}