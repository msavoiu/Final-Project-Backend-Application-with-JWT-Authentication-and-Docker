package services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dto.BrandDTO;
import entities.Brand;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.BrandRepository;

@Service
public class BrandService {
    @Autowired
    private BrandRepository repo;

    @Transactional
    public BrandDTO createNew(BrandDTO brandDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brand.setPhoneNumber(brandDTO.getPhoneNumber());
        brand.setEmail(brandDTO.getEmail());
        brand.setOwnerEmail(username);

        repo.save(brand);

        return brandDTO;
    }

    @Transactional
    public List<BrandDTO> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return repo.findAllByOwnerEmail(username).stream()
                .map(brand -> {
                    BrandDTO dto = new BrandDTO();
                    dto.setName(brand.getName());
                    dto.setPhoneNumber(brand.getPhoneNumber());
                    dto.setEmail(brand.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public BrandDTO getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!brand.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        BrandDTO dto = new BrandDTO();
        dto.setName(brand.getName());
        dto.setPhoneNumber(brand.getPhoneNumber());
        dto.setEmail(brand.getEmail());

        return dto;
    }

    @Transactional
    public BrandDTO updateById(BrandDTO brandDTO, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!brand.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        brand.setName(brandDTO.getName());
        brand.setPhoneNumber(brandDTO.getPhoneNumber());
        brand.setEmail(brandDTO.getEmail());

        repo.save(brand);

        return brandDTO;
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!brand.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        repo.deleteById(id);
    }
}