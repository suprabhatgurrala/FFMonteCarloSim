import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

import org.apache.commons.math3.distribution.NormalDistribution;

public class League {
    ArrayList<Team> div1 = new ArrayList<Team>();
    ArrayList<Team> div2 = new ArrayList<Team>();
    ArrayList<Team> teams = new ArrayList<Team>();
    
    public League(ArrayList<Team> div1, ArrayList<Team> div2) {
        this.div1.addAll(div1);
        this.div2.addAll(div2);
        this.teams.addAll(div1);
        this.teams.addAll(div2);
    }
    
    public int getCurrentWeek() {
        return div1.get(0).getGamesPlayed();
    }
    
    public ArrayList<Team> getTeams() {
    	return teams;
    }
    
    public ArrayList<Team> getDiv1() {
    	return div1;
    }
    
    public ArrayList<Team> getDiv2() {
    	return div2;
    }
    
    public ArrayList<Team> getStandings() {
        ArrayList<Team> standings = new ArrayList<Team>();
        //todo
        ArrayList<Team> divWinners = this.getDivisionWinners();
        standings.addAll(divWinners);
        ArrayList<Team> wildcards = new ArrayList<Team>();
        wildcards.addAll(teams);
        wildcards.removeAll(divWinners);
        while(standings.size() < 10) {
        	for(Integer num : getListOfWinTotals()) {
        		ArrayList<Team> candidates = getTeamsWithWins(num, wildcards);
            	if(candidates.size()==1) {
            		standings.add(candidates.get(0));
            	} else {
            		while(candidates.size() > 0) {
            			Team best = breakTie(candidates);
            			standings.add(best);
            			candidates.remove(best);
            		}
            	}
        	}
        }
    	return standings;
    }
    
    public ArrayList<Team> getDivisionWinners() {
    	ArrayList<Team> divWinners = new ArrayList<Team>();
    	Team div1Winner = breakTie(getTeamsWithWins(getListOfWinTotals(div1).get(0), div1));
    	Team div2Winner = breakTie(getTeamsWithWins(getListOfWinTotals(div2).get(0), div2));
    	if(div1Winner.getWins() > div2Winner.getWins()) {
    		divWinners.add(div1Winner);
    		divWinners.add(div2Winner);
    	} else if (div2Winner.getWins() > div1Winner.getWins()) {
    		divWinners.add(div2Winner);
    		divWinners.add(div1Winner);
    	} else {
    		divWinners.add(breakTie((ArrayList<Team>) Arrays.asList(div1Winner,div2Winner)));
    		if(divWinners.contains(div1Winner)) {
    			divWinners.add(div2Winner);
    		} else {
    			divWinners.add(div1Winner);
    		}
    	}
    	return divWinners;
    }
    
    public ArrayList<Integer> getListOfWinTotals() {
    	TreeSet<Integer> wins = new TreeSet<Integer>();
    	for(Team t : teams) {
    		wins.add(t.getWins());
    	}
    	ArrayList<Integer> winList = new ArrayList<Integer>();
    	winList.addAll(wins);
    	Collections.reverse(winList);
    	return winList;
    }
    
    public ArrayList<Integer> getListOfWinTotals(ArrayList<Team> list) {
    	TreeSet<Integer> wins = new TreeSet<Integer>();
    	for(Team t : list) {
    		wins.add(t.getWins());
    	}
    	ArrayList<Integer> winList = new ArrayList<Integer>();
    	winList.addAll(wins);
    	Collections.reverse(winList);
    	return winList;
    }
    
    public ArrayList<Team> getTeamsWithWins(int wins) {
    	ArrayList<Team> teamsWithWins = new ArrayList<Team>();
    	for(Team t : teams) {
    		if(t.getWins() == wins) {
    			teamsWithWins.add(t);
    		}
    	}
    	return teamsWithWins;
    }
    
    public Team getTeamWithPF(int pf) {
    	for(Team t : teams) {
    		if(t.getPointsFor() == pf) {
    			return t;
    		}
    	}
    	return null;
    }
    
    public ArrayList<Team> getTeamsWithWins(int wins, ArrayList<Team> list) {
    	ArrayList<Team> teamsWithWins = new ArrayList<Team>();
    	for(Team t : list) {
    		if(t.getWins() == wins) {
    			teamsWithWins.add(t);
    		}
    	}
    	return teamsWithWins;
    }
    
    public Team breakTie(ArrayList<Team> tiedTeams) {
    	if(tiedTeams.size() == 1) {
    		return tiedTeams.get(0);
    	}
    	ArrayList<Integer> headToHeadGames = new ArrayList<Integer>();
    	ArrayList<Integer> headToHeadWins = new ArrayList<Integer>();
    	ArrayList<Integer> pf = new ArrayList<Integer>();
    	for(Team t : tiedTeams) {
    		ArrayList<Team> otherTiedTeams = new ArrayList<Team>();
    		otherTiedTeams.addAll(tiedTeams);
    		otherTiedTeams.remove(t);
    		int gamesVsOtherTeams = 0;
    		int winsVsOtherTeams = 0;
    		for(Team other : otherTiedTeams) {
    			gamesVsOtherTeams += t.getGamesPlayedVsOpponent(other.getName());
    			winsVsOtherTeams += t.getWinsVsOpponent(other.getName());
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
    		for(Integer i : getIndicesOfValue(maxValue(headToHeadWins), headToHeadWins)) {
    			teamsWithMaxHeadToHeadWins.add(tiedTeams.get(i));
    		}
    		if(teamsWithMaxHeadToHeadWins.size() == 1) {
    			return teamsWithMaxHeadToHeadWins.get(0);
    		} else {
    			//return team with most pf
    			return getTeamWithPF(maxValue(pf));
    		}
    	} else {
    		//return team with most pf
    		return getTeamWithPF(maxValue(pf));
    	}
    }
    
    public int maxValue(ArrayList<Integer> nums) {
    	int max = nums.get(0);
    	for(int i=1; i<nums.size(); i++) {
    		if(nums.get(i) > max) {
    			max = nums.get(i);
    		}
    	}
    	return max;
    }
    
    public ArrayList<Integer> getIndicesOfValue(int num, ArrayList<Integer> list) {
    	ArrayList<Integer> indices = new ArrayList<Integer>();
    	for(int i=0; i<list.size(); i++) {
    		if(num == list.get(i)) {
    			indices.add(i);
    		}
    	}
    	return indices;
    }
    
    public void simulateToPlayoffs() {
        for(Team t : teams) {
            for(Matchup m : t.getSchedule()) {
                if(!m.completed()) {
                    m.simulate(getWinOdds(t, getTeamByName(m.getOpponent())));
                }
            }
        }
    }
    
    public double getWinOdds(Team a, Team b) {
        double mean = a.getAvgPts() - b.getAvgPts();
        double stdDev = Math.sqrt(Math.pow(a.getStdDev(),2) + Math.pow(b.getStdDev(),2));
        NormalDistribution dist = new NormalDistribution(mean, stdDev);
        return 1.0 - dist.cumulativeProbability(0.0);
    }
    
    public Team getTeamByName(String name) {
        for(Team t : teams) {
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