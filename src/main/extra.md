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



//    public PieceFactory(Piece piece) {
//        this.team = piece.getTeam();
//        this.x = x;
//        this.y = y;
//        this.type = type;
//    }





//        if (isOccupied(x - 1, y, spots) && isOpponent(foes, x - 1, y)){
//            legalMoves.add(new Point(x - 1, y));
//        }
//        if (isOccupied(x + 1, y, spots)){
//            legalMoves.add(new Point(x + 1, y));
//        }