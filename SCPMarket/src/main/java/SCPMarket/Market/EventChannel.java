package SCPMarket.Market;

import net.dv8tion.jda.api.JDA;

public class EventChannel {
	
	private static JDA api;
	
	public EventChannel(JDA api) {
		EventChannel.api = api;
	}
	
	public void start() {
		//i dont know why it wanted me to put this here but i am
	}
	
	public static int getNumberOfPlayersInSCPVoiceChat() {
		return api.getGuildChannelById("591457636273094666").getMembers().size();
	}
	
	public static JDA getTheJDAInstance() {
		return EventChannel.api;
	}
}
