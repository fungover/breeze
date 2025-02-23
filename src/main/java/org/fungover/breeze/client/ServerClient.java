package org.fungover.breeze.client;

import org.fungover.breeze.control.RetryExecutor;
import org.fungover.breeze.control.ServerBusyException;

import java.util.Random;

public class ServerClient {
    private final double busyProbability;
    private final Random random;

    public ServerClient() {
        this(0.7, new Random()); // Default 70% failure rate
    }

    public ServerClient(double busyProbability, Random random) {
        this.busyProbability = busyProbability;
        this.random = random;
    }

    public String fetchData() throws Exception {
        return RetryExecutor.executeWithRetry(this::attemptFetchData);
    }

    protected String attemptFetchData() throws ServerBusyException {
        if (random.nextDouble() < busyProbability) {
            throw new ServerBusyException();
        }
        return "Sample Data";
    }
}
