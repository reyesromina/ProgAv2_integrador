package com.undec.scheduled;

import input.ActivateUserInput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
@EnableScheduling
public class ActivateUserscheduled {
    private final ActivateUserInput activateUserInput;

    public ActivateUserscheduled(ActivateUserInput activateUserInput) {
        this.activateUserInput = activateUserInput;
    }
    @Scheduled(cron = "0/30 * * * * *")
    public void run(){
        activateUserInput.activateUser();
    }

}
