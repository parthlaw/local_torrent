## Todo
- [x] Client Application: Setup Socket server with non-blocking I/O (Using Selectors).
- [x] Client Application: Send File and Receive File function.
- [x] Client Application: Setup Spring-boot http server for local webpage to connect.
- [x] Client Application: Setup Store class for Properties (config) file for the application.
- [ ] Client Application: Setup local Queue service (LinkedBlockingQueue class) for http server to send commands to socket server.
- [ ] Client Application: One extra client thread to connect with the server (Socket connection). This will send updates about changing rules (to be implemented in the future) and IP.
- [ ] Client Application: Properties file is a resource shared among threads. Use mutex or semaphore to write to that file.
- [ ] Server Application: Setup Socket server to get IP updates and send rule updates.
- [ ] Server Application: Store IP-username mapping in in-memory database (SQLite).
