package gui.main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.ptg.AddPtg;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.DefineString;

public class Eval extends JFrame {
   static Document doc1,doc2,doc3,doc4 = null;
    static DefaultTableModel model;
    static JTable table;
    static int num;
    String[][] data2;

   public static void main() {
      
      new Eval();

        }

   private Eval()
   {
      
      //2014-2학기 강의평가 공지(기본교양)
       String link1 = "http://203.241.192.53:8080/URP4WN/WEB/%EA%B0%95%EC%9D%98%ED%8F%89%EA%B0%80%EA%B3%B5%EC%A7%80(%EA%B8%B0%EB%B3%B8%EA%B5%90%EC%96%91).htm";
       
       //2014-2학기 강의평가 공지(기본교양)
       String link2 = "http://203.241.192.53:8080/URP4WN/WEB/%EA%B0%95%EC%9D%98%ED%8F%89%EA%B0%80%EA%B3%B5%EC%A7%80(%ED%95%B5%EC%8B%AC%EC%9C%B5%EB%B3%B5%ED%95%A9%EA%B3%B5%ED%95%99).htm";
       
       //2014-2학기 강의평가 공지( 핵심,융복합,공학소양)
       String link3 = "http://203.241.192.53:8080/URP4WN/WEB/%EA%B0%95%EC%9D%98%ED%8F%89%EA%B0%80%EA%B3%B5%EC%A7%80(%EC%A0%84%EA%B3%B5(%ED%95%99%EA%B8%B0)).htm";
       
       //2014-2학기 강의평가 공지( 전공(학문기초))
       String link4 = "http://203.241.192.53:8080/URP4WN/WEB/%EA%B0%95%EC%9D%98%ED%8F%89%EA%B0%80%EA%B3%B5%EC%A7%80(%EA%B8%B0%ED%83%80(%EC%9E%90%EA%B5%90)).htm";
       
       String[] colName = {DefineString.Parser.SORT, DefineString.Parser.LECTURE_NUM, DefineString.Parser.LECTURE_NAME, DefineString.Parser.PROFESSOR, DefineString.Parser.EVAL};

       
        Container contentPane = this.getContentPane();
        this.setSize(400,400);
        this.setLocation(450,250);
         
       

         

         try {
            doc1 = Jsoup.connect(link1).get();
            doc2 = Jsoup.connect(link2).get();
            doc3 = Jsoup.connect(link3).get();
            doc4 = Jsoup.connect(link4).get();
             Elements rowElements1 = doc1.select("div table tr");
             Elements rowElements2 = doc2.select("div table tr");
             Elements rowElements3 = doc3.select("div table tr");
             Elements rowElements4 = doc4.select("div table tr");
             
             int num = rowElements1.size();

             data2 = new String[num][5];
                 
                 for(int i=2; i<300; i++){
                       Element li = rowElements1.get(i); // tr 갯수
                       Elements tdList = li.children(); // td갯수
                       if(tdList.size() < 2)
                       {
                          continue;
                       }
                       

                          data2[i-2][0] = tdList.get(1).text();
                          data2[i-2][1] = tdList.get(2).text();
                          data2[i-2][2] = tdList.get(3).text();
                          data2[i-2][3] = tdList.get(6).text();
                          data2[i-2][4] = tdList.get(4).text();

                 }

                 for(int i=2; i<131; i++){
                       Element li = rowElements2.get(i);
                       Elements tdList = li.children(); 
                       if(tdList.size() < 2)
                       {
                          continue;
                          }

                          data2[i+296][0] = tdList.get(1).text();
                          data2[i+296][1] = tdList.get(2).text();
                          data2[i+296][2] = tdList.get(3).text();
                          data2[i+296][3] = tdList.get(6).text();
                          data2[i+296][4] = tdList.get(4).text();

                 }
                 
                 
                 
                 for(int i=2; i<300; i++){
                       Element li = rowElements3.get(i);
                       Elements tdList = li.children();
                       if(tdList.size() < 2) continue;
                       
                       data2[i+424][0] = tdList.get(1).text();
                       data2[i+424][1] = tdList.get(2).text();
                       data2[i+424][2] = tdList.get(3).text();
                       data2[i+424][3] = tdList.get(6).text();
                       data2[i+424][4] = tdList.get(4).text();
                 }
                 
                 for(int i=2; i<40; i++){
                       Element li = rowElements4.get(i);
                       Elements tdList = li.children();
                       if(tdList.size() < 2) continue;
                       
                       data2[i+724][0] = tdList.get(1).text();
                       data2[i+724][1] = tdList.get(2).text();
                       data2[i+724][2] = tdList.get(3).text();
                       data2[i+724][3] = tdList.get(6).text();
                       data2[i+724][4] = tdList.get(4).text();
                 }
                 

         } 
         
         
         catch (IOException e)
         {
            e.printStackTrace();
         }
         catch (IndexOutOfBoundsException e)
         {
            e.printStackTrace();
         }

            

           

            model = new DefaultTableModel(data2,colName); 
            table = new JTable(model){
               public boolean isCellEditable(int row, int column) {
                    if (column >= 0) {
                        return false;
                    } else {
                        return true;
                    }
                }
               
            };
            JScrollPane js = new JScrollPane(table);
            contentPane.add(js);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);

      }
   
   

       
   }
 