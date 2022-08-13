package SCPMarket.Market;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

import SCPMarket.SCPMarketBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;

public class PriceModifiers {
	
	static Random rand = new Random();
	
	static int price = 5000;
	static double confidence = 0.5;
	
	static double seed = 0;
	static double decaySeed = 0;
	
	static LocalDate currentDate = LocalDate.now();
	static LocalTime currenTime = LocalTime.now();
	
	
	//generating seed for the day
	public static void genSeed() {
		seed = rand.nextDouble();
		if(currentDate.getDayOfWeek().getValue() >= 5) { //fridays, saturdays and sundays get a boost of 0.35 to the seed
			seed += 0.15;
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
		updatePriceFinal();
		return price;
	}
	
	
	//constant decay, a twentieth of whatever the current price is times the decayseed
	//TODO: update this to work wtih confidence system
	public static void priceUpdateDecay() {
		if(price > 1000) {
			price -= (price * 0.00015) * decaySeed * rand.nextDouble();
		}
	}
	
	
	public static void confidenceUpdate() {
		
		int playersConnected = EventChannel.getNumberOfPlayersInSCPVoiceChat();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		LocalTime nowTime = LocalTime.now();
		
		
		if(LocalTime.now().isAfter(LocalTime.parse("2100")) & LocalTime.now().isBefore(LocalTime.parse("23:00"))) { //all this only happens if it's between 9:00pm and 11:00pm
			
			//calculating minutes since 9:00

			//int t = 2145 - 2100; //21:00 (9pm) minus whatever the current time is should net us the minutes past 9pm
			Duration timeSince9 = Duration.between(LocalTime.parse("21:00:00"), LocalTime.now());
			int t = (int) (timeSince9.getSeconds() / 60);

			System.out.println("Program thinks it's "+ t+ " minutes past 9:00pm (21:00)"); //for testing
			//TODO: Remove this ^^
			
			double c = Math.pow((0.1 * t + 1), 2);
			
			
		
			//actual formula
			confidence = (Math.sin(  (Math.PI / 2)  /  (1 + Math.pow(Math.E, -0.7 * (playersConnected - 4.125 + (-0.025 * c) ))  )	));
			
		}
		
	}
	
	
	//DEPCRECATED BY THE CONFIDENCE FORMULA
	/*public static void confidenceUpdate() {
		
		int playersConnected = EventChannel.getNumberOfPlayersInSCPVoiceChat();

		if(confidence > 0) { //we dont want negative confidence
			
			
			/*
			 * general formula here until 8 players is reached is playersConnected * x = y
			 * with y being the % that confidence should increase over the total timespan of the if statement
			 * and x is found out backwards from there as the number playersConnected must be multiplied
			 * by to be equal (roughly) to y
			 
			
			if(LocalTime.now().isAfter(LocalTime.parse("20:00")) & LocalTime.now().isBefore(LocalTime.parse("20:29"))) { 
				price += rand.nextInt(15) * seed;
				//8pm - 8:29pm
				
				if(playersConnected >= 8) {
					confidence = 100;
				} else {
					confidence += playersConnected * 0.00533333333; //15% increase in confidence over half an hour for 3+ players connected.
				}
				
				
			} else if(LocalTime.now().isAfter(LocalTime.parse("20:30")) & LocalTime.now().isBefore(LocalTime.parse("19:59"))) { 
				price += rand.nextInt(25) * seed;
				//8:30pm - 8:59pm
				
				
				
			} else if(LocalTime.now().isAfter(LocalTime.parse("21:00")) & LocalTime.now().isBefore(LocalTime.parse("21:29"))) { 
				price += rand.nextInt(25) * seed;
				//9 - 9:29pm
				
				
				
			} else if(LocalTime.now().isAfter(LocalTime.parse("21:30")) & LocalTime.now().isBefore(LocalTime.parse("21:59"))) { 
				price += rand.nextInt(25) * seed;
				//9pm - 9:59pm
				
				
				
			} else if(LocalTime.now().isAfter(LocalTime.parse("22:00")) & LocalTime.now().isBefore(LocalTime.parse("22:29"))) { 
				price += rand.nextInt(25) * seed;
				//10pm - 10:30pm
				
				
				
			} else if(LocalTime.now().isAfter(LocalTime.parse("22:30")) & LocalTime.now().isBefore(LocalTime.parse("22:59"))) { 
				price += rand.nextInt(25) * seed;
				//10:30pm - 10:59pm
				
				
			}
			
			if(confidence > 100) {
				confidence = 100;
			} else if(confidence < 0) {
				confidence = 0;
			}
			
		}
	}*/
	
	
	
	
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
	
	
	public static void updatePriceFinal() {
		if(LocalTime.now().isAfter(LocalTime.parse("21:00")) & LocalTime.now().isBefore(LocalTime.parse("23:00"))) {
			//price += (int) (price * confidence);
			
			System.out.println("price = "+ price+ " | confidence = "+ confidence);
		}
	}
	
	//TODO: Wild card players like Beverage Box should skyrocket price
	//TODO: fellas online could affect it too
		
	
}
