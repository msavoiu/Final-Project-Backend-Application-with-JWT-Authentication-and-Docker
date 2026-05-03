package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public Brand createNew(Brand brand) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        brand.setOwnerEmail(username);
        return repo.save(brand);
    }

    @Transactional
    public List<Brand> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findAllByOwnerEmail(username);
    }

    @Transactional
    public Brand getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
            
        if (brand.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        return brand;
    }

    @Transactional
    public void updateById(Brand updatedBrand, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (brand.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        repo.save(updatedBrand);
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Brand brand = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (brand.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }
        
        repo.deleteById(id);
    }
}
