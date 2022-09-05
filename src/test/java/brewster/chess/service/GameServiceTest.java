package brewster.chess.service;


import brewster.chess.repository.GameRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;


class GameServiceTest {
    private GameRepository repository = mock(GameRepository.class);
    private CheckService checkService = mock(CheckService.class);

    private GameService sut = new GameService(repository, checkService);

    @Test
    void asdf(){

    }

}