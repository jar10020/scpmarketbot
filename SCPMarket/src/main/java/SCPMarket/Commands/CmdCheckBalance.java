package SCPMarket.Commands;

import java.awt.Color;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import SCPMarket.Database.AccountsParser;
import net.dv8tion.jda.api.EmbedBuilder;

public class CmdCheckBalance extends Command {

	
	public CmdCheckBalance() {
		this.name = "balance";
		this.aliases = new String[] {"checkbalance", "money", "amount", "portfolio"};
		this.help = "Tells you the current balance of your account.";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		
		
		String id = ""+ event.getAuthor();
		if(AccountsParser.doesAccountExistAlready(id)) {
			//embed stuff
			EmbedBuilder eb = new EmbedBuilder();
			
			eb.setAuthor(event.getMember().getEffectiveName());
			eb.setTitle("Account Details:");
			eb.setColor(Color.YELLOW);
			eb.addField("Points", ""+AccountsParser.getBalance(id, 0), true);
			eb.addField("Shares", ""+AccountsParser.getBalance(id, 1), true);
			
			event.reply(eb.build());;
			
		} else {
			event.reply("There is no account registered with your user ID.");
		}
		
		
		
	}
	
}
