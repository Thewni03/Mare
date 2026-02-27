package backend.controller;

import backend.exception.SalaryNotFoundException;
import backend.model.Salary;
import backend.repository.SalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryRepo salaryRepo;

    @PostMapping
    public Salary addSalary(@RequestBody Salary salary) {
        System.out.println("Incoming salary: " + salary.getEmployeeName());
        return salaryRepo.save(salary);
    }


    @GetMapping
    public List<Salary> getAllSalary() {
        return salaryRepo.findAll();
    }

    @GetMapping("/{id}")
    public Salary getSalaryById(@PathVariable Long id) {
        return salaryRepo.findById(id)
                .orElseThrow(() -> new SalaryNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Salary updateSalary(@RequestBody Salary newSalary, @PathVariable Long id) {
        return salaryRepo.findById(id)
                .map(existingSalary -> {
                    existingSalary.setEmployeeName(newSalary.getEmployeeName());
                    existingSalary.setDepartment(newSalary.getDepartment());
                    existingSalary.setRole(newSalary.getRole());
                    existingSalary.setMonth(newSalary.getMonth());
                    existingSalary.setBasicSalary(newSalary.getBasicSalary());
                    existingSalary.setWorkingDays(newSalary.getWorkingDays());
                    existingSalary.setPresent(newSalary.getPresent());
                    existingSalary.setAbsent(newSalary.getAbsent());
                    existingSalary.setLate(newSalary.getLate());
                    existingSalary.setAllowances(newSalary.getAllowances());
                    existingSalary.setDeductions(newSalary.getDeductions());
                    existingSalary.setNoOfSales(newSalary.getNoOfSales());
                    existingSalary.setTotalSales(newSalary.getTotalSales());
                    existingSalary.setCommissionPercentage(newSalary.getCommissionPercentage());
                    existingSalary.setCommissionAmount(newSalary.getCommissionAmount());
                    existingSalary.setNetSalary(newSalary.getNetSalary());
                    existingSalary.setStatus(newSalary.getStatus());
                    return salaryRepo.save(existingSalary);
                }).orElseThrow(() -> new SalaryNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public String deleteSalary(@PathVariable Long id) {
        Salary salary = salaryRepo.findById(id)
                .orElseThrow(() -> new SalaryNotFoundException(id));
        salaryRepo.delete(salary);
        return "Salary deleted successfully!";
    }
}
