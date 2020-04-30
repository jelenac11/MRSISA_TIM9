insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, jbo) values('PA', 'Pariska 33', 'Republika Srbija', 'markok@gmail.com', 'Sombor', 'Marko', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Kraljevic', true, true, true, '064-123-1234', 0, '0302');
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, jbo) values('PA', 'Dositeja Obradovica 2', 'Republika Srbija', 'hajdukveljko@gmail.com', 'Sombor', 'Veljko', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Petrovic', true, true, true, '064-123-1234', 0, '9999');
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke) values('AC', 'Malena Banjska', 'Srbija', 'netko_bese_strahinjicu_bane@gmail.com', 'Kosovo', 'Strahinja', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Banović', true, true, true, '064-123-1234', 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke) values('AK', 'Boška Jugovića 17', 'Srbija', 'vojvoda@gmail.com', 'Stalać', 'Vojvoda', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Prijezda', true, true, true, '064-123-1234', 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke) values('AK', 'Sibinjska 26', 'Rumunija', 'kajica@gmail.com', 'Sibinj', 'Vojvoda', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Kajica', true, true, true, '064-123-1234', 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Ravni Kotari', 'Hrvatska', 'uskok_stojan@gmail.com', 'Ravni Kotari 22', 'Stojan', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Jankovic', true, true, false, '064-123-1234', 0, 0, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Jedrene', 'Osmansko carstvo', 'bajazit_munjeviti@gmail.com', 'Akšehirska 7', 'Bajazit', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'od Murata', true, true, true, '064-123-1234', 0, 0, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Prokuplje', 'Srbija', 'bogi_bogi@gmail.com', 'Topolska 18', 'Jug', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Bogdan', true, true, true, '064-123-1234', 0, 0, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, verifikovan, aktiviran, promenjena_lozinka, broj_telefona, poslednja_promena_lozinke, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Prizren', 'Srbija', 'uki_mrnj1328@gmail.com', 'Kosovo polje', 'Uroš', '$2a$10$Vp90ZLx.EC./rr77SHecxeRsCeH2YdXoUBAilxpozUx9ywunntfLm', 'Mrnjavčević', true, true, true, '064-123-1234', 0, 0, 0);

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

insert into sala(broj, naziv) values(1, 'Sala 1');
insert into sala(broj, naziv) values(2, 'Sala 2');
insert into sala(broj, naziv) values(3, 'Sala 3');
insert into sala(broj, naziv) values(4, 'Sala 4');

insert into klinicki_centar(naziv) values('Klinički centar Boj na Kosovu');

insert into klinika(lokacija, naziv, klinicki_centar_id) values('Kosovo Polje', 'Klinika Miloša Obilića', 1);

insert into odsustvo(kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(0, '', false, false, 0, 1, 3);
insert into odsustvo(kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(0, '', false, false, 0, 1, 4);
insert into odsustvo(kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(0, '', false, false, 0, 1, 5);