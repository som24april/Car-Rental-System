import java.util.*;

class Bike {
    public String bikeId;
    public String brand;
    public String model;
    public double basePricePerDay;
    public boolean isAvailable;

    public Bike(String bikeId, String brand, String model, double basePricePerDay) {
        this.bikeId = bikeId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    public String getBikeId() {
        return bikeId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnBike() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Bike bike;
    private Customer customer;
    private int days;

    public Rental(Bike bike, Customer customer, int days) {
        this.bike = bike;
        this.customer = customer;
        this.days = days;
    }

    public Bike getBike() {
        return bike;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class BikeRentalSystem {
    public List<Bike> bikes;
    public List<Customer> customers;
    public List<Rental> rentals;

    public BikeRentalSystem() {
        bikes = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addBike(Bike bike) {
        bikes.add(bike);
    }
}

public class Main {
    public static void main(String[] args) {
        BikeRentalSystem rentalSystem = new BikeRentalSystem();

        String[][] bikeData = {
                {"B001", "Hero", "Glamour", "500"},
                {"B002", "TVS", "Apache 180", "750"},
                {"B003", "Honda", "Shine", "550"},
                {"B004", "Yamaha", "FZ-S", "500"},
                {"B005", "Bajaj", "Pulsar 150", "600"},
                {"B006", "Suzuki", "Gixxer", "650"},
                {"B007", "Royal Enfield", "Bullet 350", "1000"},
                {"B008", "KTM", "Duke 200", "1000"},
                {"B009", "BMW", "G310R", "1800"},
                {"B010", "Harley-Davidson", "Street 750", "2500"}
        };

        for (String[] data : bikeData) {
            Bike bike = new Bike(
                    data[0],
                    data[1],
                    data[2],
                    Double.parseDouble(data[3])
            );
            rentalSystem.addBike(bike);
        }

        new BikeGUI(rentalSystem);
    }
}
