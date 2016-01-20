import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class League {
	Set<Division> divisions;
	List<Week> schedule;
	String name;
	
	public League(String name) {
		this.name = name;
		this.divisions = new TreeSet<Division>();
		this.schedule = new ArrayList<Week>();
	}
	
	public League(String name, Collection<Division> divisions, List<Week> schedule) {
		this.divisions.addAll(divisions);
		this.schedule.addAll(schedule);
		this.name = name;
		this.updateTeamSchedules();
	}

	public Set<Division> getDivisions() {
		return divisions;
	}
	
	public boolean addDivision(Division d) {
		return divisions.add(d);
	}

	public List<Week> getSchedule() {
		return schedule;
	}
	
	public void setSchedule(List<Week> schedule) {
		this.schedule = schedule;
		this.updateTeamSchedules();
	}

	public String getName() {
		return name;
	}
	
	public List<Team> getTeams() {
    	ArrayList<Team> teams = new ArrayList<Team>();
    	for(Division d : divisions) {
    		teams.addAll(d.getTeams());
    	}
    	return teams;
    }
	
	public Team getTeamByName(String name) {
        for(Team t : getTeams()) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }
	
	public void updateTeamSchedules() {
		for(Team t : getTeams()) {
			ArrayList<Matchup> teamSchedule = new ArrayList<Matchup>();
			for(Week w : schedule) {
				teamSchedule.add(w.getMatchupOfTeam(t));
			}
			t.setSchedule(teamSchedule);
		}
	}
	
	public List<Team> getStandings(Collection<Team> teams) {
        //returns a list of a specified collections of teams ordered by their seed.
		List<Team> standings = new ArrayList<Team>();
		standings.addAll(teams);
		Collections.sort(standings);
    	return standings;
    }

	@Override
	public String toString() {
		String str = "";
		str += getName() + "\n";
		for(Division d : divisions) {
			str += d.toString() + "\n";
		}
		return str;
	}
	
}
