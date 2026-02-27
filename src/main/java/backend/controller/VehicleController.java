package backend.controller;

import backend.exception.vehicalNotFoundException;
import backend.model.Vehicle;
import backend.repository.VehicleRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleRepo vehicleRepo;

    // Add new vehicle
    @PostMapping
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        return vehicleRepo.save(vehicle);
    }

    // Get all vehicles
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }

    // Get vehicle by ID
    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable Long id) {
        return vehicleRepo.findById(id)
                .orElseThrow(() -> new vehicalNotFoundException(id));
    }

    @PutMapping("/Vehicle/{id}")
    public Vehicle updateVehicleWithFile(
            @RequestPart("VehicleDetails") String vehicleDetails,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable Long id) {

        System.out.println("VehicleDetails: " + vehicleDetails);

        if (file != null) {
            System.out.println("File received: " + file.getOriginalFilename());
        } else {
            System.out.println("No file uploaded");
        }

        ObjectMapper mapper = new ObjectMapper();
        Vehicle newVehicle;

        try {
            newVehicle = mapper.readValue(vehicleDetails, Vehicle.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing VehicleDetails", e);
        }

        return vehicleRepo.findById(id).map(existingVehicle -> {

            existingVehicle.setVehicleId(newVehicle.getVehicleId());
            existingVehicle.setBrand(newVehicle.getBrand());
            existingVehicle.setModel(newVehicle.getModel());
            existingVehicle.setYear(newVehicle.getYear());
            existingVehicle.setPrice(newVehicle.getPrice());
            existingVehicle.setTax(newVehicle.getTax());
            existingVehicle.setSellingPrice(newVehicle.getSellingPrice());


            return vehicleRepo.save(existingVehicle);

        }).orElseThrow(() -> new vehicalNotFoundException(id));
    }

    // Simple update API
    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable Long id, @RequestBody Vehicle newVehicle) {
        return vehicleRepo.findById(id).map(existingVehicle -> {

            existingVehicle.setBrand(newVehicle.getBrand());
            existingVehicle.setModel(newVehicle.getModel());
            existingVehicle.setYear(newVehicle.getYear());
            existingVehicle.setPrice(newVehicle.getPrice());
            existingVehicle.setTax(newVehicle.getTax());
            existingVehicle.setCommision(newVehicle.getCommision());
            existingVehicle.setSellingPrice(newVehicle.getSellingPrice());

            return vehicleRepo.save(existingVehicle);

        }).orElseThrow(() -> new vehicalNotFoundException(id));
    }

    // Delete vehicle
    @DeleteMapping("/{id}")
     String deleteVehicle(@PathVariable Long id) {
        //check item is exist database
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new vehicalNotFoundException(id));
//delete item from the repo
        vehicleRepo.delete(vehicle);
        return "Vehicle deleted successfully!";
    }

}
