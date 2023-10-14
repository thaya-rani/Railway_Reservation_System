package railway_reservation;
import java.util.*;
class Passenger
{
    static int id=1;
    String name;
    int age;
    String gender;
    String childname=null;
    int childage=-1;
    String berth_preference;
    int seatno;
    int passenger_id=id++;
    String berth_allotted;
    public Passenger(String name,int age,String gender,String berth_preference)
    {
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.berth_preference=berth_preference;
        this.berth_allotted=" ";
        this.seatno=-1;
    }
    public Passenger(String name,int age,String gender,String childname,int childage,String berth_preference)
    {
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.childname=childname;
        this.childage=childage;
        this.berth_preference=berth_preference;
        this.berth_allotted=" ";
        this.seatno=-1;
    }
}
class TicketBook
{
    static int lower_berth=1;
    static int upper_berth=1;
    static int middle_berth=1;
    static int rac=1;
    static int waiting_list=1;
    
    //static List<Integer> lower_berth_positions=new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21));
    //static List<Integer> middle_berth_positions=new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21));
    //static List<Integer> upper_berth_positions=new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21));
    static List<Integer> lower_berth_positions=new ArrayList<Integer>(Arrays.asList(1));
    static List<Integer> middle_berth_positions=new ArrayList<Integer>(Arrays.asList(1));
    static List<Integer> upper_berth_positions=new ArrayList<Integer>(Arrays.asList(1));
    static List<Integer> rac_positions=new ArrayList<Integer>(Arrays.asList(1));
    static List<Integer> waiting_list_positions=new ArrayList<Integer>(Arrays.asList(1));
    //above are seat numbers and below indicates queue to shift while cancellation of ticket
    //bookedticked indicates the passenger_id for ticket cancellation
    
    static Queue<Integer> rac_queue=new LinkedList<Integer>();
    static Queue<Integer> waiting_queue=new LinkedList<Integer>();
    static List<Integer> BookedTickets=new ArrayList<Integer>();
    
