package com.Dyna.personalDiary.Service;

import com.Dyna.personalDiary.login.User;
import com.Dyna.personalDiary.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    public UserRepository repository;

    private static TemplateEngine templateEngine;
    private static Context context;
    private static JavaMailSender emailSender;

    private Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    String getEmail(){
        User user = new User();
        String email = user.getEmail();
        return email;
    }

    public void prepareAndSendEmail() throws MessagingException, javax.mail.MessagingException {
        String htmlTemplate = "templates/emailTemplate";
        String mailTo = getEmail();

        //khkrishna194@gmail.com
        initializeTemplateEngine();

        context.setVariable("sender", "Thymeleaf Email");
        context.setVariable("mailTo", mailTo);

        String htmlBody = templateEngine.process(htmlTemplate, context);
        sendEmail(mailTo, "Registration successful", htmlBody);
    }

    private void sendEmail(String mailTo, String subject, String mailBody) throws javax.mail.MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(mailTo);
        helper.setSubject(subject);
        helper.setText(mailBody, true);
        emailSender.send(message);
        LOG.info("Email sent to " + mailTo);

    }

    private static void initializeTemplateEngine() throws MessagingException, javax.mail.MessagingException {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML5");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        context = new Context(Locale.UK);

    }
}
