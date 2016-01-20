import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Division implements Comparable<Division> {
	Set<Team> teams = new HashSet<Team>();
	String name;
	
	public Division(String name, Collection<Team> teams) {
		this.name = name;
		this.teams.addAll(teams);
	}
	
	public Division(String name) {
		this.name = name;
	}
	
	public boolean add(Team t) {
		return teams.add(t);
	}

	public Set<Team> getTeams() {
		return teams;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		String str = "";
		str += name + "\n";
		for(Team t : teams) {
			str += t.toString() + "\n";
		}
		return str;
	}

	@Override
	public int compareTo(Division d) {
		return name.compareTo(d.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teams == null) ? 0 : teams.hashCode());
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
		if (!(obj instanceof Division)) {
			return false;
		}
		Division other = (Division) obj;
		if (teams == null) {
			if (other.teams != null) {
				return false;
			}
		} else if (!teams.equals(other.teams)) {
			return false;
		}
		return true;
	}
}
