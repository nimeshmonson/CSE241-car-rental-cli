//Nimesh Joseph Monson (nij419)
//Final Project
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.lang.*;


public class Hurts {
  private static int cid = -1; //stores the ID of 
  private static String cname = "";
  private static Statement s;
  private static Connection con;
  private static String inputString;
  private static boolean invalidInput=true;
  private static String q;
  private static ResultSet result;
  private static Scanner myScanner = new Scanner(System.in);
/*----------------------------------------------------------*/
//start of main function
  public static void main(String[] args) {
    //code to get input from the user for userid and password
    String userId = "";
    String pass = "";
    System.out.println("WELCOME TO HURTS RENT-A-LEMON CAR RENTAL COMPANY.\nTO CONTINUE PLEASE LOGIN USING YOUR ORACLE USER ID AND PASSWORD\n");
    System.out.print("Enter your Oracle user id: ");
    userId=myScanner.nextLine();
    System.out.print("Enter your Oracle password for " + userId +": ");
    pass = myScanner.nextLine(); 

    //try connecting to the SQL server
    try{
        Class.forName ("oracle.jdbc.driver.OracleDriver");
    }catch(Exception e){e.printStackTrace();}    
    
    try(Connection con1 = DriverManager.getConnection("jdbc:oracle:thin:@edgar0.cse.lehigh.edu:1521:cse241",userId,pass);
      Statement s1 = con1.createStatement();){
      //Connection has been made
      //Now getting if user or not user
      s = s1;
      con = con1;

      System.out.print("\nWhat type of user are you?\n1-Customer \n2-Administrative\nEnter the corresponding number: ");
      inputString = myScanner.nextLine();
 		invalidInput=true;

        while(invalidInput){
          if(inputString.equals("1")){
            invalidInput = false;
            CustomerInterface();
          }
          else if(inputString.equals("2")){
            invalidInput = false;
            System.out.println("In Administrative");
          }
          else{
            System.out.println("Invalid Input. Please re-enter.");
            System.out.print("\nWhat type of user are you?\n1-Customer \n2-Administrative\nEnter the corresponding number: ");
            inputString = myScanner.nextLine();
          }
        }    
        System.out.println("back in main");              
        con.close();
        con1.close();
    } catch(Exception e){e.printStackTrace();}
    
  }
  //end of main function
  /*----------------------------------------------------------*/
  /*----------------------------------------------------------*/
  //Start of Customer Interface
  public static void CustomerInterface(){
  	 invalidInput = true;
    System.out.print("\nAre you a:\n1-Existing User\n2-New User\nEnter the corresponding number:");
    inputString = myScanner.nextLine();
    while(invalidInput){
          if(inputString.equals("1")){
            invalidInput = false;
            ExistingUser();
          }
          else if(inputString.equals("2")){
            invalidInput = false;
            NewUser();
          }
          else{
            System.out.println("Invalid Input. Please re-enter.");
            System.out.println("\nAre you a:\n1-Existing User\n2-New User\nEnter the corresponding number:");
            inputString = myScanner.nextLine();
          }
        }
    return;
  }
  /*----------------------------------------------------------*/
  //Start of new user
  public static void NewUser(){
    String name, address, city, state,date, zip, year,month,day, license, phone, email;
    int discount_id;

    System.out.println("\nCreate an Account with Hurts RENT-A-LEMON Car rentals");
    System.out.print("\nEnter your Full Name (Limit 20 Characters): ");
    name = myScanner.nextLine();
    name = ValidateString(name,20);

    System.out.print("\nEnter your Address (Limit 20 Characters): ");
    address = myScanner.nextLine();
    address = ValidateAlphanumberic(address,20);

    System.out.print("\nEnter your City (Limit 20 Characters): ");
    city = myScanner.nextLine();
    city = ValidateString(city,20);

    System.out.print("\nEnter your State (Limit 20 Characters): ");
    state = myScanner.nextLine();
    state = ValidateString(state,20);


    System.out.print("\nEnter your ZIP (Limit 5 Characters): ");
    zip = myScanner.nextLine();
    zip = ValidateDigits(zip,5);
  	
  	 // System.out.print("\nEnter your Date of Birth (in the format of 19-JAN-2017): ");
  	 // dob = myScanner.next();
  	 // dob = ValidateDate(dob)

    System.out.print("\nEnter your License Number (Limit 10 Characters): ");
    license = myScanner.nextLine();
    license = ValidateDigits(license,10);

    System.out.print("\nEnter your Phone Number (Limit 10 Characters): ");
    phone = myScanner.nextLine();
    phone = ValidateDigits(phone,10);

    System.out.print("\nEnter your Email ID (Limit  35 Characters): ");
    email = myScanner.nextLine();
    email = ValidateAlphanumberic(email,35);

    String q;
    ResultSet result;
    q = "SELECT COUNT(*) FROM CUSTOMER";
    try{
    		result = s.executeQuery(q);
    		result.next();
    		cid = Integer.parseInt(result.getString("COUNT(*)"));
    		System.out.print("Work on this later");
    }catch(Exception e){e.printStackTrace();}

    return;
  }
  //End of new user
  /*----------------------------------------------------------*/
  /*----------------------------------------------------------*/
  //Start of Existing user
  public static void ExistingUser(){
  	String  inputName;
  	invalidInput = true;

  	while(invalidInput){
	   try{
	   	System.out.println("\nPlease Login with your Customer ID and Name");
         System.out.print("\nEnter your Customer ID: ");
		  	cid = Integer.parseInt(myScanner.nextLine());
		  	System.out.print("\nEnter your Name: ");
		   cname = myScanner.nextLine();
	   	q = "SELECT CUST_ID, NAME FROM CUSTOMER WHERE CUST_ID = "+ cid ;
	   	result = s.executeQuery(q);
	   	if(!result.next()){
	   		System.out.println ("No Matches. Try Again");
	   	}
	   	else{
	   		if(cname.toUpperCase().equals(result.getString("NAME").toUpperCase())){
	   			invalidInput = false;
	   			CustomerMenu();
	   		}
	   	}

	   }catch(Exception e){
	   	System.out.println ("\nSQL ERROR: Try Again!");
         System.out.print("\nEnter your Customer ID:");
		  	cid = Integer.parseInt(myScanner.nextLine());
		  	System.out.print("\nEnter your Name:");
		   cname = myScanner.nextLine();
	   }
  	}    
  	return;
  }
  //End of Existing user
  /*----------------------------------------------------------*/
  /*----------------------------------------------------------*/
  //Start of Customer Menu
  public static void CustomerMenu(){
  	System.out.println("\nWelcome " + cname + ". What would you like to do today?" );
  	invalidInput=true;
  	while(invalidInput){
	  	System.out.println("\n1) Make New Rental \n2) See Existing Rental \n3) Finish Existing Rental\n");
	  	int a = myScanner.nextInt();
  		switch(a){
	  		case 1:
	  				{
		  				MakeRental();
		  				invalidInput = false;
		  				break; 					
	  				}

	  		case 2:
	  				{
		  				SeeRental();
		  				invalidInput = false;
		  				break; 					
	  				}
	  		case 3:
	  				{
		  				FinishRental();
		  				invalidInput = false;
		  				break; 					
	  				}
	  		default:
	  				{
	  					System.out.println("Enter valid Input:");
	  				}

  		}
  	}
  	
  	return;
  }

