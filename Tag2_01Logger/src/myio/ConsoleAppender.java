package myio;

public class ConsoleAppender implements Appender{
    @Override
    public void write(String text) {
        // Wir testen keinen fremden Code
        // Wir testen keinen generierten Code
        // Wir testen keine Methoden, die einen direkten API-Call verwenden
        System.out.println(text);
    }
}
