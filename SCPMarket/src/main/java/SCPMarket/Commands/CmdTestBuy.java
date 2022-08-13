package SCPMarket.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import SCPMarket.Database.AccountsParser;

public class CmdTestBuy extends Command {
	
	public CmdTestBuy() {
		this.name = "testbuy";
	}

	@Override
	protected void execute(CommandEvent event) {
		int amt = Integer.parseInt(event.getArgs());
		String id = ""+ event.getAuthor();
		
		if(AccountsParser.doesAccountExistAlready(id)) {
			
			AccountsParser.changeBalance(id, 0, (AccountsParser.getBalance(id, 0) + amt));
			event.reply("test buy: "+amt+ " has been added");
			
		} else {
			event.reply("You do not have an account registered.");
		}
		
	}

}
