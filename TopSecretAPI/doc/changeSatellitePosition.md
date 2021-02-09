# Change Satellite Position

Servicio que permite cambiar la posición por defecto de cada satelite

**URL** : `/position/{satellite_name}`

**Método** : `PUT`

**Ejemplo:**

**/position/kenobi**
```json
{
    "x": -5,
    "y": -2
}
```

**Response:**

200 **/position/kenobi**
```json
{
    "name": "kenobi",
    "position": {
        "x": -5.0,
        "y": -2.0
    },
    "distanceFromShip": null,
    "receiptMessage": null
}
```

404
**/position/kenobi**
```json
{
    "timestamp": "09-02-2021 12:53:50",
    "statusCode": 404,
    "message": "No existe el satelite: kenoby enviado como parametro",
    "description": "uri=/position/kenoby"
}
```
