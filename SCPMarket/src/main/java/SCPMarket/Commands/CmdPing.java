package SCPMarket.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class CmdPing extends Command {

	
	public CmdPing() {
		this.name = "ping";
		this.help = "does a ping pong";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		event.reply("pong");
		
	}

}
