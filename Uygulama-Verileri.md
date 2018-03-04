## Uygulamada Yer Alacak Veriler 

* Kullanıcı :bust_in_silhouette:
    * Kullanıcı ID
    * Kullanıcı Adı (unique)
    * Adı Soyadı
    * Email
    * Şifre
    * Lokasyon(Şehir)
    * Gezilen Şehirler []
        * Seyahat Tarihi

* Ülke :earth_africa:
    * Ülke ID
    * Ülke Adı
    * Ülke Bayrağı (olabilir)
    * Para Birimi
    * Başkent (Linkiyle birlikte)
    * Koordinat x (latitude)
    * Koordinat y (longitude)
    * Nüfus
    * Şehirler[] (Puanlara göre listele)

* Şehir :city_sunset:
    * Şehir ID
    * Şehir Adı
    * Koordinat x (latitude)
    * Koordinat y (longitude)
    * Nüfus
    * Puanlamaya göre yıldızı (5 yıldız üzerinden değerlendirme)
    * Gezilecek Yerler []

* Gezilecek Yerler :rainbow:
    * Gezilecek Yer ID
    * Gezilecek Yer Adı (Başlığı ya da)
    * Puanlamaya göre yıldızı (5 yıldız üzerinden değerlendirme)
    * Koordinat x (latitude)
    * Koordinat y (longitude)

## Android Uygulamada İstenecek İzinler 

* İnternet
* Lokasyon    

## Trip Advisor - Content API

Link: https://developer-tripadvisor.com/content-api/

Approved users of the TripAdvisor Content API can access the following business details for accommodations, restaurants, and attractions:

Location ID, name, address, latitude & longitude
Read reviews link, write-a-review link
Overall rating, ranking, subratings, awards, the number of reviews the rating is based on, rating bubbles image
Price level symbol, accommodation category/subcategory, attraction type, restaurant cuisine(s)

```Kısıtlamalar```

* Saniyede en fazla 50 istek
* Günlük en fazla 1,000 istek
* Uygulama TripAdvisor tarafından onaylanırsa günlük 10,000 e çıkıyor. (Ancak TripAdvisor'un logo kullanma vs zorunlulukları mevcut)

## Restcountries API

Link: https://restcountries.eu/

```Elde edilebilen veriler```

Dünyanın bütün ülkelerine ait;
- Ülke Adı
- Ülke Bayrağı
- Başkent
- Nüfus
- Enlem(Latitude)
- Boylam(Longitude)
- Lisan
- Para Birimi

## Max Mind World Cities Database

Link: https://www.maxmind.com/en/free-world-cities-database

```Elde edilebilen verilen```

- Ülke Kodu
- ASCII Şehir Adı
- Şehir Adı
- Bölge(Region)
- Nüfus
- Enlem(Latitude)
- Boylam(Longitude)
