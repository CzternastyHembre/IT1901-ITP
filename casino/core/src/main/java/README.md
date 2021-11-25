# The Core module

Core is the module that contains the classes and logic for the application.

It is divided into different packages based on their purpose. The Core module includes the following
packages and their respective classes:


- blackjack
- roulette
- saveHandler
- slots
- user
- validators

## Blackjack

This package contains the logic used for the Blackjack game.

### Card
The Card class creates **Card objects**, which contain an int and a string. 
 **Card objects** are used in the game logic of blackjack.

### Deck

The Deck class creates  **Deck objects** which contain **Card objects**. Decks are used in
the game logic of blackjack.

A **Deck object** can either be an empty Deck, or a Deck of 52 Card objects. 
The properties of **Deck objects** can be altered later on.

### Hand

The Hand class extends **Deck**. It has all the functionality that the Deck class has, meaning it is a type of Deck. 
A Hand has an additional property; active. 
This determines if a **Hand object** can be "played on" or not.

### BlackJack

The Blackjack class combines all the classes above, along with its own logic, to create a fully-functional
blackjack game.

The user can set a bet for the game, and then receive cards to play with. 
If the card value of the user's hand adds up to 21, the user has the possibility to "split".
The user plays against the dealer, and the objective for the user is to continue adding cards to 
their hand until they reach 21. If a user goes over 21, they automatically lose. A user can choose
to stop adding cards to their deck if they believe that another card will put their total past 21. 
A user wins a game of blackjack if the sum of their hand is more than the dealer's, or if their
hand is equal to or less than 21, and the dealer's hand is over 21. In the case of a tie, the dealer always win
If a user wins, their payout is two times the amount they originally bet. If they lose, it is 0.

## Roulette

This folder contains all the logic for the Roulette game.


### Guess

The Guess class provides the logic for the guesses the user can make in the roulette game. 
The different methods provide the logic for the user to be able to bet on the different fields in the roulette game. 
This gives the user the ability to bet on rows, single numbers or a set of numbers.

A guess object is built with an amount(double) and the numbers bet on(as a List).

### Roulette
This class uses the functionality in the **Guess class** to let the user guess on the different fields as explained previously.

In a roulette game, a player can place bets with chips representing money on numbers. Once all bets are placed,
the user spins a roulette wheel, which produces a winning number. If a user has bet on that number,
they win! Depending on which number they bet on, and how they bet on that number (either directly
or via a combination such as "odd"), their winnings will vary. A user can also undo a bet.


## SaveHandler

This class helps save the data of the users in the home directory. 
It saves json objects, and also helps read from this file.

## Slots

This class contains most of the logic used for the Slots game
and uses some helping methods from the SlotsValidator class (explained in validators).
The Slots package also contains two enums, "Combo" and "CardColor" which help complete the 
game logic of slots, and generate random symbols.

When playing a game of slots, the user can place a bet, and spin the slot machine.
The machine will then generate a random combination of symbols, and depending on these symbols, 
a user might either win or lose. The payout is dependent on the combination of symbols.

## User

This class contains the logic of the user.

The user consists of two elements, a username and a balance. 
When the username is created, it cannot be altered, but the balance of the user will fluctuate while playing casino games. 
There can only be one user per username, which means two users cannot share the same username.

## Validators

The Validators folder contains a class which helps determine the outcome of a slots game.

### SlotsValidator

This class determines what type of combination of symbols has been generated when the slots machine has been spun.

For example the **isPair** method takes in a list as param and checks if their are two identical symbols in the list.

## Class diagram

A diagram showing how the classes in core work together: 

![class diagram](docs/Images/classDiagramCoreUP.png)
