# Decode and Localize Split

Decodifica el mensaje y posición de la nave imperial utilizando el concepto de trilateración, en caso de tener la información suficiente en cada satellite rebelde.

**URL** : `/topsecret_split`

**Método** : `GET`

**Ejemplos Response:**

**200**
```json
{
    "position": {
        "x": -100.0,
        "y": 100.0
    },
    "message": "este es un mensaje secreto"
}
```

**404**
```json
{
    "timestamp": "07-02-2021 15:47:41",
    "statusCode": 404,
    "message": "No hay suficiente información para triangular la ubicación y el mensaje enviado por la nave imperial",
    "description": "uri=/topsecret_split"
}
```