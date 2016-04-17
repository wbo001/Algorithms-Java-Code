import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/*Simple program that was just me practicing on how to connect to a MYSQL database in java. 
 * This program attempts to establish a connection and then manipulates the data. 
 * Written by Wesley Olinger 4/16/2016
 */


public class JavaDB {
	//Main method that calls the other methods.
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		insert();
		getData();
	}
	//Method that receives data from the MYSQL database and stores it in an ArrayList
	public static void getData() throws Exception{
		try{
			//Connecting to DB
			Connection con = connect();
			//Query used to get the desired data from the DB. 
			String query = "Select * from javaname";
			PreparedStatement get = con.prepareStatement(query);
			//Reciving the data from the Query above.
			ResultSet result = get.executeQuery();
			
			ArrayList<String> data = new ArrayList<String>();
			//Printing the data to the screen also storing it into an arraylist
			while(result.next()){
				System.out.println(result.getString("ID") + " " + result.getString("FIRST_NAME") + " " + result.getString("LAST_NAME") );
				data.add(result.getString("ID"));
			}
			con.close();
		}catch(Exception e){System.out.print(e);}
	}
	//Method used for inserting data into the DB
	public static void insert()throws Exception{
		//Variables used to put data into the DB. 
		final String var1 = "";
		final String var2 = "";
		try{
			//Connecting to the DB
			Connection con = connect();
			//Query used to insert data into the DB
			String query = "insert into javaname (ID, FIRST_NAME, LAST_NAME) values (null, '"+var1+"', '"+var2+"')";
			PreparedStatement insert = con.prepareStatement(query);
			//Executing the query
			insert.execute();
			con.close();

		}catch(Exception e){System.out.println("here" + e);}
		finally{
			System.out.print("completed");
		}
	}
	//Method used for connection to the DB
	public static Connection connect() throws Exception{
		try{
			String driver ="com.mysql.jdbc.Driver";
			//The String tells the program where the DB is at. Mine Happens to be on my Local Machine
			String url = "jdbc:mysql://localhost:3306/javatest";
			//Credentials needed to log into the DB
			String username = "";
			String password = "";
			Class.forName(driver);
			//Attempting to connect to the DB
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Good Connection");
			return conn;
		}catch(Exception e){
			System.out.println(e);
		}
		
		
		return null;
		
	}

}
