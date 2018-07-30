
import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class Admin {
	
	String filename = "/home/aman/Desktop/Studentdata.db";
	void createStudent(Scanner scc,Connection c,Statement st) {
		System.out.print("Enter Id: ");
		String Id = scc.next();
		System.out.print("Enter First Name: ");
		String FName = scc.next();
		System.out.print("Enter Last Name: ");
		String LName = scc.next();
		System.out.print("Enter Date of Birth (format:DDMMYYYY) : ");
		String DOB = scc.next();
		System.out.print("Enter Contact: ");
		String contact = scc.next();
		Student s = new Student(Id,FName,LName,DOB,contact);
		try {
			storeStudent(s,c,st);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	void storeStudent(Student s,Connection c,Statement st) throws Exception {
			if(s.verifyId(s.Id,c,st)){
				System.out.println("Id Already present Try another Id");
				return;
			}
		String stt = "Insert into student(Id,FName,LName,DOB,contact) values('"+s.Id+"','"+s.FName+"','"+s.LName+"','"+s.DOB+"','"+s.contact+"')";
		st.executeUpdate(stt);
		System.out.println("Student Data Successfully added");
	}
	
	
	
	void listALL(Connection c,Statement st) throws Exception {
		File f = new File(filename);
		ResultSet r;
		if(!f.exists()) {
			System.out.println("<<<<NO DATA AVAILABLE>>>>");
		}
		else {
			r = st.executeQuery("select * from student");
			System.out.println("Id\tFirstName\tLastName\tDOB\tcontact");
			while(r.next()) {
				String FName = r.getString("FName");
				String LName = r.getString("LName");
				String Id = r.getString("Id");
				String DOB = r.getString("DOB");
				String contact = r.getString("contact");
				System.out.print(Id+"\t");
				System.out.print(FName+"\t");
				System.out.print(LName+"\t");
				System.out.print(DOB+"\t");
				System.out.print(contact+"\n");
			}
		}
	}
	
	
	
	void delete(String Id,Connection c,Statement st) {
		Student s = new Student();
		boolean v = false;
		try{
			v = s.verifyId(Id,c,st);
		}catch(Exception e){e.printStackTrace();}
		if(v==false) {
			System.out.println("No record found for Student ID:"+Id);
		}
		else {
			try {
				st.execute("delete from student where id ='"+Id+"';");
				System.out.println("Student Id : "+Id+" Deleted");
			}catch(Exception e) {e.printStackTrace();System.out.println("Error Unable to delete\nDatabase Busy");}
		}
	}
}