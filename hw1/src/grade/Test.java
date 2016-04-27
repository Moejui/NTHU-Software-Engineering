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
 * 		���ժ�^�����ЬO�_�M���󥻨���data�@�Ҥ@��
 */
		@org.junit.Test
		public void test_getNewData()
		{
			assertSame(grade.data,grade.getNewData());
		}
/***************************************��ܦ��Z******************************************/ 
/*
 *    ���� 962001051 ���§� ���Z = 81 32 50 90 93   
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
*    975002021 ���R�� 81 97 90 82 84
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
		
/******************************************��ܥ���***************************************/	
		
/*
 *		 ���� 962001051 ���§� �[�v����91��
 * 		�[�v��� 0.1 0.1 0.1 0.3 0.4
 */
	@org.junit.Test
	public void test_calculateGrades_case1()
	{
		grade.calculateGrades(0.1, 0.1, 0.1, 0.3, 0.4);
		int i;
		for(i = 0; i < grade.data.size(); i++){
			System.out.println("���ڦb�o��44");
			if(grade.data.get(i)[0].equals("962001051")){
				break;
			}
		}
		//assertEquals(91,91);
		assertEquals(81,Integer.parseInt(grade.getNewData().get(i)[7]));
	}
/*
 *		 ���� 962001044 ��v�� �[�v����88��
 *	 	�[�v��� 0.1 0.1 0.1 0.3 0.4
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
/******************************************��ܱƦW***************************************/ 
	/*
	 * ���� 962001051 ���§� �ƦW47
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
	 *���� 962001044 ��v�� �ƦW29
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


/****************************************���}���*****************************************/ 
	/*
	   *     ���յ�������]���ժ��ɭԭn�bconsole��JQ�^
	   */
	    @org.junit.Test
	    public void test_start_to_End() throws IOException
	    {
	      final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
	      testingUI.start();
	      //System.out.println("Enter Q to quit:\n");
	      assertEquals("��JID�� Q\r\n�����F\r\n",outContent.toString());
	      System.setOut(null);

	    }

/***************************************��s�t��******************************************/ 	    

    /*
     * ���տ�J 962001051 ���J�C�����O�o�X�����G
     * �����J 
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
     * �YPass�h��ڿ�X���G�M���յ��G�@��
     */
      @org.junit.Test
      public void test_Rearrange1() throws IOException
      {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        testingUI.start();
        assertEquals("��JID�� Q\r\n"
        		+ "Welcome ���§�\r\n"
        		+ "1) G ��ܦ��Z (Grade)\r\n"
        		+ "2) A ��ܥ��� (Average)\r\n"
        		+ "3) R ��ܱƦW (Rank)\r\n"
        		+ "4) W ��s�t�� (Weight)\r\n"
        		+ "5) E ���}��� (Exit)\r\n"
        		+ "�°t��\r\n"
        		+ "lab1 :       10%\r\n"
        		+ "lab2 :       10%\r\n"
        		+ "lab3 :       10%\r\n"
        		+ "mid-term :   30%\r\n"
        		+ "final exam : 40%\r\n"
        		+ "��J�s�t��\r\n"
        		+ "lab1 :       \r\n"
        		+ "lab2 :       \r\n"
        		+ "lab3 :       \r\n"
        		+ "mid-term :   \r\n"
        		+ "final exam : \r\n"
        		+ "�нT�{�s�t��\r\n"
        		+ "lab1 :       20%\r\n"
        		+ "lab2 :       20%\r\n"
        		+ "lab3 :       20%\r\n"
        		+ "mid-term :   20%\r\n"
        		+ "final exam : 20%\r\n"
        		+ "�H�W���T��? Y (Yes) �� N (No)\r\n"
        		+ "1) G ��ܦ��Z (Grade)\r\n"
        		+ "2) A ��ܥ��� (Average)\r\n"
        		+ "3) R ��ܱƦW (Rank)\r\n"
        		+ "4) W ��s�t�� (Weight)\r\n"
        		+ "5) E ���}��� (Exit)\r\n"
        		+ "��JID�� Q\r\n�����F\r\n"
        		+ "��JID�� Q\r\n�����F\r\n",outContent.toString());
        System.setOut(null);
      }
      /*
       * ���տ�J 962001051 ���J�C�����O�o�X�����G
       * �����J 
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
       * �YPass�h��ڿ�X���G�M���յ��G�@��
       */
        @org.junit.Test
        public void test_Rearrange2() throws IOException
        {
          final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
          System.setOut(new PrintStream(outContent));
          testingUI.start();
          assertEquals("��JID�� Q\r\n"
          		+ "Welcome ���߶�\r\n"
          		+ "1) G ��ܦ��Z (Grade)\r\n"
          		+ "2) A ��ܥ��� (Average)\r\n"
          		+ "3) R ��ܱƦW (Rank)\r\n"
          		+ "4) W ��s�t�� (Weight)\r\n"
          		+ "5) E ���}��� (Exit)\r\n"
          		+ "�°t��\r\n"
          		+ "lab1 :       10%\r\n"
          		+ "lab2 :       10%\r\n"
          		+ "lab3 :       10%\r\n"
          		+ "mid-term :   30%\r\n"
          		+ "final exam : 40%\r\n"
          		+ "��J�s�t��\r\n"
          		+ "lab1 :       \r\n"
          		+ "lab2 :       \r\n"
          		+ "lab3 :       \r\n"
          		+ "mid-term :   \r\n"
          		+ "final exam : \r\n"
          		+ "�нT�{�s�t��\r\n"
          		+ "lab1 :       0%\r\n"
          		+ "lab2 :       10%\r\n"
          		+ "lab3 :       20%\r\n"
          		+ "mid-term :   30%\r\n"
          		+ "final exam : 40%\r\n"
          		+ "�H�W���T��? Y (Yes) �� N (No)\r\n"
          		+ "1) G ��ܦ��Z (Grade)\r\n"
          		+ "2) A ��ܥ��� (Average)\r\n"
          		+ "3) R ��ܱƦW (Rank)\r\n"
          		+ "4) W ��s�t�� (Weight)\r\n"
          		+ "5) E ���}��� (Exit)\r\n"
          		+ "��JID�� Q\r\n�����F\r\n"
          		+ "��JID�� Q\r\n�����F\r\n",outContent.toString());
          System.setOut(null);
        }      
/****************************************�X�ִ���*****************************************/			
	/*		Integration Test Case1
			�o�Ӵ��ե]�t�F�e����Case1���Ҧ�����
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
			�o�Ӵ��ե]�t�F�e����Case2���Ҧ�����
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
