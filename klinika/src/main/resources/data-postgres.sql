-- sifre za sve korisnike su "sifra123"

insert into klinicki_centar (naziv) values('Klinički centar');

insert into cenovnik default values;
insert into cenovnik default values;
insert into cenovnik default values;

insert into klinika (lokacija, naziv, prosecna_ocena, klinicki_centar_id, cenovnik_id) values('Beograd', 'Stomatološka klinika', 1, 1, 1);
insert into klinika (lokacija, naziv, prosecna_ocena, klinicki_centar_id, cenovnik_id) values('Novi Sad', 'Klinika za neurologiju', 1, 1, 2);
insert into klinika (lokacija, naziv, prosecna_ocena, klinicki_centar_id, cenovnik_id) values('Novi Sad', 'Klinika za infektivne bolesti', 1, 1, 3);

insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, jbo) values('PA', 'Pariska 33', 'Republika Srbija', 'pacijent@gmail.com', 'Sombor', 'Pera', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Perić', true, true, true, '064-123-1234', 0, '29887654300');
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, jbo) values('PA', 'Zmaj Jovina 17', 'Republika Srbija', 'jelenacupac99@gmail.com', 'Sombor', 'Jelena', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Cupać', true, true, true, '064-123-1235', 0, '10756363221');
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke) values('AC', 'Narodnog fronta 23d', 'Republika Srbija', 'admincentra@gmail.com', 'Novi Sad', 'Admin', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Centrić', true, true, true, '064-123-1236', 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, klinika_id) values('AK', 'Topolska 18', 'Republika Srbija', 'adminklinike1@gmail.com', 'Beograd', 'Admir', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Admirović', true, true, true, '064-123-1237', 0, 1);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, klinika_id) values('AK', 'Sibinjska 26', 'Republika Srbija', 'lazaklinika2@gmail.com', 'Sibinj', 'Laza', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Lazić', true, true, true, '064-123-1238', 0, 2);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id, aktivan) values('LE', 'Danila Kiša 56', 'Republika Srbija', 'mikalekar@gmail.com', 'Novi Sad', 'Mika', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Mikić', true, true, true, '064-123-1239', 0, 28800000, 46800000, 1, true);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id, aktivan) values('LE', 'Akšehirska 7', 'Republika Srbija', 'petarpetrovic@gmail.com', 'Beograd', 'Petar', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Petrović', true, true, true, '064-123-1210', 0, 28800000, 46800000, 1, true);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id, aktivan) values('LE', 'Topolska 18', 'Republika Srbija', 'milosmilosevic@gmail.com', 'Sombor', 'Miloš', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Milošević', true, true, true, '064-123-1211', 0, 28800000, 46800000, 1, true);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id, aktivan) values('LE', 'Narodnog fronta 23', 'Republika Srbija', 'jelena@gmail.com', 'Novi Sad', 'Jelena', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Marković', true, true, true, '064-123-1212', 0, 28800000, 46800000, 2, true);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id) values('MS', '16. oktobra 23', 'Republika Srbija', 'mikimiki@gmail.com', 'Beograd', 'Milan', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Milanović', true, true, true, '064-123-1213', 0, 28800000, 46800000, 1);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, jbo) values('PA', 'Dositeja Obradovica 2', 'Republika Srbija', 'zikazikic@gmail.com', 'Sombor', 'Zika', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Zikić', true, true, true, '064-123-1235', 0, '99824366780');

insert into autoritet (name) values ('ROLE_PACIJENT');
insert into autoritet (name) values ('ROLE_LEKAR');
insert into autoritet (name) values ('ROLE_ADMIN_KLINIKE');
insert into autoritet (name) values ('ROLE_ADMIN_KLINICKOG_CENTRA');
insert into autoritet (name) values ('ROLE_MED_SESTRA');

insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (1, 1);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (2, 1);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (3, 4);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (4, 3);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (5, 3);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (6, 2);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (7, 2);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (8, 2);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (9, 2);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (10, 5);
insert into autoriteti_korisnika (korisnik_id, autoritet_id) values (11, 1);

insert into zdravstveni_karton (pacijent_id, visina, tezina, dioptrija, krvna_grupa) values (1, 180, 72, 0.5, 'A+');
insert into zdravstveni_karton (pacijent_id, visina, tezina, dioptrija, krvna_grupa) values (2, 177, 75, 0.2, 'AB');

insert into sala (broj, naziv, klinika_id,aktivan) values(1, 'Sala 1', 1,true);
insert into sala (broj, naziv, klinika_id,aktivan) values(2, 'Sala 2', 1,true);
insert into sala (broj, naziv, klinika_id,aktivan) values(3, 'Sala 3', 1,false);
insert into sala (broj, naziv, klinika_id,aktivan) values(4, 'Sala 4', 1,false);
insert into sala (broj, naziv, klinika_id,aktivan) values(5, 'Sala 5', 1,false);

insert into sifrarnik (naziv, tip_sifrarnika) values('Šifrarnik lekova', 'LEKOVI');
insert into sifrarnik (naziv, tip_sifrarnika) values('Šifrarnik dijagnoza', 'DIJAGNOZE');

insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('L261', 'Brufen', 'LEKOVI', 1);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('L777', 'Paracetamol', 'LEKOVI', 1);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('L939', 'Strepsils', 'LEKOVI', 1);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('L548', 'Defrinol', 'LEKOVI', 1);

insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('D813', 'Sars', 'DIJAGNOZE', 2);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('D199', 'COVID-19', 'DIJAGNOZE', 2);

insert into odsustvo (kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(1592571600000, '', false, false, 1591084800000, 1, 6);
insert into odsustvo (kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(1592571600000, '', false, false, 1591084800000, 1, 7);
insert into odsustvo (kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(1592571600000, '', false, false, 1591084800000, 1, 8);

insert into tip_pregleda (naziv, opis, klinika_id,aktivan) values ('Uklanjanje mekih naslaga', 'Dečija stomatologija', 1,true);
insert into tip_pregleda (naziv, opis, klinika_id,aktivan) values ('Stomatološki pregled', 'Klinički pregled zuba, uzimanje statusa svakog zuba pojedinačno', 1,true);
insert into tip_pregleda (naziv, opis, klinika_id,aktivan) values ('Uklanjanje zubnog kamenca', 'Parodontologija', 1,true);
insert into tip_pregleda (naziv, opis, klinika_id,aktivan) values ('Zalivanje fisura', 'Dečija stomatologija', 1,true);
insert into tip_pregleda (naziv, opis, klinika_id,aktivan) values ('Specijalistički pregled psihijatra', 'Kognitivno-bihejvioralna psihoterapija', 2,true);
insert into tip_pregleda (naziv, opis, klinika_id,aktivan) values ('Specijalistički pregled neurologa', 'Određivanje individualnih terapijskih procedura usmerenih ka prevenciji i smanjenju broja glavobolja', 2,true);

insert into specijalizovan (lekar_id, tip_pregleda_id) values (6, 1);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (6, 2);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (7, 3);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (7, 4);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (7, 1);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (8, 2);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (9, 5);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (9, 6);

--prvo odsustvo se moze testirati za 7.maj kao tip pregledati staviti pluca
--insert into odsustvo (podnosilac_id, klinika_id, pocetak, kraj, odgovoreno, odobreno) values (6, 1, 1588543200000, 1588888800000, true, true);
insert into odsustvo (podnosilac_id, klinika_id, pocetak, kraj, odgovoreno, odobreno) values (8, 2, 1588456800000, 1589493600000, true, true);

-- potvrdjeni pregledi
-- Thu May 28 2020 11:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 1, 1, 1590656400000, 3600000, 1, false, true, true, 1);
-- Thu May 28 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 1, 1, 1590652800000, 3600000, 1, false, true, true, 1);
-- Sat May 09 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1589011200000, 3600000, 1, false, true, true, 2);
-- Sun May 18 2020 00:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1589752800000, 3600000, 1, false, true, true, 1);

