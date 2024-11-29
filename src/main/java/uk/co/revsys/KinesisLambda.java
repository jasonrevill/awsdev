package uk.co.revsys;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;

import java.sql.SQLException;
import java.time.Instant;

public class KinesisLambda implements RequestHandler<KinesisEvent, Void> {

    public Void handleRequest(KinesisEvent event, Context context) {

        CricketScorerDAO dao = new CricketScorerDAO();

        if (event.getRecords().isEmpty()) {
            context.getLogger().log("Empty Kinesis Event received");
            return null;
        }
        for (KinesisEvent.KinesisEventRecord record : event.getRecords()) {
            try {
                context.getLogger().log("Processed Event with EventId: "+record.getEventID());
                String data = new String(record.getKinesis().getData().array());
                processMessage(data, context, dao);
                context.getLogger().log("Data:"+ data);
                // TODO: Do interesting work based on the new data
            }
            catch (Exception ex) {
                context.getLogger().log("An error occurred:"+ex.getMessage());

            }
        }
        context.getLogger().log("Successfully processed:"+event.getRecords().size()+" records");
        return null;



  //      try {
          //  for (SQSMessage msg : sqsEvent.getRecords()) {
        //        processMessage(msg, context, dao);
//
        //    }
        //    context.getLogger().log("done");
       //     return null;
     //   } catch (Exception e) {
    //        context.getLogger().log(e.getMessage());
    //    }

    //    return null;
    }

    private void processMessage(String data, Context context, CricketScorerDAO dao) throws Exception {
        try {



            dao.createAuditRow(new CricketScorerDAO.Audit(Instant.now(), data));

            // TODO: Do interesting work based on the new message

        } catch (SQLException e) {
            context.getLogger().log(e.getMessage());
            throw e;
        }

    }
}


