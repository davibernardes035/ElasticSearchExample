package com.elasticsearch;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elasticsearch.client.ESClient;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ESClient esClient = new ESClient("math test");

        var searchResponse = esClient.search();

        List<Hit<ObjectNode>> hits = searchResponse.hits().hits();

        hits.forEach(h -> {
            String title = h.source().get("title").asText();
            String content = h.source().get("content").asText();
            printResults(title, content);
        });
    }

    private static void printResults(String title, String content){
        System.out.println(title);
        System.out.println("--------------");
        content = content.replaceAll("</?(som|math)\\d*>","");
        content = content.replaceAll("[^A-Za-z\\s]+", "");
        content = content.replaceAll("\\s+", " ");
        content = content.replaceAll("^\\s+", "");
        System.out.println(content);
        System.out.println("=====================");;
    }
}