    static Map<Integer,Passenger> passenger_db=new HashMap<Integer,Passenger>();
    //store passenger id,passenger object as a 1 unit,so several unit can be created
    
    
    public void bookticket(Passenger p,int seatno,String allotted)
    {
        p.seatno=seatno;
        p.berth_allotted=allotted;
        passenger_db.put(p.passenger_id, p);
        BookedTickets.add(p.passenger_id);
        System.out.println("Passenger Id:"+p.passenger_id);
        System.out.println("Passenger Name:"+p.name);
        System.out.println("Passenger Age:"+p.age);
        System.out.println("Passenger Gender:"+p.gender);
        System.out.println("Passenger Berth Allotted:"+seatno+" "+allotted);
        System.out.println("---------------BOOKED SUCCESFULLY--------------");
    }
    public void racticket(Passenger p,int seatno,String allotted)
    {
        p.seatno=seatno;
        p.berth_allotted=allotted;
        passenger_db.put(p.passenger_id, p);
        BookedTickets.add(p.passenger_id);
        rac_queue.add(p.passenger_id);//adding in queue
        System.out.println("Passenger Id:"+p.passenger_id);
        System.out.println("Passenger Name:"+p.name);
        System.out.println("Passenger Age:"+p.age);
        System.out.println("Passenger Gender:"+p.gender);
        System.out.println("Passenger Berth Allotted:"+seatno+" "+allotted);
        System.out.println("------------RAC ALLOTTED-----------------");
    }
    public void waitingticket(Passenger p,int seatno,String allotted)
    {
        p.seatno=seatno;
        p.berth_allotted=allotted;
        passenger_db.put(p.passenger_id, p);
        BookedTickets.add(p.passenger_id);
        waiting_queue.add(p.passenger_id);//adding in queue
        System.out.println("Passenger Id:"+p.passenger_id);
        System.out.println("Passenger Name:"+p.name);
        System.out.println("Passenger Age:"+p.age);
        System.out.println("Passenger Gender:"+p.gender);
        System.out.println("Passenger Berth Allotted:"+seatno+" "+allotted);
        System.out.println("---------------YOU ARE IN WAITING LIST--------------");
    }
    //=======================================CASE 3================================
    public void Passeneger_details()
    {
        if(passenger_db.size()==0)
        {
            System.out.println("No datas are available");
            return;
        }
        else
        {
            for(Passenger p :passenger_db.values())
            {
                System.out.println("----------------------------------------------------------");
                System.out.println("Passenger Id:"+p.passenger_id);
                System.out.println("Passenger Name:"+p.name);
                System.out.println("Passenger Age:"+p.age);
                System.out.println("Passenger Gender:"+p.gender);
                System.out.println("Passenger Berth Allotted:"+p.seatno+" "+p.berth_allotted);
                System.out.println("----------------------------------------------------------");
            }
        }
    }
    public void cancelTicket(int id)
    {
        Passenger p=passenger_db.get(id);
        passenger_db.remove(id);//removing from db
        BookedTickets.remove(id);//removing from list contains passenger id
        
        System.out.println("---------------------Ticket Cancelled-----------------------------");
        
        int temp_seatno=p.seatno;
        if(p.berth_allotted.toUpperCase().equals("L"))
        {
            lower_berth_positions.add(temp_seatno);
            lower_berth++;
        }
        else if(p.berth_allotted.toUpperCase().equals("M"))
        {
            middle_berth_positions.add(temp_seatno);
            middle_berth++;
        }
        else if(p.berth_allotted.toUpperCase().equals("U"))
        {
            upper_berth_positions.add(temp_seatno);
            upper_berth++;
        }
        
        if(rac_queue.size()>0)
        {
         Passenger pfromrac=passenger_db.get(rac_queue.poll());//poll return and remove first memeber in queue
         int tempseatnum=pfromrac.seatno;
         rac_positions.add(tempseatnum);//so it will be available for others to book rac
         //rac_queue.remove(pfromrac.passenger_id);
         rac++;
         if(waiting_queue.size()>0)
         {
             Passenger pfromwac=passenger_db.get(waiting_queue.poll());
             int tempseat=pfromwac.seatno;
             waiting_list_positions.add(tempseat);//so it will be available for others to book rac
             //waiting_queue.remove(pfromrac.passenger_id);
             
             pfromwac.seatno=rac_positions.get(0);
             pfromwac.berth_allotted="RAC";
             rac_positions.remove(0);
             rac_queue.add(pfromwac.passenger_id);
             waiting_list++;
             System.out.println("Passenger Id:"+pfromwac.passenger_id);
             System.out.println("Passenger Name:"+pfromwac.name);
             System.out.println("Passenger Age:"+pfromwac.age);
             System.out.println("Passenger Gender:"+pfromwac.gender);
             System.out.println("Passenger Berth Allotted:"+pfromwac.seatno+" "+pfromwac.berth_allotted);
             System.out.println("---------------RAC ALLOTTED--------------");
             rac--;
         }
         Railway_reservation.bookTicket(pfromrac);
         
        }
    }
    public void AvailableTickets()
    {
      System.out.println("Available Lowerberth:"+lower_berth);
      System.out.println("Available Middleberth:"+middle_berth);
      System.out.println("Available Upperberth:"+upper_berth);      
      System.out.println("Available RAC:"+rac);
      System.out.println("Available Waiting list:"+waiting_list); 
    }
}
public class Railway_reservation 
{
    public static void cancelTicket(int id)
    {
        TicketBook tb=new TicketBook();
        if(!tb.passenger_db.containsKey(id))
        {
            System.out.println("No such Passenger Id");
        }
        else
        {
            tb.cancelTicket(id);
        }
    }
    public static void bookTicket(Passenger p)
    {
        TicketBook tb=new TicketBook();
        //------------------------------WAITING LIST ALLOCATIONS--------------------------
        if(TicketBook.waiting_list==0)
        {
            System.out.println("No Tickets Available");
            return;
        }
        //------------------------------BERTH ALLOCATIONS----------------------------------
        else if(p.age>60 && TicketBook.lower_berth>0)
        {
            System.out.println("As you are a Senior citizen,Lower berth is allotted for your convenience");
            tb.bookticket(p, TicketBook.lower_berth_positions.get(0), "L");
            TicketBook.lower_berth_positions.remove(0);
            TicketBook.lower_berth--;
        }
        else if(p.childname!=null && p.childage!=-1)
        {
            System.out.println("Lower berth is allotted for your convenience");
            tb.bookticket(p, TicketBook.lower_berth_positions.get(0), "L");
            TicketBook.lower_berth_positions.remove(0);
            TicketBook.lower_berth--;
        }
        else if( (p.berth_preference.toUpperCase().equals("L")&& TicketBook.lower_berth>0) || (p.berth_preference.toUpperCase().equals("U")&& TicketBook.upper_berth>0) || (p.berth_preference.toUpperCase().equals("M")&& TicketBook.middle_berth>0))
        {
            if(TicketBook.lower_berth>0)
            {
                System.out.println("Lower berth is allotted");
                tb.bookticket(p, TicketBook.lower_berth_positions.get(0), "L");
                //in lowerberth list has element 1 2 3 when i get value at index 0->it returns seat num as 0
                //now I remove element at index 0->list becomes 2,3->now element at index 0 become 2
                //so get 0 work for every new allocation of lower berth
                TicketBook.lower_berth_positions.remove(0);
                TicketBook.lower_berth--;
            }
            else if(TicketBook.middle_berth>0 )
            {
                System.out.println("Middle berth is allotted");
                tb.bookticket(p, TicketBook.middle_berth_positions.get(0), "M");
                TicketBook.middle_berth_positions.remove(0);
                TicketBook.middle_berth--; 
            }
            else if(TicketBook.upper_berth>0)
            {
                System.out.println("Upper berth is allotted");
                tb.bookticket(p, TicketBook.upper_berth_positions.get(0), "U");
                TicketBook.upper_berth_positions.remove(0);
                TicketBook.upper_berth--; 
            }
            
        }
        //-----------------------AVAILABLE TICKETS-----------------------------
        else if(TicketBook.lower_berth>0)
        {
            System.out.println("Lower berth is alloted");
            tb.bookticket(p, TicketBook.lower_berth_positions.get(0), "L");
            TicketBook.lower_berth_positions.remove(0);
            TicketBook.lower_berth--;
        }
        else if(TicketBook.middle_berth>0 )
        {
            System.out.println("Middle berth is alloted");
            tb.bookticket(p, TicketBook.middle_berth_positions.get(0), "M");
            TicketBook.middle_berth_positions.remove(0);
            TicketBook.middle_berth--; 
        }
        else if(TicketBook.upper_berth>0)
        {
            System.out.println("Upper berth is alloted");
            tb.bookticket(p, TicketBook.upper_berth_positions.get(0), "U");
            TicketBook.upper_berth_positions.remove(0);
            TicketBook.upper_berth--; 
        }
        else if(TicketBook.rac>0 )
        {
            System.out.println("Rac is alloted");
            tb.racticket(p, TicketBook.rac_positions.get(0), "RAC");
            TicketBook.rac_positions.remove(0);
            TicketBook.rac--; 
        }
        else if(TicketBook.waiting_list>0)
        {
            System.out.println("Waiting list");
            tb.waitingticket(p, TicketBook.waiting_list_positions.get(0), "WL");
            TicketBook.waiting_list_positions.remove(0);
            TicketBook.waiting_list--; 
        }
    }
    public static void main(String[] args) 
    {
      boolean loop=true;
      int choice;
      Scanner ob=new Scanner(System.in);
      
      while(loop)
      {
          System.out.println("Choose the option:\n1.Book your ticket\n2.Cancel\n3.Booked Tickets\n4.Available Tickets\n5.Exit");
          choice=ob.nextInt();
          switch(choice)
          {
              case 1:System.out.println("Enter passenger name:");
                     String name=ob.next();
                     System.out.println("Enter passenger age:");
                     int age=ob.nextInt();
                     System.out.println("Enter passenger gender[M/F]:");
                     String gender=ob.next();
                     String child="N",childname=null;
                     int childage=-1;
                     if(gender.equals("F") && age>18)
                     {
                         System.out.println("enter Y ,if you have a children below age of 5");
                         child=ob.next();
                         if(child.equals("Y"))
                         {
                           System.out.println("Enter child name:");
                           childname=ob.next();
                           System.out.println("Enter child age:");
                           childage=ob.nextInt();  
                         }
                     }
                     System.out.println("Enter passenger berth preference[U/L/M]:");
                     String berth_preference=ob.next();
                     Passenger p;//intailize within if else are considered as wrong..
                     if(child.equals("Y"))
                     {
                     p=new Passenger(name,age,gender,childname,childage,berth_preference);
                     }
                     else
                     {
                     p=new Passenger(name,age,gender,berth_preference);
                     }
                     bookTicket(p);
                     break;
              case 2:System.out.println("Enter Passenger Id:");
                     int id=ob.nextInt();
                     cancelTicket(id);
                     break;
              case 3:TicketBook tb=new TicketBook();
                     tb.Passeneger_details();
                     break;
              case 4:TicketBook tb1=new TicketBook();
                     tb1.AvailableTickets();
                     break;
              case 5:loop=false;
                     break;
              default:System.out.println("Please check your choice");
          }
      }
    }   
}
