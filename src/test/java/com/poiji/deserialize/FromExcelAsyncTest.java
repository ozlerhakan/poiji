package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Employee;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class FromExcelAsyncTest {

    @Test
    public void shouldDeserializeAsyncWithDefaultOptions() throws Exception {
        File file = new File("src/test/resources/employees.xlsx");
        CompletableFuture<List<Employee>> future = Poiji.fromExcelAsync(file, Employee.class);

        List<Employee> employees = future.get();

        assertThat(employees, notNullValue());
        assertThat(employees.isEmpty(), is(false));
        Employee first = employees.get(0);
        assertThat(first.getEmployeeId(), is(123923L));
        assertThat(first.getName(), is("Joe"));
    }

    @Test
    public void shouldDeserializeAsyncWithOptions() throws Exception {
        File file = new File("src/test/resources/employees.xlsx");
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
        CompletableFuture<List<Employee>> future = Poiji.fromExcelAsync(file, Employee.class, options);

        List<Employee> employees = future.get();

        assertThat(employees, notNullValue());
        assertThat(employees.isEmpty(), is(false));
    }

    @Test
    public void shouldCompleteExceptionallyOnMissingFile() {
        File file = new File("src/test/resources/nonexistent.xlsx");
        CompletableFuture<List<Employee>> future = Poiji.fromExcelAsync(file, Employee.class);

        try {
            future.get();
            fail("Expected ExecutionException");
        } catch (ExecutionException e) {
            assertThat(e.getCause(), instanceOf(PoijiException.class));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Interrupted");
        }
    }

    @Test
    public void shouldReturnCorrectEmployeeCount() throws Exception {
        File file = new File("src/test/resources/employees.xlsx");
        List<Employee> sync = Poiji.fromExcel(file, Employee.class);
        List<Employee> async = Poiji.fromExcelAsync(file, Employee.class).get();

        assertThat(async.size(), is(sync.size()));
    }
}
