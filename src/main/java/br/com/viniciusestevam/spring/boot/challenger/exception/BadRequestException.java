package br.com.viniciusestevam.spring.boot.challenger.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message){
        super(message);
    }
}
