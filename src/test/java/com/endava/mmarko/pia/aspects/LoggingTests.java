package com.endava.mmarko.pia.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.reflect.FieldSignatureImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class LoggingTests {
    private static class TestException extends Exception {}

    @Test
    public void aspectCallsAdvisedFunction() throws Throwable {
        ProceedingJoinPoint jpMock = getJoinPointMock();
        Mockito.when(jpMock.getArgs()).thenReturn(new Object[]{"arg test"});

        Logging loggingAspect = new Logging();
        loggingAspect.logging(jpMock);

        Mockito.verify(jpMock, Mockito.times(1)).proceed();
    }

    @Test(expected = TestException.class)
    public void aspectThrowsWhatAdvisedFunctionThrows() throws Throwable {
        ProceedingJoinPoint jpMock = getJoinPointMock();
        Mockito.when(jpMock.getArgs()).thenReturn(new Object[0]);
        Mockito.when(jpMock.proceed()).thenThrow(new TestException());

        Logging loggingAspect = new Logging();
        loggingAspect.logging(jpMock);
    }

    private ProceedingJoinPoint getJoinPointMock() {
        Signature sigMock = getSignatureMock();

        ProceedingJoinPoint jpMock = Mockito.mock(ProceedingJoinPoint.class);
        Mockito.when(jpMock.getSignature()).thenReturn(sigMock);
        return jpMock;
    }

    private Signature getSignatureMock() {
        Signature sigMock = Mockito.mock(FieldSignatureImpl.class);
        Mockito.when(sigMock.toShortString()).thenReturn("Logging Aspect Testing");
        return sigMock;
    }

}
