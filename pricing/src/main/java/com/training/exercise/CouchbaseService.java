package com.training.exercise;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.AsyncViewResult;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.training.exercise.config.Database;

/**
 * Skeleton class for the Price, where most couchbase-related operations are stubbed
 * for the user to fill-in.
 *
 * @author Praveen Kaujalgikar
 */
@Service
public class CouchbaseService {

    private final Database config;

    private final Bucket bucket;
    private final Cluster cluster;

    @Autowired
    public CouchbaseService(final Database config) {
        this.config = config;

        //connect to the cluster and open the configured bucket
        this.cluster = CouchbaseCluster.create(config.getNodes());
        this.bucket = cluster.openBucket(config.getBucket(), config.getPassword());
    }

    @PreDestroy
    public void preDestroy() {
        if (this.cluster != null) {
            this.cluster.disconnect();
        }
    }

    /**
     * Prepare a new JsonDocument with some JSON content
     */
    public static JsonDocument createDocument(String id, JsonObject content) {
        return JsonDocument.create(id, content);
    }

    /**
     * CREATE the document in database
     * @return the created document, with up to date metadata
     */
    public JsonDocument create(JsonDocument doc) {
        return bucket.insert(doc);
    }

    /**
     * READ the document from database
     */
    public JsonDocument read(String id) {
        return bucket.get(id);
    }

    /**
     * UPDATE the document in database
     * @return the updated document, with up to date metadata
     */
    public JsonDocument update(JsonDocument doc) {
        return bucket.replace(doc);
    }

    /**
     * DELETE the document from database
     * @return the deleted document, with only metadata (since content has been deleted)
     */
    public JsonDocument delete(String id) {
        return bucket.remove(id);
    }

    /**
     * Uses a view query to find all prices. Possibly use an offset and a limit of the
     * number of prices to retrieve.
     *
     * @param offset the number of prices to skip, null or < 1 to ignore
     * @param limit the limit of prices to retrieve, null or < 1 to ignore
     */
    public ViewResult findAllPrices(Integer offset, Integer limit) {
        ViewQuery query = ViewQuery.from("price", "by_id");
        if (limit != null && limit > 0) {
            query.limit(limit);
        }
        if (offset != null && offset > 0) {
            query.skip(offset);
        }
        ViewResult result = bucket.query(query);
        return result;
    }

    /**
     * Retrieves all the prices using a view query, returning the result asynchronously.
     */
    public Observable<AsyncViewResult> findAllPricesAsync() {
        ViewQuery allPrices = ViewQuery.from("price", "by_id");
        return bucket.async().query(allPrices);
    }

    /**
     * READ the document asynchronously from database.
     */
    public Observable<JsonDocument> asyncRead(String id) {
        return bucket.async().get(id);
    }

    /**
     * Create a ViewQuery to retrieve price for one single Product.
     * The "\uefff" character (the largest UTF8 char) can be used to put an
     * upper limit to the product key retrieved by the view (which otherwise
     * would return all prices for all products).
     *
     * @param productId the product key for which to retrieve associated price.
     */
    public static ViewQuery createQueryPriceForProduct(String productId) {
        ViewQuery forProduct = ViewQuery.from("price", "price");
        forProduct.startKey(JsonArray.from(productId));
        //the trick here is that sorting is UTF8 based, uefff is the largest UTF8 char
        forProduct.endKey(JsonArray.from(productId, "\uefff"));
        return forProduct;
    }

    /**
     * Asynchronously query the database for price associated to a Product.
     *
     * @param productId the product key for which to retrieve associated Price.
     * @see #createQueryPriceForProduct(String)
     */
    public Observable<AsyncViewResult> findPriceForProductAsync(String productId) {
        return bucket.async().query(createQueryPriceForProduct(productId));
    }



    

}
