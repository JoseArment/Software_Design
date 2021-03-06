import java.util.LinkedList;
import java.util.Random;

public final class Game {
  private LinkedList<Player> players = new LinkedList<Player>();
  private Board board;
  private Player winner;

  private final class Die {
    private static final int MINVALUE = 1;
    private static final int MAXVALUE = 6;

    public int roll () {
      return random ( MINVALUE , MAXVALUE );
      }

    private int random (int min , int max ) {
      assert min < max ;
      return ( int) ( min + Math . round (( max - min ) * Math . random ()));
      }
  }

  private void movePlayer (int roll ) {
    Player currentPlayer = players . remove (); // from the head
    currentPlayer . moveForward ( roll );
    if (!currentPlayer.getPlayerStatus()) {
      players.add(currentPlayer); // to the tail
      if (currentPlayer.wins()) {
        winner = currentPlayer;
      }
    } else {
      System.out.println(" player " + currentPlayer.getName() + " is dead");
    }
  }

  public Game ( String [] playerNames , int numSquares , int [][] snakes , int [][] ladders, int[] death) {
    makeBoard(numSquares, ladders, snakes, death);
    makePlayers(playerNames);
  }

  private void makeBoard (int numSquares , int [][] ladders , int [][] snakes, int[] death) {
    board = new Board ( numSquares , ladders , snakes, death);
    }

    private void makePlayers ( String [] playerNames ) {
    assert playerNames . length >0 : " There must be some player " ;
    System . out . println (" Players are : ");
    int i =1;
    for ( String str : playerNames ) {
      Player player = new Player ( str );
      players . add ( player ); // adds to the end
      System . out . println ( i + ". " + str );
      i ++;
      }
    }

    public void play () {
      assert ! players . isEmpty () : "No players to play ";
      assert board != null : "No scoreboard to play ";

      Die die = new Die ();
      startGame ();

      System . out . println ("Initial state : \n" + this );
      while (notOver()) {
        int roll = die.roll();
        System . out . println ("Current player is " + currentPlayer () + " and rolls " + roll );
        movePlayer(roll);
        System . out . println ("State : \n" + this );
      }
      if (allDead()) {
        System.out.println("All the players are dead");
      } else {
        System.out.println(winner + " has won.");
      }
    }

    private void startGame () {
      placePlayersAtFirstSquare ();
      winner = null ;
    }

    private void placePlayersAtFirstSquare () {
      for ( Player player : players ) {
        board . firstSquare (). enter ( player );
      }
    }

    private boolean notOver () {
      return (winner == null && !allDead()) ;
    }

    @Override
    public String toString () {
      String str = new String ();
      for ( Player player : players ) {
        str += player . getName () + " is at square " + ( player . position ()+1) + "\n";
      }
      return str ;
    }

    Player currentPlayer () {
     assert players . size () >0;
     return players . peek ();
    }

    boolean allDead() {
      return players.isEmpty();
    }
}
