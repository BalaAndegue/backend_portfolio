# Portfolio Backend API

A comprehensive Spring Boot REST API for managing a professional portfolio website.

## Features

- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **Project Management**: CRUD operations for portfolio projects with filtering and search
- **Certificate Management**: Manage professional certifications and achievements
- **Profile Management**: Personal information and skills management
- **Contact System**: Handle contact form submissions with email notifications
- **File Upload**: Secure file upload for images and documents
- **Database**: PostgreSQL with JPA/Hibernate
- **Security**: Spring Security with CORS configuration
- **Validation**: Comprehensive input validation
- **Error Handling**: Global exception handling

## Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security with JWT
- **Database**: PostgreSQL with JPA/Hibernate
- **Validation**: Bean Validation (JSR-303)
- **Documentation**: Built-in API documentation
- **Build Tool**: Maven

## Quick Start

### Prerequisites

- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE portfolio_db;
CREATE USER portfolio_user WITH PASSWORD 'portfolio_password';
GRANT ALL PRIVILEGES ON DATABASE portfolio_db TO portfolio_user;
```

### Configuration

1. Update `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/portfolio_db
    username: portfolio_user
    password: portfolio_password
```

2. Configure JWT secret and other environment variables:
```yaml
jwt:
  secret: your-secret-key-here
  expiration: 86400000

cors:
  allowed-origins: http://localhost:3000,https://your-domain.com
```

### Running the Application

1. Clone the repository
2. Navigate to the backend directory
3. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api`

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `GET /api/auth/me` - Get current user info
- `POST /api/auth/logout` - User logout

### Projects
- `GET /api/projects` - Get all projects
- `GET /api/projects/featured` - Get featured projects
- `GET /api/projects/search` - Search projects with filters
- `GET /api/projects/{id}` - Get project by ID
- `POST /api/projects` - Create project (Admin only)
- `PUT /api/projects/{id}` - Update project (Admin only)
- `DELETE /api/projects/{id}` - Delete project (Admin only)

### Certificates
- `GET /api/certificates` - Get all certificates
- `GET /api/certificates/featured` - Get featured certificates
- `GET /api/certificates/search` - Search certificates with filters
- `GET /api/certificates/{id}` - Get certificate by ID
- `POST /api/certificates` - Create certificate (Admin only)
- `PUT /api/certificates/{id}` - Update certificate (Admin only)
- `DELETE /api/certificates/{id}` - Delete certificate (Admin only)

### Profile
- `GET /api/profile` - Get profile information
- `POST /api/profile` - Create/Update profile (Admin only)

### Contact
- `POST /api/contact` - Send contact message
- `GET /api/contact/messages` - Get all messages (Admin only)
- `GET /api/contact/messages/unread` - Get unread messages (Admin only)
- `PUT /api/contact/messages/{id}/read` - Mark as read (Admin only)

### File Upload
- `POST /api/uploads` - Upload file (Admin only)
- `GET /api/uploads/{filename}` - Get uploaded file
- `DELETE /api/uploads/{filename}` - Delete file (Admin only)

## Default Admin User

The application creates a default admin user:
- **Username**: admin
- **Password**: admin123
- **Email**: admin@portfolio.com

**Important**: Change the default password in production!

## Environment Variables

For production deployment, set these environment variables:

```bash
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=your_jwt_secret_key
CORS_ORIGINS=https://your-frontend-domain.com
MAIL_HOST=your_smtp_host
MAIL_USERNAME=your_email
MAIL_PASSWORD=your_email_password
FILE_UPLOAD_DIR=/path/to/uploads
```

## Security Features

- JWT-based authentication
- Role-based authorization (ADMIN/VISITOR)
- CORS configuration
- Input validation
- SQL injection prevention
- XSS protection
- Secure file upload

## Development

### Adding New Features

1. Create entity classes in `com.portfolio.entity`
2. Create repository interfaces in `com.portfolio.repository`
3. Create DTOs in `com.portfolio.dto`
4. Create service classes in `com.portfolio.service`
5. Create controllers in `com.portfolio.controller`

### Testing

Run tests with:
```bash
mvn test
```

### Building for Production

```bash
mvn clean package
java -jar target/portfolio-backend-0.0.1-SNAPSHOT.jar
```

## Deployment

The application can be deployed to:
- Heroku
- AWS Elastic Beanstalk
- Google Cloud Platform
- Docker containers
- Traditional servers

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License.