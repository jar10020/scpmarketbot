package SCPMarket.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import SCPMarket.Market.PriceModifiers;

public class CmdGetPrice extends Command {
	
	public CmdGetPrice() {
		this.name = "price";
		this.aliases = new String[] {"checkprice", "cost"};
		this.help = "Lists the current price";
		this.cooldown = 5;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		int price = PriceModifiers.getPrice();
		
		event.reply("Shares are trading at **"+price+ "** points per share.");
		
	}
	
}
