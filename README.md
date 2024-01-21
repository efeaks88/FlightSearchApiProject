# Flight Search API

Bu proje, uçuş arama uygulamasının backend API'sini geliştirmektedir.

## Proje Açıklaması

Bu uygulama, uçuşlar ve havaalanlarıyla ilgili bilgileri saklamak, yönetmek ve kullanıcılara uçuş arama imkanı sunmak amacıyla oluşturulmuştur.

## Başlangıç

Projeyi yerel bilgisayarınıza klonlayarak başlayabilirsiniz. Aşağıdaki adımları takip edebilirsiniz:

```bash
git clone https://github.com/efeaks88/FlightSearchApiProject.git
cd FlightSearchApiProject
# Gerekli bağımlılıkları yükleyin
# Projeyi başlatın
Veritabanı Yapısı
Projede kullanılan veritabanı yapısı aşağıdaki gibidir:

Uçuşlar (Flights):

ID
Kalkış havaalanı
Varış havaalanı
Kalkış tarih/saat
Dönüş tarih/saat
Fiyat
Havaalanları (Airports):

ID
Şehir
CRUD Yapısı
Uçuşlar ve Havaalanları için CRUD (Create, Read, Update, Delete) operasyonları uygulanmıştır.

Search API
Verilen kalkış yeri, varış yeri, kalkış tarihi ve dönüş tarihine uygun uçuşları listeleyen bir API endpoint bulunmaktadır.

Tek yönlü uçuş için tek uçuş bilgisi,
Çift yönlü uçuş için iki uçuş bilgisi verilmektedir.
REST API ve Authentication
Projede Java kullanılarak REST API mimarisi uygulanmıştır. Authentication yapısı ile kullanıcı kimliği doğrulanmaktadır.

Scheduled Background Jobs
Her gün mock bir third-party API’a istek atarak uçuş bilgilerini alan ve veritabanına kaydeden bir scheduled job bulunmaktadır.
