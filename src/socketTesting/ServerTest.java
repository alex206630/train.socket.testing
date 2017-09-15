package socketTesting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.core.Is.is;

import com.google.common.base.Joiner;
import org.junit.Test;

public class ServerTest {

	@Test
	public void whenAskAnswerThenSayExit() throws IOException {
		Socket socket = mock(Socket.class);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream("exit".getBytes());
		when(socket.getInputStream()).thenReturn(in);
		when(socket.getOutputStream()).thenReturn(out);
//		Server server = new Server(socket);
//		server.start();
		Server.run(socket);
		assertThat(out.toString(), is(""));
	}
		
	@Test
	public void checkIfSocketClosed() throws IOException {
		boolean notClosed = true;
		Socket socket = mock(Socket.class);
		ByteArrayInputStream in = new ByteArrayInputStream("exit".getBytes());
		when(socket.getInputStream()).thenReturn(in);
		doThrow(new RuntimeException("Closed")).when(socket).close();
		try {
			Server.run(socket);
		} catch (RuntimeException e) {
			if ("Closed".equals(e.getMessage()))
				notClosed = false;
		}
		if (notClosed)
			fail("Socket not closed. Resouce leak.");
		
		assertTrue( ! notClosed);
	}
	
	private final String LN = System.getProperty("line.separator");
	@Test
	public void whenAskHelloThenBackGreatOracle() throws IOException {
		Socket socket = mock(Socket.class);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		/* variant using StringBuilder */
//		StringBuilder inMessage = new StringBuilder();
//		inMessage.append(String.format("Hello oracle%s", LN));
//		inMessage.append(String.format("exit%s", "\r"));
//		ByteArrayInputStream in = new ByteArrayInputStream(inMessage.toString().getBytes());

		/* variant using String.format */
//		ByteArrayInputStream in = new ByteArrayInputStream(
//				String.format(
//						"Hello oracle%s",
//						"\r"
//				).getBytes());

		/* variant using guava */
		ByteArrayInputStream in = new ByteArrayInputStream(
				Joiner.on(LN).join(
						"Hello oracle",
						"exit"
				).getBytes()
		);
		when(socket.getInputStream()).thenReturn(in);
		when(socket.getOutputStream()).thenReturn(out);
		Server server = new Server(socket);
		server.start();
//		Server.run(socket);
		assertThat(out.toString(),
				is(
						String.format("Hello, dear friend, I'm a oracle.%s%s", LN, LN)
				)
		);
	}

	@Test
	public void whenAskUnsuportedMessage() throws IOException {
		Socket socket = mock(Socket.class);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		/* variant using StringBuilder */
//		StringBuilder inMessage = new StringBuilder();
//		inMessage.append(String.format("Hello oracle%s", LN));
//		inMessage.append(String.format("exit%s", "\r"));
//		ByteArrayInputStream in = new ByteArrayInputStream(inMessage.toString().getBytes());

		/* variant using String.format */
//		ByteArrayInputStream in = new ByteArrayInputStream(
//				String.format(
//						"Hello oracle%s",
//						"\r"
//				).getBytes());

		/* variant using guava */
		ByteArrayInputStream in = new ByteArrayInputStream(
				Joiner.on(LN).join(
						"unsupported ask",
						"exit"
				).getBytes()
		);
		when(socket.getInputStream()).thenReturn(in);
		when(socket.getOutputStream()).thenReturn(out);
		Server server = new Server(socket);
		server.start();
//		Server.run(socket);
		assertThat(out.toString(),
				is(
						String.format("I don't understand%s%s", LN, LN)
				)
		);
	}

}
