# Java Connection with Elasticsearch Example

This is a README file that provides an example of how to connect Java with Elasticsearch, a popular open-source search and analytics engine. Elasticsearch allows you to store, search, and analyze large volumes of data quickly and in near real-time.

## Prerequisites

To run this example, you'll need the following:

*Java Development Kit (JDK) installed on your machine;
*Elasticsearch installed and running locally or on a remote server;
*Login and password to log in with elastic through kibana;
*Elasticsearch Java client library added to your project dependencies.

## Setting up the Project

1. Create a new Java project or use an existing one.
2. Add the Elasticsearch Java client library to your project's dependencies. You can either download the JAR file manually or use a build tool like Maven or Gradle to manage your dependencies.
Maven example (add to your project's pom.xml):

```
<properties>
        <maven.compiler.source>19</maven.compiler.source>
        <maven.compiler.target>19</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <elasticsearch-rest-client>8.8.0</elasticsearch-rest-client>
        <elasticsearch-java>8.8.0</elasticsearch-java>
        <jackson-databind>2.15.1</jackson-databind>
        <sslcontext-kickstart>7.4.11</sslcontext-kickstart>
        <slf4j-simple>2.0.5</slf4j-simple>
</properties>

<dependencies>
        <!-- Elasticsearch -->
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-client</artifactId>
            <version>${elasticsearch-rest-client}</version>
        </dependency>
        <dependency>
            <groupId>co.elastic.clients</groupId>
            <artifactId>elasticsearch-java</artifactId>
            <version>${elasticsearch-java}</version>
        </dependency>

        <!-- SSL -->
        <dependency>
            <groupId>io.github.hakky54</groupId>
            <artifactId>sslcontext-kickstart</artifactId>
            <version>${sslcontext-kickstart}</version>
        </dependency>

        <!-- Elasticsearch Response -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson-databind}</version>
        </dependency>

        <!-- Logs -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>${slf4j-simple}</version>
        </dependency>

    </dependencies>
```
3. Import the necessary Elasticsearch classes in your Java code:

```
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.altindag.ssl.SSLFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
```

## Running the Example
1. Make sure Elasticsearch is up and running.
2. Compile and run your Java application that contains the Elasticsearch code.
3. Verify that the search results are returned successfully.

## Conclusion
Congratulations! You have successfully set up a Java connection with Elasticsearch and performed a search operation. You can now expand upon this example to build more advanced search functionality or integrate other Elasticsearch features into your Java application. For more information, refer to the Elasticsearch Java client documentation: https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html
