import java.util.Scanner;
import java.sql.*;
import java.io.*;

/**
 * The program convert the csv record into SQLite database
 *
 * @author Joshua Randolph
 */
public class SQLWriter {

    private String file;
    
    /**
     * Construct the SQLite writer
     * @param filename
     */
    public SQLWriter(String file){
         System.out.println("File name " + file);
         this.file = file;
         System.out.println("File name " + file);
    }
    
    /**
     * Insert the row into the SQLite
     * @param String row
     */
    public boolean insertRow(String row){
        
        String [] columns = row.split(",");
        String query = "INSERT INTO " + file +"(A,B,C,D,E,F,G,H,I,J) VALUES(?,?,?,?,?,?,?,?,?,?)";
        System.out.println(file);
        try{
            System.out.println(columns.length);
            if (columns.length < 10){
               throw new IllegalArgumentException("There is not enough columns for this row!");
            }
            Connection connection = this.connection();
            PreparedStatement statement = connection.prepareStatement(query);
            int count = 0;
            for (String column: columns){
                statement.setString(++count, column);
            }
            statement.executeUpdate();
            return true;
        } catch(Exception e){
            System.out.println("Error = " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get the connection
     */
    private Connection connection(){
        String url = "C:/sqlite/db/" + file + ".db";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /**
     * Run the scanning of <input-file>.csv
     */
    public static void main(String [] args){
    
        if (args.length != 1){
            throw new IllegalArgumentException("You need a file name!");
        } else {
        
            
            
            try{
                //call the scanner for <input-file>.csv
                System.out.println("Calling " + args[0]+".csv ... ");
                Scanner scanner = new Scanner(new File(args[0]+".csv"));
            
                //construct the writer for <input-file>-bad.csv SQL
                FileWriter fileWriter = new FileWriter(args[0]+"-bad.csv");
                PrintWriter printWriter = new PrintWriter(fileWriter);
                
                SQLWriter sqlWriter = new SQLWriter(args[0]);
            
                //scan through the <input-file>.csv and attempt to write SQL query
                int successes = 0;
                int failures = 0;
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    System.out.println("Line " + line);
                    if (sqlWriter.insertRow(line)){
                       successes += 1;
                    } else {
                       printWriter.write(line+"/n");
                       failures += 1;
                    }
                    System.out.println(scanner.hasNextLine());
                    
                }
                System.out.println("Success = " + successes);
                
                
            } catch(IOException e){
            
                System.out.println(e.getMessage());
                
            }
            
        }
        
        
    }
}