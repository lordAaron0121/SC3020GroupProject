public class GameRecord {
    private String gameDateEst;
    private int teamIdHome;
    private int ptsHome;
    private double fgPctHome;
    private double ftPctHome;
    private double fg3PctHome;
    private int astHome;
    private int rebHome;
    private int homeTeamWins;

    public GameRecord(String gameDateEst, int teamIdHome, int ptsHome, double fgPctHome, double ftPctHome, double fg3PctHome, int astHome, int rebHome, int homeTeamWins) {
        this.gameDateEst = gameDateEst;
        this.teamIdHome = teamIdHome;
        this.ptsHome = ptsHome;
        this.fgPctHome = fgPctHome;
        this.ftPctHome = ftPctHome;
        this.fg3PctHome = fg3PctHome;
        this.astHome = astHome;
        this.rebHome = rebHome;
        this.homeTeamWins = homeTeamWins;
    }

    // Getter for fgPctHome
    public double getFgPctHome() {
        return fgPctHome;
    }

    // Other getters
    public String getGameDateEst() {
        return gameDateEst;
    }

    public int getTeamIdHome() {
        return teamIdHome;
    }

    public int getPtsHome() {
        return ptsHome;
    }

    public double getFtPctHome() {
        return ftPctHome;
    }

    public double getFg3PctHome() {
        return fg3PctHome;
    }

    public int getAstHome() {
        return astHome;
    }

    public int getRebHome() {
        return rebHome;
    }

    public int getHomeTeamWins() {
        return homeTeamWins;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "gameDateEst='" + gameDateEst + '\'' +
                ", teamIdHome=" + teamIdHome +
                ", ptsHome=" + ptsHome +
                ", fgPctHome=" + fgPctHome +
                ", ftPctHome=" + ftPctHome +
                ", fg3PctHome=" + fg3PctHome +
                ", astHome=" + astHome +
                ", rebHome=" + rebHome +
                ", homeTeamWins=" + homeTeamWins +
                '}';
    }
}
