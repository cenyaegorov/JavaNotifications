<h1>Java Notification Service</h1>

<h2>Overview</h2>

<p>Java Notification Service is a backend system for asynchronous email notification.</p>
<p>The system receives notification requests through a REST API, stores them in a database,
  and delivers them asynchronously using a Kafka-based messaging pipeline with retry and failure recovery mechanisms.</p>
The project demonstrates implementation of:
<ul>
<li>Outbox Pattern</li>

<li>Event-driven architecture</li>

<li>Kafka messaging</li>

<li>Retry scheduling</li>

<li>Idempotent message processing</li>

<li>Hexagonal Architecture (Ports & Adapters)</li>
</ul>
<h2>Architecture</h2>
<p>The system is organized using Hexagonal Architecture to separate business logic from infrastructure.</p>
<pre>
  API:
    NotificationController
  Domain:
    Notification
  Application:
    SendEmailNotificationUseCase
    ProcessEmailNotificationUseCase
  Persistence:
    repositories
    EmaiSender
  Messaging:
    KafkaConsumer
</pre>
<h2>Message Flow</h2>
<p>Client request -> REST API -> SendEmailNotificationUseCase -> Save notification + Outbox event -> Outbox scheduler -> Kafka topic -> Notification consumer -> ProcessEmailNotificationUseCase -> EmailSender</p>
<h2>Retry and failure handling</h2>
<p>The system implements a database-driven retry mechanism.</p>
<p>Notification lifecycle:</p>
<p>New -> Processing -> Sent</p>
<p>or</p>
<p>New -> Processing -> Failed -> Processing -> ... -> Sent (limited by MAX_ATTEMPT_COUNT)</p>
<h2>Retry strategy</h2>
<ul>
  <li>Failed notifications are retried by a schedulers.</li>
  <li>Retry attempts are limited</li>
  <li>Notifications exceeded retry limit are marked as Dead.</li>
</ul>
<p>This approach avoids retry storms and allows monitoring failed notifications.</p>
<h2>Technologies</h2>
<p>The project uses following technologies:</p>
<ul>
  <li>Java 17+</li>
  <li>Spring Boot</li>
  <li>Spring Web</li>
  <li>Spring Data JPA</li>
  <li>Apache Kafka</li>
  <li>PostgreSQL</li>
  <li>JUnit5</li>
  <li>Mockito</li>
  <li>Lombok</li>
</ul>
<h2>Running application</h2>
<p>Kafka and PostgreSQL must be running. The settings for connecting should be specified in the application.properties. Then run Spring Boot Application.</p>
<h2>API</h2>
<h3>Send Notification</h3>
<pre>
  POST /notifications/email
  Body:
    {
      "email":"example@mail.com",
      "payload":"Hello!"
    }
  Response:
    {
      "id":"uuid",
      "status":"ACCEPTED"
    }
</pre>
<h3>Get notification status</h3>
<pre>
  GET /notifications/email/{id}
  Response:
    {
      "id":"uuid",
      "status":"SENT",
      "attemptCount":"1"
    }
</pre>
<h2>Testing</h2>
<p>Unit tests are implemented using JUnit 5 and Mockito. Tests cover:</p>
<ul>
  <li>Application use cases</li>
  <li>Scheduler retry logic</li>
  <li>Failure scenarios</li>
</ul>
<p>Infrastructure components (Kafka, API) are not unit tested because they require integration environments.</p>
