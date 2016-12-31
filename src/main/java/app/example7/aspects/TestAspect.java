package app.example7.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by ajoshi on 31-Dec-16.
 */
@Aspect
@Component
public class TestAspect {
    @Before("execution(* app.example7.beans.TestBean.*(..))")
    private void before(JoinPoint joinPoint) {
        System.out.println("before");
    }

    @After("execution(* app.example7.beans.TestBean.*(..))")
    private void after(JoinPoint joinPoint) {
        System.out.println("after");
    }

    @Before("within(app.example7.beans.TestBean)")
    private void beforeWithin(JoinPoint joinPoint) {
        System.out.println("beforewithin");
    }
}
