package org.example;

import java.time.LocalDateTime;

public class Greeting {

    private final LocalDateTime time;

    public Greeting(LocalDateTime time) {
        this.time = time;
    }

    public void greeting() {


        System.out.println(time.getHour());
    }
}
