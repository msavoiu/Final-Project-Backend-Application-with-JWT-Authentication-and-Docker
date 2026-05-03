package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import entities.Backorder;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.BackorderRepository;

@Service
public class BackorderService {
    @Autowired
    private BackorderRepository repo;

    @Transactional
    public Backorder createNew(Backorder backorder) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        backorder.setOwnerEmail(username);
        return repo.save(backorder);
    }

    @Transactional
    public List<Backorder> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findAllByOwnerEmail(username);
    }

    @Transactional
    public Backorder getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Backorder backorder = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
            
        if (backorder.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        return backorder;
    }

    @Transactional
    public void updateById(Backorder updatedBackorder, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Backorder backorder = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (backorder.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        repo.save(updatedBackorder);
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
