package com.app.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIResponse> {

    public APIResponse handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        return buildResponse(context);
    }

    private APIResponse buildResponse(Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-amazon-author", "Balaji");
        headers.put("X-amazon-apiVersion", "v1");

        String body = null;
        Calendar calendar = Calendar.getInstance();
        int currentSecond = calendar.get(Calendar.SECOND);
        try {
            if (currentSecond % 2 == 0) {
                body = "{\"message\":\"Vote received and counted successfully for Option A" + ".\"}";
            } else {
                body = "{\"message\":\"Vote received and counted successfully for Option B" + ".\"}";
            }
        } catch (Exception e) {
            context.getLogger().log("Error:::" + e.getMessage());
            context.getLogger().log("Some internal error happened");
        }

        APIResponse response = new APIResponse(body, 200, headers);
        context.getLogger().log("APIResponse:::" + response);
        return response;
    }
}
