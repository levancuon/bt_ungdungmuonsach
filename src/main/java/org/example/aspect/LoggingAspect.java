package org.example.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* org.example.service.BookService.save(..))")
    public void bookStateChange() {
    }

    @After("bookStateChange()")
    public void logBookStateChange() {
        System.out.println("Book state has changed.");
    }

    @Pointcut("execution(* org.example.controller.*.*(..))")
    public void libraryVisit() {
    }

    @After("libraryVisit()")
    public void logLibraryVisit() {
        System.out.println("Library was visited.");
    }
}