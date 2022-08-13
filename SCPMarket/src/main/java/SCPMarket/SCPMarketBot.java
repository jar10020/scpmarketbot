package SCPMarket;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.jagrosh.jdautilities.command.CommandClientBuilder;


import SCPMarket.Commands.*;
import SCPMarket.Market.EventChannel;
import SCPMarket.Market.PriceDatabaseParser;
import SCPMarket.Market.PriceModifiers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.EventListener;




public class SCPMarketBot extends EventListener {
	
	public static void main(String[] args) throws Exception {
		
		
		//database stuff
		
		Connection c = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:accountinfo.db");
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:pricehistory.db");
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		    }
		System.out.println("Opened database successfully");
		     
		      
		      
		      
		
		//command client
		CommandClientBuilder ccb = new CommandClientBuilder()
				.setPrefix("$")
				.setOwnerId("/*censored*/")
				.setActivity(Activity.watching("the market | "+ PriceModifiers.getPrice()))
				.addCommands(new CmdPing(),
						
						new CmdCreateAccount(),
						new CmdCheckBalance(),
						//new CmdTestBuy()
						new CmdBuy(),
						new CmdGetPrice(),
						new CmdSell()
						
						);
		
		
		
		
		//building our api/client
		JDA api = JDABuilder.createDefault("/*censored*/")
				
				.addEventListeners(ccb.build())
				
				//following inspired by https://github.com/DV8FromTheWorld/JDA/wiki/10%29-FAQ#how-can-i-send-a-message-to-a-specific-channel-without-an-event
		        .addEventListeners(new ListenerAdapter() {
		            @Override public void onReady(ReadyEvent event) {
		                new EventChannel(event.getJDA()).start(); // starts your channel with the ready event
		            }
		        })
				.build();
		
		
		
		//PRICE TIMER=================================================================================================================================================
		
		
		
		//LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		//LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        
			
		Timer timer = new Timer();
		
		
		
		TimerTask calculatePrice = new TimerTask() {
			
			@Override
			public void run() {
				if(currentTime.isAfter(LocalTime.parse("14:00")) & currentTime.isBefore(LocalTime.parse("23:59"))) { //opens at 2pm and closes at 11:59pm
					
					//CALL METHODS TO CALC PRICE:
					PriceModifiers.confidenceUpdate();
					
					PriceModifiers.priceUpdateTime();
					PriceModifiers.priceUpdatePlayersConnected();
					
					PriceModifiers.priceUpdateDecay();	
					
					PriceModifiers.updatePriceFinal();
					
					
					//log it
					PriceDatabaseParser.logPrice(LocalDateTime.now().format(formatter), PriceModifiers.getPrice());
					
				}
				
			}
		};
		
		timer.scheduleAtFixedRate(calculatePrice, 5000, 2000); //2000 = every 2 seconds
		
		
		//GENERATING NEW SEED EACH DAY
		TimerTask calculateSeed = new TimerTask() {
			@Override
			public void run() { //TODO: Change this to gen a new seed at 2pm every day instead of every few hours
				PriceModifiers.genSeed();
			}
		};
		
		timer.scheduleAtFixedRate(calculateSeed, 2500, 28800000); //28800000 is 8 hrs for now
		
		
		//updating activity ticker
		TimerTask updateTicker = new TimerTask() {
			@Override
			public void run() {
				api.getPresence().setActivity(Activity.playing("you. | "+ PriceModifiers.getPrice()));
			}

		};
		
		timer.scheduleAtFixedRate(updateTicker, 7500, 15000);
	}
	

}
