package mylogger;

import myio.Appender;

public class Logger
{

    private final Appender appender;

    public Logger(Appender appender) {
        this.appender = appender;
    }

    public void log(String message) {
        final String prefix = "ComplexerPrefix: ";  // 1. Komplizierten Prefix bauen
        appender.write(prefix + message); // 2. Nachricht irgendwohin schreiben
    }
}
