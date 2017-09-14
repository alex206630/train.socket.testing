package socketTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/** Сервер должен отвечать на простые вопросы. Если Ораклу сказали пока, то приложение выключается.
 * Важно, что Оракл может отправлять большие сообщения. Что бы понять когда конец сообщение он 
 * отправляет пустую строку.
 */
public class Server {

	private Socket socket;
	
	public Server(Socket socket) {
		this.socket = socket;
	}
	
//	public Server(Socket2 socket) {
//		this.socket = socket;
//	}

	
	public void start() throws IOException {
		PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		String ask = null;
		do {
			System.out.print("wait command ... ");
			ask = in.readLine();
			System.out.println(ask);
			if (ask == null) break;
			if ("Hello oracle".equals(ask)) {
				out.println("Hello, dear friend, I'm a oracle.");
				out.println();
			} else if ( ! "exit".equals(ask)) {
				out.println("I don't understand");
				out.println();
			}
		} while ( ! "exit".equals(ask));
		System.out.println("\n<Exited>");
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		run(new ServerSocket(1111).accept());
	}
	
	public static void run() throws IOException {
		try (@SuppressWarnings("resource") /* try-with-resources statement automatically call socket.close() */
				final Socket socket =  new ServerSocket(1111).accept() ) {
			new Server(socket).start();
		}
	}
	
	public static void run(final Socket socket) throws IOException {
		try {
			new Server(socket).start();
		} finally {
			socket.close();
		}
	}
	
	public static void run2() throws IOException {
		try (@SuppressWarnings("resource") /* try-with-resources statement automatically call socket.close() */
		final Socket2 socket = new Socket2(1111).accept();) {
			System.out.println("Working ...");
		}
	}
}
