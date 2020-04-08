/**
 * 
 */
const HelloWorld = { template: '<hello-world></hello-world>' }
const Home = { template: '<home></home>' }
const ZahtjeviZaGodisnjim={template: '<zahtjevGodisnji></zahtjevGodisnji>'}


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
	    	path: '/api/zahtjevi', 
	    	component: ZahtjeviZaGodisnjim 
	    }
	    ]
});

var app = new Vue({
	router,
	el: '#login'
});