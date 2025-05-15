# Inhaltsverzeichnis
1. [Einleitung](#einleitung)
2. [Aufgabenanalyse](#aufgabenanalyse)
3. [Verfahrensbeschreibung](#verfahrensbeschreibung)
4. [Programmbeschreibung](#programmbeschreibung)
    - [UML-Klassendiagramme](#uml-klassendiagramme)
    - [UML-Sequenzdiagramm](#uml-sequenzdiagramm)
    - [Programmablaufplan](#programmablaufplan)
5. [Änderungen zum Montag](#änderungen-zum-montag)
    - [Algorithmus](#algorithmus)
    - [Optimierung](#optimierung)
6. [Benutzeranleitung](#benutzeranleitung)
    - [Installation](#installation)
    - [Ausführung der Beispiele](#ausführung-der-beispiele)
    - [Ausführung der Testfälle](#ausführung-der-testfälle)
    - [Fehlerfälle](#fehlerfälle)
7. [Entwicklerdokumentation](#entwicklerdokumentation)
    - [Projektstruktur](#projektstruktur)
    - [Funktionale Dateien](#funktionale-dateien)
        - [Code-Dateien](#code-dateien)
        - [Datenverarbeitungsdateien](#datenverarbeitungsdateien)
        - [Hilfsdateien](#hilfsdateien)
    - [Nicht-Funktionale Dateien](#nicht-funktionale-dateien)
        - [Datenhaltungsdateien](#datenhaltungsdateien)
        - [Testdateien](#testdateien)
        - [Sonstiges](#sonstiges)
8. [Testbeispiele](#testbeispiele)
    - [Vorgegebene Testfälle](#vorgegebene-testfälle)
    - [Weitere Testfälle](#weitere-testfälle)
9. [Zusammenfassung und Ausblick](#zusammenfassung-und-ausblick)
10. [Quellcode](#quellcode)

<div style="page-break-after: always;"></div>

# Einleitung
Vorliegend ist die Dokumentation meiner Lösung zur Aufgabe "Entwicklung eines Softwaresystems" aus der "Abschlussprüfung Sommer 2025" für den Ausbildungsberuf MATSE, auch "GroPro" genannt.
Ich, Hussein Idris, habe diese Aufgabe eigenhändig, ohne fremde Hilfe und mit großer Sorgfalt gelöst.

Dieses Softwareprojekt wurde in Python 3.12 im Code-Editor Visual Studio Code auf meinem Arbeitslaptop mit dem Betriebssystem Windows geschrieben.
Python ist dank der einfachen Syntax und dynamischen Bindung, die einen schnellen Einstieg ermöglichen, sehr beliebt.
Ihre Vielseitigkeit spiegelt sich in der breiten Palette an Bibliotheken und Frameworks wider.
Zudem ist Python plattformunabhängig und läuft problemlos auf verschiedenen Betriebssystemen, was die Entwicklung und Verbreitung der Software vereinfacht.
Aus diesen Gründen habe ich mich für Python entschieden.

Im Folgenden gebe eine detaillierte Benutzeranleitung zur Installation, erkläre ich die Änderungen, die ich im Gegensatz zu meinem Konzept am Montag vorgenommen habe, präsentiere eine umfassende Entwicklerdokumentation, erläutere die verschiedenen Testfälle und begründe diese.
Abschließend fasse ich das gesamte Programm zusammen und gebe einen Ausblick auf mögliche Weiterentwicklungen und zukünftige Verbesserungen.

Diese Dokumentation ist modular aufgebaut und kann in beliebiger Reihenfolge gelesen werden, wodurch die Übergänge jedoch sprunghaft oder abrupt erscheinen können.
Querverweise zum Code ermöglichen eine flexible Navigation, die Reihenfolge der Kapitel folgt nur meiner persönlichen Präferenz.

Ich erkläre verbindlich, dass das vorliegende Prüfprodukt von mir selbstständig erstellt wurde.
Die als Arbeitshilfe gentutzten Unterlagen sind in der Arbeit vollständig aufgeführt.

Ich versichere, dass der vorgelegte Ausdruck mit dem Inhalt der von mir erstellten digitalen Version identisch ist.
Weder ganz noch in Teilen wurde die Arbeit bereits als Prüfungsleistung vorgelegt.
Mir ist bewusst, dass jedes Zuwiederhandeln als Täuschungsversuch zu gelten hat, der die Anerkennung des Prüfprodukts als Prüfungsleistung ausschließt.

Aachen, 06.12.2024

Yusuf Demirel

_____________
<div style="page-break-after: always;"></div>

# Aufgabenanalyse
Es wird gefordert, die Kundenaufträge auf eine Rolle mit fester Breite, fester Optimierungstiefe und variabler Länge möglichst gut aufzuteilen und auszugeben.
Danach sollen die Ergebnisse auch graphisch abgebildet werden.
Die möglichst gute Aufteilung wird durch eine möglichst hohe genutzte Fläche der Rolle bestimmt.
Also muss das Programm zusätzlich die endgültige Länge der genutzten Rolle bestimmen.

Die Eingabedatei besteht aus einer Zeile mit dem Namen des Auftrags, einer Zeile mit der Breite der Rolle und Optimierungstiefe und den individuellen Kundenaufträgen in den nachfolgenden Zeilen.
Mit dem letzten Auftrag folgt keine neue Zeile und die Datei endet.
Sowohl die Breite der Rolle als auch die Breite und Höhe der Kundenaufträge sind in der Datei in Millimetern angegeben.
Daher bietet es sich an, sie im Programm als Ganzzahlen darzustellen, ebenso die Optimierungstiefe.
Die Kommentare können als Zeichenkette ebenfalls angemessen dargestellt werden.

Ferner sind die ersten beiden Zeilen gesondert einzulesen, wobei sich für die nachfolgenden Zeilen eine Schleife anbietet wegen ihrer Homogenität.
Weitere Details zu konkreten Algorithmen und Datenstrukturen sind beim Einlesen und Verarbeiten nicht spezifiziert.
Da die Aufgabenstellung einen objektorientierten Ansatz (wenn auch implizit) fordert, bietet es sich an, eine eigene Klasse zum Einlesen der Datei, eine zum Verarbeiten der Zeilen in Datenstrukturen und eine zum Ausführen des Algorithmus mithilfe der erstellten Datenstrukturen zu gestalten.

Letztendlich sollte eine gesonderte Klasse das Ergebnis in eine neue Datei schreiben, da bei der Ausgabe auch keine Datenstrukturen definiert sind.
Hierbei sollen sogar zwei verschiedene Dateien geschrieben werden.

Definieren wir also die Klasse FileReader zum Einlesen der Datei, Processor zum Übersetzen der Zeilen in geeignete Datenstrukturen und Writer zum Schreiben des Ergebnisses.
Der Code soll wartbar und offen für Erweiterung sein.
Um dies im Voraus einzuplanen, bietet es sich an, dass die Klassen Processor und Writer als Schnittstellen zu definieren.
Somit sind die Formate der Eingabedateien erweiterbar auf zum Beispiel .txt, .json, .xml und sonstige, außer .in, indem für jedes Format eine neue Klasse mit immer homogenen Funktionsaufrufen definiert wird, um dem Dependency Inversion Prinzip gerecht zu werden.
Darüber hinaus sorgt die Schnittstelle Writer für eine Flexibilität, die sogar in der Aufgabenstellung implizit gefordert ist.
Zum einen gibt es .out als Ausgabedatei und zum anderen .gnu, in Zukunft möglicherweise auch andere.
Die Schnittstelle sollte analog durch Unterklassen realisiert werden.
Also können wir die Unterklassen InProcessor, OutWriter und GnuWriter schon definieren.

Neben diesen Klassen ist noch eine geeignete Datenstruktur für die einzelnen Kundenaufträge auszuwählen.
Es bietet sich an, eine Klasse Task und eine Klasse Roll zu definieren, durch die die Aufträge insgesamt gespeichert werden.

Somit sind alle Klassen definiert.
Um das Programm auszuführen, kann es hilfreich sein, eine Kommandozeilen-basierte Implementierung vorzusehen.
Neben der Hauptdatei, die man ausführen möchte, sollte es folgende Argumente geben:
Das erste Argument `-f/--filepath` sollte die Eingabedatei angeben.
Das zweite Argument `-o/--output` sollte dann das Verzeichnis für die Ausgabe der beiden Ausgabedateien in .out und .gnu angeben.

Kommen wir nun zu den Instanzvariablen und maßgeblichen Funktionen der einzelnen Klassen.
Der FileReader muss nur den Dateipfad und den Inhalt der Datei speichern.
Zum Lesen wird eine Methode read() definiert.

Der Processor erhält den Inhalt der Datei als Zeichenkette und auch den Pfad, letzteres nur zum Ausgeben von detaillierten Fehlermeldungen.
Er erhält außerdem eine statische Methode create() zum Ermitteln der geeigneten Unterklasse (z.B. InProcessor für .in), daher erhält diese Funktion den Dateiinhalt und den Dateipfad als Parameter.
Zusätzlich erhält jede Unterklasse die Funktion process() zum Verarbeiten der Datei zu Datenstrukturen.
Die jeweilige Unterklasse erzeugt die Datenstruktur Roll und ihre Tasks.

Die Klasse Roll speichert Breite, Optimierungstiefe, Beschreibung der Aufgabe und eine Liste von allen Aufträgen (Klasse Task).
Ihr Herzstück ist die Methode position_tasks() zum Berechnen der Positionen der Kundenaufträge.
Sie setzt die Attribute Länge (Ganzzahl) und genutzte Fläche (Fließkommazahl), nachdem sie den Algorithmus ausführt.

Die Klasse Task speichert Breite, Höhe, ID und Auftragsbeschreibung.
Zusätzlich speichert sie die Koordinaten der unteren linken und oberen rechten Ecke, die durch den Algorithmus in Roll gesetzt werden.
Zur Hilfe wird zusätzlich die Klasse Point bestimmt, sodass die Koordinaten in den Variablen x und y für eine Darstellung in x- und y-Koordinaten in einem kartesischen Koordinatensystem als Ganzzahlen gespeichert werden.

Die Klasse Write enthält den Pfad der Ausgabedatei und eine Referenz auf ein Roll-Objekt.
Sie enthält eine Methode zum Schreiben.
Konkrete Klassen wie OutWriter, GnuWriter rufen diese Methode auf, mit ihrem eigenen Template-Pfad.

Es sei angemerkt, dass diese Klassen etwaige interne (private) Methoden und Flaggen besitzen, die mit einem Unterstrich beginnen.
<div style="page-break-after: always;"></div>

# Verfahrensbeschreibung
Die Hauptdatei (src/main.py) wird mit den Parametern filepath (Dateipfad) und output (Ausgabepfad) aufgerufen.
Die Hauptfunktion initialisiert einen FileReader mit filepath und ruft seine read() Methode auf.
Diese gibt die Datei in einem String zurück.
Danach wird Processor.create mit dem Inhalt und filepath aufgerufen.
Diese Methode gibt einen konkreten Processor zurück, dessen process() Methode aufgerufen wird.
Wir gehen im Folgenden von InProcessor aus.

Die process()-Methode wandelt den String (Inhalt der Datei) in eine Liste von Zeilenstrings um.
Sie liest die ersten beiden Zeilen ein und initialisiert ein Roll Objekt mit Breite, Optimierungstiefe und Auftragsbeschreibung.
Für jede weitere Zeile ruft sie Roll.add_task mit Breite, Höhe, ID und Auftragsbeschreibung auf.
Somit wird das Roll-Objekt mit seinen Tasks vervollständigt und zurückgegeben.

Als nächstes wird Roll.position_tasks() aufgerufen.
Hier wird der Algorithmus ausgeführt, der für alle Tasks seine beiden Eckpunkte bestimmt, auf ihn wird später eingegangen.
Diese Methode ruft außerdem Roll.calc_used_area() auf.
Sie subtrahiert die Höhe mal Breite aller Aufträge von der Höhe mal Breite der Rolle und dividiert das Ergebnis durch die Höhe mal der Breite der Rolle.
Danach speichert sie es in dem Roll-Objekt, in Prozent und auf die zweite Nachkommastelle gerundet.

Roll wird dann in einen gewünschten Writer wie OutWriter, GnuWriter gesteckt, zusammen mit dem output-Parameter (Ausgabedateipfad).
Dieser ruft dann seine interne _write() Methode auf.
Sie lädt einfach die Variablen aus Roll in das Template des Dateiformats.
Damit wird die Datei geschrieben und das Programm endet.

Der Algorithmus wird unter [Algorithmus](#algorithmus) genauer beschrieben.
Am Montag hatte ich die Idee, die Tasks nach gleicher längeren Kantenlänge zu gruppieren und wie eine Task zu behandeln.
Ich habe jedoch festgestellt, dass dies nicht die minimale untere Schranke als Länge der Rolle garantiert.
Deswegen habe ich diese Idee verworfen.
Was ich beibehalten habe, ist, dass das Programm eine Tiefensuche durchführt, um eine optimale Verteilung der Tasks zu ermitteln.
Hierbei wird Roll._position_backtracking mit einer flachen Kopie von Roll.tasks aufgerufen, die maximal so lang wie die Optimierungstiefe ist.
Dies geschieht, bis alle Tasks durchlaufen sind.
Zwischenzeitlich werden Instanzvariablen ermittelt und überschrieben.
<div style="page-break-after: always;"></div>

# Programmbeschreibung
## UML-Klassendiagramme
Es sei angemerkt, dass die Klassendiagramme Python-spezifisch formuliert sind.
Das heißt, dass alle Attribute und Methoden als public (+) markiert sind, weil in Python alles public ist.
Interne Attribute und Methoden sind jedoch mit einem Unterstrich versehen, um anzudeuten, dass sie nach außen hin nicht benutzt werden sollten.
Die Diagramme entsprechen außerdem dem endgültigen Programmentwurf und weisen dewegen vor allem in der Roll-Klasse Diskrepanzen auf.

![Klassendiagramme](./img/Class.jpg)

## UML-Sequenzdiagramm
Nachfolgend ist das Sequenzdiagramm für den Ablauf des Programms ab dem Hauptmodul.
Die beiden Parameter filepath und out_dir, die vom Nutzer eingegeben wurden, werden als gefundene Nachricht dargestellt.

![Sequenzdiagramm](./img/Sequence.jpg)

## Programmablaufplan
Hier ist der Ablauf der `Roll.position_tasks()` Methode, die den Backtracking-Algorithmus und die Instanzvariablen steuert.

![Programmablaufplan](./img/Flowchart.png)

# Änderungen zum Montag
Während der Entwicklung haben sich mehrere Aspekte ergeben, die mich dazu veranlasst haben, Änderungen gegenüber meinem ursprünglichen Konzept vom Montag vorzunehmen.
Obwohl ich zu Beginn der Woche einen detaillierten Softwareentwurf erstellt habe, sind diese Anpassungen das Ergebnis eines iterativen Entwicklungsprozesses.

## Algorithmus
Beim Ausarbeiten des Algorithmus (Roll._position_backtracking) ist mir aufgefallen, dass das Gruppieren der Aufträge nach gleicher maximaler Kantenlänge nicht das beste Ergebnis garantiert.
Deshalb taucht in meiner Impelentierung das Gruppenobjekt nicht auf, welches mehrere Task-Objekte dieser Art sammeln sollte.

Vielmehr ergibt es Sinn, Task-Objekte mit derselben Höhe und Breite zu gruppieren, um die Anzahl der Pfade bei der Tiefensuche zu verringern.
Mehr dazu unter [Optimierung](#optimierung).

Was ich beibehalten habe, ist Tasks, die in einer Dimension zu lang sind, sinnvoll zu gruppieren.
Da mein Backtracking-Ansatz am Montag nicht ganz ausgereift war, beschreibe ich den entgültigen Entwurf hier im Detail:

Der Algorithmus besitzt die Parameter tasks, eine Liste von maximal der Optimierungstiefe entsprechenden Task-Objekten, die noch nicht gesetzt wurden;
origin_y zur Angabe eines y-Versatzes, falls die Anzahl der Task-Objekte die Optimierungstiefe übersteigt, der Algortihmus also von neu gestartet werden muss und der erste Andockpunt auf P(0, origin_y) liegt;
higehst_y zur Angabe der Höhe der höchsten Task-y-Koordinate im aktuellen Schleifenaufruf;
anchors zur Angabe der verfügbaren Andockpunkte im aktuellen Schleifenaufruf;
placed_tasks zur Kollisionsüberprüfung mit bereits positionierten Tasks.

Zu Beginn wird überprüft, ob alle Tasks schon positioniert wurden.
Falls ja, wird die geschaut, ob highest_y eine neue untere Schranke für die Länge der Rolle ist und entsprechend wird diese aktualisiert.
Ebenso werden die aktuellen Andockpunkte überschrieben und von den aktuellen Tasks wird eine tiefe Kopie angelegt, damit weitere Pfaddurchläufe diese nicht beeinflussen.

Ansonsten werden anchors und placed_tasks initialisiert, falls wir uns im ersten Aufruf der Methode befinden.
anchors wird auf den Ursprungsandockpunkt gesetzt und placed_tasks auf eine leere Liste.
Jetzt beginnt die Schleife über alle Tasks und Andockpunkte.
Es werden gegebenenfalls Tasks übersprungen, was Teil der [Optimierung](#optimierung) ist.
Dank der sortierten Liste ist dies kein Problem.
Die Hilfsfunktion Roll._is_valid_position(self, anchor, task, is_horizontal=True) überprüft, ob die Task in der gewünschten Ausrichtung, standardmäßig horizontal, verlegt werden darf und keine der placed_tasks überschneidet.
Dafür wird jede Task aus placed_tasks mit den Koordinaten der zu setzenden Task auf eine Überschneidung mittels Axis-Aligned Bounding Box (AABB) Overlap Test geprüft.
Task.horizontal ist ein Attribut, dass standardmäßig True ist und nur dann False, wenn die Ausrichtung der Task gesperrt ist.
Deswegen wird jede Task, falls möglich, in beide Ausrichtungen verlegt und es entstehen zwei verschiedene Teilbäume beim Rekursionsaufruf.
Falls gerade eine Task so verlegt werden soll, dass sie die aktuelle untere Schranke überschreiten würde, wird sie nicht verlegt.
Ansonsten wird die Task über `self._set_task` verlegt, die gleichzeitig die neuen anchors zurückgibt und den aktuellen anchor rausnimmt.
Es werden die tasks-Liste und anchors-Liste flach kopiert, highest_y wird gegebenenfalls aktualisiert und die Funktion wird mit placed_tasks rekursiv aufgerufen.
Nach dem Rekursionsaufruf werden die Koordinaten der gesetzten Task mit `self._unset_task` wieder auf None gesetzt, sie wird aus placed_tasks entfernt der nächste Schleifenaufruf beginnt.

So sollte der Algorithmus vollständig sein und effizient arbeiten.
Akzeptiert wird der erste Pfad, der die minimale untere Schranke gefunden hat.

## Optimierung
Während ich den Algorithmus ausgearbeitet habe, habe ich mehrere Optimierungsmöglichkeiten festgestellt.
Zu allererst fiel mir auf, dass einige Tasks in einer Dimension länger sind als die Breite der Rolle.
Diese Tasks können nur in eine vertikale Ausrichtung verlegt werden.
Es lohnt sich also, in jedem Task ein Boolean Attribut horizontal zu speichern, das standardmäßig auf True gesetzt wird.
Falls es aber auf False gesetzt ist, können Verlegungen mit horizontaler Ausrichtung übersprungen werden.
Ebenso macht die Ausrichtung bei quadratischen Tasks keinen Unterschied.
Deswegen sollten solche dieses Attribut ebenfalls auf False setzen.

Als nächstes habe ich festgestellt, das die minimale untere Schranke für die Länge der Roll tendenziell früher gefunden werden kann, wenn die Liste vor dem Backtracking-Algorithmus zuerst nach absteigender Fläche sortiert wird.
Da größere Tasks den Raum effizienter nutzen und die Gefahr von „Löchern“ oder fragmentierten Bereichen minimieren, können weniger Pfade durchlaufen werden. Dies habe ich impelementiert, indem ich für das Task-Objekt eine `__lt__` Methode geschrieben habe.
Mit dieser kann ich den kleiner-Operator (<) auf eine Liste von Task-Objekten anwenden.
Wenn ich nun `list[Task].sort` aufrufe, wird diese Methode herangezogen, um sie zu sortieren.

Zu guter letzt ist mir aufgefallen, dass bei manchen Tasks sowohl long_side als auch short_side dieselbe Länge besitzen (zum Beispiel (400, 100) und (100, 400) in der Eingabedatei für Länge und Breite eines Tasks).
Ich habe mir vorgestellt, wie diese Tasks dieselben Teilbäume im Backtracking-Algorithmus erzeugen, nachdem sie positioniert wurden.
Deswegen habe ich mir einen Weg überlegt, diese identischen Teilbäume nicht mehrmals zu durchlaufen.
Zuerst schien es mir zu kompliziert, identische Teilbäume zu erkennen und ich dachte, dass ich eine neue Datenstruktur miteinbeziehen muss.
Nach einiger Überlegung ist mir jedoch aufgefallen, dass es mit einem simplen Ansatz gelöst werden kann.

In der Liste der Tasks müssen dafür diese gleichen Tasks direkt hintereinander stehen.
Denn falls sie dies tun, kann im Schleifenaufruf überprüft werden, ob das nächste Task-Objekt gleich dem aktuellen ist.
Falls ja, kann die Schleife mit dem continue-Befehl auf das nächste Task-Objekt zeigen.
Diese Änderung stellt schon sicher, dass gleiche Teilbäume nur einmal durchlaufen werden.
Dazu musste ich nur noch eine `__eq__` Methode in der Task-Klasse definieren, die den '==' Operator überschreibt.
Sie schaut, ob beide Dimensionen gleich sind.
Standarmäßig überprüft der '==' Operator die Identität zweier Objekte.
Deshalb habe ich alle Aufrufe von '==' vom Task Objekt mit 'is' ersetzt, welcher immer ein Identitätsoperator ist.
<div style="page-break-after: always;"></div>

# Benutzeranleitung
> Alle Befehle werden für eine konsistente Handhabung aus dem Stammverzeichnis, also aus `~gropro/` ausgeführt.
> Es ist nicht notwendig, zuerst in den `src/` oder `tests/` Ordner zu navigieren, auch nicht vorgesehen.
> Nur so kann sichergestellt werden, dass alle Pfade und Abhängigkeiten korrekt geladen werden.

Ich habe dieses Projekt in VS Code mit dem Python-Interpreter der Version 3.12 von Anfang bis Ende entwickelt.
Die einzige Abhängigkeit einer Bibliothek ist jinja2 zum rendern der Template-Dateien.
Nachfolgend wird gezeigt, wie dieses Projekt als editierbares Paket installiert werden kann.
Es werden jinja2 und sonsite Abhängigkeiten dadurch automatisch installiert und Module aus dem `src/` Ordner können an beliebigen Stellen im Code importiert werden.

## Installation
Überprüfen, dass Python auf der Version 3.12 installiert ist (Alternativ kann [pyenv](https://github.com/pyenv/pyenv) genutzt werden, um mehrere Python-Versionen zu verwalten und Python 3.12 zu installieren, ohne seine globale Python-Version ändern zu müssen):
```
python -V
# Besipielhafte Ausgabe (Windows): Python 3.12.2
```

Erstellen und aktivieren einer virtuellen Umgebung namens gropro_venv:
```
# Windows
python -m venv gropro_venv
gropro_venv\Scripts\activate

# Linux
python3 -m venv gropro_venv
source gropro_venv/bin/activate
```

Installieren des Projekts als ein editierbares Paket.
Dadurch werden Änderungen reflektiert (wenn man zum Beispiel Testmodule hinzufügt) und Importbefehle weierhin aufgelöst:
```
pip install -e .
```
> Die Installation des Projekts als ein Paket ist notwendig, damit alle Importe und Abhängigkeiten richtig aufgelöst werden.

## Ausführung der Beispiele
Zum Ausführen eines Beispiels wird das Hauptmodul wie ein CLI-Programm ausgeführt.
Dabei steht der erste Parameter `-f/--filepath` für den Pfad der Eingabedatei.
Der zweite Parameter `-o/--output` ist optional und beschreibt den Ausgabepfad.
Standardmäßig werden erstellte Ergebnisdateien unter `data/output` abgelegt.
Wenn dieser Ordner noch nicht existiert, wird er erzeugt.
Dabei werden automatisch Ausgabedateien beider Dateiformate, also .out und .gnu erstellt.
Hier ist eine beispielhafte Nutzung:
```
python src/main.py -f data/input/IHK1.in
```
## Ausführung der Testfälle
Zum Ausführen aller Unittests mit einem Befehl gibt es folgendes Modul, das alle Testdateien innerhalb [src/tests](../src/tests/) lädt, ausfürt und dabei die Gesamtzeit misst.
```
python src/tests/all_tests.py
```

Natürlich können die Testmodule auch einzeln ausgeführt werden:
```
python src/tests/test_ihk2.py
```

## Fehlerfälle
Da Nutzereingaben immer validiert werden müssen, sollten Fehler wie das Angeben einer nicht existierenden Datei oder eines nicht existierenden Verzeichnisses, aber auch falsche Argumente überprüft werden.
Jede Komponente, also FileReader, Processor und Writer Klasse überprüft für sich, ob Nutzereingaben richtig spezifiziert sind und vertraut keiner anderen Komponente.
Abgesehen von falschen Nutzereingaben werden etwaige betriebssystemspezifische Ausnahmen ebenfalls behandelt.

Der FileReader löst eine Ausnahme aus, wenn die angegebene Datei nicht existiert oder ein Betriebssystemfehler auftritt.
Ob eine Processor-Instanz für die gewünschte Dateiendung existiert, wird in Processor.create überprüft, ebenso der Dateipfad.
Immer, wenn ein Task-Objekt erstellt wird, kann der Parameter roll_width mitgegeben werden.
Dadurch wird überprüft, ob die Task in beiden Dimensionen zu groß ist, wodurch eine Ausnahme geworfen wird.
Die spezifische Unterklasse InProcessor überprüft, ob die Datei genug Zeilen hat und das richtige Format besitzt, wirft sonst einen ValueError.
Die Writer-Schnittstelle wirft etwaige Ausnahmen, die beim Öffnen oder Schließen der Datei auftreten.
Falls der Ausgabepfad in einem nichtexistenten Verzeichnis liegt, wird keine Ausnahme geworfen sondern es wird erstellt.
Zu guter Letzt überprüft die Hauptdatei ebenfalls, ob die gewünschte Eingabedatei existiert.
<div style="page-break-after: always;"></div>

# Entwicklerdokumentation
## Projektstruktur
Das Projekt ist wie folgt strukturiert:
```
gropro/
├── data/
│   ├── input/
│   ├── output/
│   └── templates/
├── docs/
│   ├── Dokumentation.pdf
│   ├── Quellcode.pdf
│   └── ...
├── src/
│   ├── file_ops/
│   ├── tests/
│   │   ├── all_tests.py
│   │   └── ...
│   ├── utils/
│   └── main.py
├── pyproject.toml
└── ...
```

Die klare Trennung von Daten, Dokumentation und Code ermöglicht eine einfache Navigation.
- **data/** Beinhaltet alle notwendigen Datenhaltungsdateien, generierte Outputdateien und Templatedateien,
- **docs/** Enthält Dokumentationen,
- **src/** Beherbergt den gesamten Quellcode, einschließlich `file_ops` für jegliche Datei- und Verzeichnisoperationen, `tests/` für Testskripte, `utils/` für Hilfsmodule und `main.py` als Haupteinstiegsstelle.

Im folgenden werden die Funktionen der einzelnen Python-Module und der sonstigen Dateien erklärt.
Auf gängige Dateien wie `.gitignore` und `__init__.py` wird nicht eingegangen, ebenso nicht auf automatisch generierte Inhalte oder Dokumentationsdateien.
Für Erläuterungen zu den einzelnen Modulen, Klassen und Funktionen siehe die Docstrings und Zeilenkommentare.

## Funktionale Dateien
> Da ich das Programm in Python geschrieben habe und alle Klassen, Methoden und Attribute immer öffentlich sind, habe ich Methoden und Attribute, die nur für interne beziehungsweise private Zwecke gedacht sind, mit einem Unterstrich vor dem Namen versehen.
> Dies ist eine Python-spezifische Konvention.
> Alles, worauf von außen als API zugegriffen werden darf, beginnt nicht mit einem Unterstrich.
> Detaillierte Modul-, Klassen- und Methodenbeschreibungen sind im Code als Doctrings angegeben.

### Code-Dateien
[src/main.py](../src/main.py)

Dieses Modul ist der Ausgangspunkt des Programms.
Es wird wie ein CLI-Programm behandelt und erhält als erste Argument eine Input-Datei und als zweites einen optionalen Output-Pfad.
Standardmäßig werden Dateien in `data/output` als .in und .gnu Dateien abgelegt.

### Datenverarbeitungsdateien
[src/file_ops/funcs.py](../src/file_ops/funcs.py)

API, die Funktionen für Dateioperationen enthält.

[src/file_ops/processor.py](../src/file_ops/processor.py)

Erweiterbare Basisklasse zum Verarbeiten von verschiedenen Dateiformate zu einer Roll-Instanz.
Diese enthält eine statische Methode zum automatischen Auswählen der richtigen Unterklasse.
Entsprechend des Dependency Inversion Prinzips sind alle Methoden der Unterklassen uniform.
Bisher realisiert die Unterklasse `InProcessor` .in Dateien.

[src/file_ops/roll.py](../src/file_ops/roll.py)
Dieses Modul enthält die Klassen Point, Task und Roll.
Roll ist das Objekt, das die Rolle darstellt, alle Tasks enthält und den Backtracking-Algorithmus enthält.
Roll wird durch das Prozessieren einer Datei zusammen mit einer Liste von allen Tasks erzeugt.
Nachdem Roll die Tiefensuche zur möglichst guten Positionierung der Tasks ausgeführt hat, wird es an eine Writer-Instanz eitergegeben.

[src/file_ops/wrappers.py](../src/file_ops/wrappers.py)

Klassen zum Laden und Schreiben von Dateien mit Ausnahmebehandlungen.
Die Klasse FileWriter ist insofern anders von der Schnittstelle Writer, dass sie keine Logik enthält, sondern einen vorgegebenen Dateiinhalt einfach schreibt.
Sie erfüllt momentan keine Funktion, außer für die Hilfsdatei [src/utils/code_to_md.py](../src/utils/code_to_md.py).

[src/file_ops/writer.py](../src/file_ops/writer.py)
Schnittstelle zum Schreiben von verschiedenen Dateiformaten.
Die Oberklasse implementiert eine write-Methode, die die Variablen aus dem Roll-Objekt lädt und in das gewünschte jinja2 Template einliest.
jinja2 ist eine Template-Engine und die Template-Dateien liegen entsprechend unter [data/templates](../data/templates/).
Die Unterklassen unterscheiden sich nur in der Auswahl der Template-Datei, Dateiendung und der Ausgabe der Task-Objekte.

### Hilfsdateien
[src/utils/code_to_md.py](../src/utils/code_to_md.py)

Dieses Modul erstellt das übersichtliche Dokument `docs/Quellcode.md` zu den Python-Modulen aus `src/`.

[src/utils/settings.py](../src/utils/settings.py)

Enthält konstante Dateipfade des Projekts, um sie im Code wiederzuverwenden.

## Nicht-Funktionale Dateien
### Datenhaltungsdateien
Die Dateien in `data/input/` umfassen sowohl die von der Aufgabenstellung vorgegebenen Eingabedateien als auch zusätzliche Dateien, die ich erstellt habe, um die Korrektheit meines Codes zu überprüfen.
Die von mir erstellten Dateien decken verschiedene Szenarien und Randfälle ab, um zu bestätigen, dass mein Code robust und fehlerfrei funktioniert.
Ferner enthält `data/output/` jene Dateien, die gemäß Aufgabenstellung durch das Ausführen von `src/main.py` automatisch entstehen.

### Testdateien
[src/tests/all_tests.py](../src/tests/all_tests.py)

Diese Datei lädt alle Testmodule aus `tests/` in eine `unittest.suite`.
Da sie dynamisch geladen werden, kann man ganz bequem neue hinzufügen, ohne den Code bearbeiten zu müssen.
Wichtig ist nur, dass die Dateinamen mit `test_` beginnen, damit sie erkannt werden.
Die Ausführung der Testsuite endet mit der Ausgabe der Anzahl der ausgeführten unittests und der verstrichenen Gesamtzeit.

### Sonstiges
[pyproject.toml](../pyproject.toml)

Diese Konfiguration legt die Build-Einstellungen fest.
Sie gewährleistet, dass alle Importe korrekt aufgelöst werden, indem sie `src/` für die Quellpakete festlegt.
Durch Befehle wie `pip install .` wird das Paket installiert, einschließlich der darin definierten Abhängigkeiten und Konfigurationen.
<div style="page-break-after: always;"></div>

# Testbeispiele
> Weitere `unittest`-Module können in den Ordner `src/tests/` eingefügt werden.
> Solange die Dateinamen mit `test_` beginnen, werden sie beim Ausführen von `src/tests/all_tests.py` erkannt und mit ausgeführt.

Ich nutze Blackbox-Testing, um festzustellen, ob das Programm die erwartet Länge, erwartete genutzte Fläche zusammen mit der richtigen Anzahl an Kundenaufträgen und verbleibenden Andockpunkten ausgibt.
Die erwartete Anzahl der Kundenaufträge wird durch abzählen der Zeilen der Aufträge in der Inputdatei bestimmt.
Die erwartete Anzahl der Andockpunkte wird durch die erwartete Anzahl der Kundenaufträge plus der Anzahl, wie oft `Roll._position_backtracking` aufgerufen wird, bestimmt.
Es können nicht bestimmte Eckpunktkoordinaten oder Andockpunktkooridnaten erwartet werden, weil diese je nach Sortierung der Tasks und Reihenfolge des Backtrackings abweichen.

Um die erwartete Länge zu bestimmen, muss man das vorliegende Beispiel selber durchgehen und die erzielte minimale obere Schranke notieren, was nur bedingt möglich ist.
Wenn die erwartete Länge bestimmt ist, ist es einfach, die erwartete genutzte Fläche zu bestimmen.
Dazu teilt man die Fläche aller Tasks durch die Fläche der Rolle, multipliziert das Ergebnis mit 100 und rundet auf die zweite Nachkommastelle.

Ich lade und verarbeite eine Input-Datei pro Testfall und nutze `unittest.TestCase.assertEqual` zum verifizieren der vier genannten Parameter.
Jeder Testfall ist eine Klasse, die von `unittest.TestCase` erbt.
Somit besitzt jeder Testfall die Eigenschaft, dass er in eine gemeinsame TestSuite geladen und gemeinsam ausgeführt werden kann.
Nachdem alle asserts erfolgreich durchlaufen, werden die Output-Dateien im .gnu und .out Format in `data/output` abgelegt.
Dieser Ordner wird erstellt, falls er noch nicht exisitiert.

## Vorgegebene Testfälle
[tests/test_ihk3.py](../src/tests/test_ihk1.py),
[tests/test_ihk2.py](../src/tests/test_ihk2.py),
[tests/test_ihk3.py](../src/tests/test_ihk3.py)

Diese drei Beispiele wurden im Rahmen der Prüfung vorgegeben.
Alle werden korrekt und zeitig ausgeführt.
Die assertEqual Werte wurden vorgegeben.

## Weitere Testfälle
[tests/test_identtasks.py](../src/tests/test_identtasks.py),
[tests/test_identgroups.py](../src/tests/test_identgroups.py)

Ersteres besteht aus Aufträgen mit immer wieder derselben Dimension.
Er soll überprüfen, ob die hohe Optimierungstiefe durch den Gewinn an Performance kompensiert wird, der dadurch entsteht, dass die Laufzeit für Tasks mit identischen Kantenlängen [optimiert](#optimierung) ist.
Die Rollenbreite von 30cm und der Dimension von 30cm * 20cm jeder Task soll vorhersehbar machen, dass eine Gesamtlänge von 60cm benötigt wird, die 100% der Rolle abdeckt.
Für einen solch einfachen Fall ist ein zuverlässiges Ergebnis bei einer Optimierungstiefe von 9 zu erwarten.

Letzteres besteht aus mehreren unterschiedlich großen Aufträgen, die mehrmals vorkommen.
Hierbei zeigt sich eine geeignete Lösung für eine Optimierungstiefe von 7, da es immer noch viele Möglichkeiten zum Verlegen gibt.
Wenn man die Optimierung der identischen Teilbäume unterlässt, ist die Laufzeit in meiner Testumgebung ungefähr 21-mal so lang, was deren Effektivität beweist.

[tests/test_ultrawide.py](../src/tests/test_ultrawide.py)

Dieser Test enthält eine ungewöhnlich lange Breite für die Rolle.
Dies führt tendenziell zu mehr Möglichkeiten bei der Ausrichtung und zu einer längern Laufzeit mit der Optimierungstiefe 7.
Die zu erwartende Länge der Taks ist 7, die Länge der Andockpunkte 8.
Die minimale untere Schranke wird nach Augenmaß gewertet.

[tests/test_fragmented.py](../src/tests/test_fragmented.py)

Dieser Test soll die Performance und Korrektheit testen, wenn eine lange Liste von Aufträgen gegeben ist.
Es wird erwartet, dass er mit der Optimirungstiefe 6 mithält und ein zuverlässiges Ergebnis liefert, indem er die einzelnen Task- und Andockpunktlisten richtig vereint.
Die zu erwartende Länge der Taks ist 26, die Länge der Andockpunkte 31.
Die minimale untere Schranke wird nach Augenmaß gewertet.

[tests/test_squares.py](../src/tests/test_squares.py)

Dieser Test soll zeigen, dass quadratische Aufträge für eine erheblich reduzierte Laufzeit und erhöhte Toleranz sorgen.
Hier ist es möglich, auf eine untere Schranke von 124cm zu kommen, da die Rolle eingeschränkt ist und man feststellt, dass nur Aufträge D und A, G und F und E und C nebeneinander passen.
Damit kommt man auch auf die zu erwartende Länge von 124cm und eine Flächenabdeckung von 76.35%.
Es müssen insgesamt 8 Tasks und 9 Andockpunkte geben.
<div style="page-break-after: always;"></div>

# Zusammenfassung und Ausblick
Schließlich wurde gezeigt, dass das Programm eine möglichst optimale Positionierung der Kundenaufträge liefert, indem es eine effiziente Tiefensuche implementiert.
Durch umfassende Tests konnte bestätigt werden, dass die Algorithmen präzise und zuverlässig arbeiten.
Alle erforderlichen und zusätzlichen Tests wurden mit Erfolg abgeschlossen, wodurch die Robustheit des Systems unter Beweis gestellt wurde.

Für zukünftige Weiterentwicklungen bietet sich die Implementierung einer grafischen Benutzeroberfläche (GUI) an.
Das .gnu-Format zeigt, dass es ohnehin ein sehr graphisches Programm ist und in zwei Dimensionen dargestellt werden kann.
Die Bedienung des Programms würde somit erheblich erleicht werden.
Man könnte es interaktiv gestalten, indem der Nutzer vorhandene Input-Dateien importieren oder auf der Oberfläche selber Daten erzeugen kann.

Um eine webbasierte Anwendung zu schreiben, könnte man Web-Frameworks wie Flask oder Django nutzen.
Sie erlauben auch das Erstellen RESTful API zum Ansprechen der Module.
Mit Bibliotheken wie Tkinter oder PyQt hingegen kann man eine Desktop-GUI erstellen.

Außerdem kann man mit einer parallelen Ausführung den Algorithmus für hohe Optimierungstiefen verbessern.
Dazu kann man einen Rekursionsaufruf für jeden der n Tasks starten und am Ende überprüfen, welcher der n Threads die kleinste untere Schranke findet.
Durck der Deaktivierung des Global Interpreter Locks (GIL) in Python 3.13 kann man echtes Multithreading erreichen.
Das Programm kann also auf Python 3.13 angepasst werden und die Laufzeitoptimierung kann so weiter analysiert werden.
Vorsicht ist jedoch geboten, da das Starten von vielen Threads gleichzeitig zu einer Überlastung des Systems führen kann.
Es ist wichtig, die Anzahl der Threads zu begrenzen und einen Thread-Pool zu verwenden, um die Ressourcen effizient zu nutzen und Synchronisationsprobleme zu vermeiden.

## Quellcode
Der gesamte Quellcode aus dem src-Ordner liegt unter [/docs/Quellcode.pdf](./Quellcode.pdf) in PDF-Form vor.
