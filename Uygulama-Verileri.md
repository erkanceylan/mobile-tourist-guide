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
    * Koordinat x
    * Koordinat y
    * Nüfus
    * Şehirler[] (Puanlara göre listele)

* Şehir :city_sunset:
    * Şehir ID
    * Şehir Adı
    * Koordinat x
    * Koordinat y
    * Nüfus
    * Puanlamaya göre yıldızı (5 yıldız üzerinden değerlendirme)
    * Gezilecek Yerler []

* Gezilecek Yerler :rainbow:
    * Gezilecek Yer ID
    * Gezilecek Yer Adı (Başlığı ya da)
    * Puanlamaya göre yıldızı (5 yıldız üzerinden değerlendirme)
    * Koordinat x
    * Koordinat y

## Android Uygulamada İstenecek İzinler 

* İnternet
* Lokasyon    