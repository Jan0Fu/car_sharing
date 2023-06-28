package carsharing;

import carsharing.entity.*;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.companyDao = new CompanyDaoImpl();
        this.carDao = new CarDaoImpl();
        this.customerDao = new CustomerDaoImpl();
    }

    public void start() {
        menu();
    }

    private void menu() {
        System.out.println("1. Log in as a manager\n2. Log in as a customer\n3. Create a customer\n0. Exit");

        while(scanner.hasNextLine()){
            String command = scanner.nextLine();
            switch (command){
                case "1" -> logIn();
                case "2" -> showCustomerList();
                case "3" -> createCustomer();
                case "0" -> System.exit(0);
            }
        }
    }

    private void logIn() {
        while (true) {
            System.out.println("1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == 0) menu();
                if (input == 1) showCompanies();
                if (input == 2) {
                    System.out.println("Enter the company name:");
                    String name = scanner.nextLine();
                    if (name.length() < 3) {
                        System.out.println("Wrong name\n");
                    }
                    companyDao.addCompany(name);
                    System.out.println("The company was created!");
                }
            } catch (Exception e) {
                System.out.println("Wrong input\n");
            }
        }
    }

    private void showCompanies() {
        List<Company> companies = companyDao.getAllCompanies();
        if(companies.size() == 0){
            System.out.println("The company list is empty!");
            System.out.println();
            return;
        }
        int index = -1;

        while(index != 0) {
            System.out.println("Choose the company:");
            companies.forEach(System.out::println);
            System.out.println("0. Back");
            index = scanner.nextInt();
            scanner.nextLine();

            if (index <= companies.size() && index > 0) {
                carMenu(companies.get(index - 1));
                break;
            }
        }
    }

    private void carMenu(Company company) {
        String input = "";
        System.out.println("'" +company.getName()+ "' company");

        while(!input.equals("0")){
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            input = scanner.nextLine();
            if (input.equals("1")) showCars(company);
            if (input.equals("2")) createCar(company);
        }
    }

    private void showCars(Company company) {
        List<Car> cars = carDao.getAllCarsByCompanyId(company.getId());
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            System.out.println();
        } else {
            for(int i = 0; i < cars.size(); i++){
                System.out.println(i+1+". "+cars.get(i).getName());
            }
            System.out.println();
        }
    }

    private void createCar(Company company) {
        System.out.println("Enter the car name:");
        String name = scanner.nextLine();
        carDao.addCar(new Car(0,name, company.getId()));
        System.out.println("The car was added!");
        System.out.println();
    }

    private void createCustomer() {
        System.out.println("Enter the customer name:");

        String name = scanner.nextLine();

        customerDao.addCustomer(new Customer(name));
        System.out.println("The customer was added!");
        System.out.println();
        menu();
    }

    private void showCustomerList() {
        List<Customer> customers = customerDao.getAllCustomers();
        if(customers.size() == 0){
            System.out.println("The customer list is empty!");
            System.out.println();
            return;
        }
        System.out.println("Customer list:");
        customers.forEach(System.out::println);
        System.out.println("0. Back");
        int index = scanner.nextInt();
        scanner.nextLine();

        if(index == 0) {
            menu();
        }

        if (index <= customers.size() && index > 0) {
            customerMenu(customers.get(index - 1));
        }
    }

    private void customerMenu(Customer customer){
        String command = "";
        while(!command.equals("0")){
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");

            command = scanner.nextLine();

            switch (command){
                case "1" -> rentACar(customer.getId());
                case "2" -> returnCar(customer.getId());
                case "3" -> showRentedCar(customer.getId());
                case "0" -> menu();
            }
        }
    }

    private void returnCar(int id) {
        Customer customer = customerDao.getCustomerById(id);
        if(customer.getRentedCarId() == null){
            System.out.println("You didn't rent a car!");
            System.out.println();
            return;
        }
        customerDao.assignCarToCustomer(customer, null);
        System.out.println("You've returned a rented car!");
    }

    private void showRentedCar(int id) {
        Customer customer = customerDao.getCustomerById(id);
        if(customer.getRentedCarId() == null){
            System.out.println("You didn't rent a car!");
            System.out.println();
            return;
        }

        Car rentedCar = carDao.getCarById(customer.getRentedCarId());
        Company company = companyDao.getCompanyById(rentedCar.getCompanyId());

        System.out.println("Your rented car:");
        System.out.println(rentedCar.getName());
        System.out.println("Company:");
        System.out.println(company.getName());
        System.out.println();
    }

    private void rentACar(int id){
        Customer customer = customerDao.getCustomerById(id);

        if(customer.getRentedCarId() != null){
            System.out.println("You've already rented a car!");
            System.out.println();
            customerMenu(customer);
        }
        List<Company> companies = companyDao.getAllCompanies();

        if(companies.size() == 0){
            System.out.println("The company list is empty!");
            System.out.println();
            customerMenu(customer);
        }

        int index = -1;

        System.out.println("Choose the company:");
        companies.forEach(System.out::println);
        System.out.println("0. Back");

        index = scanner.nextInt();
        scanner.nextLine();

        if (index <= companies.size() && index > 0) {
            List<Car> cars = carDao.getAllAvailableCarsByCompanyId(index);

            if(cars.size() == 0){
                System.out.println("The car list is empty!");
                System.out.println();
            } else{
                System.out.println("Choose a car:");
                for(int i = 0; i < cars.size(); i++){
                    System.out.println(i+1+". "+cars.get(i).getName());
                }
                System.out.println();

                index = scanner.nextInt();
                scanner.nextLine();
                if(index <= cars.size() && index > 0){
                    customerDao.assignCarToCustomer(customer, cars.get(index-1));
                    System.out.println("You rented '"+cars.get(index-1).getName()+"'");
                    System.out.println();
                    customerMenu(customer);
                }
            }
        }
    }
}
