package spark.consumer;

public class PlayerStats {
    String playerName;
    int playerAge;
    int gamesPlayed;
    int goals;
    int assists;
    double goalsPerGame;
    double assistsPerGame;

    public PlayerStats(String playerName, int playerAge, int gamesPlayed, int goals, int assists, double goalsPerGame, double assistsPerGame) {
        this.playerName = playerName;
        this.playerAge = playerAge;
        this.gamesPlayed = gamesPlayed;
        this.goals = goals;
        this.assists = assists;
        this.goalsPerGame = goalsPerGame;
        this.assistsPerGame = assistsPerGame;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public void setPlayerAge(int playerAge) {
        this.playerAge = playerAge;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public double getGoalsPerGame() {
        return goalsPerGame;
    }

    public void setGoalsPerGame(double goalsPerGame) {
        this.goalsPerGame = goalsPerGame;
    }

    public double getAssistsPerGame() {
        return assistsPerGame;
    }

    public void setAssistsPerGame(double assistsPerGame) {
        this.assistsPerGame = assistsPerGame;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "playerName='" + playerName + '\'' +
                ", playerAge=" + playerAge +
                ", gamesPlayed=" + gamesPlayed +
                ", goals=" + goals +
                ", assists=" + assists +
                ", goalsPerGame=" + goalsPerGame +
                ", assistsPerGame=" + assistsPerGame +
                '}';
    }
}
