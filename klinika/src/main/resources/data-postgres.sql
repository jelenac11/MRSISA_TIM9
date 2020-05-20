-- sifre za sve korisnike su "sifra123"

insert into klinicki_centar (naziv) values('Klinički centar');

insert into cenovnik default values;
insert into cenovnik default values;
insert into cenovnik default values;

insert into klinika (lokacija, naziv, opis, prosecna_ocena, klinicki_centar_id, cenovnik_id) values('Beograd', 'Stomatološka klinika', 'Prilikom posete našoj ordinaciji, vaš osmeh je naš prioritet. Ceo naš tim  posvećen je obezbedjivanju personalizovane i visoko-kvalitetne stomatološke usluge. Cilj nam je da vam obezbedimo najbolju stomatološku negu, dugoročno oralno zdravlje i lep osmeh.', 0, 1, 1);
insert into klinika (lokacija, naziv, opis, prosecna_ocena, klinicki_centar_id, cenovnik_id) values('Novi Sad', 'Klinika za neurologiju', 'Klinika za neurologiju nastavlja najbolju tradiciju ustanovljenu pionirskim radovima oca srpske moderne neurologije Dr Laze Lazarevića. Od svog osnivanja do danas ova ustanova predstavlja mesto okupljanja najeminentnijih kliničkih neurologa u Srbiji, edukacionu maticu u školovanju učenika, studenata, specijalizanata neurologije i drugih disciplina koji u svom programu imaju neurološko usavršavanje i središte naučno-istraživačke delatnosti iz polja kliničke i eksperimentalne neurologije.',0, 1, 2);
insert into klinika (lokacija, naziv, opis, prosecna_ocena, klinicki_centar_id, cenovnik_id) values('Novi Sad', 'Klinika za infektivne bolesti', 'Klinika za infektivne i tropske bolesti Kliničkog centra Srbije je jedna od ustanova ove vrste u zemlji koja radi 24 sata, svakog dana tokom cele godine i bavi se lečenjem obolelih od infektivnih bolesti ili osoba sumnjivih na infekciju. Ova zdravstvena ustanova osnovana je odlukom 3599 od 25.07.1926. godine i od tada funkcioniše kao samostalna stručno – obrazovna institucija. Klinika posluje u sastavu Kliničkog centra Srbije. Referentna je ustanova za oblast infektologije u stručnom i naučno – istraživačkom radu za celu zemlju. Klinika je ušla u sastav Kliničkog Centra Srbije među prvima, njegovim osnivanjem.', 0, 1, 3);

insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, jbo) values('PA', 'Pariska 33', 'Republika Srbija', 'pacijent@gmail.com', 'Sombor', 'Pera', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Perić', true, true, true, '064-123-1234', 0, '29887654300');
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, jbo) values('PA', 'Zmaj Jovina 17', 'Republika Srbija', 'jelenacupac99@gmail.com', 'Sombor', 'Jelena', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Cupać', true, true, true, '064-123-1235', 0, '10756363221');
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke) values('AC', 'Narodnog fronta 23d', 'Republika Srbija', 'admincentra@gmail.com', 'Novi Sad', 'Admin', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Centrić', true, true, true, '064-123-1236', 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, klinika_id) values('AK', 'Topolska 18', 'Republika Srbija', 'adminklinike1@gmail.com', 'Beograd', 'Admir', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Admirović', true, true, true, '064-123-1237', 0, 1);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, klinika_id) values('AK', 'Sibinjska 26', 'Republika Srbija', 'lazaklinika2@gmail.com', 'Sibinj', 'Laza', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Lazić', true, true, true, '064-123-1238', 0, 2);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id, aktivan, prosecna_ocena) values('LE', 'Danila Kiša 56', 'Republika Srbija', 'mikalekar@gmail.com', 'Novi Sad', 'Mika', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Mikić', true, true, true, '064-123-1239', 0, 28800000, 46800000, 1, true, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id, aktivan, prosecna_ocena) values('LE', 'Akšehirska 7', 'Republika Srbija', 'petarpetrovic@gmail.com', 'Beograd', 'Petar', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Petrović', true, true, true, '064-123-1210', 0, 28800000, 46800000, 1, true, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id, aktivan, prosecna_ocena) values('LE', 'Topolska 18', 'Republika Srbija', 'milosmilosevic@gmail.com', 'Sombor', 'Miloš', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Milošević', true, true, true, '064-123-1211', 0, 28800000, 46800000, 1, true, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id, aktivan, prosecna_ocena) values('LE', 'Narodnog fronta 23', 'Republika Srbija', 'jelena@gmail.com', 'Novi Sad', 'Jelena', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Marković', true, true, true, '064-123-1212', 0, 28800000, 46800000, 2, true, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena, klinika_id) values('MS', '16. oktobra 23', 'Republika Srbija', 'medicinskasestra@gmail.com', 'Beograd', 'Milan', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Milanović', true, true, true, '064-123-1213', 0, 28800000, 46800000, 1);
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
insert into zdravstveni_karton (pacijent_id, visina, tezina, dioptrija, krvna_grupa) values (11, 175, 75, 0.0, '0+');

insert into sala (broj, naziv, klinika_id,aktivan) values(1, 'Sala 1', 1, true);
insert into sala (broj, naziv, klinika_id,aktivan) values(2, 'Sala 2', 1, true);
insert into sala (broj, naziv, klinika_id,aktivan) values(3, 'Sala 3', 1, false);
insert into sala (broj, naziv, klinika_id,aktivan) values(4, 'Sala 4', 1, false);
insert into sala (broj, naziv, klinika_id,aktivan) values(5, 'Sala 5', 1, false);

