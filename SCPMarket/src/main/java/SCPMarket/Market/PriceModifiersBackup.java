package SCPMarket.Market;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import SCPMarket.SCPMarketBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;

public class PriceModifiersBackup {
	
	static Random rand = new Random();
	
	static int price = 5000;
	static double seed = 0;
	static double decaySeed = 0;
	
	static LocalDate currentDate = LocalDate.now();
	static LocalTime currenTime = LocalTime.now();
	
	
	//generating seed for the day
	public static void genSeed() {
		seed = rand.nextDouble();
		if(currentDate.getDayOfWeek().getValue() >= 5) { //fridays, saturdays and sundays get a boost of 0.35 to the seed
			seed += 0.35;
		}
		decaySeed = rand.nextDouble();
	}
	
	
	/*
	 * 
	 * PRICE METHODS=====================PRICE METHODS=====================PRICE METHODS=====================PRICE METHODS=====================PRICE METHODS=====================PRICE METHODS=====================
	 * 
	 */
	
	//just for testing and proof of concept
	/*public static void testUpdatePrice( ) {
		price+= rand.nextInt(10) * seed;
		System.out.println("testUpdatePrice executed. Price: "+ price+". Seed = "+seed);
	}*/
	
	//get the price
	public static int getPrice() {
		return price;
	}
	
	
	//constant decay, a twentieth of whatever the current price is times the decayseed
	public static void priceUpdateDecay() {
		if(price > 1000) {
			price -= (price * 0.00015) * decaySeed * rand.nextDouble();
		}
	}
	
	
	// goes up more and more closer we get to prime time
	public static void priceUpdateTime() {
		if(LocalTime.now().isAfter(LocalTime.parse("16:00")) & LocalTime.now().isBefore(LocalTime.parse("17:59"))) { //4pm - 5:59pm
			price += rand.nextInt(5) * seed;
		} else if(LocalTime.now().isAfter(LocalTime.parse("18:00")) & LocalTime.now().isBefore(LocalTime.parse("19:59"))) { //6pm - 7:59pm
			price += rand.nextInt(10) * seed;
		} else if(LocalTime.now().isAfter(LocalTime.parse("20:00")) & LocalTime.now().isBefore(LocalTime.parse("20:29"))) { //8pm - 8:29pm
			price += rand.nextInt(15) * seed;
		} else if(LocalTime.now().isAfter(LocalTime.parse("20:30")) & LocalTime.now().isBefore(LocalTime.parse("19:59"))) { //8:30pm - 8:59pm
			price += rand.nextInt(25) * seed;
		}
		
	}
	
	
	//goes up the closer we get to 8 players, skyrockets when we are above or at 8 and reaches the moon if we're setting a record
	public static void priceUpdatePlayersConnected() {
		int playersConnected = EventChannel.getNumberOfPlayersInSCPVoiceChat();
		if(LocalTime.now().isAfter(LocalTime.parse("21:00")) & LocalTime.now().isBefore(LocalTime.parse("23:59"))) { //9pm - 11:59pm
			if(playersConnected == 0) {
				price -= 100 * decaySeed; //price starts to fall immensely if there's no one there by 9
			} else if(playersConnected <= 3) {
				price += 15 * seed;
			} else if(playersConnected == 5) {
				price += 25 * seed;
			} else if(playersConnected == 6) {
				price += 35 * seed;
			} else if(playersConnected == 7) {
				price += 45 * seed;
			} else if(playersConnected >= 8 & playersConnected <= 10) {
				price += 75 * seed;
			} else if(playersConnected > 10) {
				price += (75*seed) + (playersConnected * 4);
			}
		}
	}
	
	//TODO: Wild card players like Beverage Box should skyrocket price
	//TODO: fellas online could affect it too
	//TODO: Decay should really ramp up after 10:30 or 11, and a little bit at 9 so we get catastrophic zero primetime crashes.
	
	
}
