package com.ak.native_runner_admin.notification;

import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class ProcessResultNotifier {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProcessResultNotifier.class);

  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.boot.admin.notify.mail.from")
  private String from;

  @Value("${spring.boot.admin.notify.mail.to")
  private String to;

  @Value("${spring.boot.admin.notify.mail.cc")
  private String cc;

  public void notifyError(ProcessError processError) {
    if (null == processError)
      throw new RuntimeException("Process error can not be null");

    LOGGER.info("Notifying with process error...");

    try {
      mailSender.send(getMessage(processError));
    }catch (MessagingException e) {
      LOGGER.error("Message sending failed!", e);
    }
  }

  public void notify(ProcessResult processResult) {
    if (null == processResult)
      throw new RuntimeException("Process result can not be null");

    LOGGER.info("Notifying with process result...");

    try {
      mailSender.send(getMessage(processResult));
    }catch (MessagingException e) {
      LOGGER.error("Message sending failed!", e);
    }
  }

  private MimeMessage getMessage(ProcessResultMessage processResult) throws MessagingException{
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
    message.setTo(to);
    message.setCc(cc);
    message.setFrom(from);
    message.setText(processResult.body());
    message.setSubject(processResult.title());
    return mimeMessage;
  }

}
