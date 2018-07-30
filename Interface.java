import java.sql.*;
import java.util.*;
import java.io.*;

public class Interface {
	void AdminInterface(Connection c,Statement st,Scanner sc) throws Exception {
		Admin admin = new Admin();
		while(true) {
			int n;
			System.out.println(" 1.Add Student\n 2.List ALL\n 3.Delete Student\n 4.Logout\n");
			System.out.println("Enter Choice:");
			if (sc.hasNextInt())
			n = sc.nextInt();
			else 
				n=4;
			switch(n) {
			case 1: admin.createStudent(sc,c,st);
					break;
			case 2: try{
					admin.listALL(c,st);		
					}catch (Exception e) {
						e.printStackTrace();
					}
					break;
			case 3: String id;
					System.out.print("Enter Student Id to Delete : ");
					id = sc.next();
					admin.delete(id,c,st);
					break;
			case 4: return;
			default: System.out.println("Invalid Choice!!!"); 
			}
		}
	}
	
	void StudentInterface(String Id,Connection c,Statement st,Scanner sc) {
		int n;
		Student s = new Student();
		while(true) {
			System.out.print(" 1.Show Info\n 2.Upadte Info\n 3.Logout");
			System.out.print("\nEnter Choice: ");
			n = sc.nextInt();
			switch(n) {
			case 1 : try{
						s.get_data(Id,c,st);
					}catch (Exception e) {
						e.printStackTrace();
					}
					break;
			case 2 : try {
						s.Update(Id,c,st,sc);
						break;
					}catch (Exception e) {
						System.out.println("Unable to update!!! Try Again");
					}
			case 3 : return;
			default : System.out.println("Invalid Choice");
			}
		}
	}
	
	public static void main(String[] a) throws Exception {
		String filename = "/home/aman/Desktop/Studentdata.db";
		File f = new File(filename);
		Connection c = null;
		Statement st = null;
		if(!f.exists()) {
			f.createNewFile();
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+filename);
			st = c.createStatement();
			st.executeUpdate("Create Table student(Id text NOT NULL,FName text NOT NULL,LName text,DOB text,contact text)");
		}else {
			if(c==null) {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:"+filename);
				st = c.createStatement();
			}
		}
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("1.Admin 2.Student 3.Exit");
			int n = sc.nextInt();
			Interface i = new Interface();
			String id,key;
			switch(n) {
			case 1 :System.out.print("Enter LoginID: ");
					id = sc.next();
					System.out.print("Enter Login key: ");
					key = sc.next();
					if(id.compareTo("admin")==0 && key.compareTo("admin")==0){
						try {
							i.AdminInterface(c,st,sc);
						}catch (Exception e) {e.printStackTrace();}
					}else {
						System.out.println("<<<<UNAUTHORIZED>>>>");
					}
					break;
			case 2 :System.out.print("Enter Id: ");
					id = sc.next();
					System.out.print("Enter password: ");
					key = sc.next();
					Student s = new Student();
					boolean v=false;
					try{v = s.verifyId(id,c,st);}catch(Exception e){}
					if(v==true)
						if(key.compareTo(id)==0)
							i.StudentInterface(id,c,st,sc);
						else {
							System.out.println("<<<UNAUTHORIZED>>>");
						}
					else {
						System.out.println("Student Id Not Found");
					}
					break;
			case 3 :System.out.println("<<<Exiting>>>"); 
					st.close();
					c.close();
					System.exit(0);
			default: System.out.println("Invalid Input");
			}
		}	
	}
}