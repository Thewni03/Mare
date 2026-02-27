package backend.controller;


import backend.exception.AdminNotFoundException;
import backend.exception.HrNotFoundException;
import backend.model.Hr;
import backend.repository.HrRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/hr")
public class HrController {


    @Autowired
    private HrRepo hrRepo;


    @PostMapping
    public Hr addhr(@RequestBody Hr hr) {
        return hrRepo.save(hr);   // FIXED
    }


    @GetMapping
    public List<Hr> getAllHr() {
        return hrRepo.findAll();
    }


    @GetMapping("/{id}")
    public Hr getHrById(@PathVariable Long id) {
        return hrRepo.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id));
    }


    @PutMapping("/{id}")
    public Hr updateHr(@RequestBody Hr newHr, @PathVariable Long id) {

        return hrRepo.findById(id)
                .map(existingHr -> {

                    existingHr.setStaffname(newHr.getStaffname());
                    existingHr.setDepartment(newHr.getDepartment());
                    existingHr.setRole(newHr.getRole());
                    existingHr.setDate(newHr.getDate());
                    existingHr.setCheckIn(newHr.getCheckIn());
                    existingHr.setCheckOut(newHr.getCheckOut());
                    existingHr.setStatus(newHr.getStatus());

                    return hrRepo.save(existingHr);

                }).orElseThrow(() -> new HrNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    String deleteHr(@PathVariable Long id) {

        Hr hr = hrRepo.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id));

        hrRepo.delete(hr);
        return "Hr deleted successfully!";
    }
}
