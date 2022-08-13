package SCPMarket.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import SCPMarket.MiscMethods;
import SCPMarket.Database.AccountsParser;
import SCPMarket.Market.PriceModifiers;

public class CmdSell extends Command {
	
	public CmdSell() {
		this.name = "sell";
		this.help = "Sell your shares for points at the current market price";
		this.arguments = "[number of shares to sell]";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		String id = ""+ event.getAuthor();
		int price = PriceModifiers.getPrice();
		
		if(MiscMethods.isInt(event.getArgs())) {
			int amt = Math.abs(Integer.parseInt(event.getArgs()));
			if(AccountsParser.doesAccountExistAlready(id)) {
				
				if(AccountsParser.getBalance(id, 1) >= amt) {
					AccountsParser.changeBalance(id, 0, AccountsParser.getBalance(id, 0) + (amt * price) );
					AccountsParser.changeBalance(id, 1, AccountsParser.getBalance(id, 1) - amt);
					event.reply("You sold "+ amt+" shares at "+ price+", for "+price * amt+ " points. You now have "+AccountsParser.getBalance(id, 0)+" total points and "+AccountsParser.getBalance(id, 1)+ " total shares, "+event.getAuthor().getAsMention());
				} else {
					event.reply("You do not have enough shares to make this transaction, "+event.getAuthor().getAsMention());
				}
				
			} else {
				event.reply("You do not have an account, "+ event.getAuthor().getAsMention());
			}
		} else {
			event.reply("There was an error parsing the provided argument(s), "+ event.getAuthor().getAsMention());
		}
		
	}

}
