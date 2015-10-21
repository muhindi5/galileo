package model;

import model.utilities.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kelli
 */
public class ProcessPipe {

    private PreparedStatement statement;
    private Connection connection;
    private String selectedHost;

    /* SQL statements */
    private final String saveProcess = "insert into processes(proc_id,description,win_title,"
            + "start_time,p_id) values (?,?,?,?,?)";
    private final String saveHost = "insert into hosts(system_name,operating_system,"
            + "registered_user,serial_number) values (?,?,?,?)";
    private final String getHosts = "select * from hosts";
    private final String recentProcesses = "select * from PROCESSES where P_ID = ?"
            + " order by start_time desc";
    private final String getHost = "select host_id from HOSTs WHERE SYSTEM_NAME = ?";
    private final String updateProcDetails = "update processes set description = ?"
            + ",win_title = ? where proc_id = ?";

    public ProcessPipe() {
        connection = DbConnection.getConnection();
    }

    public ProcessPipe(String host) {
        selectedHost = host;
        connection = DbConnection.getConnection();
    }

    public int getTargetHostId(String hostName) {
        int id = 0;
        try {
            connection = DbConnection.getConnection();
            statement = connection.prepareCall(getHost);
            statement.setString(1, hostName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt("host_id");
            }

            Logger.getLogger(ProcessPipe.class.getName()).log(Level.INFO, "Id: {0}", id);
        } catch (SQLException ex) {
            Logger.getLogger(ProcessPipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /* Check database for process with same id. If exists, update timestamp entry
     else create new process and insert into  database */
    public void saveProcess(Process p, String hostName) throws SQLException {

        statement = connection.prepareCall(saveProcess);
        statement.setString(1, p.getProcId());
        statement.setString(2, p.getDescription());
        statement.setString(3, p.getWinTitle());
        statement.setString(4, new java.sql.Timestamp(Long.parseLong(p.getStartTime())).toString());
        statement.setInt(5, 1);
        int done = statement.executeUpdate();
        Logger.getLogger(ProcessPipe.class.getName()).log(Level.INFO, "Saved process...{0} ", String.valueOf(done));
    }

    public void saveHost(Host host) throws SQLException {
        statement = connection.prepareCall(saveHost);
        statement.setString(1, host.getCsName());
        statement.setString(2, host.getOsName());
        statement.setString(3, host.getRegistredUser());
        statement.setString(4, host.getSerial());
        statement.executeUpdate();
//        Logger.getLogger(ProcessPipe.class.getName()).log(Level.INFO,"{0} ",String.valueOf(done));
    }

    /**
     * Get all hosts registered on the system
     *
     * @return list of all hosts on the system
     */
    public ArrayList<Host> getAllHosts() {
        ArrayList<Host> list = new ArrayList<>();
        try {
            statement = connection.prepareCall(getHosts);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Host host = new Host(
                        //rs.getString("host_id"),
                        rs.getString("system_name"),
                        rs.getString("registered_user"),
                        rs.getString("operating_system"),
                        rs.getString("serial_number"));
                list.add(host);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProcessPipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Process> getProcesses() {
        ArrayList<Process> list = new ArrayList<>();
        int hostId;
        try {
            PreparedStatement processListing = connection.prepareCall(recentProcesses);
            if (selectedHost.equals("HOST_1")) {
                hostId = 1; //default to first host in database when loading initially.
            } else {
                hostId = getTargetHostId(selectedHost);
            }
            //set the host target to get processes from
            processListing.setInt(1, hostId);
            ResultSet rs = processListing.executeQuery();
            while (rs.next()) {
                Logger.getLogger(ProcessPipe.class.getName()).log(Level.INFO,
                        "****Record****\n");
                Process currentProcess = new Process(
                        rs.getString("description"),
                        rs.getString("win_title"),
                        rs.getString("start_time"),
                        rs.getTimestamp("time_log"));
                    list.add(currentProcess);
                if (isRecentProcess(currentProcess)) {
                }
            }
            Logger.getLogger(ProcessPipe.class.getName()).log(Level.INFO, "Items in List: {0}", list.size());
        } catch (SQLException ex) {
            Logger.getLogger(ProcessPipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Check if the specified process was logged 5 minutes ago or later
     */
    private boolean isRecentProcess(Process currentProcess) {
        boolean isCurrent = false;
        Timestamp ts = currentProcess.getTimestamp();
        //create a timestamp at 5 minutes ago.
        Timestamp delayValue = new Timestamp(System.currentTimeMillis() - 5 * 60 * 1000);
        Logger.getLogger(ProcessPipe.class.getName()).log(Level.INFO, "Current Timestamp: {0}", ts.toString());
        Logger.getLogger(ProcessPipe.class.getName()).log(Level.INFO, "Timestamp 5 min ago: {0}", delayValue.toString());
        if (ts.after(delayValue)) {
            isCurrent = true;
        }
        return isCurrent;
    }

    public void updateProcess(Process process) {
        try {
            PreparedStatement ps = connection.prepareCall(updateProcDetails);
            ps.setString(1, process.getDescription());
            ps.setString(2, process.getWinTitle());
            ps.setInt(3, Integer.parseInt(process.getProcId()));
            ps.executeUpdate();
            Logger.getLogger(ProcessPipe.class.getName()).log(Level.INFO, "Update done");
        } catch (SQLException ex) {
            Logger.getLogger(ProcessPipe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
