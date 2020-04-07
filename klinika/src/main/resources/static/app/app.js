/**
 * 
 */
const HelloWorld = { template: '<hello-world></hello-world>' }
const Home = { template: '<home></home>' }
const IzmenaProfila = { template: '<izmena-profila></izmena-profila>' }

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
	    }]
});

var app = new Vue({
	router,
	el: '#login'
});