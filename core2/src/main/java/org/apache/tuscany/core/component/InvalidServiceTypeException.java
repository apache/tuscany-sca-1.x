package org.apache.tuscany.core.component;

import org.apache.tuscany.spi.component.TargetException;

/**
 * @version $$Rev$$ $$Date$$
 */
public class InvalidServiceTypeException extends TargetException {
    public InvalidServiceTypeException() {
    }

    public InvalidServiceTypeException(String message) {
        super(message);
    }

    public InvalidServiceTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidServiceTypeException(Throwable cause) {
        super(cause);
    }
}
