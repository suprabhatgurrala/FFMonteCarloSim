import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException{
        Scanner s = new Scanner(new File("divisions.csv"));
        League l = new League(s.nextLine().split(",")[0]);
        while(s.hasNextLine()) {
        	String line = s.nextLine();
        	String[] lineArray = line.split(",");
        	Division d = new Division(lineArray[0]);
        	for(int i = 1; i<lineArray.length; i++) {
        		d.add(new Team(lineArray[i]));
        	}
        	l.addDivision(d);
        }
        s.close();
        ArrayList<Week> schedule = new ArrayList<Week>();
        Scanner s2 = new Scanner(new File("schedule.csv"));
        while(s2.hasNextLine()) {
        	Week w = new Week();
        	String line = s2.nextLine();
        	String[] lineArray = line.split(",");
        	for(int i=0; i + 3 < lineArray.length; i += 4) {
        		Team home = l.getTeamByName(lineArray[i]);
        		Team away = l.getTeamByName(lineArray[i + 1]);
        		double homePF = Double.parseDouble(lineArray[i+2]);
        		double awayPF = Double.parseDouble(lineArray[i+3]);
        		w.add(new Matchup(home, away, homePF, awayPF));
        	}
        	schedule.add(w);
        }
        l.setSchedule(schedule);
        s2.close();
        System.out.println(l.getStandings(l.getTeams()).toString());
    }
}