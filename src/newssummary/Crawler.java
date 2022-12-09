/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newssummary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author parth
 */
public class Crawler {
    boolean testingApp = NewsSummary.testingApp;
    JLabel crawlingStatusLabel, newspaperLabel, bottomMsgLabel, newsCountLabel;
    int newsCount = 0, delayOfCount = 10;
    
    public Crawler(JLabel crawlingStatusLabel, JLabel newspaperLabel, JLabel bottomMsgLabel, JLabel newsCountLabel){
        this.crawlingStatusLabel = crawlingStatusLabel;
        this.newspaperLabel = newspaperLabel;
        this.newsCountLabel = newsCountLabel;
        this.crawlingStatusLabel.setText("Crawling...");
    }
    
    
    public String getResult(){
        java.lang.System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        Document document;
        Elements domElements;
        StringBuilder stringBuilder = new StringBuilder();
        String jsonString, completeJson;
        List<HashMap> urlList;
        MyLibraray Lib = new MyLibraray();
        
        
        
        
     /*   
        //start daily star
        try {
            document = Jsoup.connect("https://www.thedailystar.net/news/bangladesh").timeout(5 * 3600).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").get();
            domElements = document.select("div.card h3 a");
            String topic = "bangladesh";
            for (Element element : domElements) {
                String chunk = element.html();
                String headLine = element.text();
                if( element.parent().parent().hasClass("load-more-date") ){
                    System.out.println("previous news");
                }
                String headlineHref = "href"; //chunk.substring(chunk.indexOf("\"/") + 2, chunk.indexOf("\">"));
                headlineHref = "https://www.thedailystar.net/" + headlineHref;
                topic = Lib.extractNewsTopic(headlineHref);
                System.out.println(topic+" >>> "+headLine + "--" + headlineHref);
                jsonString = Lib.buildNewsJson("Daily Star", topic, headLine, headlineHref);
                stringBuilder.append(jsonString);
                try {
                    Thread.sleep(delayOfCount);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.newspaperLabel.setText("Daily Star > " + topic);
                newsCountLabel.setText("news count : " + ++newsCount);
                if (testingApp) {
                    //break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //end daily star
        
       */ 
        
        
        //start daily star
        urlList = new CollectionOfUrls().dailystarUrls();
        
        for (HashMap hm : urlList) {
            String newspaper = (String) hm.get("newspaper");
            String topic = (String) hm.get("topic");
            String url = (String) hm.get("url");
            this.newspaperLabel.setText(newspaper+" > "+topic);
            try {
                document = Jsoup.connect(url).timeout(5 * 3600).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").get();
                domElements = document.select("div.card");
                for (Element element : domElements) {
                    String headLine = element.select("h3 a").text();
                    String headlineHref = "https://www.thedailystar.net"+element.select("h3 a").attr("href"); // chunk.substring(chunk.indexOf("<a href=\"") + 9, chunk.indexOf("\" class="));
//System.out.println(headlineHref);
                    jsonString = Lib.buildNewsJson(newspaper, topic, headLine, headlineHref);
                    stringBuilder.append(jsonString);
                    try {
                        Thread.sleep(delayOfCount);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    newsCountLabel.setText("news count : "+ ++newsCount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (testingApp) {
                break;
            }
        }
        
        //end daily star
        
        
        
        
        //start jugantar 
        
        urlList = new CollectionOfUrls().jugantarUrls();
        String todayDate = null;
        
        for (HashMap hm : urlList) {
            String newspaper = (String) hm.get("newspaper");
            String topic = (String) hm.get("topic");
            String url = (String) hm.get("url");
            this.newspaperLabel.setText(newspaper+" > "+topic);
            try {
                document = Jsoup.connect(url).timeout(5 * 3600).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").get();
                domElements = document.select("div.align-self-end");
                for (Element element : domElements) {
                    String headLine = element.select("a h6").text();
                    String chunk = element.select("a").toString();
                    String headlineHref = chunk.substring(chunk.indexOf("<a href=\"") + 9, chunk.indexOf("\" class="));
                    String date = element.select("a > .small").text().trim();

                    if (todayDate == null) {
                        todayDate = date;
                    }
                    if (!date.equals(todayDate)) {
                        break;
                    }
                    jsonString = Lib.buildNewsJson(newspaper, topic, headLine, headlineHref);
                    stringBuilder.append(jsonString);
                    try {
                        Thread.sleep(delayOfCount);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    newsCountLabel.setText("news count : "+ ++newsCount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (testingApp) {
                break;
            }
        }
        
        //end jugantar 
        
        
        
        //start ittefaq 
        
        urlList = new CollectionOfUrls().ittafaqUrls();
            for(HashMap hm : urlList){
                String newspaper = (String) hm.get("newspaper");
                String topic = (String) hm.get("topic");
                String url = (String) hm.get("url");
                this.newspaperLabel.setText(newspaper+" > "+topic);
                try {
                    document = Jsoup.connect(url).timeout(5 * 3600).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").get();

                    domElements = document.select("div.lead_headline");
                    for (Element element : domElements) {
                        String headLine = element.select("a > h5").text();
                        //Log.d("myLog", headLine);
                        String chunk = element.select("a").toString();
                        String headlineHref = chunk.substring(chunk.indexOf("<a href=\"") + 9, chunk.indexOf(">"));
                        jsonString = Lib.buildNewsJson(newspaper, topic, headLine, headlineHref);
                        stringBuilder.append(jsonString);
                        try {
                            Thread.sleep(delayOfCount);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        newsCountLabel.setText("news count : "+ ++newsCount);
                    }

                    domElements = document.select("div.media");
                    for (Element element : domElements) {
                        String headLine = element.select("div.media-body > h5").text();
                        String chunk = element.select("a").toString();
                        //Log.d("mylog", chunk);
                        try {
                            String headlineHref = chunk.substring(chunk.indexOf("<a href=\"") + 9, chunk.indexOf(">"));
                            jsonString = Lib.buildNewsJson(newspaper, topic, headLine, headlineHref);
                            stringBuilder.append(jsonString);
                            try {
                                Thread.sleep(delayOfCount);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            newsCountLabel.setText("news count : "+ ++newsCount);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(testingApp) break;
            }
        
        //end ittefaq 
        
        
        
        //start kaler kantho 
        urlList = new CollectionOfUrls().kalerkanthoUrls();
            for (HashMap hm : urlList) {
                String newspaper = (String) hm.get("newspaper");
                String topic = (String) hm.get("topic");
                String url = (String) hm.get("url");
                this.newspaperLabel.setText(newspaper+" > "+topic);
                try {
                    document = Jsoup.connect(url).timeout(5 * 3600).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").get();
                    domElements = document.select("div.n_row");
                    for (Element element : domElements) {
                        String headLine = element.select("a").text();
                        String chunk = element.select("a").toString();
                        String headlineHref = chunk.substring(chunk.indexOf("<a href=\"") + 9, chunk.indexOf(" title=\"\" class=\"title hidden-xs\">"));
                        headlineHref = headlineHref.substring(0, headlineHref.length()-1);
                        jsonString = Lib.buildNewsJson(newspaper, topic, headLine, headlineHref);
                        stringBuilder.append(jsonString);
                        try {
                            Thread.sleep(delayOfCount);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        newsCountLabel.setText("news count : "+ ++newsCount);
                    }

                    domElements = document.select("div.top_news > .summary");
                    for (Element element : domElements) {
                        String headLine = element.select("a").text();
                        String chunk = element.select("a").toString();
                        String headlineHref = chunk.substring(chunk.indexOf("<a href=\"") + 9, chunk.indexOf("title=\"\">"));
                        headlineHref = headlineHref.substring(0, headlineHref.length()-1);
                        jsonString = Lib.buildNewsJson(newspaper, topic, headLine, headlineHref);
                        //System.out.println(headlineHref);
                        stringBuilder.append(jsonString);
                        try {
                            Thread.sleep(delayOfCount);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        newsCountLabel.setText("news count : "+ ++newsCount);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(testingApp) break;
            }
        //end kaler kantho 
        
        
        
        
        
        
        completeJson = "["+stringBuilder+"]";
        
        return completeJson;
    }
    
    boolean isNetworkAvailable(){
        boolean status = true;
        java.lang.System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        try {
            Jsoup.connect("https://www.google.com").timeout(10*1000).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get();
        } catch (IOException ex) {
            status = false;
        }
        return status;
    }
}
