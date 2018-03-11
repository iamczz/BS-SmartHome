package bs.pi.gateway.client.zigbee;

import java.util.ArrayList;
import java.util.HashMap;

import bs.pi.gateway.client.port.PortClient;
import bs.pi.gateway.main.IClient;
import bs.pi.gateway.main.IConverter;
import bs.pi.gateway.main.IReceiver;
import bs.pi.gateway.main.ISender;
import bs.pi.gateway.msg.PortSendResponseMsg;
import bs.pi.gateway.msg.SendPortMsgMsg;

public class ZigbeeClient implements IClient{

	public static final String DEFAULT_CFG_PATH = System.getProperty("user.dir")+System.getProperty("file.separator")+"zigbeeClientCfg.properties";
	private String cfgPath;
	private ZigbeeClientCfg cfg;
	private PortClient portClient;
	private ISender portSender;
	private ZigbeeSender zigbeeSender;
	private ZigbeeReceiver zigbeeReceiver;
	private IConverter converter;
	
	public ZigbeeClient(String cfgPath, PortClient portClient){
		this.cfgPath = cfgPath;
		this.portClient = portClient;
	}
	
	@Override
	public void init() throws Exception {
		
		if(cfgPath == null)
			cfgPath = DEFAULT_CFG_PATH;
		cfg = new ZigbeeClientCfg(cfgPath);

		portClient.init();
		portClient.start();
		portSender = portClient.getSender();
		
		portSend(CodeGenerator.CMD_DEVICE_RESET);
		Thread.sleep(2000);
		portSend(CodeGenerator.CMD_STARTUP_WITHOUT_LAST_STATE);
		Thread.sleep(500);
		portSend(CodeGenerator.CMD_DEVICE_RESET);
		Thread.sleep(2000);
		portSend(CodeGenerator.chanlistCfg(cfg.getChannel()));
		Thread.sleep(500);
		portSend(CodeGenerator.PANIDCfg(cfg.getPanID()));
		Thread.sleep(500);
		portSend(CodeGenerator.deviceTypeCfg(cfg.getDeviceType()));
		Thread.sleep(500);
		portSend(CodeGenerator.CMD_ZDO_DIRECT_CB);
		Thread.sleep(500);
		
		ArrayList<ZigbeeAppReg> appRegList = cfg.getAppRegList();
		if( appRegList != null && appRegList.size()>0){
			for(ZigbeeAppReg appReg : appRegList){
				registerAppToZigbee(appReg);
			}
		}
	}
	
	private boolean registerAppToZigbee(ZigbeeAppReg appReg){
		PortSendResponseMsg portSendResponseMsg = null;
		try{
			portSendResponseMsg = portSend(CodeGenerator.appRegister(appReg));
			Thread.sleep(500);
		}catch(Exception e2){
			e2.printStackTrace();
		}
		
		return portSendResponseMsg.getSendSuccess();
	}
	
	private PortSendResponseMsg portSend(byte[] data) throws Exception{
		SendPortMsgMsg portSendMsg = new SendPortMsgMsg();
		portSendMsg.setData(data);
		return (PortSendResponseMsg) portSender.send(portSendMsg);
	}

	@Override
	public void start() throws Exception {
		portSend(CodeGenerator.CMD_STARTUP_FROM_APP);
		Thread.sleep(2000);
		
		zigbeeReceiver = new ZigbeeReceiver(portClient.getReceiver(), converter);
		zigbeeReceiver.start();
		
		zigbeeSender = new ZigbeeSender(portSender, converter, cfg);
	}

	@Override
	public void destroy() {
		portClient.destroy();
		zigbeeSender = null;
		zigbeeReceiver = null;
	}

	@Override
	public ISender getSender() {
		return zigbeeSender;
	}

	@Override
	public IReceiver getReceiver() {
		return zigbeeReceiver;
	}

	@Override
	public void setConverter(IConverter converter) {
		this.converter = converter;
	}
}
