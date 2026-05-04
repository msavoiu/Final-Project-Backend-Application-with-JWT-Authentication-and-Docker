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

import dto.BrandDTO;
import services.BrandService;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService service;

    // POST /api/brand - create new item
    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody BrandDTO brandDTO) {
        BrandDTO newBrand = service.createNew(brandDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBrand);
    }

    // GET /api/brand - return all brands
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<BrandDTO> allBrandDTOs = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allBrandDTOs);
    }

    // GET /api/brand/{id} - get a specific brand by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        BrandDTO brandDTO = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(brandDTO);
    }

    // PUT /api/brand/{id} - update an existing brand
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, BrandDTO updatedBrandDTO) {
        service.updateById(updatedBrandDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource updated successfully.")
        );
    }

    // DELETE /api/brand/{id} - delete the specific item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("message", "Resource deleted successfully.")
        );
    }
}
