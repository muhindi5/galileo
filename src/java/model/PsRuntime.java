
package model;

/**
 *  Create object with name of the process and duration of its runtime.
 * @author Kelli
 */
public class PsRuntime {
    private String processApplicationName;
    private int duration;

    public PsRuntime(){
        
    }
    
    public PsRuntime(String psAppName,int duration){
        processApplicationName = psAppName;
        this.duration = duration;
    }
    
    
    /**
     * @return the processApplicationName
     */
    public String getProcessApplicationName() {
        return processApplicationName;
    }

    /**
     * @param processApplicationName the processApplicationName to set
     */
    public void setProcessApplicationName(String processApplicationName) {
        this.processApplicationName = processApplicationName;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    @Override
    public String toString(){
        return "Application: "+processApplicationName+"\nDuration: "+duration;
    }
    
}
