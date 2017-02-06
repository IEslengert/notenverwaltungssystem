package de.hslu.infomac.notenverwaltung.exceptions;

/**
 * @author Igor Eslengert
 */
public class CouldNotHashPasswordException extends Exception {
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1;

    /**
     * The Constant ERROR_CODE.
     */
    private static final String ERROR_CODE = "Error.CouldNotHashPassword";

    /**
     * Instantiates a new could not hash password exception.
     */
    public CouldNotHashPasswordException() {
        super();
    }

    /**
     * Instantiates a new could not hash password exception.
     * 
     * @param message the message.
     */
    public CouldNotHashPasswordException( final String message ) {
        super( message );
    }

    /**
     * Instantiates a new could not hash password exception.
     * 
     * @param message is the message.
     * @param cause is the cause.
     */
    public CouldNotHashPasswordException( final String message, final Throwable cause ) {
        super( message, cause );
    }

    /**
     * Instantiates a new could not hash password exception.
     * 
     * @param cause is the cause.
     */
    public CouldNotHashPasswordException( final Throwable cause ) {
        super( cause );
    }

    /**
     * Gets the error code.
     * 
     * @return the error code
     */
    public String getErrorCode() {
        return ERROR_CODE;
    }
}
