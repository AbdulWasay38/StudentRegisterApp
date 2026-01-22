package com.studentapp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/ViewStudentsServlet")
public class ViewStudentServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>All Students</title>");
        out.println("<style>");
        out.println("body {font-family: Arial; padding: 20px; background: #f0f0f0;}");
        out.println("h2 {text-align: center; color: #333;}");
        out.println("table {width: 80%; margin: 20px auto; border-collapse: collapse; background: white;}");
        out.println("th, td {border: 1px solid #ddd; padding: 12px; text-align: left;}");
        out.println("th {background: green; color: white;}");
        out.println(".btn {padding: 5px 10px; background: blue; color: white; text-decoration: none; border-radius: 3px;}");
        out.println(".btn-red {background: red;}");
        out.println(".btn-green {background: green;}");
        out.println(".center {text-align: center;}");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<h2>🎓 All Students List</h2>");
        out.println("<div class='center'>");
        out.println("<a href='register.html' class='btn btn-green'>➕ Add New Student</a>");
        out.println("</div>");
        
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Name</th><th>Roll No</th><th>Marks</th><th>Actions</th></tr>");
        
        try {
            // 1. Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb", "root", "password"
            );
            
            // 2. Query run karo
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            
            // 3. Data show karo
            while(rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("roll_no") + "</td>");
                out.println("<td>" + rs.getInt("marks") + "</td>");
                out.println("<td>");
                out.println("<a href='edit-student.html?id=" + rs.getInt("id") + "' class='btn'>✏️ Edit</a> ");
                out.println("<a href='DeleteStudentServlet?id=" + rs.getInt("id") + "' class='btn btn-red' onclick='return confirm(\"Delete this student?\")'>🗑️ Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            
            con.close();
            
        } catch (Exception e) {
            out.println("<tr><td colspan='5' style='color:red; text-align:center;'>Error: " + e.getMessage() + "</td></tr>");
        }
        
        out.println("</table>");
        out.println("</body></html>");
    }
}