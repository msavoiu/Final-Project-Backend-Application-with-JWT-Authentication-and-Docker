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

import entities.Blade;
import services.BladeService;

@RestController
@RequestMapping("/api/blade")
public class BladeController {
    @Autowired
    private BladeService service;

    // POST /api/blade - create new item
    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody Blade blade) {
        Blade newBlade = service.createNew(blade);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBlade);
    }

    // GET /api/blade - return all blades
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Blade> allBlades = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allBlades);
    }

    // GET /api/blade/{id} - get a specific blade by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Blade blade = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(blade);
    }

    // PUT /api/blade/{id} - update an existing blade
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, Blade updatedBlade) {
        service.updateById(updatedBlade, id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource updated successfully.")
        );
    }

    // DELETE /api/blade/{id} - delete the specific
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource deleted successfully.")
        );
    }
}
