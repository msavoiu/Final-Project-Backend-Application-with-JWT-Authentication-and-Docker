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

import entities.Skate;
import services.SkateService;

@RestController
@RequestMapping("/api/skate")
public class SkateController {
    @Autowired
    private SkateService service;

    // POST /api/skate - create new item
    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody Skate skate) {
        Skate newSkate = service.createNew(skate);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSkate);
    }

    // GET /api/skate - return all skates
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Skate> allSkates = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allSkates);
    }

    // GET /api/skate/{id} - get a specific skate by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Skate skate = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(skate);
    }

    // PUT /api/skate/{id} - update an existing skate
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, Skate updatedSkate) {
        service.updateById(updatedSkate, id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource updated successfully.")
        );
    }

    // DELETE /api/skate/{id} - delete the specific
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource deleted successfully.")
        );
    }
}
