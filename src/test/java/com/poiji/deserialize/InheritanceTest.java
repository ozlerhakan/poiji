package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Car;
import com.poiji.deserialize.model.byid.Vehicle;
import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.exception.PoijiExcelType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by hakan on 17/01/2017.
 */
@RunWith(Parameterized.class)
public class InheritanceTest {

    private final boolean fromStream;
    private String path;
    private List<Car> expectedCars;
    private Class<?> expectedException;

    public InheritanceTest(String path, List<Car> expectedCars, Class<?> expectedException, boolean fromStream) {
        this.path = path;
        this.expectedCars = expectedCars;
        this.expectedException = expectedException;
        this.fromStream = fromStream;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/cars.xlsx", unmarshalling(), null, false},
                {"src/test/resources/cars.xls", unmarshalling(), null, true},
                {"src/test/resources/cars.xl", unmarshalling(), InvalidExcelFileExtension.class, false},
        });
    }

    @Test
    public void shouldMapExcelToJava() {

        try {
            List<Car> actualCars;

            if (fromStream) {
                try (InputStream stream = new FileInputStream(new File(path))) {
                    actualCars = Poiji.fromExcel(stream, PoijiExcelType.XLS, Car.class);
                } catch (IOException e) {
                    fail(e.getMessage());
                    return;
                }
            } else {
                actualCars = Poiji.fromExcel(new File(path), Car.class);
            }

            assertThat(actualCars, notNullValue());
            assertThat(actualCars.size(), not(0));
            assertThat(actualCars.size(), is(expectedCars.size()));

            Vehicle vehicle1 = actualCars.get(0);
            assertThat(vehicle1, instanceOf(Car.class));

            Car actualCar1 = (Car) vehicle1;
            Car actualCar2 = actualCars.get(1);

            Car expectedCar1 = expectedCars.get(0);
            Car expectedCar2 = expectedCars.get(1);

            assertThat(actualCar1.getnOfSeats(), is(expectedCar1.getnOfSeats()));
            assertThat(actualCar1.getName(), is(expectedCar1.getName()));
            assertThat(actualCar1.getYear(), is(expectedCar1.getYear()));

            assertThat(actualCar2.getnOfSeats(), is(expectedCar2.getnOfSeats()));
            assertThat(actualCar2.getName(), is(expectedCar2.getName()));
            assertThat(actualCar2.getYear(), is(expectedCar2.getYear()));

        } catch (Exception e) {
            if (expectedException == null) {
                fail(e.getMessage());
            } else {
                assertThat(e, instanceOf(expectedException));
            }
        }
    }

    private static List<Car> unmarshalling() {
        List<Car> cars = new ArrayList<>(2);

        Car car1 = new Car();
        car1.setName("Honda Civic");
        car1.setYear(2017);
        car1.setnOfSeats(4);
        cars.add(car1);

        Car car2 = new Car();
        car2.setName("Chevrolet Corvette");
        car2.setYear(2016);
        car2.setnOfSeats(2);
        cars.add(car2);
        return cars;
    }
}
