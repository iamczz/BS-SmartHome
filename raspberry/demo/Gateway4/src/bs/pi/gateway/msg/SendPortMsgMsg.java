package bs.pi.gateway.msg;

public class SendPortMsgMsg implements IMsg {

	public final static String MSG_NAME = "PortSendMsg";
	
	private byte[] data;
	
	@Override
	public String getName() {
		return MSG_NAME;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
