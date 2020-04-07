/**
 * 
 */
const HelloWorld = { template: '<hello-world></hello-world>' }
const Home = { template: '<home></home>' }


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
	    }]
});

var app = new Vue({
	router,
	el: '#login'
});