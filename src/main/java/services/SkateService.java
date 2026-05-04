package services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dto.SkateDTO;
import entities.Brand;
import entities.Skate;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.BrandRepository;
import repositories.SkateRepository;

@Service
public class SkateService {
    @Autowired
    private SkateRepository repo;

    @Autowired
    private BrandRepository brandRepo;

    @Transactional
    public SkateDTO createNew(SkateDTO skateDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = brandRepo.findById(skateDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException(skateDTO.getBrandId()));

        Skate skate = new Skate();
        skate.setName(skateDTO.getName());
        skate.setColor(skateDTO.getColor());
        skate.setSize(skateDTO.getSize());
        skate.setPrice(skateDTO.getPrice());
        skate.setBrand(brand);
        skate.setOwnerEmail(username);

        repo.save(skate);

        return skateDTO;
    }

    @Transactional
    public List<SkateDTO> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return repo.findAllByOwnerEmail(username).stream()
                .map(skate -> {
                    SkateDTO dto = new SkateDTO();
                    dto.setName(skate.getName());
                    dto.setColor(skate.getColor());
                    dto.setSize(skate.getSize());
                    dto.setPrice(skate.getPrice());
                    dto.setBrandId(skate.getBrand().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public SkateDTO getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Skate skate = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!skate.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        SkateDTO dto = new SkateDTO();
        dto.setName(skate.getName());
        dto.setColor(skate.getColor());
        dto.setSize(skate.getSize());
        dto.setPrice(skate.getPrice());
        dto.setBrandId(skate.getBrand().getId());

        return dto;
    }

    @Transactional
    public SkateDTO updateById(SkateDTO skateDTO, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Skate skate = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!skate.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        Brand brand = brandRepo.findById(skateDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException(skateDTO.getBrandId()));

        skate.setName(skateDTO.getName());
        skate.setColor(skateDTO.getColor());
        skate.setSize(skateDTO.getSize());
        skate.setPrice(skateDTO.getPrice());
        skate.setBrand(brand);

        repo.save(skate);

        return skateDTO;
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Skate skate = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!skate.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        repo.deleteById(id);
    }
}