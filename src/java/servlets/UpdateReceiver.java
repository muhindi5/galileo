package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import model.Process;
import model.ProcessPipe;
import model.utilities.DbUtils;

/**
 * 1. Receive request with JSON data (running processes)
 * 2. Persist each processes into the database.
 *
 * @author Kelli
 */
public class UpdateReceiver extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Process> processes;
        String cid = request.getParameter("id");
        String data = request.getParameter("data");
        String timer = Calendar.getInstance().getTime().toString();
        Logger.getLogger(UpdateReceiver.class.getName()).log(Level.INFO, timer + ""
                + ": machineId= {0}, data= {1}", new Object[]{cid, data});
        ProcessPipe piper = new ProcessPipe(cid);
        if (data.equals("")) {
            Logger.getLogger(UpdateReceiver.class.getName()).log(Level.SEVERE, "Data is empty");
            return;

        }

        //get list of already logged processes
        processes = new DbUtils().getExistingProcesses();
        Logger.getLogger(UpdateReceiver.class.getName()).log(Level.INFO,
                "Number of existing processes: {0}", processes.size());

        try {
            JSONArray jSONArray = new JSONArray(data);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject currentObj = jSONArray.getJSONObject(i);
                Logger.getLogger(UpdateReceiver.class.getName()).log(Level.INFO,
                        "JSON object {0}: {1}", new Object[]{i, currentObj.toString()});

                //create process from each json object and persist. 
                int id = currentObj.getInt("Id");
                String description = currentObj.getString("Description");
                String windowTitle = currentObj.getString("MainWindowTitle");
                String startTime = currentObj.getString("StartTime");

                Process process = new Process(id, description, windowTitle, startTime, String.valueOf(id));
                Logger.getLogger(UpdateReceiver.class.getName()).log(Level.INFO,
                        "Process created");

                //check if this process already exists 
                if (processes.size() > 0) {
                    for (Process currentProc : processes) {
                        if (currentProc.getProcId().equals(process.getProcId())) {
                            Logger.getLogger(UpdateReceiver.class.getName()).log(Level.INFO,
                                    "Incoming process: {1}\nStored Process: {0}",
                                    new Object[]{currentProc.getProcId(),process.getProcId()});
                            piper.updateProcess(process);
                        }
                    }
                } else {
                    piper.saveProcess(process, cid);

                }
            }
        } catch (JSONException | SQLException ex) {
//            Logger.getLogger(UpdateReceiver.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
