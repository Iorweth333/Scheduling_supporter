package ioiobagiety.service;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.user.AppUser;

import java.util.List;

public interface AppUserService {
    AppUser create(AppUser user);

    AppUser get(Long id) throws ResourceNotFoundException;

    List<AppUser> getAll() throws ResourceNotFoundException;
}
