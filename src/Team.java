import java.util.ArrayList;

public class Team implements Comparable<Team> {
    String name;
    ArrayList<Matchup> schedule;
    
    public Team(String name, ArrayList<Matchup> schedule) {
        this.name = name;
        this.schedule = schedule;
    }
    
    public Team(String name) {
        this.name = name;
        schedule = new ArrayList<Matchup>();
    }
    
    public ArrayList<Matchup> getSchedule() {
        return schedule;
    }
    
    public String getName() {
        return name;
    }
    
    public int getWins() {
        int wins = 0;
        for(Matchup m : schedule) {
            if(m.isWin()) {
                wins++;
            }
        }
        return wins;
    }
    
    public int getPointsFor() {
        int pointsFor = 0;
        for(Matchup m : schedule) {
            pointsFor += m.getPointsFor();
        }
        return pointsFor;
    }
    
    public int getPointsAllowed() {
        int pointsAllowed = 0;
        for(Matchup m : schedule) {
            pointsAllowed += m.getPointsAllowed();
        }
        return pointsAllowed;
    }
    
    public int getGamesPlayed() {
        int gms = 0;
        for(Matchup m : schedule) {
            if(m.completed()) {
                gms++;
            }
        }
        return gms;
    }
    
    public int getWinsVsOpponent(String opponentName) {
    	int count = 0;
    	for(Matchup m : schedule) {
    		if(m.getOpponent().equals(opponentName) && m.isWin()) {
    			count++;
    		}
    	}
    	return count;
    }
    
    public int getGamesPlayedVsOpponent(String opponentName) {
    	int count = 0;
    	for(Matchup m : schedule) {
    		if(m.getOpponent().equals(opponentName)) {
    			count++;
    		}
    	}
    	return count;
    }
    
    public int getLossesVsOpponent(String opponentName) {
    	return getGamesPlayedVsOpponent(opponentName) - getWinsVsOpponent(opponentName);
    }
    
    public int getLosses() {
        return getGamesPlayed() - getWins();
    }
    
    public double getAvgPts() {
        return 1.0 * getPointsFor() / getGamesPlayed();
    }
    
    public double getStdDev() {
        int sum = 0;
        for(int i=0; i<getGamesPlayed(); i++) {
            sum += Math.pow((schedule.get(i).getPointsFor() - getAvgPts()),2);
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
    	return getWins() - o.getWins();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Team other = (Team) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
    
}