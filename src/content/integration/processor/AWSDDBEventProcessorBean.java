package content.integration.processor;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

public class AWSDDBEventProcessorBean {

    public static String getWorkflowId(DynamodbEvent ddbEvent) {
        System.out.println("\n\t\t ProcessorBean retrieves " + ddbEvent.getRecords().size() + " events");
        System.out.println("\n\t\t ProcessorBean retrieves workflowId: " + ddbEvent.getRecords().get(0).getDynamodb().getKeys().get("workflowId").getS());

        return ddbEvent.getRecords().get(0).getDynamodb().getKeys().get("workflowId").getS();
    }
}
