package org.example.tiere;

import lombok.*;

@Data
public class Schwein {


    public static final int INITIAL_WEIGHT = 10;
    private String name;

    @Setter(AccessLevel.PRIVATE)
    private int gewicht;


    public Schwein() {
        this("Nobody");
    }

    public Schwein(String name) {
       setName(name);
       setGewicht(INITIAL_WEIGHT);
    }

    public final void setName(String name) {
        if("elsa".equalsIgnoreCase(name)) throw new IllegalArgumentException("Ungueltiger Name");
        this.name = name;
    }

    public void fuettern() {
        setGewicht(getGewicht() + 1);
    }
}
