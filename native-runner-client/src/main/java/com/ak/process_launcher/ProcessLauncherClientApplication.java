package com.ak.process_launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProcessLauncherClientApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ProcessLauncherClientApplication.class, args);
	}
}
