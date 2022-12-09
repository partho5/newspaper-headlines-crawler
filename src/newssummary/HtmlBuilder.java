/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newssummary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author parth
 */
public class HtmlBuilder {
    private String htmlString = "";
    private List existingUrlList = new ArrayList();
    private List existingHeadlineList = new ArrayList();
    
    HtmlBuilder build(String jsonString){
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int length = jsonArray.length();
            String prevNewspaper = "", prevTopic = "";
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String headline = jsonObject.getString("headline");
                String url = jsonObject.getString("url");
                String newspaper = jsonObject.getString("newspaper");
                String topic = jsonObject.getString("topic");
                if (!newspaper.equals(prevNewspaper) || !topic.equals(prevTopic)) {
                    prevNewspaper = newspaper;
                    prevTopic = topic;
                    
                    htmlString = htmlString + "\n    <div class='news-topic col-md-12 text-center'>\n"
                            + "        <div class='newspaper col-md-6 text-right no-padding'>"+newspaper+" :&nbsp;</div>\n"
                            + "        <div class='topic col-md-6 text-left no-padding'>"+topic+"</div>\n"
                            + "    </div>\n";
                }

                if(! existingUrlList.contains(url) && !existingHeadlineList.contains(headline)){
                    htmlString = htmlString + "\n<div class='headline-wrapper col-md-12 no-padding'>\n"
                            + "        <div class='headline col-md-12'>\n"
                            + "            <a href='" + url + "' target='_blank'>" + headline + "</a>\n"
                            + "        </div>\n"
                            + "    </div>\n";
                    existingUrlList.add(url);
                    existingHeadlineList.add(headline);
                }
            }
            //System.out.println(jsonArray.length());
            
            htmlString = "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "    <title>Newspaper Summary</title>\n"
                    + "\n"
                    + "    <link rel=\"stylesheet\" href=\"bootstrap/bootstrap.min.css\">\n"
                    + "    <script src=\"bootstrap/jquery-1.12.4.min.js\"></script>\n"
                    + "    <script src=\"bootstrap/bootstrap.min.js\"></script>\n"
                    + "\n"
                    + "	<link href=\"https://fonts.googleapis.com/css2?family=Monda&display=swap\" rel=\"stylesheet\">\n"
                    + "	<link href=\"https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@300&display=swap\" rel=\"stylesheet\"> \n"
                    + "    \n"
                    + "    <link rel=\"stylesheet\" href=\"css/index.css\">\n"
                    + "    <script type=\"text/javascript\" src=\"js/index.js\"></script>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "<div id='container' class='col-md-12 no-padding'>"
                    +htmlString
                    +"</div>\n"
                    + "</body>\n"
                    + "</html>";
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
        
        
        return this;
    }
    
    HtmlBuilder saveInFile(String savePath){
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        savePath = savePath+"/"+todayDate+".html";
        try {
            File file = new File(savePath);
//            file.getParentFile().mkdirs();
//            FileWriter writer = new FileWriter(file);
//            
//            Files.write(Paths.get(savePath), this.htmlString.getBytes(), StandardOpenOption.APPEND);
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            writer.append(htmlString);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return this;
    }
    
    String getHtml(){
        return this.htmlString;
    }
}
