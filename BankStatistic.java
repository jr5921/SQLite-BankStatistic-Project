import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Bank Statistics
 * @author Joshua Randolph
 */
class BankStatistic{

   /**
    * Make a connection to the "back.db"
    */
   private static Connection connect(){
      String url = "jdbc:sqlite:bank.db";
      Connection conn = null;
      try {
         conn = DriverManager.getConnection(url);
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      return conn;
   }

   /**
    * Get the result set that execute the query
    * @param String query
    */
   private static ResultSet executeQuery(String query) throws SQLException{
      Connection conn = connect();
      Statement stmt  = conn.createStatement();
      ResultSet rs    = stmt.executeQuery(query);
      return rs;
   }

   /**
    * Selected statistics of the active users
    * Statistic is the outline of users account balances
    */
   public static void selectUsers(){
      try{
         ResultSet user = executeQuery("SELECT (user.first_name || \" \" || user.last_name) AS user_name, " + 
         "strftime('%m', user_transaction.timestamp) as month, SUM(user_transaction.amount) AS account_balance " +
         "from user LEFT JOIN user_transaction ON user.id = user_transaction.user_id " +
         "LEFT JOIN transaction_type ON user_transaction.transaction_type_id = transaction_type.id " +
         "LEFT JOIN group_membership ON user.id = group_membership.user_id " +
         "LEFT JOIN group_type ON group_membership.group_type_id = group_type.id " +
         "WHERE user.status == 'A' and (transaction_type.status == 'A' OR transaction_type.status IS NULL) " +
         "AND (group_type.status == 'A' or group_type.status IS NULL) group by user_name, month;");
         while(user.next()){
            System.out.printf("%s gain $%.2f in %s\n", user.getString("user_name"), user.getDouble("account_balance"), 
               month(user.getInt("month"))) ;
         }
      } catch (Exception e){
         System.out.println(e.getMessage());
      }
   }
   
   /**
    * Selected statistics of the active groups
    * Statistics is year-to-date balance of each group
    */
   public static void selectGroups(){
      try{
         ResultSet groups = executeQuery("SELECT group_type.display_name, " +
         "sum(user_transaction.amount) AS total from group_type LEFT JOIN group_membership ON group_type.id = group_membership.group_type_id " +
         "LEFT JOIN user ON group_membership.user_id = user.id LEFT JOIN user_transaction ON user.id = user_transaction.user_id " +
         "LEFT JOIN transaction_type ON user_transaction.transaction_type_id = transaction_type.id " +
         "WHERE group_type.status == 'A' AND (user.status == 'A' OR user.status IS NULL ) AND" +
         " (transaction_type.status == 'A' OR transaction_type.status IS NULL)" +
         "GROUP BY group_type.name;");
         while(groups.next()){
            System.out.printf("%s group gain $%.2f\n", groups.getString("display_name"), groups.getDouble("total")) ;
         }
      } catch (Exception e){
         System.out.println(e.getMessage());
      }
   }
   
   /**
    * Selected statistics of the active transaction types
    */
   public static void selectTransactionTypes(){
      try{
         ResultSet groups = executeQuery("SELECT transaction_type.display_name AS transaction_name, strftime('%m', user_transaction.timestamp)as month," +
         " count(*) AS num_of_transactions from transaction_type LEFT JOIN user_transaction ON transaction_type.id = user_transaction.transaction_type_id" +
         " LEFT JOIN user ON user_transaction.user_id = user.id LEFT JOIN group_membership ON user.id = group_membership.user_id " +
         "LEFT JOIN group_type ON group_membership.group_type_id = group_type.id " +
         "WHERE transaction_type.status == 'A' AND (user.status == 'A' or user.status IS NULL) AND (group_type.status == 'A' OR group_type.status IS NULL) " +
         "GROUP BY transaction_name, month;");
         while(groups.next()){
            System.out.printf("%d of %s transactions in %s\n", groups.getInt("num_of_transactions"), groups.getString("transaction_name"), 
               month(groups.getInt("month"))) ;
         }
      } catch (Exception e){
         System.out.println(e.getMessage());
      }
   }
   
   private static String month(int month){
      try{
         String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
         "October", "November", "December"};
         return months[--month];
      } catch(Exception e){return "";}
   }

}