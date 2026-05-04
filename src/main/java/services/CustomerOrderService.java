package services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dto.CustomerOrderDTO;
import entities.Blade;
import entities.CustomerOrder;
import entities.Skate;
import entities.User;
import exceptions.ForbiddenException;
import exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import repositories.BladeRepository;
import repositories.CustomerOrderRepository;
import repositories.SkateRepository;
import repositories.UserRepository;

@Service
public class CustomerOrderService {
    @Autowired
    private CustomerOrderRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SkateRepository skateRepo;

    @Autowired
    private BladeRepository bladeRepo;

    @Transactional
    public CustomerOrderDTO createNew(CustomerOrderDTO customerOrderDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findById(customerOrderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(customerOrderDTO.getUserId()));

        List<Skate> skates = skateRepo.findAllById(customerOrderDTO.getSkateIds());
        List<Blade> blades = bladeRepo.findAllById(customerOrderDTO.getBladeIds());

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setDatePlaced(customerOrderDTO.getDatePlaced());
        customerOrder.setReadyForPickup(customerOrderDTO.isReadyForPickup());
        customerOrder.setUser(user);
        customerOrder.setSkates(skates);
        customerOrder.setBlades(blades);
        customerOrder.setOwnerEmail(username);

        repo.save(customerOrder);

        return customerOrderDTO;
    }

    @Transactional
    public List<CustomerOrderDTO> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return repo.findAllByOwnerEmail(username).stream()
                .map(customerOrder -> {
                    CustomerOrderDTO dto = new CustomerOrderDTO();
                    dto.setDatePlaced(customerOrder.getDatePlaced());
                    dto.setReadyForPickup(customerOrder.getReadyForPickup());
                    dto.setUserId(customerOrder.getUser().getId());
                    dto.setSkateIds(customerOrder.getSkates().stream()
                            .map(Skate::getId)
                            .collect(Collectors.toList()));
                    dto.setBladeIds(customerOrder.getBlades().stream()
                            .map(Blade::getId)
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerOrderDTO getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerOrder customerOrder = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!customerOrder.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        CustomerOrderDTO dto = new CustomerOrderDTO();
        dto.setDatePlaced(customerOrder.getDatePlaced());
        dto.setReadyForPickup(customerOrder.getReadyForPickup());
        dto.setUserId(customerOrder.getUser().getId());
        dto.setSkateIds(customerOrder.getSkates().stream()
                .map(Skate::getId)
                .collect(Collectors.toList()));
        dto.setBladeIds(customerOrder.getBlades().stream()
                .map(Blade::getId)
                .collect(Collectors.toList()));

        return dto;
    }

    @Transactional
    public CustomerOrderDTO updateById(CustomerOrderDTO customerOrderDTO, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerOrder customerOrder = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!customerOrder.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        User user = userRepo.findById(customerOrderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(customerOrderDTO.getUserId()));

        List<Skate> skates = skateRepo.findAllById(customerOrderDTO.getSkateIds());
        List<Blade> blades = bladeRepo.findAllById(customerOrderDTO.getBladeIds());

        customerOrder.setDatePlaced(customerOrderDTO.getDatePlaced());
        customerOrder.setReadyForPickup(customerOrderDTO.isReadyForPickup());
        customerOrder.setUser(user);
        customerOrder.setSkates(skates);
        customerOrder.setBlades(blades);

        repo.save(customerOrder);

        return customerOrderDTO;
    }

    @Transactional
    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerOrder customerOrder = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!customerOrder.getOwnerEmail().equals(username)) {
            throw new ForbiddenException();
        }

        repo.deleteById(id);
    }
}