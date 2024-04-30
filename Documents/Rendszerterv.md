# Rendszerterv

## 1. A rendszer célja

A rendszer célja egy JavaFX alapú nyilvántartó rendszer létrehozása egy kisbolt számára. A rendszer lehetővé teszi a felhasználók számára, hogy regisztráljanak és bejelentkezzenek, valamint kezeljék a bolt termékeinek nyilvántartását. A rendszer főbb funkcionalitásai közé tartozik:

- Felhasználók regisztrációja és bejelentkezése: A felhasználók képesek lesznek regisztrálni a rendszerbe, majd bejelentkezni az azonosítójukkal és jelszavukkal.
- Termékek kezelése: A felhasználók hozzáadhatnak új termékeket a rendszerhez, törölhetik vagy módosíthatják meglévő termékeket.
- Termékek nyilvántartása: A rendszer nyomon követi a boltban lévő termékek mennyiségét, valamint lehetőséget biztosít a készletállomány frissítésére és kezelésére.

Ezáltal a rendszer lehetőséget biztosít a bolt tulajdonosának vagy alkalmazottainak hatékonyan kezelni a bolt termékeinek állományát és nyilvántartását.

## 2. Projektterv

### 2.1 Projektszerepkörök, felelősségek:

- Scrum master: Troll Ede
- Product owner: Troll Ede
- Üzleti szereplő: N/A

### 2.2 Projektmunkások és felelőségek:

#### JavaFX UI:

- UI tervező és fejlesztők: Bárdos Botond, Erdélyi Patrik

#### Backend:

- Rendszerarchitektúra és adatbázis tervezők: Bárdos Botond, Erdélyi Patrik
- Backend fejlesztők: Erdélyi Patrik, Bárdos Botond

#### Tesztelés:

- Funkcionális és UI tesztelők: Bárdos Botond, Erdélyi Patrik

### 2.3 Ütemterv:

| Funkció              | Feladat                    | Prioritás | Becslés (nap) | Aktuális becslés (nap) | Eltelt idő (nap) | Becsült idő (nap) |
| -------------------- | -------------------------- | --------- | ------------- | ---------------------- | ---------------- | ----------------- |
| Rendszerterv         | Megírás                    | 1         | 2             | 2                      | 2                | 2                 |
| Felhasználói felület | Képernyőtervek elkészítése | 2         | 9             | 9                      | 1                | 9                 |
| Felhasználói felület | Prototípus elkészítése     | 3         | 28            | 28                     | 1                | 28                |
| Felhasználói felület | Alapfunkciók elkészítése   | 3         | 21            | 21                     | 1                | 21                |
| Tesztelés            | Tesztelés                  | 4         | 21            | 21                     | 1                | 21                |

### 2.4 Mérföldkövek:

- Projektterv kidolgozása
- Dokumentációk (specifikációk) véglegesítése
- Prototípus átadása
- Tesztelés és hibák kijavítása
- Bemutatás

## 3. Üzleti folyamatok modellje

### 3.1 Üzleti szereplők

- Üzleti tulajdonos: A kisbolt tulajdonosa vagy üzletvezetője, aki felelős a bolt napi működéséért és a stratégiai döntések meghozataláért.
- Bolt alkalmazottak: Azok az egyének, akik a boltban dolgoznak, például eladók, raktárosok vagy pénztárosok, és felelősek a termékek kezeléséért, értékesítéséért és ügyfélszolgálatáért.
- Vásárlók: Azok a személyek, akik a boltban vásárolnak termékeket és szolgáltatásokat.

### 3.2 Üzleti folyamatok

- Termékkezelési folyamat: Ez a folyamat magában foglalja a boltban kapható termékek nyilvántartását, raktározását, készletkezelését és frissítését.
- Vásárlási folyamat: A vásárlók által végzett tranzakciók folyamata, ideértve a termékek kiválasztását, kosárba helyezését, fizetési folyamatot és vásárlás végrehajtását.
- Vevőszolgálati folyamat: Az ügyfelekkel való kommunikáció és kapcsolattartás folyamata, beleértve a kérdések megválaszolását, panaszok kezelését és kifizetett szolgáltatások nyomon követését.
- Rendeléskezelési folyamat: A termékek rendelésétől a kiszállításig terjedő folyamat, ideértve a megrendelések rögzítését, csomagolást és szállítást.

## 4. Követelmények

### 4.1 Funkcionális követelmények

| ID  | Megnevezés         | Leírás                                                                                                                                                                                                                                                           |
| --- | ------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| K1  | Regisztráció       | A felhasználó lehetőséget kap a rendszerbe történő regisztrációhoz, megadva szükséges információkat, mint például felhasználónév, email cím és jelszó.                                                                                                           |
| K2  | Bejelentkezés      | A felhasználó a rendszerbe való belépéshez megadhatja az email címét és jelszavát.                                                                                                                                                                               |
| K3  | Termék hozzáadása  | Lehetőség nyújtása a felhasználók számára új termékek hozzáadásához a rendszerhez, megadva a termék nevét, leírását, árát és egyéb releváns információkat.                                                                                                       |
| K4  | Termék törlése     | Felhasználóknak lehetőséget biztosítani meglévő termékek törlésére a rendszerből.                                                                                                                                                                                |
| K5  | Termék módosítása  | Lehetőség nyújtása a felhasználók számára a meglévő termékek adatainak módosításához, például az ár vagy leírás frissítéséhez.                                                                                                                                   |
| K6  | "Emergency delete" | Egy speciális funkció, amely lehetővé teszi az összes termék törlését a rendszerből egyetlen kattintással. Fontos, hogy ez a funkció csak körültekintően legyen elérhető, és legyen megfelelően biztosítva az esetleges véletlen törlés vagy visszaélések ellen. |

