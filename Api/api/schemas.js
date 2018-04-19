var mongoose=require('mongoose');
var Schema = mongoose.Schema;

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
    places: Array,
    editorsPick: Boolean
});

var PlaceSchema = new Schema({

    placeId: String, 
    rating: Number, 
    latitude: Number, 
    longitude: Number, 
    name: String, 
    cityCountryName: String, 
    description: String,
    longDescription: String, 
    thumbnail: String, 
    marker: String,
    media: Array,
    parentIds: Array,
    categories: Array
});

var UserSchema = new Schema({

    name: String,
    photo: String,
    email: String,
    password: String,
    latitude: Number,
    longitude:Number,
    plannedTravels: Array
});


var Country = mongoose.model("Country", CountrySchema);
var City = mongoose.model("City", CitySchema);
var Place = mongoose.model("Place", PlaceSchema);
var User = mongoose.model("User", UserSchema);

module.exports.Country = Country;
module.exports.City = City;
module.exports.Place = Place;
module.exports.User = User;