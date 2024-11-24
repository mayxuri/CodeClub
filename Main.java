package dsamini;

import java.util.*;  

class User {  
    int userId;  
    String name;  
    String email;  
    String mobileNumber;  

    // Constructor  
    public User(int userId, String name, String email, String mobileNumber) {  
        this.userId = userId;  
        this.name = name;  
        this.email = email;  
        this.mobileNumber = mobileNumber;  
    }  

    // Getters  
    public int getUserId() {  
        return userId;  
    }  

    public String getName() {  
        return name;  
    }  
}  

class Expense {  
    int payerId;  
    double amount;  
    List<Integer> participants;  
    String type;  // "equal", "exact", "percent"  
    Map<Integer, Double> splitDetails;  

    // Constructor  
    public Expense(int payerId, double amount, List<Integer> participants, String type) {  
        this.payerId = payerId;  
        this.amount = amount;  
        this.participants = participants;  
        this.type = type;  
        this.splitDetails = new HashMap<>();  
        calculateSplit();  
    }  

    private void calculateSplit() {  
        if (type.equals("equal")) {  
            double equalShare = amount / participants.size();  
            for (Integer participantId : participants) {  
                splitDetails.put(participantId, equalShare);  
            }  
        } else if (type.equals("exact")) {  
            // For simplicity, assume we are using the exact amounts for each participant  
            for (Integer participantId : participants) {  
                double individualAmount = amount / participants.size(); // You can customize this  
                splitDetails.put(participantId, individualAmount); // Adjust for actual amounts as needed  
            }  
        } else if (type.equals("percent")) {  
            // You can implement percentage calculations if desired  
            // Example: if you had a list of percentages associated with each participant  
            double[] percentages = new double[participants.size()];  
            for (int i = 0; i < percentages.length; i++) {  
                percentages[i] = 100.0 / participants.size(); // Adjust to desired percentages  
            }  
            for (int i = 0; i < participants.size(); i++) {  
                splitDetails.put(participants.get(i), (amount * percentages[i]) / 100);  
            }  
        }  
    }  

    // Getters  
    public Map<Integer, Double> getSplitDetails() {  
        return splitDetails;  
    }  
}  

class SplitwiseApp {  
    Map<Integer, User> users = new HashMap<>(); // User information  
    List<Expense> expenses = new ArrayList<>(); // Expense records  
    Map<Integer, Map<Integer, Double>> balances = new HashMap<>(); // Balance tracking  

    public void addUser(int userId, String name, String email, String mobileNumber) {  
        User user = new User(userId, name, email, mobileNumber);  
        users.put(userId, user);  
    }  

    public void addExpense(int payerId, double amount, List<Integer> participants, String type) {  
        Expense expense = new Expense(payerId, amount, participants, type);  
        expenses.add(expense);  
        updateBalances(expense);  
    }  

    private void updateBalances(Expense expense) {  
        Map<Integer, Double> splits = expense.getSplitDetails();  
        for (Map.Entry<Integer, Double> entry : splits.entrySet()) {  
            int participantId = entry.getKey();  
            double amount = entry.getValue();  
            
            // Update balances  
            balances.putIfAbsent(expense.payerId, new HashMap<>());  
            balances.putIfAbsent(participantId, new HashMap<>());  

            balances.get(expense.payerId).put(participantId,   
              balances.get(expense.payerId).getOrDefault(participantId, 0.0) - amount);  
            balances.get(participantId).put(expense.payerId,   
              balances.get(participantId).getOrDefault(expense.payerId, 0.0) + amount);  
        }  
    }  

    public void showBalances() {  
        System.out.println("User Balances:");  
        for (Integer userId : balances.keySet()) {  
            System.out.printf("%s (%d): %s\n", users.get(userId).getName(), userId, balances.get(userId));  
        }  
    }  

    public static void main(String[] args) {  
        SplitwiseApp app = new SplitwiseApp();  

        // Adding users  
        app.addUser(1, "Alice", "alice@example.com", "1234567890");  
        app.addUser(2, "Bob", "bob@example.com", "0987654321");  
        app.addUser(3, "Charlie", "charlie@example.com", "1122334455");  
        
        // Adding expenses  
        app.addExpense(1, 300, Arrays.asList(1, 2, 3), "equal");  
        app.addExpense(2, 150, Arrays.asList(1, 2), "equal");  
        
        // Show balances  
        app.showBalances();  
    }  
}
