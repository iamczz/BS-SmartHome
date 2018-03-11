package bs.pi.gateway.msg;

public class PortMsgReceivedMsg implements IMsg {

	public final static String MSG_NAME = "PortReceivedMsg";
	
	private byte cmd0;
	private byte cmd1;
	private byte[] data;
	
	@Override
	public String getName() {
		return MSG_NAME;
	}

	public byte getCmd0() {
		return cmd0;
	}

	public void setCmd0(byte cmd0) {
		this.cmd0 = cmd0;
	}

	public byte getCmd1() {
		return cmd1;
	}

	public void setCmd1(byte cmd1) {
		this.cmd1 = cmd1;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
