package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Host;
import model.ProcessPipe;
import org.json.JSONArray;

/**
 * Get List all hosts and recent processes for the selected host. Filtered by timestamp and grouped by start time.
 * @author Kelli
 */
public class PSView extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //select the first device by default
        String selectedHost = request.getParameter("id");
        if (selectedHost == null) {
            selectedHost = "HOST_1"; // a default host name.
        }
        
        ProcessPipe pipe = new ProcessPipe(selectedHost);
        ArrayList<Host> hostsList = pipe.getAllHosts();
        request.setAttribute("hosts", hostsList);
        Logger.getLogger(PSView.class.getName()).log(Level.INFO, "Number of Hosts: {0}",hostsList.size());
        
        //get chart data i.e JSON string
        JSONArray chartData = new model.ChartGen().createChartData();
        request.setAttribute("chart_data", chartData);
        
        //get processes for the selected host...default is 1;
        ArrayList<model.Process> processesList =pipe.getProcesses();
        request.setAttribute("pList", processesList);
        request.getRequestDispatcher("/ProcessViewer.jsp").forward(request, response);
        
    }
    
     // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
