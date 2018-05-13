package com.test;

import java.net.URISyntaxException;

import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;

import bs.pi.gateway.client.mqtt.MQTTClientCfg;
import bs.pi.gateway.client.mqtt.MQTTMsgSend;

public class MQTTClient implements Callback{

	private MQTTClientCfg cfg;
	private MQTT mqtt;
	
	public MQTTClient(MQTTClientCfg cfg) throws URISyntaxException{
		this.cfg = cfg;
		mqtt = new MQTT();
        mqtt.setHost(cfg.getHost(), cfg.getPort());
        mqtt.setUserName(cfg.getUsername());
        mqtt.setPassword(cfg.getPassword());
	}
	
	public void start() throws Exception {
		try {
			mqtt.callbackConnection().connect(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(MQTTMsgSend mqttMsgSend) throws Exception{
		FutureConnection connection = mqtt.futureConnection();
		connection.connect().await();
        connection.publish(mqttMsgSend.getTopic(), mqttMsgSend.getMsg(), mqttMsgSend.getQos(), mqttMsgSend.isRetain());
        connection.disconnect().await();
	}
	
	public void setReceiveListener(org.fusesource.mqtt.client.Listener listener){
		mqtt.callbackConnection().listener(listener);
	}

	@Override
	public void onFailure(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Object arg0) {
		mqtt.callbackConnection().subscribe(cfg.getTopics(), new Callback<byte[]>() {
            public void onSuccess(byte[] qoses) {
            }
            public void onFailure(Throwable value) {
                value.printStackTrace();
                System.exit(-2);
            }
        });
	}
}
