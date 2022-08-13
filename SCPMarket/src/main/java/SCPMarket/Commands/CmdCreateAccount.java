package SCPMarket.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import SCPMarket.Database.AccountsParser;

public class CmdCreateAccount extends Command {
	
	public CmdCreateAccount() {
		this.name = "create";
		this.aliases = new String[] {"createaccount", "makeaccount"};
		this.help = "Creates an account";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		String id = ""+ event.getAuthor();
		
		if(!AccountsParser.doesAccountExistAlready(id)) {
			AccountsParser.createAccount(id);
			event.reply("You successfully created an account with user ID "+id+ ".");
		} else {
			event.reply("There is already an account registered with your user ID.");
		}
		
	}

}
