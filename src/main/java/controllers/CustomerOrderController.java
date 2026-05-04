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

import dto.CustomerOrderDTO;
import services.CustomerOrderService;

@RestController
@RequestMapping("/api/customerOrder")
public class CustomerOrderController {
    @Autowired
    private CustomerOrderService service;

    // POST /api/customerOrder - create new item
    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody CustomerOrderDTO customerOrderDTO) {
        CustomerOrderDTO newCustomerOrder = service.createNew(customerOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomerOrder);
    }

    // GET /api/customerOrder - return all customerOrders
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CustomerOrderDTO> allCustomerOrderDTOs = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allCustomerOrderDTOs);
    }

    // GET /api/customerOrder/{id} - get a specific customerOrder by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CustomerOrderDTO customerOrderDTO = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customerOrderDTO);
    }

    // PUT /api/customerOrder/{id} - update an existing customerOrder
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, CustomerOrderDTO updatedCustomerOrderDTO) {
        service.updateById(updatedCustomerOrderDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource updated successfully.")
        );
    }

    // DELETE /api/customerOrder/{id} - delete the specific item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource deleted successfully.")
        );
    }
}
