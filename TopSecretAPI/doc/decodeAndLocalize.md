# Decode and Localize

Decodifica el mensaje y posición de la nave imperial utilizando el concepto de trilateración, para triangular la posición teniendo 3 puntos de referencia y la distancia al punto de incognita

**URL** : `/topsecret`

**Método** : `POST`

**Ejemplo:**
```json
{
    "satellites": [
        {
            "name": "kenobi",
            "distance": 500,
            "message": ["este","","","mensaje",""]
        },
        {
            "name": "skywalker",
            "distance": 282.8427125,
            "message": ["","es","","","secreto"]
        },
        {
            "name": "sato",
            "distance": 600,
            "message": ["este","","un","",""]
        }
    ]
}
```

**Response:**
```json
{
    "position": {
        "x": -100.0,
        "y": 100.0
    },
    "message": "este es un mensaje secreto"
}
```
