package spazioricerca;

public abstract class NewHeuristic {

    protected float PesoBalancedHeuristic;
    protected float PesoControlCenterHeuristic;
    protected float PesoEnemyNmHeuristic;
    protected float PesoDistanceHeuristic;
    protected float PesoDistanceEnemyHeuristic;
    protected float PesoVictoryHeuristic;
    protected float PesoDefeatHeuristic;
    public NewHeuristic(float PesoBalancedHeuristic,float PesoControlCenterHeuristic,float PesoEnemyNmHeuristic
            ,float PesoDistanceHeuristic,float PesoDistanceEnemyHeuristic,float PesoVictoryHeuristic,float PesoDefeatHeuristic){
        this.PesoBalancedHeuristic = PesoBalancedHeuristic;
        this.PesoControlCenterHeuristic = PesoControlCenterHeuristic;
        this.PesoEnemyNmHeuristic = PesoEnemyNmHeuristic;
        this.PesoDistanceHeuristic = PesoDistanceHeuristic;
        this.PesoDistanceEnemyHeuristic = PesoDistanceEnemyHeuristic;
        this.PesoVictoryHeuristic=PesoVictoryHeuristic;
        this.PesoDefeatHeuristic=PesoDefeatHeuristic;
    }
    public abstract float calcola(Nodo n, int colore);
    public float getPesoBalancedHeuristic() {return PesoBalancedHeuristic;}
    public float getPesoControlCenterHeuristic() {return PesoControlCenterHeuristic;}
    public float getPesoDistanceHeuristic() {return PesoDistanceHeuristic;}
    public float getPesoDistanceEnemyHeuristic() {return PesoDistanceEnemyHeuristic;}
    public float getPesoEnemyNmHeuristic() {return PesoEnemyNmHeuristic;}
    public float getPesoVictoryHeuristic() {return PesoVictoryHeuristic;}
    public float getPesoDefeatHeuristic() {return PesoDefeatHeuristic;}
}
