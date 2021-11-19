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

### Deck

### BlackJack

## Roulette

This folder contains all the logic for the Roulette game.

### Guess

### Roulette

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
