package grade;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grades {
    /**
	 * @uml.property  name="data"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="[Ljava.lang.String;"
	 */
    public List<String[]> data = new ArrayList<String[]>();
    /**
	 * @uml.property  name="totalGrades"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Integer"
	 */
    public List<Integer> totalGrades = new ArrayList<Integer>();
    
	public Grades(){
		this.loadGrades();
		this.calculateGrades(0.1, 0.1, 0.1, 0.3, 0.4);
	}
	
	/* method: loadGrades  ----------------------------------------------------------------------------------                                                                                                    
	* 用來匯入 /src/res/gradeinput.txt的資料
	* 
	* Pseudo code:
	* 1.BufferedReader( 吃檔使用 "new BufferedReader(new InputStreamReader(new FileInputStream(" 方法 )(fileScan1)讀進txt的資料，利用readline一行一行讀取
	* 2.當fileScan1未結束( while to test .ready() )時，逐行讀取
	* 3.吃進整行，parse出規格後再將String[]存進List<String[]>
    *
	* Time estimate : O(n^2)
	* Example: loadGrades()，讀進input.txt，會將資料存進List<String[]>
	----------------------------------------------------------------------------------------------------------*/
	private void loadGrades(){
		try{BufferedReader fileScan = new BufferedReader(new InputStreamReader(new FileInputStream("src/res/gradeinput.txt"), "UTF-8")); fileScan.read();//一開始不知為何會有問號char，把它吃掉並略過
			while(fileScan.ready()){         
				int i = 0;
				String[] tempString = new String[8]; // 一筆資料，真正new出空間
	            String str = fileScan.readLine(); // 一次讀一行，所以只有讀一筆資料
	            String[] token = str.split(" "); // 暫時接讀進來的一行的切割(split要用這種令法的str才能切)
	            for(String item : token){ tempString[i] = item; i++; }// 回頭利用for迴圈真正建立起要add進data的一筆資料
	            data.add(tempString);
	        }
		} catch(Exception e){};}
	
	/* method: calculateGrades  ----------------------------------------------------------------------------------                                                                                                    
	* 計算or重新計算加權總成績
	* 
	* @param lab1, lab2, lab3, mid, final，為五次成績的比重
	* 
	* Pseudo code:
	* 1.清除先前計算方式算出的總成績 
	* 2.每個人data重新依序做運算
    *
	* Time estimate : O(n)
	* Example: calculateTotalGrades(0.2, 0.2, 0.2, 0.2, 0.2)，傳入五個加權值，會更新所有人總成績
	----------------------------------------------------------------------------------------------------------*/
	public void calculateGrades(double lab1, double lab2, double lab3, double mid, double fe){
		int i;
		totalGrades.clear();
		for(i = 0; i < data.size(); i++){
			data.get(i)[7] = weightedAverage(i, lab1, lab2, lab3, mid, fe);
		}
	}
	
	/* method: weightedAverage  ----------------------------------------------------------------------------------                                                                                                    
	* 計算加權總成績
	* 
	* @param i為學生的id， lab1, lab2, la3, mid, fe為五次成績的比重
	* 
	* @return 回傳以字串表示的新成績
	* 
	* Pseudo code:
	* 計算學生成績
    *
	* Time estimate : O(1)
	* Example: weightedAverage(0, 0.2, 0.2, 0.2, 0.2, 0.2), return 89。傳入學生的id跟五個加權值，會算出這個學生的成績
	----------------------------------------------------------------------------------------------------------*/
	private String weightedAverage(int i, double lab1, double lab2, double lab3, double mid, double fe){
		float total = 0;
		total += Integer.parseInt(data.get(i)[2]) * lab1;
		total += Integer.parseInt(data.get(i)[3]) * lab2;
		total += Integer.parseInt(data.get(i)[4]) * lab3;
		total += Integer.parseInt(data.get(i)[5]) * mid;
		total += Integer.parseInt(data.get(i)[6]) * fe;
		totalGrades.add(Math.round(total));
		return  "" + Math.round(total); // 前面加""是為了表示回傳的是字串，總成績是字串！
	}
	
	/* method getNewData  ----------------------------------------------------------------------------------                                                                                                    
	* 用來取得所有人資料
	* 
	* @return List<String[]> (所有人資料)
	* 
	* Pseudo code:
	* 回傳資料
    *
	* Time estimate : O(1)
	* Example: getNewData(), return data
	----------------------------------------------------------------------------------------------------------*/
	public List<String[]> getNewData(){
		return data;
	}
	
	/* method: getRank  ----------------------------------------------------------------------------------                                                                                                    
	* 計算學生排名
	* 
	* @param id:學生在data這個list裡的id
	* @return 本學生排名
	* 
	* Pseudo code:
	* 將正在查詢之學生的成績與每個學生比較，每輸一個排名就往後一位
    *
	* Time estimate : O(n)
	* Example: getRank(3), return 22
	----------------------------------------------------------------------------------------------------------*/
	public int getRank(int id){
		int rank = 1;
		for(int i = 0; i < data.size(); i++){
			if(totalGrades.get(id) < totalGrades.get(i))
				rank ++;
		}
		return rank;
	}
}
