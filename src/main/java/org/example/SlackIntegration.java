package org.example;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import org.apache.pdfbox.pdmodel.PDDocument;

public class SlackIntegration {
    private static String webHookUrl = "YOUR-WEBHOOKURL";
    private static String oAuthToken = "xoxb-token";
    private static String slackChannel = "test";

    public static void sendMessage(String message, PDDocument
            pdfDocument) throws Exception{

        Payload payload =
                Payload.builder().channel(slackChannel).text(message).build();
        WebhookResponse webhookResponse =

                Slack.getInstance().send(webHookUrl, payload);
    }
}
