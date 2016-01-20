public class Matchup {
	Team home;
    Team away;
    double pointsForHome;
    double pointsForAway;
    boolean isComplete;
    boolean isSimulated;
    
    public Matchup(Team home, Team away, double pointsForHome, double pointsForAway) {
        this.home = home;
        this.pointsForHome = pointsForHome;
        this.away = away;
        this.pointsForAway = pointsForAway;
        isComplete = true;
    }
    
    public Matchup(Team home, Team away) {
        this.home = home;
        this.away = away;
        isComplete = false;
    }
    
    public Team getHome() {
		return home;
	}

	public Team getAway() {
		return away;
	}

	public double getPointsForHome() {
		return pointsForHome;
	}

	public double getPointsForAway() {
		return pointsForAway;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public boolean isSimulated() {
		return isSimulated;
	}

	public Team getWinner() {
    	if(pointsForHome > pointsForAway) {
    		return home;
    	} else if (pointsForAway > pointsForHome) {
    		return away;
    	} else {
    		return null;
    	}
    }
	
	public Team getLoser() {
    	if(pointsForHome > pointsForAway) {
    		return away;
    	} else if (pointsForAway > pointsForHome) {
    		return home;
    	} else {
    		return null;
    	}
    }
    
    public double getPointsFor(Team t) {
        if(t.equals(home)) {
        	return pointsForHome;
        } else if(t.equals(away)) {
        	return pointsForAway;
        } else {
        	return 0;
        }
    }
    
    public double getPointsAllowed(Team t) {
        if(t.equals(home)) {
        	return pointsForAway;
        } else if(t.equals(away)) {
        	return pointsForHome;
        } else {
        	return 0;
        }
    }
    
    public void simulate(double odds) {
       //TODO simulates this matchup based on the odds of either team winning. 
       //May or may not simulate the number of a points a team score
    }
    
    public String toString() {
        return home.getName() + " vs " + away.getName() + pointsForHome + "-" + pointsForAway;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((away == null) ? 0 : away.hashCode());
		result = prime * result + ((home == null) ? 0 : home.hashCode());
		result = prime * result + (isComplete ? 1231 : 1237);
		result = prime * result + (isSimulated ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(pointsForAway);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pointsForHome);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (!(obj instanceof Matchup)) {
			return false;
		}
		Matchup other = (Matchup) obj;
		if (away == null) {
			if (other.away != null) {
				return false;
			}
		} else if (!away.equals(other.away)) {
			return false;
		}
		if (home == null) {
			if (other.home != null) {
				return false;
			}
		} else if (!home.equals(other.home)) {
			return false;
		}
		if (isComplete != other.isComplete) {
			return false;
		}
		if (isSimulated != other.isSimulated) {
			return false;
		}
		if (Double.doubleToLongBits(pointsForAway) != Double.doubleToLongBits(other.pointsForAway)) {
			return false;
		}
		if (Double.doubleToLongBits(pointsForHome) != Double.doubleToLongBits(other.pointsForHome)) {
			return false;
		}
		return true;
	}

}