import java.util.List;

public class Team implements Comparable<Team> {
    String name;
    List<Matchup> schedule;
    
    public Team(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setSchedule(List<Matchup> schedule) {
    	this.schedule = schedule;
    }
    
    public int getWins() {
        //TODO returns the number of wins this team currently has
    	int wins = 0;
    	for(Matchup m : schedule) {
    		if(m.getWinner().equals(this)) {
    			wins++;
    		}
    	}
    	return wins;
    }
    
    public double getPointsFor() {
        //TODO returns the total points this team has scored to date
    	double pf = 0.0;
    	for(Matchup m : schedule) {
    		pf += m.getPointsFor(this);
    	}
    	return pf;
    }
    
    public double getPointsAllowed() {
        //TODO returns the total points this team's opponents have scored to date
    	double pa = 0.0;
    	for(Matchup m : schedule) {
    		pa += m.getPointsAllowed(this);
    	}
    	return pa;
    }
    
    public int getGamesPlayed() {
        //TODO returns the number of games this team has played to date
    	return schedule.size();
    }
    
    public int getWinsVsOpponent(Team opponent) {
    	//TODO returns the number of wins a team has vs a specific opponent
    	int wins = 0;
    	for(Matchup m : schedule) {
    		if(m.getWinner().equals(this) && m.getLoser().equals(opponent)) {
    			wins++;
    		}
    	}
    	return wins;
    }
    
    public int getGamesPlayedVsOpponent(Team opponent) {
    	//TODO returns the number of games a team has played vs a specific opponent
    	int count = 0;
    	for(Matchup m : schedule) {
    		if((m.getHome().equals(this) || m.getAway().equals(this)) && (m.getHome().equals(opponent) || m.getAway().equals(opponent))) {
    			count++;
    		}
    	}
    	return count;
    }
    
    public int getLossesVsOpponent(Team opponent) {
    	return getGamesPlayedVsOpponent(opponent) - getWinsVsOpponent(opponent);
    }
    
    public int getLosses() {
        return getGamesPlayed() - getWins();
    }
    
    public double getAvgPts() {
        return getPointsFor() / getGamesPlayed();
    }
    
    public double getStdDev() {
    	//TODO returns the standard deviation of points scored by this team to date
        int sum = 0;
        for(Matchup m : schedule) {
            sum += Math.pow((m.getPointsFor(this) - getAvgPts()),2);
        }
        return Math.sqrt(sum);
    }
    
    public String toString() {
        return name + " (" + getWins() + "-" + getLosses() + ") PF: " + getPointsFor() + " PA: " + getPointsAllowed() + " ";
    }
    
    public String toFile() {
        return name + ",(" + getWins() + "-" + getLosses() + ")," + getPointsFor() + "," + getPointsAllowed() + ",";
    }
    
    public int compareTo(Team o) {
    	//Teams are naturally ordered by their win total. Ties are broken by PF. Use more complicated comparators for other tiebreak methods.
    	if(o.getWins() - getWins() != 0) {
    		return o.getWins() - getWins();
    	} else {
    		return (int) (Math.round(o.getPointsFor()) - getPointsFor());
    	}
    }

	@Override
	public int hashCode() {
		final int prime = 23;
		int result = 7;
		result = prime * result + name.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Team)) {
			return false;
		}
		Team other = (Team) obj;
		return name.equals(other.getName());
	}
}