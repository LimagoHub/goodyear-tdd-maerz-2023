package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GreetingTest {
    @InjectMocks
    private Greeting objectUnderTest;

    @Mock
    private LocalDateTime timeMock;

    @Test
    void test() {
        Mockito.when(timeMock.getHour()).thenReturn(5);
        objectUnderTest.greeting();
    }


    @Test
    void testtestspy(){
        List<String> myMock = Mockito.mock(List.class);
        myMock.add("eins");
        System.out.println(myMock.size());

        List<String> mySpy = Mockito.spy(new ArrayList<>());
        mySpy.add("eins");
        System.out.println(mySpy.size());

        Mockito.verify(mySpy).add("eins");
    }

}