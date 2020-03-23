Note: The Operation System used is Windows and we use Cygwin

Application use 3 items. 2 of them are made by the programmer:
1. BankStatistic.java (API that provide functionality for command prompt tool)
2. BankStatisticsCommands.java (Command prompt tool)
3. sqlite-jdbc-3.30.1.jar (SQLite jar file)

These are the directions:
1. Go to the command prompt and go to the location of the three files
2. To create the class files, type javac BankStatistic.java and then type javac BankStatisticsCommands.java
3. Type java -classpath ".;sqlite-jdbc-3.30.1.jar" BankStatisticsCommands
4. You are given a menu and the menu is self-explanatory. Menu give you four options. Select the four based on a number.

The output for the menu is:
1. An outline of users account balances
2. The year-to-date balance of each group
3. Number of specific transactions per month
4. Quit
Note: If you do not selected any number, then an error will occur.

If you quit or have an error, then we give you "Thank you!" anyway.