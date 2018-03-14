## Schema Nedir ?

Schema, Mongo Db veritabanlarında saklayacağımız verileri tanımlayan data modellerine deniyor.

```javascript
var CountrySchema = new Schema({
    countryId: String,
    name: String,
    capitalCity: String,
    population: Number,
    flag: String,
    cities: Array
});

var CitySchema = new Schema({
    cityId: String, 
    name: String, 
    country: String, 
    places: Array
});

var PlaceSchema = new Schema({

    placeId: String, 
    rating: Number, 
    latitude: Number, 
    longitude: Number, 
    name: String, 
    cityCountryName: String, 
    description: String, 
    thumbnail: String, 
    marker: String,
    parentIds: Array,
    categories: Array
});
```