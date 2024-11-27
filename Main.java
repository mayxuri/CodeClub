import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Operations objt = new Operations();
        objt.AddToDomain();
        objt.accept();

        objt.displayCities();

    }
}

class SignUp {
    Scanner sc = new Scanner(System.in);

    void accept() {

        System.out.println("1. Login      2. SignUp     :");
        int SL = sc.nextInt();
        sc.nextLine();
        if (SL == 1) {
            login();
        } else if (SL == 2) {
            signup();
            login();
        } else {
            System.out.println("Wrong choice");
            accept();
        }
    }

    ArrayList<User> userList = new ArrayList<>();

    String[] entry() {
        System.out.println("Enter username:");
        String usernamee = sc.nextLine();
        System.out.println("Enter password:");
        String passwordd = sc.nextLine();
        String[] userr = { usernamee, passwordd };

        return userr;
    }

    User login() {
        System.out.println("Login page");
        int bool = 3;
        String[] user = entry();
        String usernamee = user[0], passwordd = user[1];
        for (User item : userList) {
            if (usernamee.equals(item.username) && passwordd.equals(item.password)) {
                bool = bool * 0;
                System.out.println("Logged-in successfully!");
                return item;
            } else {
                bool = bool * 1;
            }
        }
        if (bool != 0) {
            System.out.println("Wrong Passsword or Username");
            accept();
        }
        return null;
    }

    User signup() {
        System.out.println("Signup page");
        int bool = 3;
        String[] user = entry();
        String usernamee = user[0], passwordd = user[1];

        for (User item : userList) {
            if (usernamee.equals(item.username)) {
                System.out.println("Username already exixts, please create a new one");
                bool = bool * 0;
            } else {
                bool = bool * 1;
            }
        }
        if (bool != 0) {
            User objU = new User(usernamee, passwordd);
            userList.add(objU);
            System.out.println("Signed-up successfully!");
            return objU;
        } else {
            signup();
        }
        return null;
    }

}

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Domain extends SignUp {
    ArrayList<String> cities = new ArrayList<>();
    ArrayList<Hotels> hotels = new ArrayList<>();

    void AddToDomain() {
        cities.add("pune");
        cities.add("mumbai");
        cities.add("kolhapur");

        // kolhapur
        double[] ck5a = { 7000, 11000, 18000 };
        int[] ak5a = { 10, 12, 15 };
        Hotels k5a = new Hotels("kolhapur", 5, "sayaji", ck5a, ak5a);

        double[] ck5b = { 8500, 12000, 20000 };
        int[] ak5b = { 14, 9, 7 };
        Hotels k5b = new Hotels("kolhapur", 5, "radisson blu", ck5b, ak5b);

        double[] ck4a = { 4500, 7000, 9500 };
        int[] ak4a = { 7, 5, 10 };
        Hotels k4a = new Hotels("kolhapur", 4, "citrus", ck4a, ak4a);

        double[] ck4b = { 4000, 6500, 8000 };
        int[] ak4b = { 10, 3, 8 };
        Hotels k4b = new Hotels("kolhapur", 4, "maratha regency", ck4b, ak4b);

        double[] ck3a = { 2500, 3500, 6000 };
        int[] ak3a = { 6, 4, 7 };
        Hotels k3a = new Hotels("kolhapur", 3, "panchshil", ck3a, ak3a);

        double[] ck3b = { 2000, 3000, 5500 };
        int[] ak3b = { 5, 9, 6 };
        Hotels k3b = new Hotels("kolhapur", 3, "pearl", ck3b, ak3b);

        hotels.add(k5a);
        hotels.add(k5b);
        hotels.add(k4a);
        hotels.add(k4b);
        hotels.add(k3a);
        hotels.add(k3b);

        // pune
        double[] cp5a = { 10000, 15000, 22000 };
        int[] ap5a = { 12, 20, 10 };
        Hotels p5a = new Hotels("pune", 5, "ritz carton", cp5a, ap5a);

        double[] cp5b = { 9000, 13000, 20000 };
        int[] ap5b = { 10, 15, 12 };
        Hotels p5b = new Hotels("pune", 5, "jw marriot", cp5b, ap5b);

        double[] cp4a = { 5000, 7000, 10000 };
        int[] ap4a = { 14, 11, 9 };
        Hotels p4a = new Hotels("pune", 4, "orchid", cp4a, ap4a);
        double[] cp4b = { 5500, 6000, 9000 };

        int[] ap4b = { 8, 20, 10 };
        Hotels p4b = new Hotels("pune", 4, "lemon tree", cp4b, ap4b);
        double[] cp3a = { 4000, 5000, 8000 };

        int[] ap3a = { 12, 18, 5 };
        Hotels p3a = new Hotels("pune", 3, "deccan pavillion", cp3a, ap3a);
        double[] cp3b = { 3500, 5000, 7500 };

        int[] ap3b = { 7, 20, 10 };
        Hotels p3b = new Hotels("pune", 3, "phoenix", cp3b, ap3b);

        hotels.add(p5a);
        hotels.add(p5b);
        hotels.add(p4a);
        hotels.add(p4b);
        hotels.add(p3a);
        hotels.add(p3b);

        // mumbai
        double[] cm5a = { 22000, 27000, 34000 };
        int[] am5a = { 4, 5, 2 };
        Hotels m5a = new Hotels("mumbai", 5, "taj", cm5a, am5a);
        double[] cm5b = { 20000, 25000, 30000 };
        int[] am5b = { 5, 2, 4 };
        Hotels m5b = new Hotels("mumbai", 5, "oberoi", cm5b, am5b);
        double[] cm4a = { 17000, 21000, 24000 };
        int[] am4a = { 3, 2, 4 };
        Hotels m4a = new Hotels("mumbai", 4, "fern", cm4a, am4a);
        double[] cm4b = { 16000, 18000, 21000 };
        int[] am4b = { 4, 5, 3 };
        Hotels m4b = new Hotels("mumbai", 4, "della", cm4b, am4b);
        double[] cm3a = { 11000, 14000, 16000 };
        int[] am3a = { 2, 4, 2 };
        Hotels m3a = new Hotels("mumbai", 3, "ibis", cm3a, am3a);
        double[] cm3b = { 10000, 13000, 15000 };
        int[] am3b = { 5, 4, 7 };
        Hotels m3b = new Hotels("mumbai", 3, "south coast", cm3b, am3b);

        hotels.add(m5a);
        hotels.add(m5b);
        hotels.add(m4a);
        hotels.add(m4b);
        hotels.add(m3a);
        hotels.add(m3b);

    }

}

