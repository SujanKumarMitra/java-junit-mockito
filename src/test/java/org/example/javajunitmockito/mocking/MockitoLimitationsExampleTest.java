package org.example.javajunitmockito.mocking;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Mockito can't perform mocking on the following
 * <ul>
 *     <li>static methods</li>
 *     <li>private methods</li>
 *     <li>final methods</li>
 *     <li>final classes</li>
 *     <li>constructors</li>
 *     <li>{@link Object#equals(Object)} and {@link Object#hashCode()}</li>
 * </ul>
 */
@Disabled // use fork mode to pass the test
class MockitoLimitationsExampleTest {

    static class ClassWithStaticMethod {
        public static int generateInt() {
            return 10;
        }
    }

    @Test
    void testMockStaticMethod() {

        ClassWithStaticMethod actualObject = new ClassWithStaticMethod();
        assertEquals(10, actualObject.generateInt());

        ClassWithStaticMethod mockObject = Mockito.mock(ClassWithStaticMethod.class);
        MockitoException mockitoException = assertThrows(MockitoException.class, () ->
                Mockito.when(mockObject.generateInt())
                        .getMock());

        System.out.println(mockitoException);

    }

    @Test
    void testMockConstructor() {
        Object mock = Mockito.mock(Object.class);

        MockitoException mockitoException = assertThrows(MockitoException.class, () ->
                Mockito.when(new Object())
                        .getMock());
        System.out.println(mockitoException);
    }

    @Test
    void testMockFinalClass() {

        final class FinalClass {
        }

        MockitoException mockitoException = assertThrows(
                MockitoException.class, () ->
                        Mockito.mock(FinalClass.class));

        System.out.println(mockitoException);
    }

    @Test
    void testMockFinalMethod() {
        class ClassWithFinalMethod {
            public final int generateInt() {
                return 10;
            }
        }

        ClassWithFinalMethod actualObject = new ClassWithFinalMethod();

        assertEquals(10, actualObject.generateInt());

        ClassWithFinalMethod mockObject = Mockito.mock(ClassWithFinalMethod.class);

        MockitoException mockitoException = assertThrows(MockitoException.class, () ->
                Mockito.when(mockObject.generateInt())
                        .getMock());
        System.out.println(mockitoException);
    }

    @Test
    void testMockEqualsAndHashCode() {
        class ClassWithEqualsAndHashCode {

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof ClassWithEqualsAndHashCode;
            }
        }
        ClassWithEqualsAndHashCode actualObject = new ClassWithEqualsAndHashCode();
        assertTrue(actualObject.equals(new ClassWithEqualsAndHashCode()));
        assertEquals(0, actualObject.hashCode());

        ClassWithEqualsAndHashCode mockObject = Mockito.mock(ClassWithEqualsAndHashCode.class);

        MockitoException mockitoException;
        mockitoException = assertThrows(MockitoException.class, () ->
                Mockito.when(mockObject.equals(actualObject))
                        .getMock());
        System.out.println(mockitoException);

        mockitoException = assertThrows(MockitoException.class, () ->
                Mockito.when(mockObject.hashCode())
                        .getMock()
        );
        System.out.println(mockitoException);
    }

    @Test
    void testMockPrivateMethod() {
        class ClassWithPrivateMethod {
            private int generateInt() {
                return 10;
            }
        }

        ClassWithPrivateMethod actualObject = new ClassWithPrivateMethod();
        assertEquals(10, actualObject.generateInt());

        ClassWithPrivateMethod mockObject = Mockito.mock(ClassWithPrivateMethod.class);

        MockitoException mockitoException = assertThrows(MockitoException.class, () ->
                Mockito.when(mockObject.generateInt())
                        .getMock());

        System.out.println(mockitoException);
    }


}
