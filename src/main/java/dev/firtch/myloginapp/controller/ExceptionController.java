package dev.firtch.myloginapp.controller;

import dev.firtch.myloginapp.exception.InvalidActivationTokenException;
import dev.firtch.myloginapp.exception.UniqueEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UniqueEmailException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ModelAndView processEniqueEmailException(UniqueEmailException uniqueEmailException) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("errorCode", HttpStatus.CONFLICT.value());
        modelAndView.addObject("message", uniqueEmailException.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(InvalidActivationTokenException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView processInvalidActivationTokenException(InvalidActivationTokenException invalidActivationTokenException) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("message", invalidActivationTokenException.getMessage());
        modelAndView.addObject("errorCode", HttpStatus.BAD_REQUEST.value());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView processException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("message", "I was too lazy to handle this exception. Something went wrong :)");
        modelAndView.addObject("stacktrace", exception.getStackTrace());
        return modelAndView;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handle(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("message", "PAGE NOT FOUND");
        modelAndView.addObject("errorCode", HttpStatus.NOT_FOUND.value());
        modelAndView.addObject("stacktrace", ex.getStackTrace());
        return modelAndView;
    }

}
