package socketTesting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;
import org.junit.Test;

public class ServerTest {

	@Test
	public void whenAskAnswerThenChooseRandom() throws IOException {
		Class<Socket> classobj = Socket.class;
		Socket socket = mock(classobj);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream("exit".getBytes());
		when(socket.getInputStream()).thenReturn(in);
		when(socket.getOutputStream()).thenReturn(out);
//		when(socket.getSoTimeout()).re
//		Mockito.when(socket.close())
		Server server = new Server(socket);
		server.start();
		assertThat(out.toString(), is(""));
	}
}
