package carsharing.entity;

import java.util.List;

public interface CompanyDao {

    List<Company> getAllCompanies();
    void addCompany(String name);
    Company getCompanyById(int id);
}
