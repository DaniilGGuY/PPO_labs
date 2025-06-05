package com.example.ppo.consultant;

import com.example.ppo.exception.consultant.*;
import com.example.ppo.message.Message;
import com.example.ppo.order.Order;
import com.example.ppo.review.Review;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ConsultantService implements IConsultantService {
    private final IConsultantRepository consultRepo;

    public ConsultantService(IConsultantRepository consultRepo) {
        this.consultRepo = consultRepo;
    }

    @Override
    @Transactional
    public Consultant registerConsultant(Consultant consultant) 
            throws ConsultantValidationException, ConsultantAlreadyExistsException, ConsultantOperationException {
        if (!consultant.isValid()) {
            log.warn("Invalid consultant");
            throw new ConsultantValidationException("Invalid consultant data");
        }

        try {
            if (consultRepo.existsByLogin(consultant.getLogin())) {
                log.warn("Consultant with login={} already exists", consultant.getLogin());
                throw new ConsultantAlreadyExistsException(consultant.getLogin());
            }
            consultant = consultRepo.save(consultant);
            log.info("Saved consultant with login={}", consultant.getLogin());
            return consultant;
        } catch (Exception e) {
            log.error("Failed to register consultant", e);
            throw new ConsultantOperationException("Failed to register consultant: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Consultant getConsultantByLogin(String login) 
            throws ConsultantNotFoundException, ConsultantOperationException {
        try {
            if (!consultRepo.existsByLogin(login)) {
                log.warn("Consultant with login={} not found", login);
                throw new ConsultantAlreadyExistsException(login);
            }
            Consultant consultant = consultRepo.findByLogin(login).get();
            log.info("Got consultant with login={}", login);
            return consultant;
        } catch (Exception e) {
            log.error("Failed to get consultant", e);
            throw new ConsultantOperationException("Failed to get consultant: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Consultant updateConsultant(Consultant consultant) 
            throws ConsultantNotFoundException,  ConsultantValidationException, ConsultantOperationException {
        if (!consultant.isValid()) {
            log.warn("Invalid consultant");
            throw new ConsultantValidationException("Invalid consultant data");
        }

        try {
            if (!consultRepo.existsByLogin(consultant.getLogin())) {
                log.warn("Consultant with login={} already exists", consultant.getLogin());
                throw new ConsultantNotFoundException(consultant.getLogin());
            }
            consultant = consultRepo.update(consultant);
            log.info("Updated consultant with login={}", consultant.getLogin());
            return consultant;
        } catch (Exception e) {
            log.error("Failed to update consultant", e);
            throw new ConsultantOperationException("Failed to update consultant: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteConsultant(String login) 
            throws ConsultantNotFoundException, ConsultantOperationException {
        try {
            if (!consultRepo.existsByLogin(login)) {
                log.warn("Consultant with login={} already exists", login);
                throw new ConsultantNotFoundException(login);
            }
            consultRepo.deleteByLogin(login);
            log.info("Deleted consultant with login={}", login);
        } catch (Exception e) {
            log.error("Failed to delete consultant", e);
            throw new ConsultantOperationException("Failed to delete consultant: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean consultantExists(String login) 
            throws ConsultantOperationException {
        try {
            return consultRepo.existsByLogin(login);
        } catch (Exception e) {
            log.error("Failed to check consultant exists with", e);
            throw new ConsultantOperationException("Failed to check consultant exists: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Consultant> findAllConsultants() 
            throws ConsultantOperationException {
        try {
            return consultRepo.findAll();
        } catch (Exception e) {
            log.error("Failed to find consultants with", e);
            throw new ConsultantOperationException("Failed to find consultants: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getConsultantOrders(Consultant consultant) {
        return consultant.getOrders();
    }

    @Override
    public List<Message> getConsultantMessages(Consultant consultant) {
        return consultant.getMessages();
    }

    @Override
    public List<Review> getConsultantReviews(Consultant consultant) {
        return consultant.getReviews();
    }
}