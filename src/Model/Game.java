package Model;

import java.util.ArrayList;

public class Game {
	private ArrayList<Player> players;
	private Map gameMap;
	private Deck deck;
	private Country selectedCountry;
	private boolean placePhase, playGamePhase;
	private int humans;
	private int totalPlayers, armiesPlaced;
	private static Game theGame;

	private Game(int numOfHumanPlayers, int totalNumOfPlayers)
	{
		humans = numOfHumanPlayers;
		totalPlayers = totalNumOfPlayers;
		armiesPlaced = 0;
		placePhase = true;
		playGamePhase = false;
		newGame();

	}

	public static Game getInstance(int numOfHumanPlayers, int totalNumOfPlayers)
	{
		if (theGame == null)
			theGame = new Game(numOfHumanPlayers, totalNumOfPlayers);

		return theGame;
	}

	private void newGame()
	{
		selectedCountry = null;
		gameMap = new Map();
		deck = Deck.getInstance();
		players = new ArrayList<>();
		addHumanPlayers(humans);
		addAI(totalPlayers - humans);
		int startingPlayer = 0; // this should change to a method that returns
								// the number of the position in the players
								// list of who is going first
		// or write a function that randomizes everyones position in the array,
		// and start at 0

	}

	public void startGame(int startingPlayer)
	{

		// pick starting countries
		while (placePhase)
		{
			// this loop just does nothing until all armies are placed!
		}
		// doNextThing();

	}

	//this is called by the countryClickListener, and "places" an army in a country, and sets the occupier to whichever player is up
	public void placeArmies()
	{
		if (armiesPlaced < 10)
		{
			if (selectedCountry.getOccupier() == null)
			{
				players.get(0).occupyCountry(selectedCountry);
				selectedCountry.setOccupier(players.get(0));
				selectedCountry.setForcesVal(1);
				armiesPlaced++;
				System.out.println(armiesPlaced);
			} else
			{
				System.out.println("That country is already Occupied");
				System.out.println(armiesPlaced);
			}
		} else if(armiesPlaced < 20)
		{
			if(selectedCountry.getOccupier() == null)//TODO this will need to be deleted, this is just for testing
				System.out.println("You don't occupy this country");
			else if(selectedCountry.getOccupier().equals(players.get(0)))
			{
				selectedCountry.setForcesVal(1);
				armiesPlaced++;
				System.out.println("Reinforced " + selectedCountry.getName());
			}
			else
				System.out.println("You don't occupy this country");
		}
		{
			placePhase = false;
			playGamePhase = true;

		}
	}

	private void addAI(int numOfAI)
	{
		for (int i = 0; i < numOfAI; i++)
			players.add(new AI());

	}

	public Country getSelectedCountry()
	{
		return selectedCountry;
	}

	public void setSelectedCountry(Country selectedCountry)
	{
		this.selectedCountry = selectedCountry;
	}

	private void addHumanPlayers(int numOfHumanPlayers)
	{
		for (int i = 0; i < numOfHumanPlayers; i++)
		{
			players.add(new HumanPlayer());
		}

	}

	public Map getGameMap()
	{
		return gameMap;
	}

}
