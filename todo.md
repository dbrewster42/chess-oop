
## Abram chat
1. return all valid moves at once and let front end select from that list, thus limiting the calls
2. include promotion options and special moves in the validMoves method return
3. User must know the games it is in. Does player need user? 
4. Add board. Both board and piece keep track of location
5. game should control the pieces directly. Is player needed?
6. make iGame
7. isOnBoard part of Square's responsibility instead of Piece

player needs user to keep track of wins, losses, etc
how to map relationship between player and game? 1 to 1 but how does it know which one?

[//]: # (### simplicity vs pojo)

[//]: # (should Piece implement its own business logic?)

### move logic
should I first move and then check if moved-or-defeated check? and then move back


### stalemate
have method to request draw. upon request, check if stalemate. if not, then ask 2nd player if it accepts
use ifDidCheckMate plus all other pieces have empty legalMoves

### use DTOs
could use it for piece logic- squares, friends, foes - and simplify Knight at least


### game over response
should it have its own POJO? should have separate POJO for draw request?



### update user stats
### message system



### promotion
what to do if in check after promotion?
must check for check afterwards and get move message.

### change game moves to list of string instead of 1 big string