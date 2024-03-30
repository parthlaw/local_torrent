<br/>
<p align="center">
  <a href="https://github.com/parthlaw/ReadME-Generator">
    <img src="https://github.com/parthlaw/local_torrent/assets/56805405/d9d78d74-d0cb-4ff0-bfa9-e183eec90bd3" alt="Logo">
  </a>

  <h3 align="center">Local Torrent</h3>

  <p align="center">
    A local file sharing application which runs on a local area network (LAN).
    <br/>
    <br/>
<!--     <a href="https://github.com/parthlaw/ReadME-Generator"><strong>Explore the docs »</strong></a> -->
    <br/>
    <br/>
<!--     <a href="https://github.com/parthlaw/ReadME-Generator">View Demo</a>
    .
    <a href="https://github.com/parthlaw/ReadME-Generator/issues">Report Bug</a>
    .
    <a href="https://github.com/parthlaw/ReadME-Generator/issues">Request Feature</a> -->
  </p>
</p>
![Issues](https://img.shields.io/github/issues/parthlaw/local_torrent)

## Table Of Contents

* [About the Project](#about-the-project)
* [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Roadmap](#roadmap)
* [Authors](#authors)
* [Acknowledgements](#acknowledgements)

## About The Project

![Screen Shot](https://github.com/parthlaw/local_torrent/assets/56805405/8afe7a26-e0d7-4094-87a2-deec61c5a0eb)

Experience seamless file sharing within your local network with our innovative torrent application. Simply install the client application to join the network and start sharing files securely. This application ensures encrypted peer-to-peer connections, guaranteeing your privacy and confidentiality throughout your file-sharing experience. Say goodbye to concerns about unauthorized access – your communications are shielded from prying eyes, giving you peace of mind while you share files effortlessly.

## Built With

This application is build using Java. Three major components include:

* [Spring Boot](https://spring.io)
* [Java NIO]()
* [Java LinkedBlockingQueues]()

## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

To run this locally, you need Java version 17.

### Installation

1. Clone the repo

```sh
git clone https://github.com/parthlaw/local_torrent.git
```
2. Install dependencies

```sh
./gradlew --refresh-dependencies
```
3. You may need to clear gradle cache and perform previous step again to make you LSP pick up the new dependencies. Restart the LSP afterwards.
4. Format the code after any changes.

```sh
./gradlew spotlessApply
```
5.Build and run the application.
```sh
./gradlew bootRun
```

## Roadmap

- [x] Client Application: Setup Socket server with non-blocking I/O (Using Selectors).
- [x] Client Application: Send File and Receive File function.
- [x] Client Application: Setup Spring-boot http server for local webpage to connect.
- [x] Client Application: Setup Store class for Properties (config) file for the application.
- [x] Client Application: Setup local Queue service (LinkedBlockingQueue class) for http server to send commands to socket server.
- [ ] Client Application: One extra client thread to connect with the server (Socket connection). This will send updates about changing rules (to be implemented in the future) and IP.
- [ ] Client Application: Properties file is a resource shared among threads. Use mutex or semaphore to write to that file.
- [ ] Server Application: Setup Socket server to get IP updates and send rule updates.
- [ ] Server Application: Store IP-username mapping in in-memory database (SQLite).

## Authors

* **Parth Lawania** - *Comp Sci Student* - [Parth Lawania](https://github.com/parthlaw/) - **
