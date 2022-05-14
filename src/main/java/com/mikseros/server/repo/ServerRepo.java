package com.mikseros.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikseros.server.model.Server;

public interface ServerRepo extends JpaRepository<Server, Long>{
	Server findByIpAddress(String ipAddress);
}
