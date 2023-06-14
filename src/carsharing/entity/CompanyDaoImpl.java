package carsharing.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static carsharing.util.DbConnection.closeConnection;
import static carsharing.util.DbConnection.getConnection;

public class CompanyDaoImpl implements CompanyDao {

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT ID, NAME FROM COMPANY";

        try(Statement statement = getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                companies.add(new Company(rs.getInt("ID"), rs.getString("NAME")));
            }
            rs.close();
            closeConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return companies;
    }

    @Override
    public void addCompany(String name) {
        String sql = "INSERT INTO COMPANY(NAME) VALUES(?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
            closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Company getCompanyById(int id) {
        return null;
    }
}
