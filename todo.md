[//]: # (### simplicity vs pojo)

[//]: # (should Piece implement its own business logic?)

### move logic
should I first move and then check if moved-or-defeated check? and then move back


### stalemate
have method to request draw. upon request, check if stalemate. if not, then ask 2nd player if it accepts
use ifDidCheckMate plus all other pieces have empty legalMoves

### use DTOs
could use it for piece logic- spots, friends, foes - and simplify Knight at least


### game over response
should it have its own POJO? should have separate POJO for draw request?



### update user stats
### message system



### promotion
what to do if in check after promotion?
must check for check afterwards and get move message.

### change game moves to list of string instead of 1 big string