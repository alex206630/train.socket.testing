package socketTesting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Socket2 implements java.io.Closeable {

	Socket2(int i) {
		System.out.println(" - Constructor: " + i);
	}
	
	public Socket2 accept() {
		return this;
	}
	
	@Override
	public void close() throws IOException {
		System.out.println("Closed");
	}

//	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream("exit".getBytes());
	}

//	@Override
	public OutputStream getOutputStream() throws IOException {
//		return new ByteArrayOutputStream();
		return System.out;
	}

}
