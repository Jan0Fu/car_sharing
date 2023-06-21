package carsharing;

import carsharing.entity.*;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;
    private final CompanyDao companyDao;
    private final CarDao carDao;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.companyDao = new CompanyDaoImpl();
        this.carDao = new CarDaoImpl();
    }

    public void start() {
        logIn();
    }

    private void logIn() {
        while (true) {
            System.out.println("1. Log in as a manager\n0. Exit");
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == 0) break;
                if (input == 1) menu();
            } catch (Exception e) {
                System.out.println("Wrong input");
            }
        }
    }

    private void menu() {
        while (true) {
            System.out.println("1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == 0) break;
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
}
