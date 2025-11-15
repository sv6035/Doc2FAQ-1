# Doc2FAQ Application

A Spring Boot web application designed to transform documents into comprehensive FAQ formats. This application provides an intelligent system for processing documents and generating relevant questions and answers.

## Features

- **Easy Document Upload**: Support for various document formats
- **Smart Processing**: Intelligent algorithms to analyze content and generate FAQs
- **Export Functionality**: Download generated FAQs in multiple formats
- **Responsive Web Interface**: Modern, user-friendly design
- **RESTful API**: Ready for integration with other systems

## Technology Stack

- **Java 17**: Programming language
- **Spring Boot 3.2.0**: Application framework
- **Maven**: Build and dependency management
- **Thymeleaf**: Template engine for web pages
- **Bootstrap 5**: Frontend CSS framework
- **Font Awesome**: Icons

## Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+** (or use the Maven wrapper included in the project)
- **Git** (for cloning the repository)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd doc2faq
```

### 2. Build the Application

Using Maven:
```bash
mvn clean compile
```

Or using the Maven wrapper (if available):
```bash
./mvnw clean compile
```

### 3. Run the Application

Using Maven:
```bash
mvn spring-boot:run
```

Or using the Maven wrapper:
```bash
./mvnw spring-boot:run
```

Alternatively, you can build a JAR file and run it:
```bash
mvn clean package
java -jar target/doc2faq-1.0.0-SNAPSHOT.jar
```

### 4. Access the Application

Once the application starts successfully, you can access it at:
- **URL**: http://localhost:8080
- **Port**: 8080 (configurable in `application.properties`)

## Project Structure

```
doc2faq/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── docfaq/
│   │   │           ├── Doc2FaqApplication.java      # Main application class
│   │   │           └── controller/
│   │   │               └── HomeController.java      # Home page controller
│   │   └── resources/
│   │       ├── templates/
│   │       │   └── index.html                       # Landing page template
│   │       ├── application.properties               # Application configuration
│   │       └── static/                              # Static resources (CSS, JS, images)
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── docfaq/
│       │           └── Doc2FaqApplicationTests.java # Integration tests
│       └── resources/
│           └── application-test.properties          # Test configuration
├── target/                                          # Build output (generated)
├── pom.xml                                          # Maven configuration
├── .gitignore                                       # Git ignore rules
└── README.md                                        # This file
```

## Configuration

The application can be configured through the `application.properties` file located in `src/main/resources/`. Key configuration options include:

- `server.port`: Application port (default: 8080)
- `spring.application.name`: Application name
- `logging.level.*`: Logging levels for different packages

## Development

### Running Tests

To run the test suite:
```bash
mvn test
```

### Development Mode

The application includes Spring Boot DevTools for enhanced development experience:
- Automatic restart when code changes
- LiveReload support for web resources
- Enhanced debugging capabilities

### Adding New Features

The application is structured to easily accommodate new features:

1. **Controllers**: Add new controllers in `com.docfaq.controller` package
2. **Services**: Create service classes in `com.docfaq.service` package
3. **Models**: Add data models in `com.docfaq.model` package
4. **Templates**: Add new Thymeleaf templates in `src/main/resources/templates/`
5. **Static Resources**: Add CSS, JS, and images in `src/main/resources/static/`

## API Endpoints

Currently available endpoints:

- `GET /`: Landing page with application information

## Troubleshooting

### Common Issues

1. **Port Already in Use**
   - Change the port in `application.properties`: `server.port=8081`
   - Or kill the process using port 8080

2. **Java Version Issues**
   - Ensure Java 17 or higher is installed
   - Check with: `java -version`

3. **Maven Issues**
   - Ensure Maven is properly installed
   - Try using the Maven wrapper: `./mvnw` instead of `mvn`

### Logs

Application logs are available in the console output. For more detailed logging, modify the logging levels in `application.properties`.

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Make your changes and commit: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature-name`
5. Submit a pull request

## License

This project is licensed under the terms specified in the LICENSE file.

## Support

For questions or issues, please create an issue in the project repository.

---

**Status**: ✅ Application is ready for development and adding core features.