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

import dto.BladeDTO;
import services.BladeService;

@RestController
@RequestMapping("/api/blade")
public class BladeController {
    @Autowired
    private BladeService service;

    // POST /api/blade - create new item
    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody BladeDTO bladeDTO) {
        BladeDTO newBlade = service.createNew(bladeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBlade);
    }

    // GET /api/blade - return all blades
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<BladeDTO> allBlades = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allBlades);
    }

    // GET /api/blade/{id} - get a specific blade by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        BladeDTO bladeDTO = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bladeDTO);
    }

    // PUT /api/blade/{id} - update an existing blade
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, BladeDTO updatedBladeDTO) {
        service.updateById(updatedBladeDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource updated successfully.")
        );
    }

    // DELETE /api/blade/{id} - delete the specific item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource deleted successfully.")
        );
    }
}
