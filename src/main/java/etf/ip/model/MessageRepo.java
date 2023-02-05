package etf.ip.model;
import java.util.List;

import etf.ip.dao.MessageDAO;
public class MessageRepo {
	

	public List<Message> getMsgs() {
		return new MessageDAO().getMsgs();
	}
	public List<Message> getMsgs(String key) {
		return new MessageDAO().getMsgs(key);
	}
	
}
