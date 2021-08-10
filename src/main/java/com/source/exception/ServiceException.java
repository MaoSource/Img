package com.source.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/08/02/9:59
 * @Description:
 */
public class ServiceException extends Exception{

    private static final long serialVersionUID = -6916154462432027437L;

    public ServiceException(String message){
        super(message);
    }
}
