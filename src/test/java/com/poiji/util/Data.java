package com.poiji.util;

import com.poiji.deserialize.model.Student;
import com.poiji.deserialize.model.byid.Employee;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.deserialize.model.byid.Sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hakan on 18/03/2018
 */
public final class Data {

    public static List<Employee> unmarshallingDeserialize() {
        List<Employee> employees = new ArrayList<>(3);

        Employee employee1 = new Employee();
        employee1.setEmployeeId(123923L);
        employee1.setName("Joe");
        employee1.setSurname("Doe");
        employee1.setSingle(true);
        employee1.setAge(30);
        employee1.setBirthday("4/9/1987");
        employees.add(employee1);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(123123L);
        employee2.setName("Sophie");
        employee2.setSurname("Derue");
        employee2.setSingle(false);
        employee2.setAge(20);
        employee2.setBirthday("5/3/1997");
        employees.add(employee2);

        Employee employee3 = new Employee();
        employee3.setEmployeeId(135923L);
        employee3.setName("Paul");
        employee3.setSurname("Raul");
        employee3.setSingle(false);
        employee3.setAge(31);
        employee3.setBirthday("4/9/1986");
        employees.add(employee3);

        return employees;
    }

    public static List<Person> unmarshallingPersons() {
        List<Person> persons = new ArrayList<>(5);
        Person person1 = new Person();
        person1.setRow(1);
        person1.setName("Rafique");
        person1.setAddress("Melbourne");
        person1.setEmail("raf@abc.com");
        person1.setMobile("91701");
        person1.setInsurance(30);
        persons.add(person1);
        Person person2 = new Person();
        person2.setRow(2);
        person2.setName("Sam");
        person2.setAddress("Sydney");
        person2.setEmail("sam@xyz.com");
        person2.setMobile("617019");
        person2.setInsurance(-20);
        persons.add(person2);
        Person person3 = new Person();
        person3.setRow(3);
        person3.setName("Tony");
        person3.setAddress("Perth");
        person3.setEmail("tony@tyo.com");
        person3.setMobile("90187");
        person3.setInsurance(4.12F);
        persons.add(person3);
        Person person4 = new Person();
        person4.setRow(4);
        person4.setName("Terry");
        person4.setAddress("Darwin");
        person4.setEmail("terry@jojo.com");
        person4.setMobile("909091");
        person4.setInsurance(-8);
        persons.add(person4);
        Person person5 = new Person();
        person5.setRow(5);
        person5.setName("Michael");
        person5.setAddress("Brisbane");
        person5.setEmail("mic@koko.com");
        person5.setMobile("808918");
        person5.setInsurance(-12);
        persons.add(person5);
        return persons;
    }

    public static List<Sample> unmarshallingSamples() {
        List<Sample> samples = new ArrayList<>(2);

        Sample sample1 = new Sample();
        sample1.setAmount(null);
        sample1.setMonth(null);
        sample1.setOther("foo");

        Sample sample2 = new Sample();
        sample2.setAmount(27.5);
        sample2.setMonth("APR");
        sample2.setOther("bar");


        samples.add(sample1);
        samples.add(sample2);
        return samples;
    }


    public static List<Student> unmarshallingStudents() {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setName("Terry");
        student.setId("3");
        student.setPhone("123");
        students.add(student);

        student = new Student();
        student.setName("William");
        student.setId("4");
        student.setPhone("456");
        students.add(student);
        return students;
    }
}
