/*
 *
 */
package model.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PsRuntime;

/**
 *
 * @author Kelli
 */
public class DbUtils {
    
    private final Connection connection;
    private PreparedStatement statement;
    private ResultSet resultset;
    private final String getAllProcesses = "select proc_id from processes";
    private final String getProcsRunTime = 
            "select description, timestampdiff(hour,start_time,time_log) as hr_duration from processes;";
    
    
    public DbUtils(){
        connection = DbConnection.getConnection();
    }
    
    /**
     * Get the Ids of all the procs in the database 
     * @return list of processes (Ids only)
     */
    public List<model.Process>  getExistingProcesses() {
        List<model.Process> processList = new ArrayList<>();
        try {
            statement = connection.prepareCall(getAllProcesses);
            resultset = statement.executeQuery();
            
            while(resultset.next()){
                String id = resultset.getString("proc_id");
                model.Process p = new model.Process();
                p.setProcId(id);
                processList.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  processList;
    }
    
    /**
     * Get application name and duration from all running processes in the table 
     * @return list of applications (with duration)
     */
    public List<PsRuntime> getProcsRuntime() {
        List<PsRuntime> list = new ArrayList<>();
        
        try {
            statement = connection.prepareCall(getProcsRunTime);
            resultset = statement.executeQuery();
            while(resultset.next()){
                PsRuntime current = new PsRuntime(resultset.getString("description"),
                resultset.getInt("hr_duration"));
                list.add(current);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  list;
    }
    
    public static void main(String[] args) {
        DbUtils u = new DbUtils();
        List<model.Process> procs = u.getExistingProcesses();
        for (model.Process proc : procs) {
            System.out.println("Id: "+proc.getProcId());
        }
    }

}
