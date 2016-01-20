/*
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.math3.distribution.NormalDistribution;

public class League {
    List<Division> divisions;
    
    public League(Division...divisions) {
        for(Division d : divisions) {
        	this.divisions.add(d);
        }
    }
    
    public List<Team> getTeams() {
    	ArrayList<Team> teams = new ArrayList<Team>();
    	for(Division d : divisions) {
    		teams.addAll(d.getTeams());
    	}
    	return teams;
    }
    
    public List<Team> getStandings() {
        //TODO returns a list of teams ordered by their seed.
    	return null;
    }
    
    public List<Team> getDivisionWinners() {
    	//TODO returns a list of division winners ordered by their seed
    	return null;
    }
    
    public List<Integer> getListOfWinTotals() {
    	//returns a list of win totals in descending order without any duplicates
    	TreeSet<Integer> wins = new TreeSet<Integer>();
    	for(Team t : getTeams()) {
    		wins.add(t.getWins());
    	}
    	ArrayList<Integer> winList = new ArrayList<Integer>();
    	winList.addAll(wins);
    	Collections.reverse(winList);
    	return winList;
    }
    
    public ArrayList<Integer> getListOfWinTotals(Collection<Team> teams) {
    	//returns a list of win totals in descending order without any duplicates for a specified collection of teams
    	TreeSet<Integer> wins = new TreeSet<Integer>();
    	for(Team t : teams) {
    		wins.add(t.getWins());
    	}
    	ArrayList<Integer> winList = new ArrayList<Integer>();
    	winList.addAll(wins);
    	Collections.reverse(winList);
    	return winList;
    }
    
    public List<Team> getTeamsWithWins(int wins) {
    	//returns a list of teams with the specified win total
    	ArrayList<Team> teamsWithWins = new ArrayList<Team>();
    	for(Team t : getTeams()) {
    		if(t.getWins() == wins) {
    			teamsWithWins.add(t);
    		}
    	}
    	return teamsWithWins;
    }
    
    public List<Team> getTeamsWithWins(int wins, Collection<Team> teams) {
    	//returns a list of teams with the specified win total for a specified collection of teams
    	ArrayList<Team> teamsWithWins = new ArrayList<Team>();
    	for(Team t : teams) {
    		if(t.getWins() == wins) {
    			teamsWithWins.add(t);
    		}
    	}
    	return teamsWithWins;
    }
    
    public Team getTeamWithPF(double pf) {
    	//returns the team with a given point total
    	for(Team t : getTeams()) {
    		if(Math.abs(t.getPointsFor() - pf) < 0.001) {
    			return t;
    		}
    	}
    	return null;
    }
    
    public Team breakTie(List<Team> tiedTeams) {
    	//returns the team that wins the tie breaker among a given list of teams
    	//currently uses head to head and then PF as the tie breakers
    	//needs to be able to use different tie break criteria
    	if(tiedTeams.size() == 1) {
    		return tiedTeams.get(0);
    	}
    	ArrayList<Integer> headToHeadGames = new ArrayList<Integer>();
    	ArrayList<Integer> headToHeadWins = new ArrayList<Integer>();
    	ArrayList<Double> pf = new ArrayList<Double>();
    	for(Team t : tiedTeams) {
    		ArrayList<Team> otherTiedTeams = new ArrayList<Team>();
    		otherTiedTeams.addAll(tiedTeams);
    		otherTiedTeams.remove(t);
    		int gamesVsOtherTeams = 0;
    		int winsVsOtherTeams = 0;
    		for(Team other : otherTiedTeams) {
    			gamesVsOtherTeams += t.getGamesPlayedVsOpponent(other);
    			winsVsOtherTeams += t.getWinsVsOpponent(other);
    		}
    		headToHeadGames.add(gamesVsOtherTeams);
    		headToHeadWins.add(winsVsOtherTeams);
    		pf.add(t.getPointsFor());
    	}
    	int count = 1;
    	for(int i=1; i<headToHeadGames.size(); i++) {
    		if(headToHeadGames.get(0) == headToHeadGames.get(i)) {
    			count++;
    		}
    	}
    	if(count == headToHeadGames.size()) {
    		//return team with most head to head wins
    		ArrayList<Team> teamsWithMaxHeadToHeadWins = new ArrayList<Team>();
    		for(Integer i : getIndicesOfValue(Collections.max(headToHeadWins), headToHeadWins)) {
    			teamsWithMaxHeadToHeadWins.add(tiedTeams.get(i));
    		}
    		if(teamsWithMaxHeadToHeadWins.size() == 1) {
    			return teamsWithMaxHeadToHeadWins.get(0);
    		} else {
    			//return team with most pf
    			return getTeamWithPF(Collections.max(pf));
    		}
    	} else {
    		//return team with most pf
    		return getTeamWithPF(Collections.max(pf));
    	}
    }
    
    public int maxValue(ArrayList<Integer> list) {
    	//returns the maximum value of a list
    	Collections.max(list);
    	int max = list.get(0);
    	return max;
	}

	public double maxValue(List<Double> list) {
    	//returns the maximum value of a list
    	Collections.sort(list);
    	double max = list.get(0);
    	return max;
    }
    
    public <T> List<Integer> getIndicesOfValue(T obj, List<T> list) {
    	//gets the indices of every instance of an integer in a list of Integers
    	ArrayList<Integer> indices = new ArrayList<Integer>();
    	for(int i=0; i<list.size(); i++) {
    		if(obj.equals(list.get(i))) {
    			indices.add(i);
    		}
    	}
    	return indices;
    }
    
    public void simulateToPlayoffs() {
    	//TODO
    	//simulates a league to the playoffs by simulated all incomplete matchups
    }
    
    public double getWinOdds(Team a, Team b) {
        double mean = a.getAvgPts() - b.getAvgPts();
        double stdDev = Math.sqrt(Math.pow(a.getStdDev(),2) + Math.pow(b.getStdDev(),2));
        NormalDistribution dist = new NormalDistribution(mean, stdDev);
        return 1.0 - dist.cumulativeProbability(0.0);
    }
    
    public Team getTeamByName(String name) {
        for(Team t : getTeams()) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }
    
    public String toString() {
        String str = "";
        for(Team t : getStandings()) {
            str += t.toString();
        }
        str += "\n";
        return str;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getStandings() == null) ? 0 : getStandings().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		League other = (League) obj;
		if (getStandings() == null) {
			if (other.getStandings() != null)
				return false;
		} else if (!getStandings().equals(other.getStandings()))
			return false;
		return true;
	}
}
*/