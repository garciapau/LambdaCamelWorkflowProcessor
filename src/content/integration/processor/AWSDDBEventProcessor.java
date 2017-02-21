package content.integration.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;


public class AWSDDBEventProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        System.out.println("\n\t\t ------------ Processor -------------" + exchange.getIn().getBody().getClass());
    	DynamodbEvent ddbEvent = exchange.getIn().getBody(DynamodbEvent.class);
        System.out.println("\n\t\tRetrieved " + ddbEvent.getRecords().size() + " events");
        System.out.println("\n\t\tRetrieved " + ddbEvent.getRecords().get(0).getDynamodb().getKeys().get("workflowId") + " events");
        System.out.println("\n\t\tRetrieved " + ddbEvent.getRecords().get(0).getDynamodb().getKeys().get("workflowId").getS() + " events");
    }

}
