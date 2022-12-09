/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newssummary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MyLibraray {
    String buildNewsJson(String newspaper, String topic, String headLine, String headlineHref){
        String jsString = "";
        try {
            Gson gsonBuilder = new GsonBuilder().serializeNulls().create();
            News newsBuilder = new News(newspaper, topic, headLine, headlineHref);
            jsString = gsonBuilder.toJson(newsBuilder);
            jsString = jsString.replaceAll("[‘’]]", "\'");
            jsString = jsString.replaceAll("\\}", "},");
        }catch (Exception e){
            System.out.println("error at "+headLine);
        }
        return jsString;
    }
    
    public String extractNewsTopic(String url){
        String category = "News";//default value
        Pattern pattern = Pattern.compile("https://www.thedailystar.net/(.*?)/");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            //System.out.println(matcher.group(1));
            category = matcher.group(1);
        }
        return category;
    }
    
    
}

