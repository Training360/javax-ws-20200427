class: inverse, center, middle

# JSON és kezelése Java eszközökkel

---

## JSON

* JavaScript Object Notation
* XML leváltására, JavaScript elterjedésével, adatok átvitelére
* Tipikusan böngészős frontend és backend közötti kommunikációra
* Ember és számítógép számára értelmezhető szöveges, kiterjeszthető formátum
    * Érvényes JavaScript kifejezés
* Kulcs-érték párokat (object) és tömböket (array) tartalmaz
* Adattípusok: szám, karakterlánc, logikai
* Speciális üres érték: null
* Fa hierarchia

---

## Példa JSON dokumentum

```javascript
{"books":[
  {"isbn10":"059610149X",
    "title":"Java and XML"},
  {"isbn10":"1590597060",
    "title":"Pro XML Development with Java Technology"}]}
```