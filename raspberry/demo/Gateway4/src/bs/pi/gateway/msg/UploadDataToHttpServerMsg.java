package bs.pi.gateway.msg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

public class UploadDataToHttpServerMsg implements IMsg {

	public final static String MSG_NAME = "UploadDataToHttpServerMsg";

	private int sensorID;
	private float sensorValue;
	
	@Override
	public String getName() {
		return MSG_NAME;
	}

	public int getSensorID() {
		return sensorID;
	}

	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}

	public float getSensorValue() {
		return sensorValue;
	}

	public void setSensorValue(float sensorValue) {
		this.sensorValue = sensorValue;
	}
}
