package content.integration.processor;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

public class AWSDDBEventProcessorBean {

    public static String getWorkflowId(DynamodbEvent ddbEvent) {
        System.out.println("\n\t\t ProcessorBean retrieves " + ddbEvent.getRecords().size() + " events");
        System.out.println("\n\t\t ProcessorBean retrieves workflowId: " + ddbEvent.getRecords().get(0).getDynamodb().getKeys().get("workflowId").getS());

        return ddbEvent.getRecords().get(0).getDynamodb().getKeys().get("workflowId").getS();
    }

    public static String getTaskId(DynamodbEvent ddbEvent) {
        System.out.println("\n\t\t ProcessorBean retrieves " + ddbEvent.getRecords().size() + " events");
        System.out.println("\n\t\t ProcessorBean retrieves taskId: " + ddbEvent.getRecords().get(0).getDynamodb().getKeys().get("taskId").getS());

        return ddbEvent.getRecords().get(0).getDynamodb().getKeys().get("taskId").getS();
    }

    public static String getAction(DynamodbEvent ddbEvent) {
        System.out.println("\n\t\t ProcessorBean retrieves " + ddbEvent.getRecords().size() + " events");
        System.out.println("\n\t\t ProcessorBean retrieves taskId: " + ddbEvent.getRecords().get(0).getDynamodb().getNewImage().get("action").getS());

        return ddbEvent.getRecords().get(0).getDynamodb().getNewImage().get("action").getS();
    }

    public static String getUser(DynamodbEvent ddbEvent) {
        System.out.println("\n\t\t ProcessorBean retrieves " + ddbEvent.getRecords().size() + " events");
        System.out.println("\n\t\t ProcessorBean retrieves taskId: " + ddbEvent.getRecords().get(0).getDynamodb().getNewImage().get("userId").getS());

        return ddbEvent.getRecords().get(0).getDynamodb().getNewImage().get("userId").getS();
    }

}
