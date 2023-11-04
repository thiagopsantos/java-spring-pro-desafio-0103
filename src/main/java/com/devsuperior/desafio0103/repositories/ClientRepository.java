package com.devsuperior.desafio0103.repositories;

import com.devsuperior.desafio0103.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
