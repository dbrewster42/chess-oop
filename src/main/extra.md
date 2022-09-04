
####   public PieceFactory(Piece piece) {
       this.team = piece.getTeam();
       this.x = x;
       this.y = y;
       this.type = type;
   }
###



Pawn.java
public Piece createPawn(Team team, int x){
int y;
if (team.equals(WHITE)){
y = 2;
this.direction = 1;
} else {
y = 7;
this.direction = -1;
}
return new Pawn(team, x, y);
}
//        if (isOccupied(x - 1, y, spots) && isOpponent(foes, x - 1, y)){
//            legalMoves.add(new Point(x - 1, y));
//        }
//        if (isOccupied(x + 1, y, spots)){
//            legalMoves.add(new Point(x + 1, y));
//        }



### Knight.java
int x = 2 * xDirection + spot.x;
int y = yDirection + spot.x;
if (isOnBoard(x, y)){
if (!(isOccupied(x, y, spots) && !isOpponent(foes, x, y))){
moves.add(new Point(x, y));
}
}

#### for (int i = -1; i <= 1; i++){
for (int j = -1; j <= 1; j++){
if (i == 0 || j == 0){
continue;
}
makeJump(moves, allSpots, foes, i, j);
}
}
####




#### sockets
    public NewGameResponse startGame(User user) {
        //todo sockets find 2nd player
        Game newGame = repository.save(new Game(user1, user2));

        return new NewGameResponse(1, null, null);
    }
##