package org.fungover.breeze.client;

import org.fungover.breeze.control.RetryExecutor;
import org.fungover.breeze.control.ServerBusyException;

public class ServerClient {
    public String fetchData() throws Exception {
        return RetryExecutor.executeWithRetry(() -> {
            // Add actual server call logic here
            if (Math.random() < 0.7) {
                throw new ServerBusyException();
            }
            return "Sample Data";
        });
    }
}