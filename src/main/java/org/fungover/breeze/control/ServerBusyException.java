// ServerBusyException.java
package org.fungover.breeze.control;

public class ServerBusyException extends RuntimeException {
    public ServerBusyException() {
        super("Server is busy. Please try again later.");
    }

    public ServerBusyException(String message) {
        super(message);
    }
}