import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Simulator {
	League league;
	ArrayList<League> sims;
	int numSims;
	
	public Simulator(int numSims) {
		try {
			reloadLeague();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.numSims = numSims;
		sims = simulate();
	}
	
	public ArrayList<League> simulate() {
		ArrayList<League> sims = new ArrayList<League>();
		for(int i=0; i<numSims; i++) {
			League simmedLeague = league;
			simmedLeague.simulateToPlayoffs();
			sims.add(simmedLeague);
			try {
				reloadLeague();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sims;
	}
	
	public ArrayList<League> getScenarios() {
		ArrayList<League> scenarios = new ArrayList<League>();
		scenarios = simulate();
		HashSet<League> scenarioSet = new HashSet<League>();
		for(League l : scenarios) {
			scenarioSet.add(l);
		}
		return scenarios;
	}
	
	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public double[] getOddsForSeed(Team t) {
		double[] seedCount = new double[10];
		for(League l : sims) {
			seedCount[l.getStandings().indexOf(t)]++;
		}
		double[] seedOdds = new double[10];
		for(int i=0; i<seedCount.length; i++) {
			seedOdds[i] = seedCount[i]/numSims;
		}
		return seedOdds;
	}
	
	public String toString() {
		String str = String.format("%35s", "Seed: ");
		for(int i=1; i<=10; i++) {
			str += String.format("%-8d",i);
		}
		str += "\n";
		for(Team t : league.getTeams()) {
			str += String.format("%35s", t.toString());
			double[] odds = getOddsForSeed(t);
			for(int i = 0; i < odds.length; i++) {
				str += String.format("%-7.4f ", odds[i]);
			}
			str += "\n";
		}
		return str;
	}
	
	public String toFile() {
		String str = "Team,Record,PF,PA,1,2,3,4,5,6,7,8,9,10\n";
		for(Team t : league.getTeams()) {
			str += t.toFile();
			double[] odds = getOddsForSeed(t);
			for(int i = 0; i < odds.length; i++) {
				str += String.format("%.4f,", odds[i]);
			}
			str += "\n";
		}
		return str;
	}
	
	public void reloadLeague() throws FileNotFoundException {
		Scanner file = new Scanner(new File("teamMatchups.csv"));
        ArrayList<Team> allTeams = new ArrayList<Team>();
        while(file.hasNextLine()) {
            String line = file.nextLine();
            String[] teamData = line.split(",");
            String teamName = teamData[0];
            ArrayList<Matchup> schedule = new ArrayList<Matchup>();
            for(int i = 1; i + 2 < teamData.length; i = i + 3) {
                String opponent = teamData[i];
                int pf = 0;
                int pa = 0;
                try {
                    pf = Integer.parseInt(teamData[i+1]);
                    pa = Integer.parseInt(teamData[i+2]);
                } catch (Exception e) {}
                if(pf == 0 && pa == 0) {
                    schedule.add(new Matchup(opponent));
                } else {
                    schedule.add(new Matchup(opponent, pf, pa));
                }
            }
            allTeams.add(new Team(teamName, schedule));
        }
        ArrayList<Team> div1 = new ArrayList<Team>(5);
        ArrayList<Team> div2 = new ArrayList<Team>(5);
        for(int i=0; i<5; i++) {
            div1.add(allTeams.get(i));
        }
        for(int i=5; i<10; i++) {
            div2.add(allTeams.get(i));
        }
        this.league = new League(div1, div2);
        file.close();
	}
}
