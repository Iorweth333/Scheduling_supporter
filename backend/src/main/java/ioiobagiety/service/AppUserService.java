package ioiobagiety.service;

import ioiobagiety.exception.BadRequestException;
import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.user.AppUser;

import java.util.List;

public interface AppUserService {
    AppUser create (AppUser user) throws BadRequestException;

    AppUser get (Long id) throws ResourceNotFoundException;

    List<AppUser> getAll () throws ResourceNotFoundException;
}
