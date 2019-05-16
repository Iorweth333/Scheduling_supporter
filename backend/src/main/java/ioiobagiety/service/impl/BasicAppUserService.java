package ioiobagiety.service.impl;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.user.AppUser;
import ioiobagiety.repository.AppUserRepository;
import ioiobagiety.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BasicAppUserService implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Transactional
    public AppUser create(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Transactional
    public AppUser get(Long id) {
        return appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppUser not found"));
    }

    @Transactional
    public List<AppUser> getAll() {
        List<AppUser> appUsers = appUserRepository.findAll();
        if (appUsers.size() > 0) {
            return appUsers;
        } else {
            throw new ResourceNotFoundException("No AppUser found");
        }
    }
}
