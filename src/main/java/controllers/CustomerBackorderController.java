package controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.CustomerBackorderDTO;
import services.CustomerBackorderService;

@RestController
@RequestMapping("/api/customerBackorder")
public class CustomerBackorderController {
    @Autowired
    private CustomerBackorderService service;

    // POST /api/customerBackorder - create new item
    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody CustomerBackorderDTO customerBackorderDTO) {
        CustomerBackorderDTO newCustomerBackorder = service.createNew(customerBackorderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomerBackorder);
    }

    // GET /api/customerBackorder - return all customerBackorders
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CustomerBackorderDTO> allCustomerBackorderDTOs = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allCustomerBackorderDTOs);
    }

    // GET /api/customerBackorder/{id} - get a specific customerBackorder by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CustomerBackorderDTO customerBackorderDTO = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customerBackorderDTO);
    }

    // PUT /api/customerBackorder/{id} - update an existing customerBackorder
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, CustomerBackorderDTO updatedCustomerBackorderDTO) {
        service.updateById(updatedCustomerBackorderDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource updated successfully.")
        );
    }

    // DELETE /api/customerBackorder/{id} - delete the specific item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource deleted successfully.")
        );
    }
}
