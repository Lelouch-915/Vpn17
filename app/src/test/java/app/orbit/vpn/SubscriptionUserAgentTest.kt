package app.orbit.vpn

import app.orbit.vpn.data.Http
import app.orbit.vpn.model.normalizeSubscriptionUserAgent
import java.net.ServerSocket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import org.junit.Assert.*
import org.junit.Test
import java.net.InetSocketAddress

class SubscriptionUserAgentTest {
    @Test fun normalizesAndRejectsHeaderInjection() {
        assertNull(normalizeSubscriptionUserAgent(null))
        assertNull(normalizeSubscriptionUserAgent("   "))
        assertEquals("v2rayNG/1.10.0", normalizeSubscriptionUserAgent(" v2rayNG/1.10.0 "))
        for (value in listOf("agent\r\nX-Test: value", "agent\n", "agent\t", "Клиент", "agent\u007f")) {
            assertTrue(runCatching { normalizeSubscriptionUserAgent(value) }.isFailure)
        }
    }

    @Test fun customAgentIsSentOnlyForItsRequest() {
        val server = ServerSocket().apply { bind(InetSocketAddress("127.0.0.1", 0)); soTimeout = 5000 }
        val executor = Executors.newSingleThreadExecutor()
        val requests = executor.submit<List<String>> {
            List(3) {
                server.accept().use { socket ->
                    socket.soTimeout = 5000
                    val reader = socket.getInputStream().bufferedReader()
                    val headers = generateSequence { reader.readLine() }.takeWhile { it.isNotEmpty() }.toList()
                    socket.getOutputStream().write("HTTP/1.1 200 OK\r\nContent-Length: 2\r\nConnection: close\r\n\r\nok".toByteArray())
                    headers.first { it.startsWith("User-Agent:", ignoreCase = true) }.substringAfter(':').trim()
                }
            }
        }
        try {
            val url = "http://127.0.0.1:${server.localPort}/"
            Http.get(url, "v2rayNG/1.10.0")
            Http.get(url)
            Http.get(url, "   ")
            assertEquals(listOf("v2rayNG/1.10.0", "Orbit/${BuildConfig.VERSION_NAME}", "Orbit/${BuildConfig.VERSION_NAME}"), requests.get(10, TimeUnit.SECONDS))
        } finally {
            server.close()
            executor.shutdownNow()
        }
    }
}
