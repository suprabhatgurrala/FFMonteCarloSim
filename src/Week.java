import java.util.HashSet;
import java.util.Set;

public class Week {
	Set<Matchup> matchups;
	Set<Team> teams;
	
	public Week() {
		this.matchups = new HashSet<Matchup>();
		this.teams = new HashSet<Team>();
	}
	
	public boolean add(Matchup m) {
		if (teams.contains(m.getHome()) || teams.contains(m.getAway())) {
			return false;
		} else {
			return this.matchups.add(m);
		}
	}
	
	public Matchup getMatchupOfTeam(Team t) {
		for(Matchup m : matchups) {
			if(m.getHome().equals(t) || m.getAway().equals(t)) {
				return m;
			}
		}
	    return null;
	}
}
