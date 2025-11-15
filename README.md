# Doc2FAQ Application

A Spring Boot web application designed to transform documents into comprehensive FAQ formats. This application provides an intelligent system for processing documents and generating relevant questions and answers.

## Features

- **File Upload**: Upload PDF, DOCX, and TXT files up to 10 MB
- **Cloud Storage**: Secure file storage using Amazon S3
- **File Validation**: Automatic validation of file types and sizes
- **Unique File IDs**: Generate unique identifiers for uploaded files
- **Responsive Web Interface**: Modern, user-friendly design with drag-and-drop upload
- **Real-time Feedback**: Progress indicators and success/error messages
- **RESTful API**: Ready for integration with other systems

## Technology Stack

- **Java 17**: Programming language
- **Spring Boot 3.2.0**: Application framework
- **Maven**: Build and dependency management
- **Thymeleaf**: Template engine for web pages
- **Bootstrap 5**: Frontend CSS framework
- **Font Awesome**: Icons
- **AWS SDK**: Amazon S3 integration for file storage

## Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+** (or use the Maven wrapper included in the project)
- **Git** (for cloning the repository)
- **AWS Account** with S3 access (for file upload functionality)

## AWS Configuration

To use the file upload feature, you need to configure AWS credentials. The application supports multiple credential sources:

### Option 1: Environment Variables
```bash
export AWS_ACCESS_KEY_ID=your-access-key-id
export AWS_SECRET_ACCESS_KEY=your-secret-access-key
```

### Option 2: AWS Credentials File
Create `~/.aws/credentials` file:
```ini
[default]
aws_access_key_id = your-access-key-id
aws_secret_access_key = your-secret-access-key
```

### Option 3: IAM Roles (for EC2 instances)
If running on EC2, attach an IAM role with S3 permissions.

### S3 Bucket Setup
Ensure the S3 bucket `bucket-name-51720177` exists and your AWS credentials have the following permissions:
- `s3:PutObject`
- `s3:PutObjectAcl`

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
│   │   │           ├── Doc2FaqApplication.java           # Main application class
│   │   │           ├── config/
│   │   │           │   └── AwsConfig.java                # AWS S3 configuration
│   │   │           ├── controller/
│   │   │           │   ├── HomeController.java           # Home page controller
│   │   │           │   └── FileUploadController.java     # File upload API controller
│   │   │           ├── service/
│   │   │           │   ├── FileUploadService.java        # File upload business logic
│   │   │           │   └── S3Service.java                # S3 operations service
│   │   │           └── model/
│   │   │               └── UploadResponse.java           # Upload response model
│   │   └── resources/
│   │       ├── templates/
│   │       │   └── index.html                            # Landing page with upload form
│   │       ├── application.properties                    # Application configuration
│   │       └── static/                                   # Static resources (CSS, JS, images)
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── docfaq/
│       │           └── Doc2FaqApplicationTests.java      # Integration tests
│       └── resources/
│           └── application-test.properties               # Test configuration
├── target/                                               # Build output (generated)
├── pom.xml                                               # Maven configuration
├── .gitignore                                            # Git ignore rules
└── README.md                                             # This file
```

## Configuration

The application can be configured through the `application.properties` file located in `src/main/resources/`. Key configuration options include:

- `server.port`: Application port (default: 8080)
- `spring.application.name`: Application name
- `spring.servlet.multipart.max-file-size`: Maximum file upload size (default: 10MB)
- `spring.servlet.multipart.max-request-size`: Maximum request size (default: 10MB)
- `aws.s3.bucket-name`: S3 bucket name for file storage
- `aws.s3.region`: AWS region for S3 bucket
- `logging.level.*`: Logging levels for different packages

## API Endpoints

Currently available endpoints:

- `GET /`: Landing page with file upload interface
- `POST /api/upload`: File upload endpoint
  - Accepts: multipart/form-data with 'file' parameter
  - Supported formats: PDF, DOCX, TXT
  - Maximum size: 10 MB
  - Returns: JSON response with upload status and file ID
- `GET /api/upload/status`: Upload service health check

### Upload API Response Format

**Success Response:**
```json
{
  "success": true,
  "message": "File uploaded successfully",
  "fileId": "uuid-generated-id.pdf",
  "fileName": "original-filename.pdf",
  "fileSize": 1024000
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Error description"
}
```

## File Upload Feature

### Supported File Types
- **PDF**: `.pdf` files
- **Word Documents**: `.docx` files  
- **Text Files**: `.txt` files

### File Size Limits
- Maximum file size: 10 MB per file
- Maximum request size: 10 MB

### Upload Process
1. User selects file via drag-and-drop or file browser
2. Client-side validation checks file type and size
3. File is uploaded to `/api/upload` endpoint
4. Server validates file and uploads to S3
5. Unique file ID is generated and returned
6. User receives success confirmation with file ID

### Error Handling
- Invalid file types are rejected with clear error messages
- Files exceeding size limits are rejected
- Network errors are handled gracefully
- S3 upload failures are reported to the user

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

4. **AWS Credentials Issues**
   - Verify AWS credentials are properly configured
   - Check S3 bucket exists and permissions are correct
   - Test with: `aws s3 ls s3://bucket-name-51720177`

5. **File Upload Issues**
   - Check file size is under 10 MB limit
   - Verify file type is PDF, DOCX, or TXT
   - Ensure S3 bucket has proper write permissions

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

**Status**: ✅ File upload feature implemented with S3 storage, validation, and responsive UI.