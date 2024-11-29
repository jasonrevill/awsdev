package uk.co.revsys;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class CricketScorerLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        LambdaLogger logger = context.getLogger();

        //  input.forEach((key,value)->logger.log(key+":"+value));

        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "application/json");
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(responseHeaders);

        try {

            CricketScorerDAO dao = new CricketScorerDAO();
            CricketScorerDAO.Audit audit = new CricketScorerDAO.Audit(Instant.now(), "This has come from the lambda function");
            dao.createAuditRow(audit);


        } catch (SQLException e) {
            logger.log(e.toString());
            return response
                    .withStatusCode(500)
                    .withBody("Error in processing");
        }

        String output = "Hello world";
        return response
                .withStatusCode(200)
                .withBody(output);

        // return new APIResponse(event, 200, "Thanks for contacting scorer").returnJson();


    }
}




