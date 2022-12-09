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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;

public class NewsSummary {
    static boolean testingApp = false;
    static String savePath = "D:/news";
    static Crawler crawler;
    
    public static void main(String[] args) {
        JFrame homeJFrame = new HomeFrame();
        homeJFrame.setVisible(true);
        
        
//        String url = "https://www.thedailystar.net/frontpage/news/bangladesh-economy-2020-rising-the-rubble-2020481";
//        System.out.println(new MyLibraray().extractNewsCategory(url));
    }
    
    
}
