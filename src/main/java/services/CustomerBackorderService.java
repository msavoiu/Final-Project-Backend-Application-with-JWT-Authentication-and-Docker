package services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dto.CustomerBackorderDTO;
import entities.Backorder;
import entities.CustomerBackorder;
import entities.User;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.BackorderRepository;
import repositories.CustomerBackorderRepository;
import repositories.UserRepository;

@Service
public class CustomerBackorderService {
    @Autowired
    private CustomerBackorderRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BackorderRepository backorderRepo;

    @Transactional
    public CustomerBackorderDTO createNew(CustomerBackorderDTO customerBackorderDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findById(customerBackorderDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(customerBackorderDTO.getCustomerId()));

        Backorder backorder = backorderRepo.findById(customerBackorderDTO.getBackorderId())
                .orElseThrow(() -> new ResourceNotFoundException(customerBackorderDTO.getBackorderId()));

        CustomerBackorder customerBackorder = new CustomerBackorder();
        customerBackorder.setUser(user);
        customerBackorder.setBackorder(backorder);
        customerBackorder.setOwnerEmail(username);

        repo.save(customerBackorder);

        return customerBackorderDTO;
    }

    @Transactional
    public List<CustomerBackorderDTO> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return repo.findAllByOwnerEmail(username).stream()
                .map(customerBackorder -> {
                    CustomerBackorderDTO dto = new CustomerBackorderDTO();
                    dto.setCustomerId(customerBackorder.getUser().getId());
                    dto.setBackorderId(customerBackorder.getBackorder().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerBackorderDTO getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerBackorder customerBackorder = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!customerBackorder.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        CustomerBackorderDTO dto = new CustomerBackorderDTO();
        dto.setCustomerId(customerBackorder.getUser().getId());
        dto.setBackorderId(customerBackorder.getBackorder().getId());

        return dto;
    }

    @Transactional
    public CustomerBackorderDTO updateById(CustomerBackorderDTO customerBackorderDTO, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerBackorder customerBackorder = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!customerBackorder.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        User user = userRepo.findById(customerBackorderDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(customerBackorderDTO.getCustomerId()));

        Backorder backorder = backorderRepo.findById(customerBackorderDTO.getBackorderId())
                .orElseThrow(() -> new ResourceNotFoundException(customerBackorderDTO.getBackorderId()));

        customerBackorder.setUser(user);
        customerBackorder.setBackorder(backorder);

        repo.save(customerBackorder);

        return customerBackorderDTO;
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerBackorder customerBackorder = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!customerBackorder.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        repo.deleteById(id);
    }
}