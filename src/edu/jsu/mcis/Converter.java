package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            
            JSONArray records = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            String[] record;
         
            
            record = iterator.next();
            JSONArray jsonCol = new JSONArray();
            for (int i = 0; i < record.length; i++){
                jsonCol.add(record[i]);
            }
            
            jsonObject.put("colHeaders", jsonCol);
            records.add(jsonObject);
            JSONArray jsonRowHeaders = new JSONArray();
            JSONArray jsonData = new JSONArray();
            
            while(iterator.hasNext()){
                record = iterator.next();
                jsonRowHeaders.add(record[0]);
                JSONArray rowData = new JSONArray();
                
                for(int i = 1; i < record.length; i++){
                    int data = Integer.parseInt(record[i]);
                    rowData.add(data);
                }
                jsonData.add(rowData);
            }
            jsonObject.put("rowHeaders", jsonRowHeaders);
            jsonObject.put("data", jsonData);
            //System.out.println(jsonObject);
            results = jsonObject.toJSONString();
            
        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            
            JSONArray jsonArray = new JSONArray();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            JSONArray colHeaders = (JSONArray)jsonObject.get("colHeaders");
            JSONArray rowHeaders = (JSONArray)jsonObject.get("rowHeaders");
            JSONArray data = (JSONArray)jsonObject.get("data");
            
            String[] colStringArray = new String[colHeaders.size()];
            String[] rowStringArray = new String[rowHeaders.size()];
            String[] dataStringArray = new String[data.size()];
            
            for (int i = 0; i < colHeaders.size(); i++){
                colStringArray[i] = colHeaders.get(i).toString();
            }
            csvWriter.writeNext(colStringArray);
            
            for (int i = 0; i < rowHeaders.size(); i++){
                rowStringArray[i] = rowHeaders.get(i).toString();
                dataStringArray[i] = data.get(i).toString();
            }
            
            for (int i = 0; i < dataStringArray.length; i++) {
                JSONArray dataValues = (JSONArray)parser.parse(dataStringArray[i]);
                String[] row = new String[dataValues.size() + 1];
                row[0] = rowStringArray[i];
                for(int j = 0; j < dataValues.size(); j++){
                    row[j+1] = dataValues.get(j).toString();
                }
                csvWriter.writeNext(row);
            }
            results = writer.toString();
            
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}