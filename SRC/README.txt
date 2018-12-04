To compile and execute the code in Linux platform follow the below instructions:

1. Make sure you have Java Development Kit(jdk).If you do not have this, execute following command to install the jdk:

   sudo apt-get install openjdk-7-jdk         

   After entering this command in terminal, you will be asked your password. Enter your password to enable installation.

2. After the installation of the jdk, from the terminal go to the directory that contains the project folder. Go to SRC/src directory.
   As you are in SRC/src directory,execute the following command to compile all five java files:

   javac -cp ".:../dist/lib/*" rdfquery/*.java

3. As you execute this command, five different compiled classes will be generated. Finally execute the following command to run Main file  

   java -cp ".:../dist/lib/*" rdfquery/Main

   This will run our Main file and eventually in the SRC/src/ folder you will see five different output files
   (computw16a3_q1_Atakishiyev.tsv,computw16a3_q2_Atakishiyev.tsv,computw16a3_q3_Atakishiyev.tsv,computw16a3_q4_Atakishiyev.tsv,
   computw16a3_q5_Atakishiyev.tsv) generated for each of five code files.



To compile and execute the code in OS X platform follow the below instructions.This is very similar to instructions for Linux Platform.

1. Open terminal and go to the directory that contains the project folder. Go to SRC/src directory.
   Execute following command to compile all five java files:

   javac -cp ".:../dist/lib/*" rdfquery/*.java

2. As you execute this command, five different compiled classes will be generated. Finally execute the following command to run Main file in command line:
 
   java -cp ".:../dist/lib/*" rdfquery/Main

   This will run the Main file and eventually in the SRC/src/ folder you will see five different output files
   (computw16a3_q1_Atakishiyev.tsv,computw16a3_q2_Atakishiyev.tsv,computw16a3_q3_Atakishiyev.tsv,computw16a3_q4_Atakishiyev.tsv,
   computw16a3_q5_Atakishiyev.tsv) that are generated for each of the five requirements.

 





 

 
 