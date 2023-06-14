package carsharing;

import carsharing.entity.Company;
import carsharing.entity.CompanyDao;
import carsharing.entity.CompanyDaoImpl;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;
    private CompanyDao companyDao;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.companyDao = new CompanyDaoImpl();
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
        System.out.println("Company list:");
        companies.forEach(System.out::println);
        System.out.println();
    }
}
