package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    UserService userService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        Schedule newSchedule = scheduleService.createSchedule(schedule);
        return convertScheduleToScheduleDTO(newSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Optional<Pet> pet = petService.getPet(petId);
        if (pet.isPresent()){
            return scheduleService.getScheduleByPet(pet.get()).stream()
                    .map(this::convertScheduleToScheduleDTO)
                    .collect(Collectors.toList());
        } else{
            throw new NoSuchElementException();
        }
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Optional<Employee> employee = userService.getEmployee(employeeId);
        if (employee.isPresent()){
            return scheduleService.getScheduleByEmployee(employee.get()).stream()
                    .map(this::convertScheduleToScheduleDTO)
                    .collect(Collectors.toList());
        } else{
            throw new NoSuchElementException();
        }
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Optional<Customer> customer = userService.getCustomer(customerId);
        if (customer.isPresent()){
            return scheduleService.getScheduleByCustomer(customer.get()).stream()
                    .map(this::convertScheduleToScheduleDTO)
                    .collect(Collectors.toList());
        } else{
            throw new NoSuchElementException();
        }
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();

        List<Long> petIds = scheduleDTO.getPetIds();
        List<Pet> pets = new ArrayList<>();
        if (petIds != null){
            for (Long petId: petIds){
                petService.getPet(petId).ifPresent(pets::add);
            }
        }

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Employee> employees = new ArrayList<>();
        if (employeeIds != null){
            for (Long employeeId: employeeIds){
                userService.getEmployee(employeeId).ifPresent(employees::add);
            }
        }

        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setPets(pets);
        schedule.setEmployees(employees);

        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if(schedule.getEmployees() != null){
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList()));
        }
        if(schedule.getPets() != null){
            scheduleDTO.setPetIds(schedule.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList()));
        }
        return scheduleDTO;
    }
}
