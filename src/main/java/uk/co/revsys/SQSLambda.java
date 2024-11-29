package uk.co.revsys;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

import java.sql.SQLException;
import java.time.Instant;

public class SQSLambda implements RequestHandler<SQSEvent, Void> {

    public Void handleRequest(SQSEvent sqsEvent, Context context) {

        CricketScorerDAO dao = new CricketScorerDAO();
        try {
            for (SQSMessage msg : sqsEvent.getRecords()) {
                processMessage(msg, context, dao);

            }
            context.getLogger().log("done");
            return null;
        } catch (Exception e) {
            context.getLogger().log(e.getMessage());
        }

        return null;
    }

    private void processMessage(SQSMessage msg, Context context, CricketScorerDAO dao) throws Exception {
        try {

            String body = msg.getBody();

            context.getLogger().log("Starting processing message " + body);
            dao.createAuditRow(new CricketScorerDAO.Audit(Instant.now(), body));

            // TODO: Do interesting work based on the new message

        } catch (SQLException e) {
            context.getLogger().log("An error occurred");
            throw e;
        }

    }
}


