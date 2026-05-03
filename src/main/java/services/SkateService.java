package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import entities.Skate;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.SkateRepository;

@Service
public class SkateService {
    @Autowired
    private SkateRepository repo;

    @Transactional
    public Skate createNew(Skate skate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        skate.setOwnerEmail(username);
        return repo.save(skate);
    }

    @Transactional
    public List<Skate> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findAllByOwnerEmail(username);
    }

    @Transactional
    public Skate getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Skate skate = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
            
        if (skate.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        return skate;
    }

    @Transactional
    public void updateById(Skate updatedSkate, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Skate skate = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (skate.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        repo.save(updatedSkate);
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Skate skate = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (skate.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }
        
        repo.deleteById(id);
    }
}
