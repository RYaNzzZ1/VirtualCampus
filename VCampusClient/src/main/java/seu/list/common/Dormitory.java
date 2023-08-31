//package main.java.seu.list.common;
package seu.list.common;

import java.io.Serializable;
import java.util.Vector;

public class Dormitory implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userID;  //学生姓名
	private String dormitoryID;  //宿舍号
	private int studentBunkID;  //床位
	private int water;  //水费
	private int electricity;  //电费
	private int dormitoryScore;  //卫生评分
	private String dormitoryMaintain;  //维修申请
	private String studentExchange;  //调换申请

	public Dormitory()
	{
        super();
    }

	public Dormitory(String userID,String DormitoryID, int StudentBunkID,int Water,int Electricity,int DormitoryScore,String DormitoryMaintain,String StudentExchange)
	{
		this.userID=userID;
        this.dormitoryID =DormitoryID;
        this.studentBunkID =StudentBunkID;
        this.water =Water;
        this.electricity =Electricity;
        this.dormitoryScore =DormitoryScore;
        this.dormitoryMaintain =DormitoryMaintain;
        this.studentExchange =StudentExchange;
    }

	public String getuserID()
	{
        return userID;
    }

    public void setuserID(String userID)
    {
        this.userID = userID;
    }

	public String getDormitoryID()
	{
        return dormitoryID;
    }

    public void setDormitoryID(String DormitoryID)
    {
        this.dormitoryID = DormitoryID;
    }

    public int getStudentBunkID()
	{
        return studentBunkID;
    }

    public void setStudentBunkID(int StudentBunkID)
    {
        this.studentBunkID =StudentBunkID;
    }

    public int getWater()
	{
        return water;
    }

    public void setWater(int Water)
    {
        this.water =Water;
    }

    public int getElectricity()
	{
        return electricity;
    }

    public void setElectricity(int Electricity)
    {
        this.electricity =Electricity;
    }

    public int getDormitoryScore()
	{
        return dormitoryScore;
    }

    public void setDormitoryScore(int DormitoryScore)
    {
        this.dormitoryScore =DormitoryScore;
    }

    public String getDormitoryMaintain()
	{
        return dormitoryMaintain;
    }

    public void setDormitoryMaintain(String DormitoryMaintain)
    {
        this.dormitoryMaintain =DormitoryMaintain;
    }

    public String getStudentExchange()
   	{
           return studentExchange;
       }

    public void setStudentExchange(String StudentExchange)
    {
    	this.studentExchange =StudentExchange;
    }

    @Override
    public String toString()
    {
        return "Dormitory{" + "userID="+userID+"DormitoryID=" + dormitoryID + ", StudentBunkID=" + studentBunkID +",Water="+ water +",Electricity="+ electricity +",DormitoryScore="+ dormitoryScore +",DormitoryMaintain="+ dormitoryMaintain +",StudentExchange="+ studentExchange +"}";
    }

    public Vector<String> getContent() {
		Vector<String> dormitoryContents = new Vector<String>();
		dormitoryContents.add(userID);
		dormitoryContents.add(dormitoryID);
		dormitoryContents.add(String.valueOf(studentBunkID));
    	//dormitoryContents.add(StudentBunkID);
		dormitoryContents.add((String.valueOf(water)));
    	//dormitoryContents.add(Water);
		dormitoryContents.add(String.valueOf(electricity));
    	//dormitoryContents.add(Electricity);
		dormitoryContents.add(String.valueOf(dormitoryScore));
    	//dormitoryContents.add(DormitoryScore);
		dormitoryContents.add(dormitoryMaintain);
    	//dormitoryContents.add(DormitoryMaintain);
		dormitoryContents.add(studentExchange);
    	//dormitoryContents.add(StudentExchange);
		return dormitoryContents;
	}

	public void setContent(Vector<String> content) {
		userID = content.get(0);
		dormitoryID = content.get(1);
    	studentBunkID = Integer.parseInt(content.get(2));
    	water = Integer.parseInt(content.get(3));
    	electricity = Integer.parseInt(content.get(4));
    	dormitoryScore = Integer.parseInt(content.get(5));
    	dormitoryMaintain = content.get(6);
    	studentExchange = content.get(7);
	}
}
