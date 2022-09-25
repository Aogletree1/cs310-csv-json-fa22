package edu.jsu.mcis.cs310;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
        
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and JSON.simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            // Initialize CSV Reader and Iterator
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            /* INSERT YOUR CODE HERE */
            JSONObject Json = new JSONObject();   //Json object created to convert values.
            JSONArray Column = new JSONArray();   //Column created for array.
            JSONArray Row = new JSONArray();      //Row created for array.
            JSONArray Data = new JSONArray();     //Data created for array.
            JSONArray holder; //This array simply holds the data being converted.
            String[] Info = iterator.next();
            
              for(int i = 0; i < Info.length; i++) { //for statement to iterate through the columns
                Column.add(Info[i]);                 //array until all data is read.
                }
               while(iterator.hasNext()) {
                holder = new JSONArray();
                Info = iterator.next();
                Row.add(Info[0]);
                    for(int i = 1; i < Info.length; i++) {
                        int stringHolder = Integer.parseInt(Info[i]);//This parses the data to Json.
                        holder.add(stringHolder);
                        }
                Data.add(holder);
                }
            Json.put("rowHeaders", Row);//The following puts the data where it should go.
            Json.put("colHeaders", Column);
            Json.put("data", Data);
            results = JSONValue.toJSONString(Json);
            
        }
        catch(Exception e) { e.printStackTrace(); }
        
        // Return JSON String
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            
            // Initialize JSON Parser and CSV Writer
            
            JSONParser parser = new JSONParser();
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            
            /* INSERT YOUR CODE HERE */
            
            JSONObject Json = (JSONObject)parser.parse(jsonString);//More or less the same as the previous method,
            JSONArray Column = (JSONArray)Json.get("colHeaders"); //but to convert json to csv.
            JSONArray Row = (JSONArray)Json.get("rowHeaders");
            JSONArray Data = (JSONArray)Json.get("data");
            JSONArray Holder;
            String [] Info = new String[Column.size()];
            
            for(int i = 0; i < Column.size(); i ++){//iterates through columns.
                Info[i] = (String) Column.get(i);
                }
            csvWriter.writeNext(Info);
            for (int i = 0; i < Data.size(); i ++){//writes json to csv.
                Holder = (JSONArray) Data.get(i);
                Info = new String[Holder.size() + 1];
                Info[0] = (String) Row.get(i);
                for(int e = 0; e < Holder.size(); e++) {
                    Info[e + 1] = Long.toString((long)Holder.get(e));
                    }
                csvWriter.writeNext(Info);
            }
            results = writer.toString();
            
            
        }
        catch(Exception e) { e.printStackTrace(); }
        
        // Return CSV String
        
        return results.trim();
        
    }
	
}