class Hotels {
    String place;
    int star;
    String name;
    String[] roomtype = { "single", "double", "suite" };
    double[] cost = new double[3];
    int[] availability = new int[3]; // Track availability

    Hotels(String place, int star, String name, double[] cost, int[] availability) {
        this.place = place;
        this.star = star;
        this.name = name;
        this.cost = cost;
        this.availability = availability;
    }

}

class Operations extends Domain {
    int bool;
    Scanner sc = new Scanner(System.in);
    String city;
    int stars;
    String hotel;
    String room;
    double price;
    int rooms;
    double totalR, total;

    void displayCities() {
        System.out.println(" ");
        System.out.println("Cities:");
        for (String item : cities) {
            System.out.println("		-" + item);
        }
        chooseC();
    }

    void chooseC() {
        bool = 3;
        System.out.println("Enter city name:");
        this.city = sc.nextLine().toLowerCase().trim();
        for (String item : cities) {
            if (city.equals(item)) {
                checkin();
                bool = bool * 0;
                return;
            } else {
                bool = bool * 1;
            }
        }
        if (bool == 0) {
            System.out.println(" ");
            displayStars();
        } else {
            System.out.println("No such city present in the domain");
            chooseC();
        }
    }

    int d, m, y, d1, m1, y1, D;

    void checkin() {
        System.out.println(" ");
        System.out.println("Enter Check-in date");
        System.out.println("Enter date");
        d = sc.nextInt();
        System.out.println("Enter month");
        m = sc.nextInt();
        System.out.println("Enter year");
        y = sc.nextInt();
        if (d < 32 && m < 13) {
            System.out.println("Check in Date is: " + d + "/" + m + "/" + y);
            checkout();
        } else {
            System.out.println("Enter appropriate data");
            checkin();
        }
    }

    void checkout() {
        System.out.println("Enter Check-out date");
        System.out.println("Enter date");
        d1 = sc.nextInt();
        System.out.println("Enter month");
        m1 = sc.nextInt();
        System.out.println("Enter year");
        y1 = sc.nextInt();
        if (d1 < 32 && m1 < 13) {
            System.out.println("Check out Date is: " + d1 + "/" + m1 + "/" + y1);
            tot();
        } else {
            System.out.println("Enter appropriate data");
            checkout();
        }
    }

    void tot() {
        System.out.println("Total number of nights");
        if (m == m1 && y == y1) {
            this.D = d1 - d;
            System.out.println(D);
        } else if (m1 > m && y == y1) {
            int d2 = d1 - 1;
            int d3 = (30 - d) + 1;
            this.D = d2 + d3;
            System.out.println(D);
        } else if (y1 > y) {
            int d2 = d1 - 1;
            int d3 = (30 - d) + 1;
            this.D = d2 + d3;
            System.out.println(D);
        }
        displayStars();
    }

    void displayStars() {
        System.out.println(" ");
        System.out.println("Stars:");
        System.out.println("		5 *****");
        System.out.println("		4 ****");
        System.out.println("		3 ***");
        chooseS();
    }

    void chooseS() {
        System.out.println("Enter number of stars:");
        this.stars = sc.nextInt();
        sc.nextLine();
        if (stars == 3 || stars == 4 || stars == 5) {
            displayH();
        } else {
            System.out.println("No such option present in the domain");
            chooseS();
        }
    }

