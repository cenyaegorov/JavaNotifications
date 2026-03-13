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
