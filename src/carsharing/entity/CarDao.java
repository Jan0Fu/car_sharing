package carsharing.entity;

import java.util.List;

public interface CarDao {
    List<Car> getAllCarsByCompanyId(int id);
    void addCar(Car car);
    Car getCarById(int id);
    List<Car> getAllAvailableCarsByCompanyId(int id);
}
