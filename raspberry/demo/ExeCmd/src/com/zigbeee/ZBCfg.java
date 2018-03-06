package com.zigbeee;

public class ZBCfg {
	
	private byte[] channel = new byte[4];	//�ŵ�
	private byte[] panID = new byte[2];		//�����
	private ZBDeviceType zdType;		//zigbee�豸����
	
	public ZBCfg()
	{
		//����0x00000800�ŵ���Ĭ��Ҳ��0x00000800�ŵ�
		channel[0] = 0x00;
		channel[1] = 0x08;
		channel[2] = 0x00;
		channel[3] = 0x00;

		//����Ĭ�������Ϊ1234
		panID[0] = 0x34;
		panID[1] = 0x12;

		//����Ĭ���豸����Ϊ·����
		zdType = ZBDeviceType.ZB_DEVICE_TYPE_ROUTE;
	}

	public ZBCfg(ZBCfg cfg){
		setChannel(cfg.getChannel());
		setPanID(cfg.getPanID());
		zdType = cfg.getZdType();
	}
	
	public byte[] getChannel() {
		return channel;
	}
	public void setChannel(byte[] channel) {
		this.channel = channel.clone();
	}
	public byte[] getPanID() {
		return panID;
	}
	public void setPanID(byte[] panID) {
		this.panID = panID.clone();
	}

	public ZBDeviceType getZdType() {
		return zdType;
	}

	public void setZdType(ZBDeviceType zdType) {
		this.zdType = zdType;
	}
	
}