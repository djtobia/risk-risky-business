package Model;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JMenuItem;

public class AI extends Player implements Serializable{

	private JMenuItem myDiff;
	private Random rand;
	private AIStrategy strategy;
	private int timesIAttacked;
	// private Game theGame;

	public AI(AIStrategy strat, int numOfPlayers) {
		super(numOfPlayers);
		timesIAttacked = 0;
		strategy = strat;
		strategy.setMe(this);
		rand = new Random();
	}// end AI constructor

	

	


	// checks an ai's countries neighbors, to see if they are occupied. if they
	// are, go to the next one, otherwise
	// return that country as a selection. Used for placement in the first turn.
	public Country checkAllNeighbors() {
		int i = 0, j = 0;
		// get my first countries neighbors
		ArrayList<Country> neighbors = getCountries().get(i).getNeighbors();
		while (i < neighbors.size()) {
			j = 0;
			while (j < neighbors.size() && neighbors.get(j).getOccupier() != null) {
				j++;
			}

			if (j < neighbors.size())
				return neighbors.get(j);

			i++;
			if (i < getCountries().size())
				neighbors = getCountries().get(i).getNeighbors();
		}
		return null;
	}// end checkAllNeighbors



	
	public Country pickRandomOwnedCountry() {
		Random rand = new Random();
		int randNum = rand.nextInt(getCountries().size());
		return getCountries().get(randNum);
	}// end pickRandomOwnedCountry

	

	// returns true if it is finished attacking, and false otherwise
	// grabs a country to attack, and the country that it is attacking from
	// if there is no country to attack, return. if it loses a battle, check if
	// there are still other countries it can attack
	public String aiAttack() {
		
//		Country attacking = getCountryToAttack();
//		if (attacking == null)
//			return null;
//		Country attackingFrom = findAttackingCountry(attacking);
//
//		// change this for dice roll later, but for now, just take over
//		if (attackingFrom.getForcesVal() - 1 > attacking.getForcesVal()) {
//			String str = this.getName() + " defeated " + attacking.getOccupier().getName() + " and took " + attacking.getName() + ".\n";
//			int oldForces = attacking.getForcesVal();
//			attacking.getOccupier().loseCountry(attacking);
//			attacking.removeUnits(oldForces);
//			attacking.setForcesVal(attackingFrom.getForcesVal() - 1);
//			attacking.setOccupier(this);
//			this.occupyCountry(attacking);
//			System.out.println(this.getName() + " took " + attacking.getName());
//			attackingFrom.removeUnits(attackingFrom.getForcesVal() - 1);
//			return str;
			return null;
		}//end aiAttack

		/*
		 * for when dice roll exists
		 * if(myStrat == AIStrat.EASY){
		 * 	if(attackingFrom.getForcesVal() > 1){
		 * 		do dice roll stuff against attacking and take all units but 1 from attackinFrom
		 * 		return false;		
		 * 	}
		 * else
		 * 		return true;
		 * }
		 * else{
		 * 	if(attackingFrom.getForces() == attacking.getForces() or up to 2 less)
		 * 			do dice roll stuff against attacking and take all units but 1 from attacking from
		 * 			return false
		 * else
		 * 		return true;t 
		 * }
		 */
	
	public int getAmountToAttackWith(Country from, Country to){
		timesIAttacked++;
		return from.getForcesVal() - 1; //STUB!
	}//end getAmoutnToAttackWith

	public Country findAttackingCountry(Country attacking) {

		// System.out.println("find attacking");
		for (Country c1 : findFringeCountries()) {
			for (Country c2 : c1.getNeighbors()) {
				if (c2.equals(attacking)) {
					return c1;
				}
			}
		}
		return null;
	}
	
	public boolean finishedAttacking(){
		if (timesIAttacked == 3){
			timesIAttacked = 0;
			return true;
		}
		else
			return false;
	}//end finishedAttacking

	// returns a country it can attack
	public Country getCountryToAttack() {
		// System.out.println("get country to attack");
		Country attackMe = pickRandomFromList(findCountriesToAttack());
		return attackMe;
	}// end
		// getCountryToAttack

	// picks a random country from the list of countries to attack
	private Country pickRandomFromList(ArrayList<Country> countriesToAttack) {
		if (countriesToAttack == null)
			return null;

		Random rand = new Random();
		int randInt = 0;

		randInt = rand.nextInt(countriesToAttack.size());

		return countriesToAttack.get(randInt);
	}// end pickRandomFromList

	

	// creates the ai's menuItem for changing difficulty
	public void makeMenuItem(int i, ActionListener aiDiffChangeListener) {
		myDiff = new JMenuItem("AI " + i);
		myDiff.addActionListener(aiDiffChangeListener);
		myDiff.setActionCommand(String.valueOf(i));
	}// end makeMenuItem

