package br.com.Glaudencio12.repository;

import br.com.Glaudencio12.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
