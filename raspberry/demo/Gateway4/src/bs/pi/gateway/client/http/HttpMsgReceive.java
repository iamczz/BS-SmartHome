package bs.pi.gateway.client.http;

import net.sf.json.JSONObject;

public class HttpMsgReceive {
	
	public final static String K_SUCCESS = "success";
	public final static String K_MSG = "msg";
	public final static String K_APP_ID = "appID";
	public final static String K_CMD = "cmd";
	public final static String K_PARAMS = "params";
	
	public final static boolean V_SUCCESS_TRUE = true;
	public final static boolean V_SUCCESS_FALSE = false;
	
	
	public final static int TYPE_DEVICE_CMD = 1;
	
	private int type;
	private JSONObject data;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public JSONObject getData() {
		return data;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
}
