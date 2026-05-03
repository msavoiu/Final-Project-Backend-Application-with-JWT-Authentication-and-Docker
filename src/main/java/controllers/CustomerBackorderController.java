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

import entities.CustomerBackorder;
import services.CustomerBackorderService;

@RestController
@RequestMapping("/api/customerBackorder")
public class CustomerBackorderController {
    @Autowired
    private CustomerBackorderService service;

    // POST /api/customerBackorder - create new item
    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody CustomerBackorder customerBackorder) {
        CustomerBackorder newCustomerBackorder = service.createNew(customerBackorder);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomerBackorder);
    }

    // GET /api/customerBackorder - return all customerBackorders
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CustomerBackorder> allCustomerBackorders = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allCustomerBackorders);
    }

    // GET /api/customerBackorder/{id} - get a specific customerBackorder by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CustomerBackorder customerBackorder = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customerBackorder);
    }

    // PUT /api/customerBackorder/{id} - update an existing customerBackorder
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, CustomerBackorder updatedCustomerBackorder) {
        service.updateById(updatedCustomerBackorder, id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource updated successfully.")
        );
    }

    // DELETE /api/customerBackorder/{id} - delete the specific
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource deleted successfully.")
        );
    }
}
