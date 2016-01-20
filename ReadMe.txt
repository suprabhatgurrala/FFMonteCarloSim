Issues:
-Multiple teams can win the same game in a simulation -> can be fixed by adding a Week class that makes sure PF and PA are corresponding
-Normal Distribution generation is inefficient -> look into splitting into multiple threads/caching the distribution
-Requires manual input -> scraper using BeautifulSoup and Python can output a .csv to read
-GUI could be useful
-add a playoff simulation
-use Elo ratings to calculate win odds

Hierarchy:
League has a number of Division object, and a Schedule object
Division has a number of Team objects
Schedule has a number of Week objects
Week has a number of Matchup objects
Matchup has 2 Team objects
