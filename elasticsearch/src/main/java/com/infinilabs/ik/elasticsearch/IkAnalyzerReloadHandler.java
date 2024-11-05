package com.infinilabs.ik.elasticsearch;

import java.util.List;

import org.elasticsearch.client.internal.node.NodeClient;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestResponse;
import org.elasticsearch.rest.RestStatus;
import org.wltea.analyzer.dic.Dictionary;

public class IkAnalyzerReloadHandler extends BaseRestHandler {

    @Override
    public String getName() {
        return "ik_analyzer_reload_action";
    }

    @Override
    public List<Route> routes() {
        return List.of(new Route(RestRequest.Method.POST, "/_ik_analyzer/reload"));
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) {
        return new RestChannelConsumer() {
            @Override
            public void accept(RestChannel channel) throws Exception {
                // Call reload function and send response
                boolean reloadSuccessful = reloadCustomDictionaries();
                if (reloadSuccessful) {
                    RestResponse response = new RestResponse(RestStatus.OK, "Dictionaries reloaded");
                    channel.sendResponse(response);
                } else {
                    RestResponse response = new RestResponse(RestStatus.INTERNAL_SERVER_ERROR, "Error reloading dictionaries");
                    channel.sendResponse(response);
                }
            }
        };
    }
    
    private boolean reloadCustomDictionaries() {
        // This is where you invoke your actual reload functionality
        // Implement your reload logic, accessing the relevant analyzer resources
        Dictionary.getSingleton().reLoadMainDict();
        return true;
    }
}