    void displayH() {
        System.out.println(" ");
        bool = 3;
        for (Hotels obj : hotels) {
            if (city.equals(obj.place) && obj.star == stars) {
                System.out.println("Name:" + obj.name);// room avail cost
                System.out.println("	RoomType	" + "Cost	   " + "Availability");
                for (int i = 0; i < 3; i++) {
                    System.out.println(
                            "	" + obj.roomtype[i] + "		" + obj.cost[i] + "		" + obj.availability[i]);
                }
                System.out.println(" ");
            }
        }
        chooseH();
    }

    void chooseH() {
        System.out.println("Enter hotel name:");
        this.hotel = sc.nextLine().toLowerCase().trim();
        int bool = 3;
        System.out.println("Enter roomtype:");
        this.room = sc.nextLine().toLowerCase().trim();
        int i;
        for (Hotels obj : hotels) {
            if (hotel.equals(obj.name)) {
                for (i = 0; i < 3; i++) {
                    if (room.equals(obj.roomtype[i])) {
                        System.out.println("Enter number of rooms:");
                        this.rooms = sc.nextInt();

                        if (rooms <= obj.availability[i]) {
                            bool = bool * 0;
                            this.price = obj.cost[i];
                            obj.availability[i] = obj.availability[i] - rooms;
                            display();
                        } else {
                            sc.nextLine();
                            System.out.println(
                                    "Requested number of rooms not available. Please choose a different option.");
                            chooseH();
                        }
                    } else {
                        bool = bool * 1;
                    }
                }
            }
        }

        if (bool != 0) {
            sc.nextLine();
            System.out.println("No such option present in the domain");
            chooseH();
        } else {
            sc.nextLine();
        }
    }

    void display() {
        sc.nextLine();
        System.out.println("Hotel name		: " + hotel + " (" + stars + "star hotel)");
        System.out.println("Room type 		: " + room);
        System.out.println("Number of rooms 	: " + rooms);
        this.totalR = rooms * price;
        System.out.println("Total cost		: " + totalR + "(per night)");
        meal();
    }

    int c;

    void meal() {
        int i;
        // previous total of room booking
        int a[] = { 1, 2, 3, 4 };
        int b1[] = { 0, 800, 1200, 1800 };
        int b2[] = { 0, 1000, 2000, 3000 };
        int b3[] = { 0, 2000, 4000, 5000 };
        // Scanner sc = new Scanner(System.in);
        System.out.println("Choose meal options");
        if (stars == 3) {
            System.out.println(
                    "1)No meal\n2)Only Breakfast - 800rs\n3)Breakfast+One meal - 1200rs\n4)Breakfast+lunch+Dinner-1800rs");
            System.out.println("Choose any 1 option");
            this.c = sc.nextInt();
            sc.nextLine();
            for (i = 0; i < 4; i++) {
                if (c == a[i]) {
                    this.total = totalR + b1[i];
                }
            }
            System.out.println("Total amount is: " + total);
        } else if (stars == 4) {
            System.out.println(
                    "1)No meal\n2)Only Breakfast - 1000rs\n3)Breakfast+One meal - 2000rs\n4)Breakfast+lunch+Dinner-3000rs");
            System.out.println("Choose any 1 option");
            this.c = sc.nextInt();
            sc.nextLine();
            for (i = 0; i < 4; i++) {
                if (c == a[i]) {
                    this.total = totalR + b2[i];
                }
            }
        } else if (stars == 5) {
            System.out.println(
                    "1)No meal\n2)Only Breakfast - 2000rs\n3)Breakfast+One meal - 4000rs\n4)Breakfast+lunch+Dinner-5000rs");
            System.out.println("Choose any 1 option");
            this.c = sc.nextInt();
            sc.nextLine();
            for (i = 0; i < 4; i++) {
                if (c == a[i]) {
                    this.total = totalR + b3[i];
                }
            }
        }
        System.out.println("Total amount is      : " + total + "(per night)");
        System.out.println(" ");
        System.out.println("Final Total amount is: " + total * D);
        payment();
    }

    void payment() {
        System.out.println(" ");
        System.out.println("Please proceed to pay-");
        System.out.println("1)UPI 2)Credit card");
        int d = sc.nextInt();
        sc.nextLine();
        if (d == 1) {
            System.out.println("Enter password");
            String e = sc.nextLine();
            System.out.println("Payment successful!\nThank You!!");
        } else if (d == 2) {
            System.out.println("Enter mobile number-");
            double f = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter card number-");
            double g = sc.nextDouble();
            sc.nextLine();
            System.out.println("Payment Successful!\nThank You!!");
        }
        displayAll();
    }

    void displayAll() {
        System.out.println("  \n  ");
        System.out.println("Details:");
        // Display hotel name,stay period,type of room,no of rooms,meal type
        System.out.println("Hotel name		: " + hotel + " (" + stars + "star hotel)");
        System.out.println("Room type 		: " + room);
        System.out.println("Number of rooms 	: " + rooms);
        System.out.println("Stay period-" + D + " days");
        System.out.println(
                "Meal Type options-1)No meal\n2)Only Breakfast\n3)Breakfast+One meal\n4)Breakfast+lunch+Dinner");
        System.out.println("Meal Type-" + c);
        System.out.println(" ");
        System.out.println("THANK YOU!!");
    }
}
