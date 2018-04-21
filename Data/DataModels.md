
### Örnek Ülke Modeli

```javascript
{
	"countryId":"country:172",
	"name":"Afghanistan",
	"capitalCity":"Kabul",
	"population":27657145,
	"flag":"https://restcountries.eu/data/afg.svg",
	"cities":[
		{"cityId":"city:2383","name":"Herat"},
		{"cityId":"city:2386","name":"Kandahar"}
		...
	]
}
```

### Örnek Şehir Modeli

```javascript
{
	"cityId":"city:1",
	"name":"London",
	"country":"country:1",
	"places":["poi:441","poi:440",...]
}
```

## Örnek Turistik Yer Modeli

```javascript
{
    "placeId" : "poi:441",
    "rating" : 10.010573717949,
    "latitude" : 51.5006895,
    "longitude" : -0.1245839,
    "name" : "Big Ben",
    "cityCountryName" : "London, United Kingdom",
    "description" : "The most famous bell in Europe, perhaps...",
    "thumbnail" : "https://media-cdn.sygictraveldata.com/media/poi:441",
    "marker" : "other:building:tower:bell",
    "parentIds" : [ 
        "city:87673", 
        "region:2006111", 
        "region:228", 
        "city:1", 
        "region:44", 
        "country:1", 
        "continent:1"
    ],
    "categories" : [ "sightseeing"],
    "longDescription" : "The most famous bell in Europe, perhaps even in the world, Big Ben, is one ...",
    "media" : [ "https://media-cdn.sygictraveldata.com/media/612664395a40232133447d33247d3832343638393731.jpg"]
}
```
