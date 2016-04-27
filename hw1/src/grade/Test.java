package grade;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;

public class Test {

	/**
	 * @uml.property  name="testingUI"
	 * @uml.associationEnd  
	 */
	UI testingUI = null;
	/**
	 * @uml.property  name="grade"
	 * @uml.associationEnd  
	 */
	Grades grade = null;
	@Before
	public void testUI() throws IOException{
		this.testingUI = new UI();
		this.grade = new Grades();
		FileOutputStream f = new FileOutputStream("file.txt");
		System.setOut(new PrintStream(f));
	}
	@After
	public void stop() throws IOException{
		this.grade = null;
		System.setOut(null);
	}

/*********************************************************************************/	
	
/*
 * 		測試返回的指標是否和物件本身的data一模一樣
 */
		@org.junit.Test
		public void test_getNewData()
		{
			assertSame(grade.data,grade.getNewData());
		}
/***************************************顯示成績******************************************/ 
/*
 *    測試 962001051 李威廷 成績 = 81 32 50 90 93   
 */
		@org.junit.Test
		public void test_showGrades_case1()
		{
		  int i;
		  for(i = 0; i < grade.data.size(); i++){
		    if(grade.data.get(i)[0].equals("962001051")){
		      break;
		    }
		  }
		  assertEquals("81 32 50 90 93",grade.getNewData().get(i)[2]+" "
					+grade.getNewData().get(i)[3]+" "
					+grade.getNewData().get(i)[4]+" "
					+grade.getNewData().get(i)[5]+" "
					+grade.getNewData().get(i)[6]);
		}    		
/*
*    975002021 楊祺賢 81 97 90 82 84
*/
		@org.junit.Test
		public void test_showGrades_case2()
		{
		  int i;
		  for(i = 0; i < grade.data.size(); i++){
		    if(grade.data.get(i)[0].equals("975002021")){
		      break;
		    }
		  }
		  assertEquals("81 97 90 82 84",grade.getNewData().get(i)[2]+" "
					+grade.getNewData().get(i)[3]+" "
					+grade.getNewData().get(i)[4]+" "
					+grade.getNewData().get(i)[5]+" "
					+grade.getNewData().get(i)[6]); 
		}    	
		
/******************************************顯示平均***************************************/	
		
/*
 *		 測試 962001051 李威廷 加權平均91分
 * 		加權比例 0.1 0.1 0.1 0.3 0.4
 */
	@org.junit.Test
	public void test_calculateGrades_case1()
	{
		grade.calculateGrades(0.1, 0.1, 0.1, 0.3, 0.4);
		int i;
		for(i = 0; i < grade.data.size(); i++){
			System.out.println("媽我在這裡44");
			if(grade.data.get(i)[0].equals("962001051")){
				break;
			}
		}
		//assertEquals(91,91);
		assertEquals(81,Integer.parseInt(grade.getNewData().get(i)[7]));
	}
/*
 *		 測試 962001044 凌宗廷 加權平均88分
 *	 	加權比例 0.1 0.1 0.1 0.3 0.4
 */
	@org.junit.Test
	public void test_calculateGrades_case2()
	{
		grade.calculateGrades(0.1, 0.1, 0.1, 0.3, 0.4);
		int i;
		for(i = 0; i < grade.data.size(); i++){
			if(grade.data.get(i)[0].equals("962001044")){
				break;
			}
		}
		assertEquals(88,Integer.parseInt(grade.getNewData().get(i)[7]));
	}
/******************************************顯示排名***************************************/ 
	/*
	 * 測試 962001051 李威廷 排名47
	 */
	@org.junit.Test
	public void test_getRank_case1()
	{
		int i;
		for(i = 0; i < grade.data.size(); i++){
			if(grade.data.get(i)[0].equals("962001051")){
				break;
			}
		}
		assertEquals(47,grade.getRank(i));
	}
	/*
	 *測試 962001044 凌宗廷 排名29
	 */
	@org.junit.Test
	public void test_getRank_case2()
	{
		int i;
		for(i = 0; i < grade.data.size(); i++){
			if(grade.data.get(i)[0].equals("962001044")){
				break;
			}
		}
		assertEquals(29,grade.getRank(i));
	}


/****************************************離開選單*****************************************/ 
	/*
	   *     測試結束按鍵（測試的時候要在console輸入Q）
	   */
	    @org.junit.Test
	    public void test_start_to_End() throws IOException
	    {
	      final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
	      testingUI.start();
	      //System.out.println("Enter Q to quit:\n");
	      assertEquals("輸入ID或 Q\r\n結束了\r\n",outContent.toString());
	      System.setOut(null);

	    }

/***************************************更新配分******************************************/ 	    