	// returns its jMenuItem
	public JMenuItem getMenuItem() {
		return myDiff;
	}// end getMenuItem

	// returns the ai's current strategy as a string, used for checking if the
	// ai difficulty menu in the gui was working
	



	// returns a randomlist of countries to add units to out of the ai's owned
	// countries
	private ArrayList<Country> pickSetOfRandomOwnedCountry() {
		ArrayList<Country> countries = new ArrayList<>();
		Random rand = new Random();
		int randNum = 0;
		int i=0;
		while (getAvailableTroops() > i) {
			i++;
			randNum = rand.nextInt(getCountries().size());
			countries.add(getCountries().get(randNum));
		}
		return countries;
	}// end pickSetOfRandomOwnedCountry

	// gets all fringe countries, then for each neihbor that fringe country has,
	// if it isn't owned by me
	// check if i have more units on my country than that country, if I do, add
	// that country to my list of countriesWorthAttacking
	private ArrayList<Country> findCountriesToAttack() {
		// System.out.println("find countries to attack");
		ArrayList<Country> fringeCountries = findFringeCountries();
		ArrayList<Country> countriesWorthAttacking = new ArrayList<>();
		for (Country country : fringeCountries) {
			ArrayList<Country> neighbors = country.getNeighbors();
			for (Country neighboringCountry : neighbors) {
				if (neighboringCountry.getOccupier().getFaction().compareTo(this.getFaction()) != 0) {
						countriesWorthAttacking.add(neighboringCountry);
				} // end if
			} // end for
		} // end for

		
		if (countriesWorthAttacking.size() == 0)
			return null;

		return countriesWorthAttacking;
	}// end findCountriesToAttack

	

	// starts at first country, checks if it is surrounded by friendlies, if it
	// is
	// moves all of its units except for one to its neighbors
	
	
	
	public  Country pickRandomFromFringe() {
		ArrayList<Country> fringeCountries = findFringeCountries();

		int randNum = 0;
		// System.out.println(fringeCountries.size() + " size of list to choose
		// from");
		if (fringeCountries.size() == 0)
			return null;
		randNum = rand.nextInt(fringeCountries.size());
		return fringeCountries.get(randNum);
	}
	
	public Country pickRandomCountry()
	{
		Map map = Map.getInstance(0);
		Country[] countries = map.getCountries();
		int randNum = rand.nextInt(50);
		return countries[randNum];
	}
	public ArrayList<Country> findFringeCountries() {
		ArrayList<Country> fringeCountries = new ArrayList<>();

		int i = 0, j = 0;
		ArrayList<Country> neighbors = getCountries().get(i).getNeighbors();
		while (i < getCountries().size()) {
			j = 0;
			while (j < neighbors.size()) {

				if (!this.equals(neighbors.get(j).getOccupier())) {
					fringeCountries.add(getCountries().get(i));
					j = neighbors.size();
				}
				j++;
			}
			i++;
			if (i < getCountries().size())
				neighbors = getCountries().get(i).getNeighbors();
		}

		return fringeCountries;
	}
		// moves units to other countries if it has more than 2 units occupying


	
	@Override
	public ArrayList<Card> redeemCards() {
		if (getCards().size() == 5) {
			return findmyCardsToRedeem();
		}//end if
		else
			return null;
//			int numArmies = -1;
//
//			Card one = myCardsToRedeem.get(0);
//			Card two = myCardsToRedeem.get(1);
//			Card three = myCardsToRedeem.get(2);
//
//			// redeemable: three of the same unit type, one of each type, two + wild
//			// if can redeem:
//			if ((one.getUnit().compareTo(two.getUnit()) == 0 && one.getUnit().compareTo(three.getUnit()) == 0
//					&& three.getUnit().compareTo(two.getUnit()) == 0)
//					|| (one.getUnit().compareTo(two.getUnit()) != 0 && one.getUnit().compareTo(three.getUnit()) != 0
//							&& three.getUnit().compareTo(two.getUnit()) != 0)
//					|| (one.getUnit().compareTo("WILD") == 0 )
//					|| (two.getUnit().compareTo("WILD") == 0 )
//					|| (three.getUnit().compareTo("WILD") == 0)) {
//
//				numArmies = 0;
//				redemptions++;
//				switch (redemptions) {
//				case 1:
//					numArmies = 4;
//					break;
//				case 2:
//					numArmies = 6;
//					break;
//				case 3:
//					numArmies = 8;
//					break;
//				case 4:
//					numArmies = 10;
//					break;
//				case 5:
//					numArmies = 12;
//					break;
//				case 6:
//					numArmies = 15;
//					break;
//				default:
//					numArmies = 15 + 5 * (redemptions - 6);
//					break;
//				}
//
//				// if any one of the redeemable cards contains a country that the
//				// player has, add 2 armies to that country.
//				boolean added = false;
//				for (Card c : myCardsToRedeem) {
//					for (Country t : this.getCountries()) {
//						if (c.getCountry().compareTo(t.getName()) == 0) {
//							// add 2 armies to that country
//							added = true;
//							int currentForces = t.getForcesVal();
//							System.out.println("current Forces" + currentForces + t.getName());
//							t.setForcesVal(2);
//							System.out.println("updated Forces" + t.getForcesVal() + t.getName());
//							break; //can only redeem a country card for extra armies once per turn
//						}
//					}
//					if(added)
//						break;
//				}
//				if (!added)
//					System.out.println("no country cards to redeem");
//			} else
//				System.out.println("unable to redeem cards");
//			
//			this.discardCards(myCardsToRedeem);
//			deck.addToDiscardPile(myCardsToRedeem);
//			return numArmies;
//		}
//		return 0;
	}

