Student name: Jonathan Chiu
Student number: XXXXXXXXX


Please complete this README file for your level 3 mini-project submission.

If your level 1/2 submission did not get full marks you MUST complete the second part of this template describing how and where your have improved your code so that it now meets the level 1 and 2 requirements. If your code still does not meet level 1 and 2 requirements then you will get 0 for level 3.

LEVEL THREE

[To the marker: The main method is in the LoginGUI class. I have provided a login account to the Stock Trading Simulator, Username: admin, Password: admin]

GUI

[One of the buttons is the Execute Order Button which allows the user to buy shares in a stock. Before the order is made, a sequence of checks are used to ensure that the user has made a selection in the table of the desired stock they wish to buy, and it also checks that the user has sufficient funds in order to 
purchase the stock and there is also input validation (if the user enters alphabet letter and not numbers, then the program will tell the user that they entered something invalid). For example, if the user wanted to buy Bitcoin but has insufficient funds, some code will change the text in a GUI label to indicate that 
the user does not have the funds to buy this. If the user has sufficient funds, then the share will be "stored" in the user's transactions and can be sold later at a profit. The file names and line numbers: MainMenu, line 265-306.]


Exceptions

[Company, line 71, 74, 77]


[We must have some exception handling when making use of the API calls in order to get the live prices for the company because, in some circumstances, the server where the API call request is sent to could be down and so without dealing with an error message after an API call will cause the program to crash since the program 
is expecting a live feed of company stock prices. Furthermore, there is a limit to the number of API calls I can make by using the twelve data API to make calls from, once the limit is reached, a good program should run no matter what (defensive programming), so if an exception is thrown, then the program will default to using an 
algorithm to determine the stock price of the company in the scenarios I've mentioned above.]


Collections

[Transactions, line 94]


[The use of the ArrayList are crucial in my implementation of an ADT in order to be able to keep the details of which stocks the user has purchased, this ADT is used to display these details to the user in the ViewPortfolioGUI. The implementation involves using three different ArrayList in the ADT that allows the program to store an 
ArrayList of Stock subclass instances, the number of shares purchased, and the price at which the user bought each share. As a result of multiple ArrayLists, the transaction ADT code implementation means that the program can calculate a stock's profitability to know when to sell the stock and if the user decides to sell all of their shares. 
I have destructively implemented code to remove the element that corresponds to the sold shares. ]



File I/O

[I have made use of input and output of my program to ensure that users who have a real-money account, the program can retain the shares that the user has bought. The program will check if a text file for that user exists, and if not, the program will create a text file with the username and store details of the stock that the user has bought.
 If the user log backs onto the system, the program will read in the text file containing the bought stocks that correspond to their username. For example, if the user has bought some shares in Apple and then closes the program, I have used file input and output to see that they still have their Apple shares when they log back into the system. 
This feature would allow the user to continue making money if the prices dramatically increase in one of their stocks in a few weeks as the program can remember the shares they bought and the amount they paid to purchase them. Furthermore, input and output are also used to store
accounts into a text file so that the system can check if the user has entered the correct credentials when logging in.]

'Something impressive'

[My "wow" factor involves making use of reading real live data for those that are an instance of the company class, currency class and cryptocurrency class (I had to limit the api call interval to every 30 seconds due to the API call limit of 8 per minute). I was able to get real live data by using the twelve data website, which required me to obtain a developer account 
to get access to an API key so that I could access the data of a particular company. The API calls are done using a URL link that uses the company's name abbreviation and the API key. Once the API call is made, I had to format the stock price in such a way (removing { and "" symbols) in order to make use of the stock price in the program (since JSON parsers are not 
allowed due to the need for external libraries). To create a connection between the program and the API call made, I used the URL class, which is part of the java collections library. I have used the java principles in the following lines: Company, lines 63-79 and Currency, lines 99-128.


With regards to OOP principles that go beyond the specification, my GUI code involves using JTable, DefaultTableModel and JScrollPane (which allows the user to navigate via a scrollbar through the table) to display the stocks. In order to prevent the user being able to edit a cell in the table, I had to override the isCellEditable 
method in the class NonEditableTable to return false so that the user cannot change the names of the stocks or their prices in the table. I have used these principles in the following classes: NonEditableTable, line 7 (Overrides the isCellEditable). MainMenu, line 110-117, line 188. ViewPortfolio, line 63-68]



LEVELS 1 AND 2

(ONLY complete this if you did not get full marks for your level 1/2 submission.)

LEVEL ONE

[Briefly (three sentences) explain the changes you have made to your code so that it fully meets the level one requirements.]

My code demonstrates inheritance in the following way...

I have a superclass called [Insert name of superclass here]

This superclass is extended into at least two subclasses called [Insert names of the subclasses here]

For each of the named subclasses complete the following...

Subclass 1.

Subclass [Insert name of subclass] extends the superclass by adding at least one property and its getters and setters. The name(s) of the added properties are [Insert names of properties here.]

These/this new properties/property are used by the subclass in the following way: [Insert justification for adding the property and give the line numbers in the code of where the property is used.]

Subclass [Insert name of subclass] extends the superclass by overriding the following methods (there must be at least one): [Insert names of overridden methods and their line numbers in the code.]

These overridden methods are used in the working code in the following places: [list the file names and line numbers where the overridden methods are called.]

Subclass 2.

Subclass [Insert name of subclass] extends the superclass by adding at least one property and its getters and setters. The name(s) of the added properties are [Insert names of properties here.]

These/this new properties/property are used by the subclass in the following way: [Insert justification for adding the property and give the line numbers in the code of where the property is used.]

Subclass [Insert name of subclass] extends the superclass by overriding the following methods (there must be at least one): [Insert names of overridden methods and their line numbers in the code.]

These overridden methods are used in the working code in the following places: [list the file names and line numbers where the overridden methods are called.]


LEVEL TWO

[Briefly (three sentences) explain the changes you have made to your code so that it fully meets the level two requirements.]

Polymorphism consists of the use of the Substitution principle and Late Dynamic binding.

In my code, polymorphism is implemented in at least two places…

Example 1.

The substitution principle can be seen in use in [class name and line number where substitution is used]. The name of the superclass used this example is [name of superclass] and the subclasses used are [names of subclasses].

Late dynamic binding can be seen in [class name and line number].

[Explain briefly (no more than four sentences), why this example of polymorphism is necessary in your code.]

Example 2.

The substitution principle can be seen in use in [class name and line number where substitution is used]. The name of the superclass used this example is [name of superclass] and the subclasses used are [names of subclasses].

Late dynamic binding can be seen in [class name and line number].

[Explain briefly (no more than four sentences), why this example of polymorphism is necessary in your code.]
