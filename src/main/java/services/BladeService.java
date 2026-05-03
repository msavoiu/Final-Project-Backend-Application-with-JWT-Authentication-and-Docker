package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import entities.Blade;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.BladeRepository;

@Service
public class BladeService {
    @Autowired
    private BladeRepository repo;

    @Transactional
    public Blade createNew(Blade blade) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        blade.setOwnerEmail(username);
        return repo.save(blade);
    }

    @Transactional
    public List<Blade> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findAllByOwnerEmail(username);
    }

    @Transactional
    public Blade getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Blade blade = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
            
        if (blade.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        return blade;
    }

    @Transactional
    public void updateById(Blade updatedBlade, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Blade blade = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (blade.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        repo.save(updatedBlade);
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Blade blade = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (blade.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }
        
        repo.deleteById(id);
    }
}
