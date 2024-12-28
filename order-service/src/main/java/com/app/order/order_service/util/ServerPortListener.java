package com.app.order.order_service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
@Component
public class ServerPortListener {
@Value("${server.address:localhost}")
		private String serverAddress;

		@EventListener
		public void onApplicationEvent(WebServerInitializedEvent event) {
			int port = event.getWebServer().getPort();
			System.out.println("Application is running on " + serverAddress + ":" + port);
		}
}
