package model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Process {

    private Integer pId;
    private String description;
    private String winTitle;
    private String startTime;
    private Timestamp timestamp;
    private String procId;

    public Process() {
    }

    /**
     * Constructor to create a process from received client PC JSON data
     *
     * @param pId Id of the process
     * @param description Name of the application using the process.
     * @param winTitle Title of the file opened by the process.
     * @param startTime Time the process was loaded into memory
     * @param procId
     * @param timestamp
     */
    public Process(int pId, String description, String winTitle, String startTime, String procId) {
        this.pId = pId;
        this.procId = procId;
        this.description = description;
        this.winTitle = winTitle;
        this.startTime = extractDateFromString(startTime);
    }

    /*Constructor to create process data from retrieved database record
     for dispatch to client display browser
     */
    public Process(String description, String winTitle, String startTime, Timestamp timestamp) {
        this.description = description;
        this.winTitle = winTitle;
        this.startTime = startTime;
//                calculateRunningTime(new Long(startTime));
        this.timestamp = timestamp;
    }

    public Process(Integer pId) {
        this.pId = pId;
    }

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWinTitle() {
        return winTitle;
    }

    public void setWinTitle(String winTitle) {
        this.winTitle = winTitle;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pId != null ? pId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Process)) {
            return false;
        }
        Process other = (Process) object;
        if ((this.pId == null && other.pId != null) || (this.pId != null && !this.pId.equals(other.pId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "peek.Processes[ pId=" + pId + " ]";
    }

    /* utility methods */
    private String extractDateFromString(String sDate) {
        String[] tokens = sDate.split("\\D+");
        System.out.println("Seconds extracted: " + tokens[1]);
        return tokens[1];
    }

    private String calculateRunningTime(long seconds) {
        Date d = new Date(seconds);
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        String dsf = df.format(d);
        return dsf;
    }

    /*
     Get the name of the file opened by the process by filtering the 'MainWindowTitle'
     string 
     */
    private String getFileName(String mainWindowTitle) {
        String fileName = "";
        System.out.println("Match found...");
        String[] strings = mainWindowTitle.split("\\ - ");
        if (strings.length > 1) {
            return strings[1];
        } else {
            return strings[0];
        }
    }

    public static void main(String[] args) {
//        Process s = new Process();
//        s.setDescription("/Date(1442427851955)/");
//        String p=s.extractDateFromString(s.getDescription());
//        System.out.println("p="+p);
//        System.out.println(new Process().calculateRunningTime(new Long("1442427851955")));

    }

    /**
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the procId
     */
    public String getProcId() {
        return procId;
    }

    /**
     * @param procId the procId to set
     */
    public void setProcId(String procId) {
        this.procId = procId;
    }

}
