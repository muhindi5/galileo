
package model;

import java.util.ArrayList;
import java.util.List;
import model.utilities.DbUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *  Interrogate the processes table and get processes runtime, create JSON 
 *  data from results for chart generation.
 * 
 * @author Kelli
 */

public class ChartGen {
    

    
    /**
     * Fetch list of applications+runtime 
     * @return JSON formatted array from data
     */
    public JSONArray  createChartData(){
        JSONArray psLists = new JSONArray();
        
        List<PsRuntime> psRuns = new ArrayList<>();
        psRuns = new DbUtils().getProcsRuntime();
        
        //create JSON array from list
        for (PsRuntime psRun : psRuns) {
            JSONObject obj = new JSONObject(psRun);
            psLists.put(obj);
        }
        return psLists;
    }
    
    public static void main(String[] args) {
        System.out.println("JSON data: "+new ChartGen().createChartData());
    }
}
