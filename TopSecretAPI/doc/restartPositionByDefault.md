# Restart Position By Default

Resetea la configuración por default de los satelites rebeldes, ubicandolos en sus puntos iniciales del ejercicio:

* Kenobi: [-500, -200] 
* Skywalker: [100, -100] 
* Sato: [500, 100] 


**URL** : `/position/restart`

**Método** : `POST`

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


