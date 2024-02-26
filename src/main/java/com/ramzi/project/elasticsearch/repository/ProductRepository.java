package com.ramzi.project.elasticsearch.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.ramzi.project.elasticsearch.domain.Product;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public class ProductRepository {
    private ElasticsearchClient elasticsearchClient;
    private static final String PRODUCT = "product";

    public String createOrUpdateDocument(Product product) throws IOException {
        IndexResponse response = elasticsearchClient.index(i-> i
                .index(PRODUCT)
                .id(product.getId())
                .document(product));
        Map<String, String> responseMessage = Map.of(
                "Created", "Document has been created",
                "Updated", "Document has been updated"
        );

        return responseMessage.getOrDefault(response.result().name(), "Error has occurred");
    }

    public Product findDocById(String productId) throws IOException {
        return elasticsearchClient.get(g -> g.index(PRODUCT).id(productId), Product.class).source();
    }

    public String deleteById(String productId) throws IOException {
        DeleteRequest deleteRequest = DeleteRequest.of(d->d.index(PRODUCT).id(productId));
        DeleteResponse response = elasticsearchClient.delete(deleteRequest);

        return new StringBuffer(response.result().name().equalsIgnoreCase("NOT_FOUND")
            ? "Document not found with id" + productId : "Document has been deleted"
        ).toString();
    }
}
