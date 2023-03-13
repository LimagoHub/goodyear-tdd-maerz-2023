package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {

    @InjectMocks
    private ServiceImpl objectUnderTest;

    @Mock
    private Dependency dependencyMock;

//    @BeforeEach
//    void setUp() {
//        dependency = Mockito.mock(Dependency.class);
//        objectUnderTest = new ServiceImpl(dependency);
//    }

    @Test
    void demo() {

        // Arrange
        when(dependencyMock.foobar("Peter")).thenReturn(5); // Recordmode
        //when(dependency.foobar("Anny")).thenReturn(41); // Recordmode
        // replaymode

        assertEquals(15, objectUnderTest.xyz());
        verify(dependencyMock, times(1)).foobar("Peter");
    }

    @Test
    void abc() {

        // Wenn Methode einen Rueckgabewert hat
        // when(mock.method()).thenReturn(1);
        // oder when(mock.method()).thenThrows(Exception.class);

        // Wenn Methode keinen Rueckgabewert hat (Void)
        // Nicht notwendig
        // Falls doch notwendig doNothing().when(mock).method();
        // oder doThrow(Exception.class).when(mock).method();


        // Arrange
        doThrow(new ArrayIndexOutOfBoundsException("Upps")).when(dependencyMock).foo(anyString()); // Recordmode
        //when(dependency.foobar("Anny")).thenReturn(41); // Recordmode
        // replaymode

        assertThrows(ArrayIndexOutOfBoundsException.class, ()-> objectUnderTest.abc());

    }

}