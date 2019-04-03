package com.ak.native_runner_admin.config;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.MailNotifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;

@Configuration
public class NotifierConfiguration {

  @Primary
  @Bean(initMethod = "start", destroyMethod = "stop")
  public RemindingNotifier remindingNotifier(final MailNotifier notifier, final InstanceRepository instanceRepository) {
    RemindingNotifier remindingNotifier = new RemindingNotifier(notifier, instanceRepository);
    remindingNotifier.setReminderPeriod(Duration.ofMinutes(10));
    remindingNotifier.setCheckReminderInverval(Duration.ofSeconds(10));
    return remindingNotifier;
  }
}