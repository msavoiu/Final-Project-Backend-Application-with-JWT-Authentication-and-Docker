package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import entities.CustomerOrder;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.CustomerOrderRepository;

@Service
public class CustomerOrderService {
    @Autowired
    private CustomerOrderRepository repo;

    @Transactional
    public CustomerOrder createNew(CustomerOrder customerOrder) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        customerOrder.setOwnerEmail(username);
        return repo.save(customerOrder);
    }

    @Transactional
    public List<CustomerOrder> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findAllByOwnerEmail(username);
    }

    @Transactional
    public CustomerOrder getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerOrder customerOrder = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
            
        if (customerOrder.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        return customerOrder;
    }

    @Transactional
    public void updateById(CustomerOrder updatedCustomerOrder, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerOrder customerOrder = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (customerOrder.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }

        repo.save(updatedCustomerOrder);
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerOrder customerOrder = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        if (customerOrder.getOwnerEmail() != username) {
            throw new ForbiddenException();
        }
        
        repo.deleteById(id);
    }
}
