package services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dto.BladeDTO;
import entities.Blade;
import entities.Brand;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.BladeRepository;
import repositories.BrandRepository;

@Service
public class BladeService {
    @Autowired
    private BladeRepository repo;

    @Autowired
    private BrandRepository brandRepository;

    @Transactional
    public BladeDTO createNew(BladeDTO bladeDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = brandRepository.findById(bladeDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException(bladeDTO.getBrandId()));

        Blade blade = new Blade();
        blade.setName(bladeDTO.getName());
        blade.setLength(bladeDTO.getLength());
        blade.setRocker(bladeDTO.getRocker());
        blade.setPrice(bladeDTO.getPrice());
        blade.setBrand(brand);
        blade.setOwnerEmail(username);

        repo.save(blade);

        return bladeDTO;
    }

    @Transactional
    public List<BladeDTO> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return repo.findAllByOwnerEmail(username).stream()
                .map(blade -> {
                    BladeDTO dto = new BladeDTO();
                    dto.setName(blade.getName());
                    dto.setLength(blade.getLength());
                    dto.setRocker(blade.getRocker());
                    dto.setPrice(blade.getPrice());
                    dto.setBrandId(blade.getBrand().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public BladeDTO getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Blade blade = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!blade.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        BladeDTO dto = new BladeDTO();
        dto.setName(blade.getName());
        dto.setLength(blade.getLength());
        dto.setRocker(blade.getRocker());
        dto.setPrice(blade.getPrice());
        dto.setBrandId(blade.getBrand().getId());

        return dto;
    }

    @Transactional
    public BladeDTO updateById(BladeDTO bladeDTO, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Blade blade = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!blade.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        Brand brand = brandRepository.findById(bladeDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException(bladeDTO.getBrandId()));

        blade.setName(bladeDTO.getName());
        blade.setLength(bladeDTO.getLength());
        blade.setRocker(bladeDTO.getRocker());
        blade.setPrice(bladeDTO.getPrice());
        blade.setBrand(brand);

        repo.save(blade);

        return bladeDTO;
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Blade blade = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!blade.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        repo.deleteById(id);
    }
}