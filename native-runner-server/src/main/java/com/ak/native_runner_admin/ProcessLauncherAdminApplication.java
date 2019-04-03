package com.ak.native_runner_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;


@SpringBootApplication
@EnableAdminServer
public class ProcessLauncherAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessLauncherAdminApplication.class, args);
	}

}

