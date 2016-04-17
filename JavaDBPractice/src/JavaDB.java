import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



public class JavaDB {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		insert();
		getData();
	}
	public static void getData() throws Exception{
		try{
			Connection con = connect();
			String query = "Select * from javaname";
			PreparedStatement get = con.prepareStatement(query);
			
			ResultSet result = get.executeQuery();
			
			ArrayList<String> data = new ArrayList<String>();
			
			while(result.next()){
				System.out.println(result.getString("ID") + " " + result.getString("FIRST_NAME") + " " + result.getString("LAST_NAME") );
				data.add(result.getString("ID"));
			}
		}catch(Exception e){System.out.print(e);}
	}
	
	public static void insert()throws Exception{
		final String var1 = "Kaleigh";
		final String var2 = "Smith";
		try{
			Connection con = connect();
			String query = "insert into javaname (ID, FIRST_NAME, LAST_NAME) values (null, '"+var1+"', '"+var2+"')";
			PreparedStatement insert = con.prepareStatement(query);
			insert.execute();

		}catch(Exception e){System.out.println("here" + e);}
		finally{
			System.out.print("completed");
		}
	}
	public static Connection connect() throws Exception{
		try{
			String driver ="com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/javatest";
			String username = "root";
			String password = "wesley94";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Good Connection");
			return conn;
		}catch(Exception e){
			System.out.println(e);
		}
		
		
		return null;
		
	}

}
