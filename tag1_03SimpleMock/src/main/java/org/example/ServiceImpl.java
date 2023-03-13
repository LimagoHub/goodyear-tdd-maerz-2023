package org.example;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServiceImpl implements Service {

    private final Dependency dependency;// Stub Mock

    @Override
    public void abc() {

        dependency.foo("Peter");
    }

    @Override
    public int xyz() {

        return dependency.foobar("Peter") + 10;
    }
}
