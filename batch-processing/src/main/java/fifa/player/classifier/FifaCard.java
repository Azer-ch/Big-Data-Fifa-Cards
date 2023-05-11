package fifa.player.classifier;

import org.apache.hadoop.io.Text;

import java.util.UUID;

public class FifaCard {
    public String playerID;

    public String name;
    public Integer rating;
    public Integer potential;
    public Integer age;
    public Long marketValue;

    public String tier;


    public FifaCard(String name, Integer rating, Integer potential, Integer age, Long marketValue) {
        this.playerID = UUID.randomUUID().toString();
        this.name = name;
        this.rating = rating;
        this.potential = potential;
        this.age = age;
        this.marketValue = marketValue;
    }

    public Text toText() {
        return new Text(name + "," + rating + "," + potential + "," + age + "," + marketValue + (tier != null ? "," + tier : ""));
    }

    public Text getPlayerID() {
        return new Text(playerID);
    }
    public void computeTier(){
        double score = computeScore();
        if (score > 70){
            tier = "S";
        } else if (score > 60){
            tier = "A";
        } else if (score > 50){
            tier = "B";
        } else {
            tier = "C";
        }
    }
    public double computeScore(){
        return 0.5 * rating + 0.3 * potential  + 0.2 * normalizeMarketValue();
    }
    public double normalizeMarketValue(){
        long maxValue = 190500000;
        long minValue = 0;
        return (double) (marketValue - minValue) / (maxValue - minValue);
    }
}