    /*
     * 測試輸入 962001051 後輸入每項指令得出的結果
     * 接續輸入 
     * 962001051
     * W
     * 20
     * 20
     * 20
     * 20
     * 20
     * Y
     * E
     * Q
     * Q
     * 若Pass則實際輸出結果和測試結果一樣
     */
      @org.junit.Test
      public void test_Rearrange1() throws IOException
      {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        testingUI.start();
        assertEquals("輸入ID或 Q\r\n"
        		+ "Welcome 李威廷\r\n"
        		+ "1) G 顯示成績 (Grade)\r\n"
        		+ "2) A 顯示平均 (Average)\r\n"
        		+ "3) R 顯示排名 (Rank)\r\n"
        		+ "4) W 更新配分 (Weight)\r\n"
        		+ "5) E 離開選單 (Exit)\r\n"
        		+ "舊配分\r\n"
        		+ "lab1 :       10%\r\n"
        		+ "lab2 :       10%\r\n"
        		+ "lab3 :       10%\r\n"
        		+ "mid-term :   30%\r\n"
        		+ "final exam : 40%\r\n"
        		+ "輸入新配分\r\n"
        		+ "lab1 :       \r\n"
        		+ "lab2 :       \r\n"
        		+ "lab3 :       \r\n"
        		+ "mid-term :   \r\n"
        		+ "final exam : \r\n"
        		+ "請確認新配分\r\n"
        		+ "lab1 :       20%\r\n"
        		+ "lab2 :       20%\r\n"
        		+ "lab3 :       20%\r\n"
        		+ "mid-term :   20%\r\n"
        		+ "final exam : 20%\r\n"
        		+ "以上正確嗎? Y (Yes) 或 N (No)\r\n"
        		+ "1) G 顯示成績 (Grade)\r\n"
        		+ "2) A 顯示平均 (Average)\r\n"
        		+ "3) R 顯示排名 (Rank)\r\n"
        		+ "4) W 更新配分 (Weight)\r\n"
        		+ "5) E 離開選單 (Exit)\r\n"
        		+ "輸入ID或 Q\r\n結束了\r\n"
        		+ "輸入ID或 Q\r\n結束了\r\n",outContent.toString());
        System.setOut(null);
      }
      /*
       * 測試輸入 962001051 後輸入每項指令得出的結果
       * 接續輸入 
       * 985002001
       * W
       * 0
       * 10
       * 20
       * 30
       * 40
       * Y
       * E
       * Q
       * Q
       * 若Pass則實際輸出結果和測試結果一樣
       */
        @org.junit.Test
        public void test_Rearrange2() throws IOException
        {
          final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
          System.setOut(new PrintStream(outContent));
          testingUI.start();
          assertEquals("輸入ID或 Q\r\n"
          		+ "Welcome 蔡立雯\r\n"
          		+ "1) G 顯示成績 (Grade)\r\n"
          		+ "2) A 顯示平均 (Average)\r\n"
          		+ "3) R 顯示排名 (Rank)\r\n"
          		+ "4) W 更新配分 (Weight)\r\n"
          		+ "5) E 離開選單 (Exit)\r\n"
          		+ "舊配分\r\n"
          		+ "lab1 :       10%\r\n"
          		+ "lab2 :       10%\r\n"
          		+ "lab3 :       10%\r\n"
          		+ "mid-term :   30%\r\n"
          		+ "final exam : 40%\r\n"
          		+ "輸入新配分\r\n"
          		+ "lab1 :       \r\n"
          		+ "lab2 :       \r\n"
          		+ "lab3 :       \r\n"
          		+ "mid-term :   \r\n"
          		+ "final exam : \r\n"
          		+ "請確認新配分\r\n"
          		+ "lab1 :       0%\r\n"
          		+ "lab2 :       10%\r\n"
          		+ "lab3 :       20%\r\n"
          		+ "mid-term :   30%\r\n"
          		+ "final exam : 40%\r\n"
          		+ "以上正確嗎? Y (Yes) 或 N (No)\r\n"
          		+ "1) G 顯示成績 (Grade)\r\n"
          		+ "2) A 顯示平均 (Average)\r\n"
          		+ "3) R 顯示排名 (Rank)\r\n"
          		+ "4) W 更新配分 (Weight)\r\n"
          		+ "5) E 離開選單 (Exit)\r\n"
          		+ "輸入ID或 Q\r\n結束了\r\n"
          		+ "輸入ID或 Q\r\n結束了\r\n",outContent.toString());
          System.setOut(null);
        }      
/****************************************合併測試*****************************************/			
	/*		Integration Test Case1
			這個測試包含了前面對Case1的所有測試
	*/
		@org.junit.Test
		public void Integration_test_case1() throws IOException
		{	
			test_getNewData();
			test_calculateGrades_case1();
			test_showGrades_case1();
			test_getRank_case1();
			test_start_to_End();
		
		}
		
	/*		Integration Test Case2
			這個測試包含了前面對Case2的所有測試
	*/
		@org.junit.Test
		public void Integration_test_case2() throws IOException
		{
			test_getNewData();
			test_calculateGrades_case2();
			test_showGrades_case2();
			test_getRank_case2();
			test_start_to_End();
		}
}
