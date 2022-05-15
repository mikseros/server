package com.mikseros.server.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mikseros.server.model.Response;
import com.mikseros.server.model.Server;
import com.mikseros.server.service.implementation.ServerServiceImpl;

import lombok.RequiredArgsConstructor;

import java.io.IOException;


import javax.validation.Valid;
import java.util.Map;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static com.mikseros.server.enumeration.Status.SERVER_UP;

// Technically it is controller
@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
	private final ServerServiceImpl serverService;
	
	@GetMapping("/list")
	public ResponseEntity<Response> getServers() {
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(now())
						.data(Map.of("servers", serverService.list(30)))
						.message("Servers retrieved")
						.status(OK)
						.statusCode(OK.value())
						.build()
		);
	}
	
	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
		Server server = serverService.ping(ipAddress);
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(now())
						.data(Map.of("server", server))
						.message(server.getStatus() == SERVER_UP ? "Ping success" : "Ping failed")
						.status(OK)
						.statusCode(OK.value())
						.build()
		);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(now())
						.data(Map.of("server", serverService.create(server)))
						.message("Server created")
						.status(CREATED)
						.statusCode(CREATED.value())
						.build()
		);
	}
}
