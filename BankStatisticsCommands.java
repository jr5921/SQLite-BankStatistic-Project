import java.util.Scanner;
class BankStatisticsCommands{
   public static void main(String [] args){
      boolean exit = false;
      Scanner scanner = new Scanner(System.in);
      
      System.out.println("Welcome to the bank statistics service!");
      while(!exit){
         System.out.println();
         System.out.println("1. An outline of users account balances");
         System.out.println("2. The year-to-date balance of each group");
         System.out.println("3. Number of specific transactions per month");
         System.out.println("4. Quit");
         System.out.print("Select this options based on the number : ");
         try{
         int option = scanner.nextInt();
            switch(option){
               case 1:
                  BankStatistic.selectUsers();
                  break;
               case 2:
                  BankStatistic.selectGroups();
                  break;
               case 3:
                  BankStatistic.selectTransactionTypes();
                  break;
               case 4:
                  exit = true;
                  break;
            }
         } catch (Exception e){
            System.out.println("You just make an error!");
            break;
         }
      }
      System.out.println("Thank you!");
   }
}