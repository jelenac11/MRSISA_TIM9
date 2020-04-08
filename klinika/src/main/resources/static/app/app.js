/**
 * 
 */
const HelloWorld = { template: '<hello-world></hello-world>' }
const Home = { template: '<home></home>' }
const Sale = { template: '<sale></sale>' }
const IzmenaProfila = { template: '<izmena-profila></izmena-profila>' }
const DodavanjeSale = { template: '<dodavanje-sale></dodavanje-sale>' }
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
		path: '/dodavanjeSale',
		component: DodavanjeSale
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