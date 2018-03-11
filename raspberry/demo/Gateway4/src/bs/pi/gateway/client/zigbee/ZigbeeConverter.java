package bs.pi.gateway.client.zigbee;

import java.util.HashMap;

import bs.pi.gateway.assist.Tool;
import bs.pi.gateway.main.IConverter;
import bs.pi.gateway.msg.IMsg;
import bs.pi.gateway.msg.PortMsgReceivedMsg;
import bs.pi.gateway.msg.SendMsgToAppMsg;
import bs.pi.gateway.msg.UploadDataToHttpServerMsg;

public class ZigbeeConverter implements IConverter {
	
	@Override
	public IMsg convertMsgReceive(Object msg) {
		if(msg ==null)
			return null;
		
		ZigbeeMsgReceive zigbeeMsgReceive = null;
		try{
			zigbeeMsgReceive = (ZigbeeMsgReceive)msg;
		}catch(Exception e){
			return null;
		}
		
		PortMsgReceivedMsg msg1 = zigbeeMsgReceive.getMsg();
		
		byte cmd0 = msg1.getCmd0();
		byte cmd1 = msg1.getCmd1();
		byte[] data = msg1.getData();
		
		if(cmd0 == (byte)0x44 && cmd1 == (byte)0x81){
			if(data != null || data.length > 18){
				byte len = data[16];
				byte cmd3 = data[17];
				byte cmd4 = data[18];
				//0x0001为数据上传命令
				if(cmd3 == 0x00 && cmd4 == 0x01 && len == 8){
					byte[] data1 = new byte[6];
					System.arraycopy(data, 19, data1, 0, 6);
					return resolveUplaodDataToHttpServerCMsg(data1);
				}
			}
		}
		
		return null;
	}
	
	private IMsg resolveUplaodDataToHttpServerCMsg(byte[] data){
		int sensorID = data[0]*256 + data[1];
		byte[] valueBytes = new byte[4];
		System.arraycopy(data, 2, valueBytes, 0, 4);
		float sensorValue;
		try {
			sensorValue = Tool.bytesToFloat(valueBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		UploadDataToHttpServerMsg msg = new UploadDataToHttpServerMsg();
		msg.setSensorID(sensorID);
		msg.setSensorValue(sensorValue);
		
		return msg;
	}

	@Override
	public Object convertMsgSend(IMsg msg) {
		if(msg ==null)
			return null;
		
		if(SendMsgToAppMsg.MSG_NAME.equals(msg.getName())){
			SendMsgToAppMsg sendMsgToAppMsg = (SendMsgToAppMsg)msg;
			int appID = sendMsgToAppMsg.getAppID();
			byte appIDHigh = (byte) (appID / 256);
			byte appIDLow = (byte) (appID % 256);
			String cmd = sendMsgToAppMsg.getCmd();
			HashMap<String, Object> params = sendMsgToAppMsg.getParams();
			byte[] data = null;
			if("openLight".equals(cmd)){
				data = new byte[4];
				data[0] = appIDHigh;
				data[1] = appIDLow;
				data[2] = 0x00;
				data[3] = 0x01;
			}
			ZigbeeMsgSend zigbeeMsgSend = new ZigbeeMsgSend();
			zigbeeMsgSend.setData(data);
			byte[] dstAddr = {(byte)0xff, (byte)0xff};
			zigbeeMsgSend.setDstAddr(dstAddr);
			return zigbeeMsgSend;
		}
		return null;
	}
	
}
