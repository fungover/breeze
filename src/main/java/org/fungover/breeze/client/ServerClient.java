package org.fungover.breeze.client;

import org.fungover.breeze.control.RetryExecutor;
import org.fungover.breeze.control.ServerBusyException;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

public class ServerClient {
    private static final double DEFAULT_BUSY_PROBABILITY = 0.7;
    private final double busyProbability;
    private final Random random;

    public ServerClient() {
        this(DEFAULT_BUSY_PROBABILITY, new SecureRandom());
    }

    public ServerClient(double busyProbability, Random random) {
        if (busyProbability < 0 || busyProbability > 1) {
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        }
        this.busyProbability = busyProbability;
        this.random = Objects.requireNonNull(random, "Random instance cannot be null");
    }

    public String fetchData() throws Exception {
        return RetryExecutor.executeWithRetry(this::attemptFetchData);
    }

    String attemptFetchData() throws ServerBusyException {
        if (shouldSimulateBusyServer()) {
            throw new ServerBusyException();
        }
        return "Sample Data";
    }

    private boolean shouldSimulateBusyServer() {
        return random.nextDouble() < busyProbability;
    }
}