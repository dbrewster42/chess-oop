
####   public PieceFactory(Piece piece) {
       this.team = piece.getTeam();
       this.x = x;
       this.y = y;
       this.type = type;
   }
### Piece.java
    public Piece(Team team, int x, Type type) {
        this.team = team;
        this.type = type;
        this.spot = new Point(x, selectY());
    }

    private int selectY() {
        int y = 1;
        if (type.equals(PAWN)) {
            y = 2;
        }
        if (team.equals(BLACK)) {
            y = 9 - y;
        }
        return y;
    }



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
            if (!isTeammate()){ moves.add(new Point(x, y));}

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


##### player.java
    public Piece getKing(){
        //todo remove check if unnecessary
        Piece piece = pieces.get(0);
        if (piece instanceof King){
            return pieces.get(0);
        }
        throw new PieceNotFound();
    }


##### gameService.java
not needed because irrelevant if already in check
//        if (game.isCheck()){
////todo            if (checkService.didDefeatCheck(game))
//        }


#### checkService.java
            if (friend.calculateLegalMoves(dto.getSpots(), dto.getFoes()).contains(kingsMove)){
    public boolean isInCheckAfterMove(GamePiecesDto dto) {
        Point kingsLocation = dto.getFriends().get(0).getSpot();
        for (Piece foe : dto.getFoes()){
            if (foe.isLegalAttack(kingsLocation, dto.getSpots())){
                return true;
            }
        }
        return false;
    }

    public boolean didCheck(GamePiecesDto dto) {
        Point kingsLocation = dto.getFoes().get(0).getSpot();
        for (Piece friend : dto.getFriends()){
            if (friend.isLegalAttack(kingsLocation, dto.getSpots())){
                attacker = friend;
                return true;
            }
        }
        return false;
    }
    private boolean isSpotDefended(GamePiecesDto dto, Point spot){
        for (Piece friend : dto.getFriends()) {
            if (friend.isLegalAttack(spot, dto.getSpots())) {
                return true;
            }
        }
        return false;

//        if (attacker instanceof King || attacker instanceof Pawn || attacker instanceof Knight){
//            return false;
//        }
private long findAllAttackers(GamePiecesDto dto) {
Point kingsLocation = dto.getFoes().get(0).getSpot();
return dto.getFriends().stream()
.filter(friend -> friend.isLegalAttack(kingsLocation, dto.getSpots()))
.count();
}