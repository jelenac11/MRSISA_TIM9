/**
 * 
 */
const HelloWorld = { template: '<hello-world></hello-world>' }
const Home = { template: '<home></home>' }
const Sale = { template: '<sale></sale>' }
const Lekari = { template: '<lekari></lekari>' }
const IzmenaProfila = { template: '<izmena-profila></izmena-profila>' }
const DodavanjeSale = { template: '<dodavanje-sale></dodavanje-sale>' }
const DodavanjeLekara = { template: '<dodavanje-lekara></dodavanje-lekara>' }
const ZahtjeviZaGodisnjim = {template: '<zahtjevGodisnji></zahtjevGodisnji>'}


const router = new VueRouter({
	mode: 'hash',
		routes: [
	{
		path: '/', 
		component: Home
	},
	{
		path: '/api/greetings', 
		component: HelloWorld 
	},
	{
	
		path: '/izmenaProfila',
		component: IzmenaProfila
	},
	{
		path: '/sale',
		component: Sale
	},
	{
		path: '/lekari',
		component: Lekari
	},
	{
		path: '/dodavanjeSale',
		component: DodavanjeSale
	},
	{
		path: '/dodavanjeLekara',
		component: DodavanjeLekara
	},
	{
		path: '/api/zahtjevi', 
		component: ZahtjeviZaGodisnjim 
	}]
});

var app = new Vue({
	router,
	el: '#login'
});