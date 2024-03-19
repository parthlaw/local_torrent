## Todo
[] Client Application: Setup Socket server with non blocking I/O (Using Selectors).
[] Client Application : Send File and Receive File function.
[] Client Application : Setup Spring-boot http server for local webpage to connect.
[] Client Application: Setup local Queue service (LinkedBlockingQueue class) for http server to send commands to socket server.
[] Client Application: One extra client thread to connect with server (Socket connection). This will send updates about changing rules (to be implemented in future) and IP.
[] Client Application: Properties file is a resource shared among threads. Use mutex or semphore to write to that file.
[] Server Application: Setup Socket server to get IP updates and send rule updates.
[] Server Application: Store IP- username mapping in in-memory database (sqllite).
