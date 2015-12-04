import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException{
        Simulator sim = new Simulator(10000);
        System.out.println(sim.toString());
        //System.out.println(sim.getScenarios().toString());
        PrintStream outFile = new PrintStream(new File("playoffOdds.csv"));
        outFile.print(sim.toFile());
    }
}