  //End of Customer Menu
  /*----------------------------------------------------------*/

 /*----------------------------------------------------------*/
 //Start of MakeRental
 public static void MakeRental(){
 	String state="",location_id="",vehicle_id="", model="",make="",year="",start="",end="",insurance="", gps="", satelite="",surge="";
 	
 	a:{
 		System.out.println("What State would you like your Rental to be in?");
	 	q = "SELECT STATE FROM LOCATION GROUP BY STATE ORDER BY STATE";
	 	invalidInput = true;
	 	try{
	 		result = s.executeQuery(q);
	 		while(invalidInput){
				if(!result.next()){
						continue;
				}	
				else{
					do{
						System.out.println(result.getString("STATE"));
					}while(result.next());		
					invalidInput = false;		
				}
				state = myScanner.nextLine();
				state= ValidateString(state,20);
	 		}				
	 	}catch(Exception e){
	 		System.out.println("SQL ERROR: Try Again");
	 		break a;
	 	}
 	}
 	b:{
 		System.out.println("Select the location_id of the location in " + state);
 		q = "SELECT LOCATION_ID, ADDRESS, CITY FROM LOCATION WHERE STATE= '" +state+"' ORDER BY LOCATION_ID";
	 	invalidInput = true;
	 	try{
	 		result = s.executeQuery(q);
	 		while(invalidInput){
				if(!result.next()){
						continue;
				}	
				else{
					do{
						System.out.println(result.getString("LOCATION_ID")+")  "+result.getString("ADDRESS")+", " + result.getString("CITY"));
					}while(result.next());
					invalidInput = false;
				}
				location_id = myScanner.nextLine();
	 		}				
	 	}catch(Exception e){
	 		System.out.println("SQL ERROR: Try Again");
	 		break b;
	 	}

 	}
 	c:{
 		System.out.println("Select the Vehicle you want of the location in " + state);
 		q = "SELECT VEHICLE_ID,MAKE,MODEL,YEAR, TYPE BASE_CHARGE  FROM VEHICLE WHERE VEHICLE_LOCATION_ID = '" + location_id +"' ORDER BY VEHICLE_ID";
	 	invalidInput = true;
	 	try{
	 		result = s.executeQuery(q);
	 		while(invalidInput){
				if(!result.next()){
						continue;
				}	
				else{
					do{
						System.out.println(result.getString("VEHICLE_ID")+")  "+result.getString("MAKE") +" " +result.getString("MODEL")+ " " +result.getString("TYPE")+" "+ result.getString("YEAR") +" @ $" +result.getString("BASE_CHARGE"));
					}while(result.next());
				}
				vehicle_id = myScanner.nextLine();
				location_id = ValidateAlphanumberic(location_id,10);
	 		}				
	 	}catch(Exception e){
	 		System.out.println("SQL ERROR: Try Again");
	 		break c;
	 	}

 	}


 }
  //End of MakeRental
  /*----------------------------------------------------------*/
  /*----------------------------------------------------------*/
  //Start of SeeRental
  public static void SeeRental(){
  	q ="SELECT * FROM RESERVATION WHERE CUST_ID ='"+ cid +"'";
  	try{
	  	result = s.executeQuery(q);
	  	if (!result.next()){
	      System.out.println ("You currently have no reservations");
		}
	   else{
	      System.out.println ("Here is a list of all your Reservations");
         System.out.println(String.format("%1$8s %2$8s %3$15s %4$25s %5$17s %6$8s %7$8s %8$4s %9$9s %10$8s %11$8s", 
                             "RESERVE_ID",
                             "VEHICLE_ID",
                             "START_DATE",
                             "END_DATE",
                             "INSURANCE",
                             "CHILD_SEAT",
                             "SATELITE_PHONE",
                             "GPS",
                             "IS_SURGED",
                             "TOTAL_CHARGE",
                             "DROP_LOCATION"));
	      do{
	      	System.out.println(String.format("%1$4s %2$12s %3$25s %4$25s %5$8s %6$8s %7$10s %8$12s %9$6s %10$10s %11$13s",
                                       result.getString("RESERVE_ID"),
                                       result.getString("VEHICLE_ID"),
                                       result.getString("START_DATE"), 
                                       result.getString("END_DATE"), 
                                       result.getString("INSURANCE"), 
                                       result.getString("CHILD_SEAT"),
                                       result.getString("SATELITE_PHONE"),
                                       result.getString("GPS"),
                                       result.getString("IS_SURGED"),
                                       result.getString("TOTAL_CHARGE"),
                                       result.getString("DROP_LOCATION")));

	      }while (result.next());
	   }
  	}catch(Exception e){e.printStackTrace();}
  	return;
	}
  //End of SeeRental
  /*----------------------------------------------------------*/
  /*----------------------------------------------------------*/
  //Start of FinishRental
  public static void FinishRental(){
  	System.out.print("Enter the ");
		return;
	}
  //End of FinishRental
  /*----------------------------------------------------------*/

