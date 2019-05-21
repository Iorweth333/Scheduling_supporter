/* @(#) $Id$
 *
 * Copyright (c) 2000-2019 Comarch SA All Rights Reserved. Any usage,
 * duplication or redistribution of this software is allowed only according to
 * separate agreement prepared in written between Comarch and authorized party.
 */
package ioiobagiety.repository;

import ioiobagiety.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Krystian Życiński
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
     AppUser findBySurname(String name);
     
     AppUser findByName(String name);
}
