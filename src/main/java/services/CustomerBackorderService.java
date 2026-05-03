package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import entities.CustomerBackorder;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.CustomerBackorderRepository;

@Service
public class CustomerBackorderService {
    @Autowired
    private CustomerBackorderRepository repo;

    @Transactional
    public CustomerBackorder createNew(CustomerBackorder customerBackorder) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        customerBackorder.setOwnerEmail(username);
        return repo.save(customerBackorder);
    }

    @Transactional
    public List<CustomerBackorder> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findAllByOwnerEmail(username);
    }

    @Transactional
    public CustomerBackorder getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerBackorder customerBackorder = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
            
        if (customerBackorder.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        return customerBackorder;
    }

    @Transactional
    public void updateById(CustomerBackorder updatedCustomerBackorder, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerBackorder customerBackorder = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (customerBackorder.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        repo.save(updatedCustomerBackorder);
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerBackorder customerBackorder = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (customerBackorder.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }
        
        repo.deleteById(id);
    }
}
