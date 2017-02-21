package content.integration.processor;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

import content.integration.processor.DDBEventProcessor;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DDBEventProcessorTest {

    private static DynamodbEvent input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = TestUtils.parse("dynamodb-update-event.json", DynamodbEvent.class);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("WorkflowDDBEventProcessor");

        return ctx;
    }

    @Test
    public void testDDBEventProcessor() {
        DDBEventProcessor handler = new DDBEventProcessor();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);
        // TODO: validate output here if needed.
        if (output != null) {
            System.out.println("Test succeded. " + output.toString());
        }
    }
}
