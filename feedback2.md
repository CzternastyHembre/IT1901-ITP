# Tilbakemelding på innlevering 2

... under arbeid ...

## Bygging

- ved åpning i gitpod fikk jeg denne: bash: cd: javafx-template: No such file or directory
- ellers gikk testene pent gjennom og de ser greie ut

## Dokumentasjon

## Design

## Kodegjennomgang

### Guess og subklasser

- eneste forskjellen på subklassene er initialiseringen av numbers, så da kunne dere nøyd dere med tre statisk metoder i Guess for å lage nye Guess-instanser

### Roulette

- burde ikke rollNumber bruke rouletteSize?

### Slots

- bruk gjerne enum-klasse til kortfargene og "combo"-verdiene

### UserSaveHandler

- ikke bruk relative stier, og ikke lagre inn i prosjektet (det finnes jo ikke når appen er installert)
- cleanUserList: tilsvarer ikke denne updateFile(new ArrayList<User>())?

### SlotsDisplay

- skal loader være @FXML-annotert?
- spin: bør ikke denne være @FXML-annotert? trenger ikke ta inn argument, hvis det ikke brukes (gjelder flere metoder)
- createImageView: samme Image kan brukes i flere ImageView, så en trenger bare én bakside
- backToMainMenu: naviger heller tilbake til den opprinnelige instansen av start-vinduet, heller enn å lese inn på nytt

### SelectGameController

- unngå duplisering av kode for innlesing og aktivering av "nytt" vindu ett sted, og unngå å lese inn samme fil mer enn én gang (gjelder alle kontrollere)
