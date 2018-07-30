import java.sql.*;
import java.util.Scanner;
public class Student {
	String FName,LName,Id,DOB,contact;
	String filename = "/home/aman/Desktop/Studentdata.db";
	
	Student(){}
	
	Student(String Id,String FName,String LName,String DOB,String contact){
		 this.Id = Id;
		 this.FName = FName;
		 this.LName = LName;
		 this.DOB = DOB;
		 this.contact = contact;
	}
	
	
	void get_data(String Id,Connection c,Statement st) throws Exception{
		ResultSet r = st.executeQuery("select * from student where Id='"+Id+"'");
		System.out.println("Id\tFirstName\tLastName\tDOB\tcontact");
		while(r.next()) {
			FName = r.getString("FName");
			LName = r.getString("LName");
			Id = r.getString("Id");
			DOB = r.getString("DOB");
			contact = r.getString("contact");
			System.out.print(Id+"\t");
			System.out.print(FName+"\t");
			System.out.print(LName+"\t");
			System.out.print(DOB+"\t");
			System.out.print(contact+"\n");
		}
	}
	
	
	boolean verifyId(String Id,Connection c,Statement st) throws Exception{
		ResultSet r = st.executeQuery("Select count(*) from student where id='"+Id+"'");
		if(r.getInt("count(*)")==0) {
			return false;	
		}
		return true;
	}
	
	void Update(String Id,Connection c,Statement st,Scanner s) throws Exception {
		System.out.println("\n 1.Update Name \n2.Update DOB\n3.Update contact\n4.back\n5.Logoutand Exit");
		Student stu;
		int n = s.nextInt();
		switch(n) {
		case 1 : System.out.print("Enter New First Name: ");
				String name = s.next();
				st.executeUpdate("Update student set FName = '"+name+"' where Id = '"+Id+"'");
				System.out.print("Enter New Last Name: ");
				name = s.next();
				st.executeUpdate("Update student set LName = '"+name+"' where Id = '"+Id+"'");
				System.out.println("<<<Information Updated>>>");
				stu = new Student();
				stu.get_data(Id,c,st);
				break;
		case 2 : System.out.print("Enter Date of Birth (format:DDMMYYYY) : ");
				String DOB = s.next();
				st.executeUpdate("Update student set DOB = '"+DOB+"' where Id = '"+Id+"'");
				System.out.println("<<<Information Updated>>>");
				stu = new Student();
				stu.get_data(Id,c,st);
				break;
		case 3 : System.out.print("Enter New contact: ");
				String contact = s.next();
				st.executeUpdate("Update student set contact = '"+contact+"' where Id = '"+Id+"'");
				System.out.println("<<<Information Updated>>>");
				stu = new Student();
				stu.get_data(Id,c,st);
				break;
		case 4 : return;
		
		case 5 : System.out.println("<<<Exiting>>>");
				System.exit(0);
		default: System.out.println("Invalid choice\n<<Exiting>>");
		}
	}
	
}