package org.example;

import java.time.LocalDateTime;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        LocalDateTime time = LocalDateTime.now();
        new Greeting(time).greeting();
        System.out.println( "Hello World!" );
    }
}
