package grade;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    /**
	 * @uml.property  name="scanner"
	 */
    private Scanner scanner = new Scanner(System.in);
    /**
	 * @uml.property  name="iDorQ"
	 */
    private String IDorQ;
    /**
	 * @uml.property  name="data"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="[Ljava.lang.String;"
	 */
    private List<String[]> data = new ArrayList<String[]>();
    /**
	 * @uml.property  name="grades"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private Grades grades = new Grades();
    /**
	 * @uml.property  name="weights" multiplicity="(0 -1)" dimension="1"
	 */
    private double[] weights = new double[5];
    
	public UI(){
		// Constructor, initialize some data
		this.data = grades.data;
		this.weights[0] = 0.1;
		this.weights[1] = 0.1;
		this.weights[2] = 0.1;
		this.weights[3] = 0.3;
		this.weights[4] = 0.4;
	}
	
	/* method: start  ----------------------------------------------------------------------------------                                                                                                    
	* 開始介面
	* 
	* Pseudo code:
	* 1.顯示輸入ID或結束
	* 2.讀取輸入
	* 3.判斷輸入
    *
	* Time estimate : O (1)
	* Example: start()，輸入1111111，判斷ID；輸入Q，顯示結束了
	----------------------------------------------------------------------------------------------------------*/
	public void start(){
		System.out.println("輸入ID或 Q");
		this.IDorQ = scanner.nextLine();
		if(this.IDorQ.equals("Q"))
			System.out.println("結束了");
		else
			this.checkIfExist(IDorQ);// 此時IDorQ會是ID，可代進checkIfExist
	}
	
	/* method: checkIfExist  ----------------------------------------------------------------------------------                                                                                                    
	* 檢查ID是否存在
	* 
	* @param ID  要檢查的ID
	* 
	* Pseudo code:
	* 1.從data去搜尋，判斷是否存在
	* 2.若存在則顯示歡迎此ID，並列出選單
	* 3.若不存在則顯示ID錯了，回到開始介面
    *
	* Time estimate : O(n)
	* Example: chechIfExist(965002038) --> showList(3) & flag = 1   or   ID錯了
	----------------------------------------------------------------------------------------------------------*/
	private void checkIfExist(String ID){
		int i, flag = 0;
		for(i = 0; i < data.size(); i++){
			if(ID.equals(data.get(i)[0])){
				System.out.println("Welcome " + data.get(i)[1]);	this.showList(i);	flag = 1;}
		}
		if(flag == 0)
			System.out.println("ID錯了!");	this.start();// back to original start screen
	}
	
	/* method: showList  ----------------------------------------------------------------------------------                                                                                                    
	* 選單介面
	* 
	* @param id 學生在data裡的id(index number)
	* 
	* Pseudo code:
	* 1.將選單列印出來
	* 2.讀取輸入
	* 3.將id 與我們輸入的選項傳入chooseList()
    *
	* Time estimate : O (1)
	* Example: showList(3), 輸入(R) --> chooseList(3, R);
	----------------------------------------------------------------------------------------------------------*/
	private void showList(int id){
		String choose;
		System.out.println("1) G 顯示成績 (Grade)");
		System.out.println("2) A 顯示平均 (Average)");
		System.out.println("3) R 顯示排名 (Rank)");
		System.out.println("4) W 更新配分 (Weight)");
		System.out.println("5) E 離開選單 (Exit)");
		choose = scanner.nextLine();
		chooseList(id, choose);
	}
	
	/* method: chooseList  ----------------------------------------------------------------------------------                                                                                                    
	* 判斷輸入
	* 
	* @param id 學生在data裡的id
	* @param choose 先前輸入的指令
	* 
	*Pseudo code:
	*1.判斷是不是G or R or W or E
	*2.讀取輸入
	*3.將id 和輸入傳入chooseList()
    *
	* Time estimate : O(1)
	* Example: chooseList(3, R) --> showRank(3) ;
	----------------------------------------------------------------------------------------------------------*/
	private void chooseList(int id, String choose){
		if(choose.equals("G"))		showGrades(id);
		else if(choose.equals("A")) showAverage(id);
		else if(choose.equals("R"))	showRank(id);
		else if(choose.equals("W"))	reCalculate(id);
		else if(choose.equals("E"))	Exit();
		else{
			System.out.println("指令錯了!");
			showList(id);
		}
	}
	/* method: showGrades  ----------------------------------------------------------------------------------                                                                                                    
	* 顯示成績
	* 
	* @param id 學生在data裡的id
	* 
	* Pseudo code:
	* 逐一印出各項成績（不含平均成績）
    *
	* Time estimate : O(1)
	* Example: showGrades(3) --> 印出蘇亨玠的成績, showList(3) ;
	----------------------------------------------------------------------------------------------------------*/
	private void showGrades(int id){
		System.out.println("lab1 :        " + data.get(id)[2] + ((Integer.parseInt(data.get(id)[2])<60) ? "*" : ""));
		System.out.println("lab2 :        " + data.get(id)[3] + ((Integer.parseInt(data.get(id)[3])<60) ? "*" : ""));
		System.out.println("lab3 :        " + data.get(id)[4] + ((Integer.parseInt(data.get(id)[4])<60) ? "*" : ""));
		System.out.println("mid-term :    " + data.get(id)[5] + ((Integer.parseInt(data.get(id)[5])<60) ? "*" : ""));
		System.out.println("final exam :  " + data.get(id)[6] + ((Integer.parseInt(data.get(id)[6])<60) ? "*" : ""));
		System.out.println("total grade : " + data.get(id)[7] + ((Integer.parseInt(data.get(id)[7])<60) ? "*" : ""));
		showList(id);
	}
	/* method: showAverage  ----------------------------------------------------------------------------------                                                                                                    
	* 顯示加權平均成績
	* 
	* @param id 學生在data裡的id
	* 
	* Pseudo code:
	* 印出加權平均成績
    *
	* Time estimate : O(1)
	* Example: showGrades(3) --> 印出蘇亨玠的平均成績, showList(3) ;
	----------------------------------------------------------------------------------------------------------*/
	private void showAverage(int id){
		System.out.println("Average : " + data.get(id)[7] + ((Integer.parseInt(data.get(id)[7])<60) ? "*" : ""));
		showList(id);
	}
	/* method showRank  ----------------------------------------------------------------------------------                                                                                                    
	* 顯示成績
	* 
	* @param id 學生在data裡的id
	* 
	* Pseudo code:
	* 到Grades裡查詢排名
    *
	* Time estimate : O(n)
	* Example: showRank(3) --> 印出grades.getRank(3), showList(3) ;
	----------------------------------------------------------------------------------------------------------*/
	private void showRank(int id){
		System.out.println(data.get(id)[1] + "：排名第" + grades.getRank(id));
		showList(id);
	}
	
	/* method: reCalculate  ----------------------------------------------------------------------------------                                                                                                    
	* 更新配分
	* 
	* @param id 學生在data裡的id
	* 
	* Pseudo code:
	* 1.顯示現在配分
	* 2.要求輸入新配分
	* 3.要求確認新配分
	* 4.更新總成績
    *
	* Time estimate : O(n)
	* Example: reCalculate(3), 輸入(0.2, 0.2, 0.2, 0.2, 0.2) --> grades.calculateTotalGrades(0.2, 0.2, 0.2, 0.2, 0.2)
	*           , showList(3) ;
	----------------------------------------------------------------------------------------------------------*/
	private void reCalculate(int id){
		showNowWeights();
		inputNewWeights();
		String yesOrNo = checkNewWeights();
		if(yesOrNo.equals("Y")){ //對才真的把資料傳進Grades.java給他重算
			grades.calculateGrades(weights[0], weights[1], weights[2], weights[3], weights[4]);
			this.data = grades.getNewData();
			showList(id);
		}
		else
			reCalculate(id); //recursive~
	}
	
	/* method showNowWerghts  ----------------------------------------------------------------------------------                                                                                                    
	* 顯示目前配分，給人確認
	* 
	* Pseudo code:
	* 顯示目前配分
    *
	* Time estimate : O(1)
	* Example: showNowWeights()，印出目前配分 ;
	----------------------------------------------------------------------------------------------------------*/
	public void showNowWeights(){
		System.out.println("舊配分");
		System.out.println("lab1 :       " + (int)(weights[0] * 100) + "%");
		System.out.println("lab2 :       " + (int)(weights[1] * 100) + "%");
		System.out.println("lab3 :       " + (int)(weights[2] * 100) + "%");
		System.out.println("mid-term :   " + (int)(weights[3] * 100) + "%");
		System.out.println("final exam : " + (int)(weights[4] * 100) + "%");
	}
	
	/* method inputNowWerghts  ----------------------------------------------------------------------------------                                                                                                    
	* 輸入新配分
	* 
	* Pseudo code:
	* 輸入新配分
    *
	* Time estimate : O(1)
	* Example: inputNewWeights()，要求輸入新配分 ;
	----------------------------------------------------------------------------------------------------------*/
	public void inputNewWeights(){
		double[] temp = new double[5];
		System.out.println("輸入新配分");
		System.out.println("lab1 :       ");	temp[0] = scanner.nextDouble();
		System.out.println("lab2 :       ");	temp[1] = scanner.nextDouble();
		System.out.println("lab3 :       ");	temp[2] = scanner.nextDouble();
		System.out.println("mid-term :   ");	temp[3] = scanner.nextDouble();
		System.out.println("final exam : ");	temp[4] = scanner.nextDouble();
		for(int i = 0; i < 5; i++)
			weights[i] = temp[i] / (temp[0] + temp[1] + temp[2] + temp[3] + temp[4]);
	}
	
	/* method checkNowWerghts  ----------------------------------------------------------------------------------                                                                                                    
	* 確認新配分
	* 
	* @return String Y或N
	* 
	* Pseudo code:
	* 1.印出新配分
	* 2.要求輸入是否正確，若不是則重新輸入
    *
	* Time estimate : O(1)
	* Example: checkNewWeights()，要求確認新配分 ;
	----------------------------------------------------------------------------------------------------------*/
	public String checkNewWeights(){
		String yesOrNo;
		System.out.println("請確認新配分");
		System.out.println("lab1 :       " + (int)(weights[0] * 100) + "%");
		System.out.println("lab2 :       " + (int)(weights[1] * 100) + "%");
		System.out.println("lab3 :       " + (int)(weights[2] * 100) + "%");
		System.out.println("mid-term :   " + (int)(weights[3] * 100) + "%");
		System.out.println("final exam : " + (int)(weights[4] * 100) + "%");
		System.out.println("以上正確嗎? Y (Yes) 或 N (No)");
		yesOrNo = scanner.nextLine();
		while(!yesOrNo.equals("Y") && !yesOrNo.equals("N"))
			yesOrNo = scanner.nextLine();
		return yesOrNo;
	}
	
	/* method Exit  ----------------------------------------------------------------------------------                                                                                                    
	* 離開選單
	* 
	* Pseudo code:
	* 回到開始介面
    *
	* Time estimate : O(1)
	* Example: Exit() --> start();
	----------------------------------------------------------------------------------------------------------*/
	private void Exit(){
		start();
	}
}
