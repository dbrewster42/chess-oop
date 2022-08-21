package brewster.chess.api;


import java.awt.*;
import java.util.List;
import java.util.Optional;

import brewster.chess.exception.GameNotFound;
import brewster.chess.model.Game;
import brewster.chess.repository.GameRepository;
import brewster.chess.service.GameService;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/game")
public class Controller {
    public final GameRepository gameRepository;
    public final GameService gameService;

    public Controller(GameRepository gameRepository, GameService gameService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    @PostMapping("/players")
    public List<String> createPlayer(@RequestBody String request){
//        game = Manager.createGame(request.getName1(), request.getName2());
//        board = Manager.getBoard(game.getId());
//        List<Response> returnValue = board.returnBoard();
//        Player player1= game.players[0];
//        Status status = game.getStatus();
//        StatusResponse statusResponse = new StatusResponse(status.isActive(), status.isCheck(), player1, game.getId());
//        returnValue.add(statusResponse);
//        return returnValue;
        return List.of("");
    }

    @GetMapping("/{id}")
    public List<Point> selectPiece(@PathVariable long id, int position){
        Game game = gameRepository.findById(id).orElseThrow(GameNotFound::new);
        return gameService.calculatePossibleMoves(game, position);
    }

//    @PostMapping("/restart")
//    public List<Response> restart(@PathVariable int id){
//        game = Manager.getGame(id);
//        String name1 = game.players[0].getName();
//        String name2 = game.players[1].getName();
//        game = Manager.createGame(name1, name2);
//        board = Manager.getBoard(game.getId());
//        List<Response> returnValue = board.returnBoard();
//        Player player1= game.players[0];
//        StatusResponse status = new StatusResponse(true, false, player1, game.getId());
//        returnValue.add(status);
//        return returnValue;
//    }
//
//    @PostMapping("/{id}")
//    public List<Response> makeMove(@PathVariable int id, @RequestBody BoardRequest boardRequest) {
//        Game game = Manager.getGame(id);
//        board = Manager.getBoard(game.getId());
//        StatusResponse status = game.run(boardRequest, board);
//        List<Response> returnValue = board.returnBoard();
//        returnValue.add(status);
//        return returnValue;
//    }
//
//    @PostMapping("/{id}/undo")
//    public List<Response> undo(@PathVariable int id){
//        Game game = Manager.getGame(id);
//        board = Manager.getBoard(game.getId());
//        StatusResponse status = game.undo(board);
//        List<Response> returnValue = board.returnBoard();
//        returnValue.add(status);
//        return returnValue;
//    }
//
//    @PostMapping("/{id}/end")
//    public StatusResponse endGame(@PathVariable int id, @RequestBody StatusRequest statusRequest){
//        Game game = Manager.getGame(id);
//        Status status = game.getStatus();
//        status.setActive(false);
//        if (statusRequest.isForfeit()){
//            StatusResponse statusResponse = new StatusResponse(statusRequest.getPlayerName() + " declares defeat! Game Over!");
//            return statusResponse;
//        }
//        StatusResponse statusResponse = new StatusResponse("We have a draw! Good Game!");
//        return statusResponse;
//    }
//
//    @GetMapping("/{id}/moves")
//    public MovesResponse displayMoves(@PathVariable int id){
//        ///must separate out moves lists
//        Game game = Manager.getGame(id);
//        MovesResponse movesResponse = new MovesResponse(game.returnMoveMessages());
//        return movesResponse;
//    }
//
////    @GetMapping("/pieces")
////    public PieceResponse displayPieces(Player player){
////        Player otherPlayer = game.getOtherTeam(player);
////        PieceResponse pieceResponse = new PieceResponse(player.getTeam(), otherPlayer.getTeam());
////        return pieceResponse;
////    }
}

