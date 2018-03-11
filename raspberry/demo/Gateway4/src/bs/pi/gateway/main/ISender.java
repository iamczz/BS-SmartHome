package bs.pi.gateway.main;

import java.util.HashMap;

import bs.pi.gateway.msg.IMsg;

public interface ISender {

	public final static String K_SEND_STATUS = "SendResult";
	
	public final static String V_SEND_STATUS_SUCCESS = "Success";
	public final static String V_SEND_STATUS_FAIL = "Fail";
	
	public final static String V_SEND_NAME_HTTP_SNEDER = "HttpSender";
	public final static String V_SEND_NAME_PORT_SNEDER = "PortSender";
	public final static String V_SEND_NAME_ZIGBEE_SNEDER = "ZigbeeSender";
	
	public String getName();
	public IMsg send(IMsg msg);
}
