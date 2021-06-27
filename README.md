## Uruchomienie aplikacji

Aby uruchomić aplikację, należy w konsoli wykonać komendę:
```bash
gradlew bootRun
```
Alternatywnie do uruchomienia aplikacji można również użyć IDE np. Intellij IDEA

## Dostępne endpointy

* POST `/quote` - Dodaje nowy cytat. Wymagane jest podanie cytatu w `body` requestu. Treść cytatu oraz autor nie mogą być puste oraz cytat musi mieć długość w przedziale 3-250 znaków, a autor 3-100.

* GET `/quote` - Zwraca listę wszystkich cytatów.

* PUT `/quote/{id}` - Aktualizuje cytat w bazie danych. Wymagane jest podanie w parametrze zapytania `id` cytatu do aktualizacji oraz w `body` zaktualizowanego cytatu. Id cytatu w body i w parametrze zapytania muszą być takie same oraz cytat o podanym id musi być w bazie danych. Treść cytatu oraz autor nie mogą być puste oraz cytat musi mieć długość w przedziale 3-250 znaków, a autor 3-100. 

* DELETE `/quote/{id}` - Usuwa cytat o danym id. Niezależnie od tego, czy cytat o danym id istnieje zwraca status `200 ok`

