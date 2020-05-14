const NavigBar = { template: '<navig-bar></navig-bar>' }
const Login = { template: '<login></login>' }
const Sale = { template: '<sale></sale>' }
const Lekari = { template: '<lekari></lekari>' }
const MedSestre= { template: '<medSestre></medSestre>'}
const Pacijenti = { template: '<pacijenti></pacijenti>' }
const SifrarnikLekova = {template: '<sifrarnik-lekova></sifrarnik-lekova>'}
const SifrarnikDijagnoza = {template: '<sifrarnik-dijagnoza></sifrarnik-dijagnoza>'}
const AdminiKlinike = { template: '<admini-klinike></admini-klinike>' }
const AdminiCentra = { template: '<admini-centra></admini-centra>' }
const KlinikeAdmin = { template: '<klinike-admin></klinike-admin>' }
const IstorijaPregleda = { template: '<istorija-pregleda></istorija-pregleda>' }
const ZdravstveniKarton = { template: '<zdravstveni-karton></zdravstveni-karton>' }
const RadniKalendar = { template: '<radni-kalendar></radni-kalendar>' }
const IzmenaProfila = { template: '<izmena-profila></izmena-profila>' }
const IzmenaProfilaKlinike = { template: '<izmena-profilaKlinike></izmena-profilaKlinike>' }
const DodavanjeSale = { template: '<dodavanje-sale></dodavanje-sale>' }
const DodavanjeLekara = { template: '<dodavanje-lekara></dodavanje-lekara>' }
const DodavanjeSifreLeka = { template: '<dodavanje-sifre-leka></dodavanje-sifre-leka>' }
const DodavanjeSifreDijagnoze = { template: '<dodavanje-sifre-dijagnoze></dodavanje-sifre-dijagnoze>' }
const DodavanjePopusta = { template: '<dodavanje-popusta></dodavanje-popusta>' }
const DodavanjeMedSestara = { template: '<dodavanje-medSestara></dodavanje-medSestara>'}
const DodavanjeKlinike = { template: '<dodavanje-klinike></dodavanje-klinike>' }
const DodavanjeAdminaKlinike = { template: '<dodavanje-admina-klinike></dodavanje-admina-klinike>' }
const DodavanjeAdminaCentra = { template: '<dodavanje-admina-centra></dodavanje-admina-centra>' }
const ZahtjeviZaGodisnjim = {template: '<zahtjevGodisnji></zahtjevGodisnji>'}
const DodavanjeTipaPregleda = {template: '<dodavanje-tipaPregleda></dodavanje-tipaPregleda>'}
const DodavanjeSifrarnika = {template: '<dodavanje-sifrarnika></dodavanje-sifrarnika>'}
const TipoviPregleda = {template: '<tipoviPregleda></tipoviPregleda>'}
const Cenovnik = { template: '<cenovnik></cenovnik>' }
const Registracija = {template: '<registracija></registracija>'}
const PotvrdaRegistracije = {template: '<potvrda-registracije></potvrda-registracije>'}
const ZahteviRegistracija = {template: '<zahtevi-registracija></zahtevi-registracija>'}
const ProveriEmail = {template: '<proveri-email></proveri-email>'}
const PromenaLozinke = {template: '<promena-lozinke></promena-lozinke>'}
const PretragaKlinika = {template : '<pretraga-klinika></pretraga-klinika>'}
const PretragaLekara = {template : '<pretraga-lekara></pretraga-lekara>'}
const PotvrdaZakazivanja = {template : '<potvrda-zakazivanja></potvrda-zakazivanja>'}
const Zaposleni = {template : '<zaposleni></zaposleni>'}
const DefinisanjeSlobodnogTermina = {template: '<definisanje-slobodnog-termina></definisanje-slobodnog-termina>'}
const ZakazaniPregledi = {template : '<zakazani-pregledi></zakazani-pregledi>'}
const NaCekanjuTermini = {template : '<na-cekanju-termini></na-cekanju-termini>'}
const NaCekanjuOperacije = {template : '<na-cekanju-operacije></na-cekanju-operacije>'}
const PotvrdaTerminaPregleda = {template: '<potvrda-termina-pregleda></potvrda-termina-pregleda>'}
const ProfilPacijenta= {template: '<profil-pacijenta></profil-pacijenta>'}
const router = new VueRouter({
	mode: 'hash',
		routes: [
	{
		path: '/',
		name: 'login',
		component: Login
	},
	{
		path: '/navigBar',
		name: 'navigBar',
		component: NavigBar
	},
	{
		path: '/izmenaProfila',
		name: 'izmenaProfila',
		component: IzmenaProfila
	},
	{
		path: '/izmenaProfilaKlinike',
		name: 'izmenaProfilaKlinike',
		component: IzmenaProfilaKlinike
	},
	{
		path: '/sale',
		name: 'sale',
		component: Sale
	},
	{
		path: '/lekari',
		name: 'lekari',
		component: Lekari
	},
	{
		path: '/medSestre',
		name: 'medSestre',
		component: MedSestre
	},
	{
		path: '/pacijetni',
		name: 'pacijenti',
		component: Pacijenti
	},
	{
		path: '/adminiKlinike',
		name: 'adminiKlinike',
		component: AdminiKlinike
	},
	{
		path: '/adminiCentra',
		name: 'adminiCentra',
		component: AdminiCentra
	},
	{
		path: '/klinikeAdmin',
		name: 'klinikeAdmin',
		component: KlinikeAdmin
	},
	{
		path: '/sifrarnikLekova',
		name: 'sifrarnikLekova',
		component: SifrarnikLekova
	},
	{
		path: '/sifrarnikDijagnoza',
		name: 'sifrarnikDijagnoza',
		component: SifrarnikDijagnoza
	},
	{
		path: '/zdravstveniKarton',
		name: 'zdravstveniKarton',
		component: ZdravstveniKarton
	},
	{
		path: '/dodavanjeSale',
		name: 'dodavanjeSale',
		component: DodavanjeSale
	},
	{
		path: '/dodavanjeKlinike',
		name: 'dodavanjeKlinike',
		component: DodavanjeKlinike
	},
	{
		path: '/dodavanjeAdminaKlinike',
		name: 'dodavanjeAdminaKlinike',
		component: DodavanjeAdminaKlinike
	},
	{
		path: '/dodavanjeAdminaCentra',
		name: 'dodavanjeAdminaCentra',
		component: DodavanjeAdminaCentra
	},
	{
		path: '/dodavanjeSifrarnika',
		name: 'dodavanjeSifrarnika',
		component: DodavanjeSifrarnika
	},
	{
		path: '/dodavanjeLekara',
		name: 'dodavanjeLekara',
		component: DodavanjeLekara
	},
	{
		path: '/dodavanjeMedSestara',
		name: 'dodavanjeMedSestara',
		component: DodavanjeMedSestara
	},
	{
		path: '/zahtjevi',
		name: 'zahtjevi',
		component: ZahtjeviZaGodisnjim 
	},
	{
		path: '/dodavanjeTipaPregleda',
		name: 'dodavanjeTipaPregleda',
		component: DodavanjeTipaPregleda
	},
	{
		path: '/dodavanjePopusta',
		name: 'dodavanjePopusta',
		component: DodavanjePopusta
	},
	{
		path: '/dodavanjeSifreLeka',
		name: 'dodavanjeSifreLeka',
		component: DodavanjeSifreLeka
	},
	{
		path: '/dodavanjeSifreDijagnoze',
		name: 'dodavanjeSifreDijagnoze',
		component: DodavanjeSifreDijagnoze
	},
	{
		path: '/cenovnik',
		name: 'cenovnik',
		component: Cenovnik
	},
	{
		path: '/registracija',
		name: 'registracija',
		component: Registracija
	},
	{
		path: '/potvrdaRegistracije',
		name: 'potvrdaRegistracije',
		component: PotvrdaRegistracije
	},
	{
		path: '/zahteviRegistracija',
		name: 'zahteviRegistracija',
		component: ZahteviRegistracija
	},
	{
		path: '/proveriEmail',
		name: 'proveriEmail',
		component: ProveriEmail
	},
	{
		path: '/promenaLozinke',
		name: 'promenaLozinke',
		component: PromenaLozinke
	},
	{
		path: '/pretragaKlinika',
		name: 'pretragaKlinika',
		component: PretragaKlinika
	},
	{
		path: '/pretragaLekara',
		name: 'pretragaLekara',
		component: PretragaLekara,
		props : true
	},
	{
		path: '/potvrdaZakazivanja',
		name: 'potvrdaZakazivanja',
		component: PotvrdaZakazivanja,
		props : true
	},
	{
		path: '/tipoviPregleda',
		name: 'tipoviPregleda',
		component:TipoviPregleda
	},
	{
		path: '/zaposleni',
		name: 'zaposleni',
		component:Zaposleni,
		props : true
	},
	{
		path: '/radniKalendar',
		name: 'radniKalendar',
		component: RadniKalendar
	},
	{
		path: '/zakazaniPregledi',
		name: 'zakazaniPregledi',
		component: ZakazaniPregledi
	},
	{
		path: '/naCekanjuTermini',
		name: 'naCekanjuTermini',
		component: NaCekanjuTermini
	},
	{
		path: '/naCekanjuOperacije',
		name: 'naCekanjuOperacije',
		component: NaCekanjuOperacije
	},
	{
		path: '/definisanjeSlobodnogTermina',
		name: 'definisanjeSlobodnogTermina',
		component: DefinisanjeSlobodnogTermina
	},
	{
		path: '/istorijaPregleda',
		name: 'istorijaPregleda',
		component: IstorijaPregleda
	},
	{
		path: '/potvrdaTerminaPregleda/:token',
		name: 'potvrdaTerminaPregleda',
		component: PotvrdaTerminaPregleda
	},
	{
		path: '/profilPacijenta',
		name: 'profilPacijenta',
		component: ProfilPacijenta
	},
	]
});

var app = new Vue({
	router,
	el: '#login',
	vuetify:new Vuetify(),
});