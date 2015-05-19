package gui.main;

import java.awt.Container;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import org.jsoup.Jsoup;
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
		
		//기본교양
		 String link1 = "http://203.241.192.53:8080/URP4WN/WEB/%EA%B0%95%EC%9D%98%ED%8F%89%EA%B0%80%EA%B3%B5%EC%A7%80(%EA%B8%B0%EB%B3%B8%EA%B5%90%EC%96%91).htm";
		 
		 //핵심, 융복합교양
		 String link2 = "http://203.241.192.53:8080/URP4WN/WEB/%EA%B0%95%EC%9D%98%ED%8F%89%EA%B0%80%EA%B3%B5%EC%A7%80(%ED%95%B5%EC%8B%AC%EC%9C%B5%EB%B3%B5%ED%95%A9%EA%B3%B5%ED%95%99).htm";
		 
		 //전공(학문기초)
		 String link3 = "http://203.241.192.53:8080/URP4WN/WEB/%EA%B0%95%EC%9D%98%ED%8F%89%EA%B0%80%EA%B3%B5%EC%A7%80(%EC%A0%84%EA%B3%B5(%ED%95%99%EA%B8%B0)).htm";
		 
		 //기타(자유교양)
		 String link4 = " http://203.241.192.53:8080/URP4WN/WEB/%EA%B0%95%EC%9D%98%ED%8F%89%EA%B0%80%EA%B3%B5%EC%A7%80(%EA%B8%B0%ED%83%80(%EC%9E%90%EA%B5%90)).htm";
		 
		 String[] colName = {DefineString.Parser.SORT, DefineString.Parser.LECTURE_NUM, DefineString.Parser.LECTURE_NAME, DefineString.Parser.PROFESSOR, DefineString.Parser.EVAL};

		 
		  Container contentPane = this.getContentPane();
		  this.setSize(400,400);
		  this.setLocation(450,250);
	      
		 

			System.out.println("zzzzzzzzz");

			try {
				doc1 = Jsoup.connect(link1).get();
				//doc2 = Jsoup.connect(link2).get();
				//doc3 = Jsoup.connect(link3).get();
				//doc4 = Jsoup.connect(link4).get();
				 Elements rowElements1 = doc1.select("div table tr");
				 //Elements rowElements2 = doc2.select("div table tr");
				 //Elements rowElements3 = doc3.select("div table tr");
				 //Elements rowElements4 = doc4.select("div table tr");
				 
				 int num = rowElements1.size();
				 //int num2 = rowElements1.size() + rowElements2.size();
				 //System.out.println(num2);
				 data2 = new String[num][5];
			        
			        for(int i=2; i<num; i++){
				           Element li = rowElements1.get(i); // tr 갯수
				           Elements tdList = li.children(); // td갯수
				        // 맨 위의 tr은 제목이니까 제외(2014-2학기 강의평가 공지( 핵심,융복합,공학소양))
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
			        
			        /*for(int i=0; i<131; i++){
				           Element li = rowElements2.get(i); // tr 태그의 목록
				           Elements tdList = li.children(); // td 태그의 목록
				            // // 맨 위의 tr은 제목이니까 제외(2014-2학기 강의평가 공지( 핵심,융복합,공학소양))
				           if(tdList.size() < 2)
				           {
				        	   continue;
				        	   }
				            // td 목록 �? 과목명�? 4번째, ?��?��?? 5번째?�� ?��치한?��. 

				        	   data2[i+1000][0] = tdList.get(1).text();
				        	   data2[i+1000][1] = tdList.get(2).text();
				        	   data2[i+1000][2] = tdList.get(3).text();
				        	   data2[i+1000][3] = tdList.get(6).text();
				        	   data2[i+1000][4] = tdList.get(4).text();

			        }
			        */
			        
			        /*
			        for(int i=0; i<rowElements3.size(); i++){
				           Element li = rowElements3.get(i); // tr ?���? 목록
				           Elements tdList = li.children(); // td?�� 목록
				            // �? ?��?�� tr?? ?��목이?���? ?��?��(2014-2?���? 강의?���? 공�?( ?��?��,?��복합,공학?��?��))
				           if(tdList.size() < 2) continue;
				           
			        	   data2[i+1131][0] = tdList.get(1).text();
			        	   data2[i+1131][1] = tdList.get(2).text();
			        	   data2[i+1131][2] = tdList.get(3).text();
			        	   data2[i+1131][3] = tdList.get(6).text();
			        	   data2[i+1131][4] = tdList.get(4).text();
			        }
			        */

			} 
			catch (IOException e)
			{
				
			}
			catch (IndexOutOfBoundsException e)
			{
				
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
 

