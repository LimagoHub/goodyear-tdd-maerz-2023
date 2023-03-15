package de.goodyear.gui.presenter;

import de.goodyear.gui.Euro2DollarRechnerView;
import de.goodyear.model.Euro2DollarRechner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Euro2DollarPresenterImplTest {

    @InjectMocks
    private Euro2DollarPresenterImpl objectUnderTest;

    @Mock
    private Euro2DollarRechnerView viewMock;

    @Mock
    private Euro2DollarRechner modelMock;

    @Test
    void onBeenden_happyDay_maskShouldBeClosed() {
        assertDoesNotThrow(()->objectUnderTest.onBeenden());
        verify(viewMock, times(1)).close();
    }

}