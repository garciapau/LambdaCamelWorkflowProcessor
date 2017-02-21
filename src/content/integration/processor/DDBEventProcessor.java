package content.integration.processor;

import java.util.Map;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DDBEventProcessor implements RequestHandler<DynamodbEvent, String> {
	private static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
	}

	@Override
	public String handleRequest(DynamodbEvent ddbEvent, Context context) {
		String output = new String();
		context.getLogger().log("Received " + ddbEvent.getRecords().size() + " record(s)");
		for (DynamodbStreamRecord record : ddbEvent.getRecords()) {
			context.getLogger().log("EventID: " + record.getEventID());
			context.getLogger().log("EventName: " + record.getEventName());
			context.getLogger().log("EventContent: " + record.getDynamodb().toString());
			Map<String, AttributeValue> currentRecord = record.getDynamodb().getNewImage();
			// output = startCamel(mapper.writeValueAsString(record), context);

		}
//			String jsonEvent = mapper.writeValueAsString(ddbEvent);
//			context.getLogger().log("Content as JSON: " + jsonEvent);
		output = startCamel(ddbEvent, context);
		System.out.println("Successfully processed " + ddbEvent.getRecords().size() + " records.");
		return output;
	}

	private String startCamel(DynamodbEvent ddbEvent, Context context) {
		ApplicationContext spring = new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
		String output = new String();
		SpringCamelContext camelContext = null;
		try {
			camelContext = SpringCamelContext.springCamelContext(spring);
		} catch (Exception e) {
			context.getLogger().log("Error: " + e.getMessage());
			return "Error creating camel context";
		}
		ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
		ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
		output = consumerTemplate.receiveBodyNoWait("direct:end", String.class);
		producerTemplate.sendBody("direct:start", ddbEvent);
		output = consumerTemplate.receiveBodyNoWait("direct:end", String.class);
		context.getLogger().log("\nResult is: " + output);
		try {
			camelContext.stop();
		} catch (Exception e) {
			return "Error creating camel context";
		}
		context.getLogger()
				.log("\nCamel stopped. Remaining time at the end: " + context.getRemainingTimeInMillis() + " ms");
		return output;
	}

}
