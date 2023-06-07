deploy:
	gcloud functions deploy codewarsLeadersKt --trigger-http \
					--gen2
					--source=build/libs \
                    --runtime=java17 --entry-point=com.andmal.Main \
                    --allow-unauthenticated \
                    --memory=256MB
logs:
	gcloud functions logs read
	gcloud functions logs read FUNCTION_NAME --execution-id EXECUTION_ID