-- zahtevi nepotvrdjeni bez sale
-- Thu May 30 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1590825600000, 3600000, 1, false, false, true, null);
-- Fri May 29 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (7, 1, 1, 1590739200000, 3600000, 1, false, false, true, null);
-- Fri May 29 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (8, 11, 2, 1590739200000, 3600000, 1, false, false, true, null);
-- Fri May 29 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1590739200000, 3600000, 1, false, false, true, null);

-- predefinisani
-- Wed May 27 2020 09:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, null, 1, 1590562800000, 3600000, 1, false, false, false, 1);
-- Wed May 27 2020 09:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (7, null, 1, 1590562800000, 3600000, 1, false, false, false, 2);
-- Wed May 27 2020 08:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (7, null, 3, 1590559200000, 3600000, 1, false, false, false, 1);
-- Sun May 31 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (7, null, 3, 1590912000000, 3600000, 1, false, false, false, 1);

-- operacije
-- Tue Jun 02 2020 10:00:00
insert into operacija (pacijent_id, vreme, klinika_id, otkazana) values (1, 1591084800000, 1, false);
-- Tue Jun 02 2020 10:00:00
insert into operacija (pacijent_id, vreme, klinika_id, otkazana) values (2, 1591084800000, 1, false);
-- Tue Jun 02 2020 10:00:00
insert into operacija (pacijent_id, vreme, klinika_id, otkazana) values (11, 1591084800000, 1, false);

insert into operisali (lekar_id, operacija_id) values (7, 1);
insert into operisali (lekar_id, operacija_id) values (8, 2);
insert into operisali (lekar_id, operacija_id) values (6, 3);

insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (1, 2500, 1);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (2, 3000, 1);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (3, 4000, 1);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (4, 5000, 1);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (5, 4500, 2);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (6, 5500, 2);

insert into popust (pocetak, kraj, procenat, stavka_cenovnika_id, klinika_id) values (1588510800000, 1589115600000, 10, 2, 1);
