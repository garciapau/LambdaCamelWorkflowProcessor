#!/bin/bash
echo 1. packaging the lambda funtion...
mvn package
echo Done. 
echo 2. Updating AWS...
aws lambda update-function-code --function-name WorkflowDDBEventProcessor --zip-file fileb://target/workflow.logic.processor-0.0.1-SNAPSHOT-jar-with-dependencies.jar
echo Done. 
#echo 3. Testing
#aws lambda invoke --function-name WorkflowDDBEventProcessor --invocation-type RequestResponse --region us-west-2 --payload file://tst/content/integration/processor/dynamodb-update-event.json outputfile.txt
