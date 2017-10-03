import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;

class Point {
  Point(int a, int b) {
    x = a;
    y = b;
  }
  public int x;
  public int y;

  public String toString() {
    return "(" + Integer.toString(x) + "," + Integer.toString(y) + ")";
  }
}

class Ticket {
  Ticket(double price) {
    m_price = price;
  }

  public double getPrice() {
    return m_price;
  }
  private double m_price;
}
class Event {
  Event(int id, ArrayList<Ticket> tickets, Point location) {
    uid = id;
    m_tickets = new ArrayList<Ticket>(tickets);
    Collections.sort(m_tickets, new Comparator<Ticket>(){
      @Override
      public int compare(Ticket a, Ticket b) {
        return Double.compare(a.getPrice(), b.getPrice());
      }
    });
    m_location = location;
  }

  public String toString() {
    String eventInfo = "Event id: " + Integer.toString(uid) + ", Number of Tickets: " + Integer.toString(m_tickets.size())
    + ", Location: " + m_location.toString();
    return eventInfo;
  }

  public double getCheapestTicketPrice() {
    if (m_tickets.size() > 0)
      return m_tickets.get(0).getPrice();
    return 999999.0;
  }

  public Point getLocation() {
    return m_location;
  }

  public int getId() {
    return uid;
  }

  public int getTicketNumber() {
    return m_tickets.size();
  }

  private int uid;
  private ArrayList<Ticket> m_tickets;
  private Point m_location;
}

public class ViagogoTest {

  private static ArrayList<Event> m_events = new ArrayList<Event>();
  private static int[][] visited = new int[20][20];

  private static boolean checkUniquePoint(int x, int y) {
    if (visited[x+10][y+10] != 0) {
      return false;
    } else {
      return true;
    }
  }

  /*** fill m_events with random events ***/
  private static void generateSeedData() {
    //randomly generate 20 events
    System.out.println("Generating Events");
    for (int i = 0; i < 20; i++) {
      //generate random number of tickets first
      ArrayList<Ticket> temp_tickets = new ArrayList<Ticket>();
      Random rand = new Random();
      int ticket_number = rand.nextInt(10);
      for (int j = 0; j < ticket_number; j++) {
        double price = 50*rand.nextDouble();
        Ticket new_ticket = new Ticket(price);
        temp_tickets.add(new_ticket);
      }
      int random_x = -10 + rand.nextInt(20);
      int random_y = -10 + rand.nextInt(20);

      while (!checkUniquePoint(random_x, random_y)) {
        random_x = -10 + rand.nextInt(20);
        random_y = -10 + rand.nextInt(20);
      }

      Point eventLocation = new Point(random_x, random_y);
      visited[random_x+10][random_y+10] = 1;
      Event new_event = new Event(i, temp_tickets, eventLocation);
      System.out.println(new_event.toString());
      m_events.add(new_event);
    }
    return;
  }

  /*** Calculate Manhattan Distance ***/
  private static int calculateDistance(Point a, Point b) {
    return Math.abs(a.x-b.x) + Math.abs(a.y-b.y);
  }
  public static void main(String[] args) {

    //take user input and find nearest events...

    // generate seed data first
    generateSeedData();

    Scanner sc = new Scanner(System.in);
    System.out.println("Please Input Coordinates: ");

    String entry = sc.next();
    String[] coordinates = entry.split(",");

    // validate input
    if (coordinates.length != 2) {
      System.out.println("Please Enter input with format x,y");
      return;
    }

    int user_x = Integer.parseInt(coordinates[0]);
    int user_y = Integer.parseInt(coordinates[1]);

    if (user_x < -10 || user_x > 10 || user_y < -10 || user_y > 10) {
      System.out.println("User should be within -10 to +10 in both X and Y coordinates");
      return;
    }
    Point userLocation = new Point(user_x, user_y);

    //trying to find top five closest events

    // make a copy of current events to sort
    ArrayList<Event> close_events = new ArrayList<Event>(m_events);

    Collections.sort(close_events, new Comparator<Event>() {
      @Override
      public int compare(Event a, Event b) {
        return calculateDistance(userLocation, a.getLocation()) - calculateDistance(userLocation, b.getLocation());
      }
    });

    //print the smallest 5 with their cheapest ticket price
    System.out.println("Closest events to " + userLocation.toString() + ": ");
    int result_length = close_events.size() > 5 ? 5 : close_events.size();
    int count = 0;
    for (int i = 0; i < close_events.size(); i++) {
      Event e = close_events.get(i);
      if (e.getTicketNumber() > 0) {
        count++;
        System.out.printf("Event %03d - $%05.2f, Distance %d\n", e.getId(), e.getCheapestTicketPrice(), 
          calculateDistance(userLocation, e.getLocation()));

        if (count == 5) {
          break;
        }
      }
    }

    return;

  }

}