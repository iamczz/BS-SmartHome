package bs.pi.gateway.main;

import java.util.ArrayList;

public class Gateway {
	
	private ArrayList<ISender> senderList;
	private ArrayList<IReceiver> receiverList;
	private IProcessor processor;
	
	public Gateway(){
		receiverList = new ArrayList<IReceiver>();
		senderList = new ArrayList<ISender>();
	}
	
	public void installSender(ISender sender){
		senderList.add(sender);
	}
	
	public void installReceiver(IReceiver receiver){
		receiverList.add(receiver);
	}
	
	public void setProcessor(IProcessor processor){
		this.processor = processor;
	}
	
	public void start(){
		
		//processor监听各个接收器消息接收事件
		if(receiverList.size()>0){
			for(IReceiver receiver : receiverList)
				receiver.addReceivedListenr(processor);
		}
		//给processor发送器提供发送器
		processor.setSenders(senderList);
		
		processor.start();
	}
	
	public void destroy(){
		
	}
}