  //End of Customer interface
  /*----------------------------------------------------------*/

  /*----------------------------------------------------------*/
  //Start of helper functions
  public static String ValidateString(String x, int n){
    invalidInput = true;
    while(invalidInput){
      if(!x.matches("[a-zA-Z ]+")){
        System.out.print("\nInvalid Input. Please re-enter: ");
        x = myScanner.nextLine();
      }
      else if(x.length()>n){
        System.out.print("\nPlease enter an input with less than " + n + " Characters: ");
        x = myScanner.nextLine();
      }
      else{
        invalidInput = false;
      }
    }
    return x;
  }
	public static String ValidateDigits(String x, int n){
		invalidInput = true;
		while(invalidInput){
			if(!x.matches("[1-9]+")){
			  System.out.print("\nInvalid Input. Please re-enter: ");
			  x = myScanner.nextLine();
			}
	      else if(x.length()!=n){
        		System.out.print("\nPlease enter an input with " + n + " Characters: ");
        		x = myScanner.nextLine();
     		}
			else{
			  invalidInput = false;
			}
		}
		return x;
	}
	public static String ValidateAlphanumberic(String x, int n){
		invalidInput = true;
		while(invalidInput){
			if(!x.matches("[a-zA-Z1-9.@ ]+")){
			  System.out.print("\nInvalid Input. Please re-enter: ");
			  x = myScanner.nextLine();
			}
	      else if(x.length()>n){
        		System.out.print("\nPlease enter an input with less than " + n + " Characters: ");
        		x = myScanner.nextLine();
     		}
			else{
			  invalidInput = false;
			}
		}
		return x;
	}


  //End of helper functions
  /*----------------------------------------------------------*/
}


/*----------------------------------------------------------*/
  //Start of blah
  //End of blah
  /*----------------------------------------------------------*/