package backend.controller;

import backend.exception.vehicalNotFoundException;
import backend.model.Sales;
import backend.repository.SalesRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesRepo salesRepo;


    @PostMapping
    public Sales addSales(@RequestBody Sales sales) {
        return salesRepo.save(sales);   // FIXED
    }


    @GetMapping
    public List<Sales> getAllSales() {
        return salesRepo.findAll();
    }


    @GetMapping("/{id}")
    public Sales getSalesById(@PathVariable Long id) {
        return salesRepo.findById(id)
                .orElseThrow(() -> new vehicalNotFoundException(id));
    }


    @PutMapping("/{id}")
    public Sales updateSalesWithFile(
            @RequestPart("SalesDetails") String salesDetails,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable Long id) {

        System.out.println("SalesDetails: " + salesDetails);

        if (file != null) {
            System.out.println("File received: " + file.getOriginalFilename());
        }

        ObjectMapper mapper = new ObjectMapper();
        Sales newSales;

        try {
            newSales = mapper.readValue(salesDetails, Sales.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SalesDetails", e);
        }

        return salesRepo.findById(id).map(existingSales -> {

            existingSales.setVehicleId(newSales.getVehicleId());
            existingSales.setSoldTo(newSales.getSoldTo());
            existingSales.setSoldDate(newSales.getSoldDate());
            existingSales.setTaxPercentage(newSales.getTaxPercentage());
            existingSales.setCommissionPercentage(newSales.getCommissionPercentage());
            existingSales.setSellingPrice(newSales.getSellingPrice());
            existingSales.setSalesmanId(newSales.getSalesmanId());
            existingSales.setSalesmanName(newSales.getSalesmanName());

            return salesRepo.save(existingSales);

        }).orElseThrow(() -> new vehicalNotFoundException(id));
    }


    @DeleteMapping("/{id}")
    String deleteSales(@PathVariable Long id) {

        Sales sales = salesRepo.findById(id)
                .orElseThrow(() -> new vehicalNotFoundException(id));

        salesRepo.delete(sales);
        return "Sales deleted successfully!";
    }
}
