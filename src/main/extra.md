
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
        this.square = new Spot(x, selectY());
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
//        if (isOccupied(x - 1, y, squares) && isOpponent(foes, x - 1, y)){
//            legalMoves.add(new Spot(x - 1, y));
//        }
//        if (isOccupied(x + 1, y, squares)){
//            legalMoves.add(new Spot(x + 1, y));
//        }



### Knight.java
            if (!isTeammate()){ moves.add(new Spot(x, y));}

int x = 2 * xDirection + square.x;
int y = yDirection + square.x;
if (isOnBoard(x, y)){
if (!(isOccupied(x, y, squares) && !isOpponent(foes, x, y))){
moves.add(new Spot(x, y));
}
}

#### for (int i = -1; i <= 1; i++){
for (int j = -1; j <= 1; j++){
if (i == 0 || j == 0){
continue;
}
makeJump(moves, allSquares, foes, i, j);
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
        Spot kingsLocation = dto.getFriends().get(0).getSpot();
        for (Piece foe : dto.getFoes()){
            if (foe.isLegalAttack(kingsLocation, dto.getSpots())){
                return true;
            }
        }
        return false;
    }

    public boolean didCheck(GamePiecesDto dto) {
        Spot kingsLocation = dto.getFoes().get(0).getSpot();
        for (Piece friend : dto.getFriends()){
            if (friend.isLegalAttack(kingsLocation, dto.getSpots())){
                attacker = friend;
                return true;
            }
        }
        return false;
    }
    private boolean isSpotDefended(GamePiecesDto dto, Spot square){
        for (Piece friend : dto.getFriends()) {
            if (friend.isLegalAttack(square, dto.getSpots())) {
                return true;
            }
        }
        return false;

//        if (attacker instanceof King || attacker instanceof Pawn || attacker instanceof Knight){
//            return false;
//        }
private long findAllAttackers(GamePiecesDto dto) {
Spot kingsLocation = dto.getFoes().get(0).getSpot();
return dto.getFriends().stream()
.filter(friend -> friend.isLegalAttack(kingsLocation, dto.getSpots()))
.count();
}



##### gameService with moves
//        removeFoeIfCaptured(game, request.getEnd());
//        final StringBuilder message = new StringBuilder(getCurrentPlayer(game).getName() + " has moved his " + piece.getType().name + " from " + request.getStart() + " to " + request.getEnd());
//        potentialFoe.ifPresent(value -> message.append(" and has captured a ").append(value.getType().name));
//        if (game.isCheck()) { message.append("and put his opponent in check!"); }
//        Move move = new Move(getCurrentPlayer(game).getName(), piece.getType().name, request, potentialFoe, game.isCheck());
removeFoeIfCaptured
//        Optional<Piece> possibleFoe = getFoesPieces(game).stream().filter(p -> p.isAtPosition(end)).findAny();
//        possibleFoe.ifPresent(foe -> getFoesPieces(game).remove(foe));
//        return possibleFoe.isPresent();


//    public GameResponse restart(Game game) {
//        game.setWhitesTurn(true);
//        game.setCheck(false);
//        game.setMoves("");
//        game.setActive(true);
//    }

////        List<String> moves = repository.findById(game.getId()).map(Game::getMoves).orElseThrow();
//        game.setMoves(updateMoveMessage(game, piece.getType().name, request, potentialFoe, game.isCheck()));
    private String updateMoveMessage(Game game, String pieceName, MoveRequest request, Optional<Piece> potentialFoe, boolean isCheck){
        int turn = game.getMoves().split("\\.").length;
        StringBuilder message = new StringBuilder(game.getMoves() + turn + ". " + getCurrentPlayer(game).getName());
        message.append(" has moved his ").append(pieceName).append(" from ").append(request.getStart()).append(" to ").append(request.getEnd());
        potentialFoe.ifPresent(foe -> message.append(" and has captured a ").append(foe.getType()));
        if (isCheck) { message.append(" - CHECK!"); }
        return message.append("\n").toString();
    }