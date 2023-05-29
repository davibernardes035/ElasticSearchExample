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

## Connecting to Elasticsearch
1. Create a new istance of `ElasticsearchClient` and its constructor:

```
private ElasticsearchClient elasticsearchClient;
private final String query;

public ESClient(String query) {
        this.query = query;
        createConnection();
}
```
2. Create a new method (obs: there is a bad practice in the code, putting login and password inside the snippet. Please never do this. It's just for testing.)
```
 private void createConnection() {
        String USER = "user";
        String PWD = "password";
        .
        .
        .
}
```

3. Create a `CredentialsProvider` and sets the username and password for authentication.

```
CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USER, PWD));
```

4.Create an SSLFactory instance to configure SSL/TLS settings for secure communication. In this example, i'm use an unsafe trust material and hostname verifier. For production use, you should use proper trust material and verify the server's hostname.
```
SSLFactory sslFactory = SSLFactory.builder()
                .withUnsafeTrustMaterial()
                .withUnsafeHostnameVerifier()
                .build();
```
5.In the next step, a RestClient instance is created, specifying the Elasticsearch server's host, port, and the "https" scheme for secure communication. The HttpClientConfigCallback is used to configure the underlying HTTP client with the provided credentials and SSL/TLS settings.
```
RestClient restClient = RestClient.builder(
                        new HttpHost("localhost", 9200, "https"))
                        .setHttpClientConfigCallback((HttpAsyncClientBuilder httpClientBuilder) -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setSSLContext(sslFactory.getSslContext())
                        .setSSLHostnameVerifier(sslFactory.getHostnameVerifier())
                        ).build();
```
6.An ElasticsearchTransport instance is created using the RestClient and a JacksonJsonpMapper, which handles JSON serialization and deserialization.
```
ElasticsearchTransport transport = new RestClientTransport(
        restClient, new JacksonJsonpMapper()
);

```
7.Finally, an ElasticsearchClient is instantiated using the transport, allowing you to perform operations on Elasticsearch.
```
elasticsearchClient = new ElasticsearchClient(transport);
```

8.This section defines a matchQuery using the MatchQuery builder, which matches documents based on the content field. The query variable represents the search query term.
```
public SearchResponse search() {
    Query matchQuery = MatchQuery.of(q -> q.field("content").query(query))._toQuery();

```
9.Here, the search method is called on the elasticsearchClient object. It performs a search operation on the wikipedia index, starting from the first document (from index 0) and returning a maximum of 10 documents
(size 10). The matchQuery is used as the search query. The response is captured in a SearchResponse<ObjectNode>.

```
SearchResponse<ObjectNode> response;
try {
        response = elasticsearchClient.search(s -> s
                .index("wikipedia").from(0).size(10)
                .query(matchQuery), ObjectNode.class);
} catch (IOException e) {
        throw new RuntimeException(e);
}

```
10. The method returns the SearchResponse object containing the search results.

Please note that you need to import the necessary classes, such as SearchResponse, Query, MatchQuery, ObjectNode, and IOException.
```
return response;
```

## Running the Example
1. In the main method, an instance of the ESClient class is created, passing "math test" as the query parameter.
        
```
public class Main {
    public static void main(String[] args) {
        ESClient esClient = new ESClient("math test");

```
2. The search method is called on the esClient object to execute the search operation. The searchResponse variable holds the response object. The hits are retrieved from the response and stored in the hits list.
        
```
var searchResponse = esClient.search();
List<Hit<ObjectNode>> hits = searchResponse.hits().hits();

```
3. Using a forEach loop, each hit in the hits list is processed. The title and content fields of each hit are retrieved using h.source().get("title").asText() and h.source().get("content").asText(), respectively.
Then, the printResults method is called to print the formatted results.

```
hits.forEach(h -> {
        String title = h.source().get("title").asText();
        String content = h.source().get("content").asText();
        printResults(title, content);
});

```
        
4.The printResults method is a helper method that formats and prints the title and content of a search result.
        
```
private static void printResults(String title, String content) {
        // Formatting and printing the results
}

```

## Conclusion
Congratulations! You have successfully set up a Java connection with Elasticsearch and performed a search operation. You can now expand upon this example to build more advanced search functionality or integrate other Elasticsearch features into your Java application. For more information, refer to the Elasticsearch Java client documentation: https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html
