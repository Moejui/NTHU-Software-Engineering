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
	* �}�l����
	* 
	* Pseudo code:
	* 1.��ܿ�JID�ε���
	* 2.Ū����J
	* 3.�P�_��J
    *
	* Time estimate : O (1)
	* Example: start()�A��J1111111�A�P�_ID�F��JQ�A��ܵ����F
	----------------------------------------------------------------------------------------------------------*/
	public void start(){
		System.out.println("��JID�� Q");
		this.IDorQ = scanner.nextLine();
		if(this.IDorQ.equals("Q"))
			System.out.println("�����F");
		else
			this.checkIfExist(IDorQ);// ����IDorQ�|�OID�A�i�N�icheckIfExist
	}
	
	/* method: checkIfExist  ----------------------------------------------------------------------------------                                                                                                    
	* �ˬdID�O�_�s�b
	* 
	* @param ID  �n�ˬd��ID
	* 
	* Pseudo code:
	* 1.�qdata�h�j�M�A�P�_�O�_�s�b
	* 2.�Y�s�b�h����w�惡ID�A�æC�X���
	* 3.�Y���s�b�h���ID���F�A�^��}�l����
    *
	* Time estimate : O(n)
	* Example: chechIfExist(965002038) --> showList(3) & flag = 1   or   ID���F
	----------------------------------------------------------------------------------------------------------*/
	private void checkIfExist(String ID){
		int i, flag = 0;
		for(i = 0; i < data.size(); i++){
			if(ID.equals(data.get(i)[0])){
				System.out.println("Welcome " + data.get(i)[1]);	this.showList(i);	flag = 1;}
		}
		if(flag == 0)
			System.out.println("ID���F!");	this.start();// back to original start screen
	}
	
	/* method: showList  ----------------------------------------------------------------------------------                                                                                                    
	* ��椶��
	* 
	* @param id �ǥͦbdata�̪�id(index number)
	* 
	* Pseudo code:
	* 1.�N���C�L�X��
	* 2.Ū����J
	* 3.�Nid �P�ڭ̿�J���ﶵ�ǤJchooseList()
    *
	* Time estimate : O (1)
	* Example: showList(3), ��J(R) --> chooseList(3, R);
	----------------------------------------------------------------------------------------------------------*/
	private void showList(int id){
		String choose;
		System.out.println("1) G ��ܦ��Z (Grade)");
		System.out.println("2) A ��ܥ��� (Average)");
		System.out.println("3) R ��ܱƦW (Rank)");
		System.out.println("4) W ��s�t�� (Weight)");
		System.out.println("5) E ���}��� (Exit)");
		choose = scanner.nextLine();
		chooseList(id, choose);
	}
	
	/* method: chooseList  ----------------------------------------------------------------------------------                                                                                                    
	* �P�_��J
	* 
	* @param id �ǥͦbdata�̪�id
	* @param choose ���e��J�����O
	* 
	*Pseudo code:
	*1.�P�_�O���OG or R or W or E
	*2.Ū����J
	*3.�Nid �M��J�ǤJchooseList()
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
			System.out.println("���O���F!");
			showList(id);
		}
	}
	/* method: showGrades  ----------------------------------------------------------------------------------                                                                                                    
	* ��ܦ��Z
	* 
	* @param id �ǥͦbdata�̪�id
	* 
	* Pseudo code:
	* �v�@�L�X�U�����Z�]���t�������Z�^
    *
	* Time estimate : O(1)
	* Example: showGrades(3) --> �L�XĬ���k�����Z, showList(3) ;
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
	* ��ܥ[�v�������Z
	* 
	* @param id �ǥͦbdata�̪�id
	* 
	* Pseudo code:
	* �L�X�[�v�������Z
    *
	* Time estimate : O(1)
	* Example: showGrades(3) --> �L�XĬ���k���������Z, showList(3) ;
	----------------------------------------------------------------------------------------------------------*/
	private void showAverage(int id){
		System.out.println("Average : " + data.get(id)[7] + ((Integer.parseInt(data.get(id)[7])<60) ? "*" : ""));
		showList(id);
	}
	/* method showRank  ----------------------------------------------------------------------------------                                                                                                    
	* ��ܦ��Z
	* 
	* @param id �ǥͦbdata�̪�id
	* 
	* Pseudo code:
	* ��Grades�̬d�߱ƦW
    *
	* Time estimate : O(n)
	* Example: showRank(3) --> �L�Xgrades.getRank(3), showList(3) ;
	----------------------------------------------------------------------------------------------------------*/
	private void showRank(int id){
		System.out.println(data.get(id)[1] + "�G�ƦW��" + grades.getRank(id));
		showList(id);
	}
	
	/* method: reCalculate  ----------------------------------------------------------------------------------                                                                                                    
	* ��s�t��
	* 
	* @param id �ǥͦbdata�̪�id
	* 
	* Pseudo code:
	* 1.��ܲ{�b�t��
	* 2.�n�D��J�s�t��
	* 3.�n�D�T�{�s�t��
	* 4.��s�`���Z
    *
	* Time estimate : O(n)
	* Example: reCalculate(3), ��J(0.2, 0.2, 0.2, 0.2, 0.2) --> grades.calculateTotalGrades(0.2, 0.2, 0.2, 0.2, 0.2)
	*           , showList(3) ;
	----------------------------------------------------------------------------------------------------------*/
	private void reCalculate(int id){
		showNowWeights();
		inputNewWeights();
		String yesOrNo = checkNewWeights();
		if(yesOrNo.equals("Y")){ //��~�u�����ƶǶiGrades.java���L����
			grades.calculateGrades(weights[0], weights[1], weights[2], weights[3], weights[4]);
			this.data = grades.getNewData();
			showList(id);
		}
		else
			reCalculate(id); //recursive~
	}
	
	/* method showNowWerghts  ----------------------------------------------------------------------------------                                                                                                    
	* ��ܥثe�t���A���H�T�{
	* 
	* Pseudo code:
	* ��ܥثe�t��
    *
	* Time estimate : O(1)
	* Example: showNowWeights()�A�L�X�ثe�t�� ;
	----------------------------------------------------------------------------------------------------------*/
	public void showNowWeights(){
		System.out.println("�°t��");
		System.out.println("lab1 :       " + (int)(weights[0] * 100) + "%");
		System.out.println("lab2 :       " + (int)(weights[1] * 100) + "%");
		System.out.println("lab3 :       " + (int)(weights[2] * 100) + "%");
		System.out.println("mid-term :   " + (int)(weights[3] * 100) + "%");
		System.out.println("final exam : " + (int)(weights[4] * 100) + "%");
	}
	
	/* method inputNowWerghts  ----------------------------------------------------------------------------------                                                                                                    
	* ��J�s�t��
	* 
	* Pseudo code:
	* ��J�s�t��
    *
	* Time estimate : O(1)
	* Example: inputNewWeights()�A�n�D��J�s�t�� ;
	----------------------------------------------------------------------------------------------------------*/
	public void inputNewWeights(){
		double[] temp = new double[5];
		System.out.println("��J�s�t��");
		System.out.println("lab1 :       ");	temp[0] = scanner.nextDouble();
		System.out.println("lab2 :       ");	temp[1] = scanner.nextDouble();
		System.out.println("lab3 :       ");	temp[2] = scanner.nextDouble();
		System.out.println("mid-term :   ");	temp[3] = scanner.nextDouble();
		System.out.println("final exam : ");	temp[4] = scanner.nextDouble();
		for(int i = 0; i < 5; i++)
			weights[i] = temp[i] / (temp[0] + temp[1] + temp[2] + temp[3] + temp[4]);
	}
	
	/* method checkNowWerghts  ----------------------------------------------------------------------------------                                                                                                    
	* �T�{�s�t��
	* 
	* @return String Y��N
	* 
	* Pseudo code:
	* 1.�L�X�s�t��
	* 2.�n�D��J�O�_���T�A�Y���O�h���s��J
    *
	* Time estimate : O(1)
	* Example: checkNewWeights()�A�n�D�T�{�s�t�� ;
	----------------------------------------------------------------------------------------------------------*/
	public String checkNewWeights(){
		String yesOrNo;
		System.out.println("�нT�{�s�t��");
		System.out.println("lab1 :       " + (int)(weights[0] * 100) + "%");
		System.out.println("lab2 :       " + (int)(weights[1] * 100) + "%");
		System.out.println("lab3 :       " + (int)(weights[2] * 100) + "%");
		System.out.println("mid-term :   " + (int)(weights[3] * 100) + "%");
		System.out.println("final exam : " + (int)(weights[4] * 100) + "%");
		System.out.println("�H�W���T��? Y (Yes) �� N (No)");
		yesOrNo = scanner.nextLine();
		while(!yesOrNo.equals("Y") && !yesOrNo.equals("N"))
			yesOrNo = scanner.nextLine();
		return yesOrNo;
	}
	
	/* method Exit  ----------------------------------------------------------------------------------                                                                                                    
	* ���}���
	* 
	* Pseudo code:
	* �^��}�l����
    *
	* Time estimate : O(1)
	* Example: Exit() --> start();
	----------------------------------------------------------------------------------------------------------*/
	private void Exit(){
		start();
	}
}
