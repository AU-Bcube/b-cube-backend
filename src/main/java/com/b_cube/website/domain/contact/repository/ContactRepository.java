package com.b_cube.website.domain.contact.repository;

import com.b_cube.website.domain.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
