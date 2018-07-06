package com.axon.complaintsstats.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e) {
        System.out.println(e.getCause().getMessage());
       // redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return e.getCause().getMessage() ;
    }
}
