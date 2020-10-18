package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByPet(Pet pet){
        return scheduleRepository.findAllByPets(pet);
    }

    public List<Schedule> getScheduleByEmployee(Employee employee){
        return scheduleRepository.findAllByEmployees(employee);
    }

    public List<Schedule> getScheduleByCustomer(Customer customer){
        return scheduleRepository.findAllByPetsCustomer(customer);
    }
}
