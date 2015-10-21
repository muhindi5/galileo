
package model;

/**
 *
 * @author Kelli
 */
public class Host {

    private String csName;
    private String registredUser;
    private String osName;
    private String serial;

    public Host(String csName,String user,String osName,String serial){
        this.csName = csName;
        this.registredUser = user;
        this.osName = osName;
        this.serial =serial;
    }
    /**
     * @return the csName
     */
    public String getCsName() {
        return csName;
    }

    /**
     * @param csName the csName to set
     */
    public void setCsName(String csName) {
        this.csName = csName;
    }

    /**
     * @return the registredUser
     */
    public String getRegistredUser() {
        return registredUser;
    }

    /**
     * @param registredUser the registredUser to set
     */
    public void setRegistredUser(String registredUser) {
        this.registredUser = registredUser;
    }

    /**
     * @return the osName
     */
    public String getOsName() {
        return osName;
    }

    /**
     * @param osName the osName to set
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }

    /**
     * @return the serial
     */
    public String getSerial() {
        return serial;
    }

    /**
     * @param serial the serial to set
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    public String toString(){
        return "[system name]: "+getCsName()+"[os]: "+getOsName()+"[user]: "+getRegistredUser()+"[serial]: "+getSerial();
    }

}
