insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, jbo) values('PA', 'Pariska 33', 'Republika Srbija', 'markok@gmail.com', 'Sombor', 'Marko', 'caricajelena', 'Kraljevic', '0302');
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, jbo) values('PA', 'Dositeja Obradovica 2', 'Republika Srbija', 'hajdukveljko@gmail.com', 'Sombor', 'Veljko', 'jelenaharambasa', 'Petrovic', '9999');
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Malena Banjska', 'Srbija', 'netko_bese_strahinjicu_bane@gmail.com', 'Kosovo', 'Strahinja', 'vlahalija', 'Banović', 0, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Boška Jugovića 17', 'Srbija', 'vojvoda@gmail.com', 'Stalać', 'Vojvoda', 'vojvodaprijezda', 'Prijezda', 0, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Sibinjska 26', 'Rumunija', 'kajica@gmail.com', 'Sibinj', 'Vojvoda', 'vojvodakajica', 'Kajica', 0, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Ravni Kotari', 'Hrvatska', 'uskok_stojan@gmail.com', 'Ravni Kotari 22', 'Stojan', 'jankovicuskok', 'Jankovic', 0, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Jedrene', 'Osmansko carstvo', 'bajazit_munjeviti@gmail.com', 'Akšehirska 7', 'Bajazit', 'sultanbaja', 'od Murata', 0, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Prokuplje', 'Srbija', 'bogi_bogi@gmail.com', 'Topolska 18', 'Jug', 'jugovici', 'Bogdan', 0, 0);
insert into korisnici (tip, adresa, drzava, email, grad, ime, lozinka, prezime, pocetak_radnog_vremena, kraj_radnog_vremena) values('LE', 'Prizren', 'Srbija', 'uki_mrnj1328@gmail.com', 'Kosovo polje', 'Uroš', 'mrnjavcevici', 'Mrnjavčević', 0, 0);

insert into sala(broj, naziv) values(1, 'Sala 1');
insert into sala(broj, naziv) values(2, 'Sala 2');
insert into sala(broj, naziv) values(3, 'Sala 3');
insert into sala(broj, naziv) values(4, 'Sala 4');

insert into klinicki_centar(naziv) values('Klinički centar Boj na Kosovu');

insert into klinika(lokacija, naziv, klinicki_centar_id) values('Kosovo Polje', 'Klinika Miloša Obilića', 1);

insert into odsustvo(kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(0, '', false, false, 0, 1, 3);
insert into odsustvo(kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(0, '', false, false, 0, 1, 4);
insert into odsustvo(kraj, obrazlozenje, odgovoreno, odobreno, pocetak, klinika_id, podnosilac_id) values(0, '', false, false, 0, 1, 5);