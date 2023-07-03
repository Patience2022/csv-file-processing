package com.eviro.assessment.grad001.patienceCele.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviro.assessment.grad001.patienceCele.entity.AccountProfile;

public interface AccountProfileRepo extends JpaRepository<AccountProfile, Long> {
	Optional<AccountProfile> findByNameAndSurname(String name, String surname);
}
