package services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dto.BackorderDTO;
import entities.Backorder;
import entities.Blade;
import entities.Brand;
import entities.Skate;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.BackorderRepository;
import repositories.BladeRepository;
import repositories.BrandRepository;
import repositories.SkateRepository;

@Service
public class BackorderService {
    @Autowired
    private BackorderRepository repo;

    @Autowired
    private SkateRepository skateRepo;

    @Autowired
    private BladeRepository bladeRepo;

    @Autowired
    private BrandRepository brandRepo;

    @Transactional
    public BackorderDTO createNew(BackorderDTO backorderDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = brandRepo.findById(backorderDTO.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        List<Skate> skates = skateRepo.findAllById(backorderDTO.getSkateIds());
        List<Blade> blades = bladeRepo.findAllById(backorderDTO.getBladeIds());

        Backorder backorder = new Backorder();
        backorder.setDatePlaced(backorderDTO.getDatePlaced());
        backorder.setReceived(backorderDTO.getReceived());
        backorder.setBrand(brand);
        backorder.setSkates(skates);
        backorder.setBlades(blades);
        backorder.setOwnerEmail(username);

        repo.save(backorder);

        return backorderDTO;
    }

    @Transactional
    public List<Backorder> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findAllByOwnerEmail(username);
    }

    @Transactional
    public BackorderDTO getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Backorder backorder = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!backorder.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        BackorderDTO dto = new BackorderDTO();
        dto.setDatePlaced(backorder.getDatePlaced());
        dto.setReceived(backorder.getReceived());
        dto.setBrandId(backorder.getBrand().getId());
        dto.setSkateIds(backorder.getSkates().stream()
                .map(Skate::getId)
                .collect(Collectors.toList()));
        dto.setBladeIds(backorder.getBlades().stream()
                .map(Blade::getId)
                .collect(Collectors.toList()));

        return dto;
    }

    @Transactional
    public BackorderDTO updateById(BackorderDTO backorderDTO, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Backorder backorder = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!backorder.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        Brand brand = brandRepo.findById(backorderDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException(backorderDTO.getBrandId()));

        List<Skate> skates = skateRepo.findAllById(backorderDTO.getSkateIds());
        List<Blade> blades = bladeRepo.findAllById(backorderDTO.getBladeIds());

        backorder.setDatePlaced(backorderDTO.getDatePlaced());
        backorder.setReceived(backorderDTO.getReceived());
        backorder.setBrand(brand);
        backorder.setSkates(skates);
        backorder.setBlades(blades);

        repo.save(backorder);

        return backorderDTO;
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Backorder backorder = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (backorder.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }
        
        repo.deleteById(id);
    }
}
