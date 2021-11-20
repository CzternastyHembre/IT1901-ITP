# The Core module

Core is the module that contains all the classes and logic for the application.

It is divided into different folder depending on the logic the class contains.

- blackjack
- roulette
- saveHandler
- slots
- user
- validators

## Blackjack

This folder contains the logic used for the Blackjack game.

### Card

This class creates **Card objects** with a number, a char, image and a back image. These objects are further used in the blackjack logic.

### Deck

This class creates **Deck objects** which are going to contain **Card objects**. These Decks will be further used in the blackjack logic.

The Deck contains either a empty Deck or a Deck of 52 Card objects. These Decks can be altered later on.

### Hand

This class extends **Deck** so it has all the functionality that the Deck class has. It is used to alter the Deck to be the active Deck or not. This is used for the know which Deck that is going to be altered in the game.

### BlackJack

This is where all the previous classes are brought together with extra logic to create the blackjack game.

The user can set a bet for the game, then the user will receive cards to play with. If it is possible the user can split and if the user's hand has a sum over 21, the dealer wins. Same goes for the dealer, otherwise the one with the greater sum wins. In a tie the dealer wins. Afterwards the winnings are added to the user's balance.

## Roulette

This folder contains all the logic for the Roulette game.

### Guess

This a the logic for the guesses the user can make in the roulette game. The different methods provides the logic for the user to be able to bet on the different fields in the roulette game. Thus gives the user the ability to bet on rows, single numbers or a set of numbers.

A guess object is built with a amount(double) and the numbers betted on(as a List).

### Roulette

This class uses the functionality in the **Guess class** to let the user guess on the different fields as explained previously.

The user places their bets, then the wheel gets spun. The payout is calculated on the probablity of the guess become true. Afterwards the payout is added to the user's balance. If the user accidently bets on the wrong field, there is a undo method that allows the user to cancel their bet.

## SaveHandler

This class helps save the data of the users in the home directory. It is saved a json objects and it also helps read from this file.

## Slots

This class contains most of the logic used for the Slots game and uses some help methods from the SlotsValidator class(explained in the validators part).

This is a Slot game which lets one place a bet, then this money is withdrawed from the user's balance. Afterwards the slots machine is spun and it calculates the winnings if there are any, then adds those winnings to the balance of the user.

## User

This class contains the logic of the user.

The user consists of two elements, a username and a balance. When the username is created, it cannot be altered, but the balance of the user will fluctuate when playing the casino games. There can only be one user per username, hence two users with the same username cannot occur.

## Validators

In this folder there are classes which are used as help for the main classes such as Slots.

### SlotsValidator

This is a help class to the Slots class. The methods in the class is used in the Slots method calculateWinnings to calculate the outcome of the spin.

For example the **isPair** method takes in a list as param and checks if their are two identical symbols in the list.
