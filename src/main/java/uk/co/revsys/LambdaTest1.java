package uk.co.revsys;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class LambdaTest1 implements RequestHandler<Map<String,String>, String>{

    @Override
    /*
     * Takes an Integer as input, adds 1, and returns it.
     */
    public String handleRequest(Map<String, String> event, Context context)
    {
        LambdaLogger logger = context.getLogger();

        String output="";
        output+="Player:"+event.get("batter")+" Bowler: "+event.get("bowler")+" runs:"+event.get("runs");
        return output;

    }



    }
