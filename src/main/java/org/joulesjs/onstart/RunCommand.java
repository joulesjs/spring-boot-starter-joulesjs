package org.joulesjs.onstart;

import java.io.IOException;
import java.util.List;

import javax.annotation.PreDestroy;

import org.joulesjs.JoulesJsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(JoulesJsProperties.class)
public final class RunCommand implements ApplicationListener<ApplicationReadyEvent> {

	private final JoulesJsProperties properties;
	private final ApplicationContext appContext;
	private Process cmdProcess;

	@Autowired
	public RunCommand(JoulesJsProperties properties, ApplicationContext appContext) {
		this.properties = properties;
		this.appContext = appContext;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		Integer port = appContext.getEnvironment().getProperty("local.server.port", Integer.class);
		String sbUrl = "http://localhost:" + port;

		List<String> cmd = properties.getCommand();

		if (cmd.isEmpty()) {
			System.out.println("Your application is available at: " + sbUrl);
		} else {
			// cmd will contain a placeholder "%s" that will be replaced with the server url
			String[] updatedCmd = cmd.stream().map(c -> String.format(c, sbUrl)).toArray(String[]::new);
			new Thread(() -> runCommand(updatedCmd)).start();
		}
	}

	@PreDestroy
	private void onExitKillCommand() {
		cmdProcess.destroy();
	}

	private void runCommand(String[] cmd) {
		try {
			cmdProcess = Runtime.getRuntime().exec(cmd);
			cmdProcess.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (Boolean.TRUE.equals(properties.getAllowHeadless())) {
				// Continue SpringBoot execution in the background
			} else {
				SpringApplication.exit(appContext, () -> 0);
			}
		}
	}
}