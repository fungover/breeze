package org.fungover.breeze.client;

import org.junit.jupiter.api.Test;
import java.security.SecureRandom;
import static org.junit.jupiter.api.Assertions.*;

class ServerClientTest {
    @Test
    void testSuccessfulFetch() throws Exception {
        ServerClient client = new ServerClient(0.0, new SecureRandom());
        assertEquals("Sample Data", client.fetchData());
    }

    @Test
    void testFailedFetch() {
        ServerClient client = new ServerClient(1.0, new SecureRandom());
        assertThrows(Exception.class, client::fetchData);
    }
}