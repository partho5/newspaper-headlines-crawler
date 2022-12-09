/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newssummary;


/**
 *
 * @author parth
 */
public class News {
    private final String newspaper;
    private final String topic;
    private final String headline;
    private final String url;
    
    public News(String newspaper, String topic, String headline, String url){
        this.newspaper = newspaper;
        this.topic = topic;
        this.headline = headline;
        this.url = url;
    }
}
