const express = require('express');
var mongoose=require('mongoose');
var schemas=require('./schemas.js');

const app = express();

const CountryModel=schemas.Country;
const CityModel=schemas.City;
const PlaceModel=schemas.Place;

const mongoDB='mongodb://localhost/deneme';

mongoose.connect(mongoDB, function(err) {
    if (err) throw err;
});

app.get('/',(req,res)=>{
    var link1='<a href="/api/countries">Get All Countries</a>';
    var link2='<a href="/api/countries/country:34">Get A Country</a>';
    var link3='<a href="/api/cities/city/city:2072">Get City With City Id</a>';
    var link4='<a href="/api/cities/country/country:34">Get Cities With Country Id</a>';
    var link5='<a href="/api/places/poi:441">Get A Place</a>';
    var link6='<a href="/api/places/country/country:34">Get Places With City Id</a>';
    var link7='<a href="/api/places/city/city:2072">Get Places With Country Id</a>';
    res.send(`Örnek Sorgu Linkleri</br>${link1}</br>${link2}</br>${link3}</br>${link4}</br>${link5}</br>${link6}</br>${link7}</br>`);
});

app.get('/api/countries',(req,res)=>{
    
    CountryModel.find({}).exec((err,countries)=>{
        if(err) throw err;
    
        res.send(countries);
    });
    
});

app.get('/api/countries/:id',(req,res)=>{

    CountryModel.findOne({countryId: req.res.id}).exec((err,country)=>{
        if(err) throw err;
    
        res.send(country);
    });
});

app.get('/api/cities/:idType/:id',(req,res)=>{

    if(req.params.idType=='city'){
        CityModel.findOne({cityId: req.params.id}).exec((err,city)=>{
            if(err) throw err;
        
            res.send(city);
        });
    }
    else if(req.params.idType=='country'){
        CityModel.find({country: req.params.id}).exec((err,cities)=>{
            if(err) throw err;
        
            res.send(cities);
        });
    }
    else{
        res.send('invalid parameter: '+req.params.idType);
    }
});


app.get('/api/places/:id',(req,res)=>{
    PlaceModel.findOne({placeId: req.params.id}).exec((err,place)=>{
        if(err) throw err;
    
        res.send(place);
    });
});

app.get('/api/places/:idType/:id',(req,res)=>{
    if(req.params.idType=='city'){

        CityModel.findOne({cityId:req.params.id}).exec((err,city)=>{
            if (err) throw err;
        
            PlaceModel.find({placeId: { $in: city.places}}).exec((err,places)=>{
                if(err) throw err;
        
                res.send(places);
            });
        });
    }
    else if(req.params.idType=='country'){

        CityModel.find({country: req.params.id}).exec((err,cities)=>{
            if (err) throw err;
            
            var allPlaceIds=[];
            cities.forEach((city)=>{
                allPlaceIds=allPlaceIds.concat(city.places);
            });
        
        
            PlaceModel.find({placeId: { $in: allPlaceIds}}).exec((err,places)=>{
                if(err) throw err;
            
                res.send(places);
            });
        });

    }
    else{
        res.send('invalid parameter: '+req.params.idType);
    }
});


const port = process.env.PORT || 8080;
app.listen(port, ()=>{
    console.log(`listening ${port}...`);
});