insert into sifrarnik (naziv, tip_sifrarnika) values('Šifrarnik lekova', 'LEKOVI');
insert into sifrarnik (naziv, tip_sifrarnika) values('Šifrarnik dijagnoza', 'DIJAGNOZE');

insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('L261', 'Brufen', 'LEKOVI', 1);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('L777', 'Paracetamol', 'LEKOVI', 1);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('L939', 'Strepsils', 'LEKOVI', 1);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('L548', 'Defrinol', 'LEKOVI', 1);

insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('D813', 'Karijes', 'DIJAGNOZE', 2);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('D199', 'COVID-19', 'DIJAGNOZE', 2);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('D254', 'Tuberkuloza', 'DIJAGNOZE', 2);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('D451', 'Brazilska groznica', 'DIJAGNOZE', 2);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('D162', 'Sepsa', 'DIJAGNOZE', 2);
insert into stavka_sifrarnika (sifra, naziv, tip_sifre, sifrarnik_id) values('D787', 'Šarlah', 'DIJAGNOZE', 2);

-- od Thu Jun 04 2020 01:00:00 do Fri Jun 19 2020 15:00:00
insert into odsustvo (kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(1592571600000, '', false, false, 1591225200000, 1, 6);
insert into odsustvo (kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(1592571600000, '', false, false, 1591225200000, 1, 7);
insert into odsustvo (kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(1592571600000, '', false, false, 1591225200000, 1, 8);

insert into tip_pregleda (naziv, opis, klinika_id, aktivan) values ('Uklanjanje mekih naslaga', 'Dečija stomatologija', 1,true);
insert into tip_pregleda (naziv, opis, klinika_id, aktivan) values ('Stomatološki pregled', 'Klinički pregled zuba, uzimanje statusa svakog zuba pojedinačno', 1,true);
insert into tip_pregleda (naziv, opis, klinika_id, aktivan) values ('Uklanjanje zubnog kamenca', 'Parodontologija', 1,true);
insert into tip_pregleda (naziv, opis, klinika_id, aktivan) values ('Zalivanje fisura', 'Dečija stomatologija', 1,true);
insert into tip_pregleda (naziv, opis, klinika_id, aktivan) values ('Specijalistički pregled psihijatra', 'Kognitivno-bihejvioralna psihoterapija', 2,true);
insert into tip_pregleda (naziv, opis, klinika_id, aktivan) values ('Specijalistički pregled neurologa', 'Određivanje individualnih terapijskih procedura usmerenih ka prevenciji i smanjenju broja glavobolja', 2,true);

insert into specijalizovan (lekar_id, tip_pregleda_id) values (6, 1);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (6, 2);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (7, 3);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (7, 4);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (7, 1);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (8, 2);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (9, 5);
insert into specijalizovan (lekar_id, tip_pregleda_id) values (9, 6);

-- potvrdjeni pregledi
-- Thu May 9 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1589011200000, 3600000, 1, false, true, true, 1);
-- Thu May 10 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 1, 1, 1589097600000, 3600000, 1, false, true, true, 1);
-- Sat May 20 2020 21:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1590004800000, 3600000, 1, false, true, true, 1);
-- Sun May 20 2020 21:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (7, 1, 1, 1590004800000, 3600000, 1, false, true, true, 2);
-- Sun May 21 2020 08:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1590040800000, 3600000, 1, false, true, true, 1);
-- Sun May 22 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1590134400000, 3600000, 1, false, true, true, 1);
-- Sun May 24 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1590307200000, 3600000, 1, false, true, true, 1);

-- zahtevi bez sale
-- Fri May 27 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (7, 1, 1, 1590566400000, 3600000, 1, false, false, true, null);
-- Fri May 27 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (8, 11, 2, 1590566400000, 3600000, 1, false, false, true, null);
-- Fri May 27 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, 2, 1, 1590566400000, 3600000, 1, false, false, true, null);

-- predefinisani
-- Wed Jun 01 2020 08:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (6, null, 1, 1590991200000, 3600000, 1, false, false, false, 1);
-- Wed Jun 01 2020 09:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (7, null, 1, 1590994800000, 3600000, 1, false, false, false, 2);
-- Wed Jun 01 2020 10:00:00
insert into pregled (lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, otkazan, potvrdjen, zauzet, sala_id) values (7, null, 3, 1590998400000, 3600000, 1, false, false, false, 1);

-- operacije
-- Tue Jun 02 2020 10:00:00
insert into operacija (pacijent_id, vreme, klinika_id, otkazana) values (1, 1591084800000, 1, false);
-- Tue Jun 02 2020 10:00:00
insert into operacija (pacijent_id, vreme, klinika_id, otkazana) values (2, 1591084800000, 1, false);
-- Tue Jun 02 2020 10:00:00
insert into operacija (pacijent_id, vreme, klinika_id, otkazana) values (11, 1591084800000, 1, false);

insert into operisali (lekar_id, operacija_id) values (7, 1);
insert into operisali (lekar_id, operacija_id) values (6, 2);
insert into operisali (lekar_id, operacija_id) values (8, 3);

insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (1, 2500, 1);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (2, 3000, 1);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (3, 4000, 1);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (4, 5000, 1);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (5, 4500, 2);
insert into stavka_cenovnika (tip_pregleda_id, cena, cenovnik_id) values (6, 5500, 2);

insert into popust (pocetak, kraj, procenat, stavka_cenovnika_id, klinika_id) values (1589493600000, 1593036000000, 10, 1, 1);
