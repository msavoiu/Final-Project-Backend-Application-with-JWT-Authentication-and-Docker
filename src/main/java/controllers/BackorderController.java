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

import entities.Backorder;
import services.BackorderService;

@RestController
@RequestMapping("/api/backorder")
public class BackorderController {
    @Autowired
    private BackorderService service;

    // POST /api/backorder - create new item
    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody Backorder backorder) {
        Backorder newBackorder = service.createNew(backorder);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBackorder);
    }

    // GET /api/backorder - return all backorders
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Backorder> allBackorders = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allBackorders);
    }

    // GET /api/backorder/{id} - get a specific backorder by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Backorder backorder = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(backorder);
    }

    // PUT /api/backorder/{id} - update an existing backorder
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, Backorder updatedBackorder) {
        service.updateById(updatedBackorder, id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource updated successfully.")
        );
    }

    // DELETE /api/backorder/{id} - delete the specific
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource deleted successfully.")
        );
    }
}
