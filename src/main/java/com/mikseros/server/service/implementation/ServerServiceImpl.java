package com.mikseros.server.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mikseros.server.enumeration.Status;
import com.mikseros.server.model.Server;
import com.mikseros.server.repo.ServerRepo;
import com.mikseros.server.service.ServerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Lombok is creating constructor with field serverRepo
@RequiredArgsConstructor
@Service
@Transactional
// Slf4j This allow us to see in the console what is happening.
@Slf4j
public class ServerServiceImpl implements ServerService{
	private final ServerRepo serverRepo;
	
	@Override
	public Server create(Server server) {
		log.info("Saving new server: {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		return serverRepo.save(server);
	}
	
	@Override
	public Server ping(String ipAddress) throws IOException {
		log.info("Pinging server IP: {}", ipAddress);
		Server server = serverRepo.findByIpAddress(ipAddress);
		InetAddress address = InetAddress.getByName(ipAddress);
		server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		serverRepo.save(server);
		return server;
	}

	@Override
	public Collection<Server> list(int limit) {
		log.info("Fetching all servers");
		return serverRepo.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		log.info("Fetching server by id: {}", id);
		return serverRepo.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("Updating server: {}", server.getName());
		return serverRepo.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting server by ID: {}", id);serverRepo.deleteById(id);
		return true;
	}
	
	private String setServerImageUrl() {
		return null;
	}
	
}
