package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(Long id){
        return  customerRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployee(Long id){
        return  employeeRepository.findById(id);
    }

    public Customer getOwnerByPet(Pet pet){
        return pet.getCustomer();
    }
}

