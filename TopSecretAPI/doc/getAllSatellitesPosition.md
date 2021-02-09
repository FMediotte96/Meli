# Get All Satellites Position

Obtiene la información de la posición actual de todos los satelites rebeldes, como así también su distancia al enemigo y el mensaje en partes que recibio el satelite.

**URL** : `/position`

**Método** : `GET`

**Ejemplos Response:**
```json
{
    "kenobi": {
        "name": "kenobi",
        "position": {
            "x": -500.0,
            "y": -200.0
        },
        "distanceFromShip": null,
        "receiptMessage": null
    },
    "skywalker": {
        "name": "skywalker",
        "position": {
            "x": 100.0,
            "y": -100.0
        },
        "distanceFromShip": null,
        "receiptMessage": null
    },
    "sato": {
        "name": "sato",
        "position": {
            "x": 500.0,
            "y": 100.0
        },
        "distanceFromShip": null,
        "receiptMessage": null
    }
}
```