### 4.2 Nemfunkcionális követelmények

| ID  | Megnevezés                  | Leírás                                                                                             |
| --- | --------------------------- | -------------------------------------------------------------------------------------------------- |
| K3  | Jogosultsági szintek        | Hozzáférési jogok megfelelő kezelése a rendszerben.                                                |
| K10 | Teljesítmény, skálázhatóság | Gyors működés és a rendszer skálázhatósága még akkor is, ha sok felhasználó egyidejűleg használja. |
| K11 | Biztonság                   | Felhasználók adatainak védelme illetéktelen hozzáféréstől.                                         |
| K12 | Felhasználói élmény         | Könnyen átlátható, felhasználóbarát felület biztosítása a gyorsabb kezelhetőség érdekében.         |
| K13 | Kompatibilitás              | Különböző eszközök és böngészők támogatása, reszponzív UI biztosítása.                             |

### 4.3 Támogatott eszközök

Asztali számítógépek, hordozható számítógépek.

Rendben, itt van az átdolgozott Funkcionális terv a nyilvántartó rendszerhez:

## 5. Funkcionális terv

### Rendszerszereplők:

- **Adminisztrátor**
- **Felhasználó**
  - Ügyfél
  - Dolgozó

### Rendszerhasználati esetek és lefutásaik:

- **Felhasználó**
  - Bejelentkezés a rendszerbe
  - Regisztráció a rendszerbe (csak ügyfelek számára)
  - Termékek, készletek böngészése
  - Termék hozzáadása a nyilvántartáshoz (csak dolgozók számára)
  - Termék törlése a nyilvántartásból (csak dolgozók számára)
  - Termék módosítása a nyilvántartásban (csak dolgozók számára)
  - Ügyfél adatainak megtekintése és módosítása (csak ügyfelek számára)
  - Rendelés leadása (csak ügyfelek számára)
  - Rendelés státuszának ellenőrzése (csak ügyfelek számára)
- **Adminisztrátor**
  - Felhasználók kezelése (új felhasználó hozzáadása, meglévő felhasználó törlése)
  - Termékek, készletek kezelése (hozzáadás, törlés, módosítás)
  - Rendelések kezelése és státuszuk frissítése

## 6. Fizikai környezet

### Vásárolt softwarekomponensek és külső rendszerek:

- Nincsenek megvásárolt komponensek.

### Hardver topológia:

- Az alkalmazás helyi számítógépen fut, amely egy dedikált MySQL adatbázis-szerverhez kapcsolódik.

### Fizikai alrendszerek:

- Az alkalmazás backendje JAVA, JavaFX-es ablakos alkalmazással megjelenítve.

### Fejlesztő eszközök:

- IntelliJ IDEA
- Postman
- Git

## 7. Adatbázis terv

### Táblák:

- Termékek: ID, Name, Type, Price
- Felhasználók: ID, Name, Password, Email
-

## 8. Architekturális terv

### Adatbázis rendszer:

- Az alkalmazás MySQL adatbázist használ, ami lehetővé teszi hogy gyorsan és könnyen hozzáférjenek az adatokhoz valós időben.

### Program elérése, kezelése:

- Az alkalmazás elérhető saját gépen, letöltés után egy jar fájlal futtatható.

## 9. Implementációs terv

- Az alkalmazás Java nyelven fog készülni JavaFX ablakos megjelenítővel. Különböző technológiákat nem használunk hozzá annak érdekében, hogy később könnyebben bővíthessük, és mások számára is olvashatóbbá váljon.
- Az adatokat MySQL adatbázisban fogjuk tárolni

## 10. Tesztterv

- Az alkalmazásunkat unit tesztek segítségével fogjuk ellenőrizni és tesztelni, emellett manuálisan is tesztelni a komponenseket, amiket mindenki fog végezni a csapatból

- Tesztesetek

| Teszteset | Elvárt eredmény |
| --------- | --------------- |

- A tesztelési jegyzőkönyv kitöltésére egy sablon:

**Tesztelő:**

**Tesztelés dátuma:**

| Tesztszám | Rövid leírás | Várt eredmény | Eredmény | Megjegyzés |
| --------- | ------------ | ------------- | -------- | ---------- |

## 11. Telepítési terv

## Fizikai telepítés:

- Fizikai módon való telepítésre nincs szüksség, egy jar fájlal futtatható

## Szoftver telepítés:

- Java 20.0.1 telepítése szükséges

## 12. Karbantartási terv

### Figyelembe kell venni a felhasználók által jött visszajelzéseket a programmal kapcsolatban. Ha hibát talált, mielőbb orvosolni kell, lehet az:

- Működéssel kapcsolatos
- Kinézeti, vagy dizájnnal kapcsolatos (UI)