	private ArrayList<Card> findmyCardsToRedeem() {

		ArrayList<Card> myThreeCards = new ArrayList<>();
		int infantryCount = 0, calvaryCount = 0, artilleryCount = 0, wildCount = 0;
		// step through 5 cards, and count how many of each
		for (Card card : getCards()) {
			switch (card.getUnit()) {
			case "infantry":
				infantryCount++;
				break;
			case "calvary":
				calvaryCount++;
				break;
			case "artillery":
				artilleryCount++;
				break;
			case "WILD":
				wildCount++;
				break;
			}
		}
		if (infantryCount >= 3 || (wildCount == 1 && infantryCount >= 2) || (wildCount == 2 && infantryCount >= 1)) {
			myThreeCards = findThreeInfantry();
		} else if (calvaryCount >= 3 || (wildCount == 1 && calvaryCount >= 2) || (wildCount == 2 && calvaryCount >= 1)) {
			myThreeCards = findThreeCalvary();
		} else if (artilleryCount >= 3 || (wildCount == 1 && calvaryCount >= 2) || (wildCount == 2 && artilleryCount >= 1)) {
			myThreeCards = findThreeArtillery();
		} else {
			myThreeCards = findOneOfEach();
		}

		return myThreeCards;
	}// end findmyCardsToRedeem

	private ArrayList<Card> findOneOfEach() {
		ArrayList<Card> myThreeCards = new ArrayList<>();
		boolean infantry = false, calvary = false, artillery = false;
		for (Card card : getCards()) {
			if (!infantry && (card.getUnit().compareTo("infantry") == 0 || card.getUnit().compareTo("WILD") == 0)) {
				myThreeCards.add(card);
				infantry = true;
			}

			if (!calvary && (card.getUnit().compareTo("calvary") == 0 || card.getUnit().compareTo("WILD") == 0)) {
				myThreeCards.add(card);
				calvary = true;
			}

			if (!artillery && (card.getUnit().compareTo("artillery") == 0 || card.getUnit().compareTo("WILD") == 0)) {
				myThreeCards.add(card);
				artillery = true;
			}
		}
		return myThreeCards;
	}// end findOneOfEach

	private ArrayList<Card> findThreeArtillery() {
		ArrayList<Card> threeArtillery = new ArrayList<>();
		for (Card card : getCards()) {
			if (card.getUnit().compareTo("artillery") == 0) {
				threeArtillery.add(card);
			}

			if (threeArtillery.size() == 3) {
				break;
			}
		}
		return threeArtillery;
	}// end findThreeArtillery

	private ArrayList<Card> findThreeCalvary() {
		ArrayList<Card> threeCalvary = new ArrayList<>();
		for (Card card : getCards()) {
			if (card.getUnit().compareTo("calvary") == 0) {
				threeCalvary.add(card);
			}

			if (threeCalvary.size() == 3) {
				break;
			}
		}
		return threeCalvary;
	}// end findThreeCalvary

	private ArrayList<Card> findThreeInfantry() {

		ArrayList<Card> threeInfantry = new ArrayList<>();
		for (Card card : getCards()) {
			if (card.getUnit().compareTo("infantry") == 0 || card.getUnit().compareTo("WILD") == 0) {
				threeInfantry.add(card);
			}

			if (threeInfantry.size() == 3) {
				break;
			}
		}
		return threeInfantry;
	}// end findThreeInfantry
	
	public AIStrategy getStrategy()
	{
		return strategy;
	}
	
	public void setStrategy(AIStrategy strat)
	{
		strategy = strat;
		strategy.setMe(this);
	}
	
}
