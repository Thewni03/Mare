package backend.controller;

import backend.exception.vehicalNotFoundException;
import backend.model.Appointment;
import backend.repository.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentRepo appointmentRepo;


    @PostMapping
    public Appointment addAppointment(@RequestBody Appointment appointment) {
        return appointmentRepo.save(appointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentRepo.findById(id)
                .orElseThrow(() -> new vehicalNotFoundException(id));
    }


    @PutMapping("/{id}")
    public Appointment updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment newAppointment) {

        return appointmentRepo.findById(id).map(existingAppointment -> {

            existingAppointment.setName(newAppointment.getName());
            existingAppointment.setPhone(newAppointment.getPhone());
            existingAppointment.setEmail(newAppointment.getEmail());
            existingAppointment.setBranch(newAppointment.getBranch());
            existingAppointment.setAppointmentDate(newAppointment.getAppointmentDate());
            existingAppointment.setPurpose(newAppointment.getPurpose());
            existingAppointment.setVehicleId(newAppointment.getVehicleId());
            existingAppointment.setVehicleModel(newAppointment.getVehicleModel());
            existingAppointment.setNote(newAppointment.getNote());
            existingAppointment.setStatus(newAppointment.getStatus());
            existingAppointment.setCreatedAt(newAppointment.getCreatedAt());
            existingAppointment.setUpdatedAt(newAppointment.getUpdatedAt());

            return appointmentRepo.save(existingAppointment);

        }).orElseThrow(() -> new vehicalNotFoundException(id));
    }

    // Delete Appointment
    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id) {

        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new vehicalNotFoundException(id));

        appointmentRepo.delete(appointment);

        return "Appointment deleted successfully!";
    }

    // Get appointments by email
    @GetMapping("/user/{email}")
    @CrossOrigin(origins = "http://localhost:3000")   // IMPORTANT
    public List<Appointment> getAppointmentsByEmail(@PathVariable String email) {
        return appointmentRepo.findByEmail(email);
    }



}
